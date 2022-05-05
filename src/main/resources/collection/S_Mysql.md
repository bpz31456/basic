## Mysql
* mariaDB可以理解为Mysql的社区版，mysql主要开发人员外出开发的mariaDB，与mysql兼容
### mysql 性能优化
> 从现有的软件和硬件配置中获得最佳的数据库性能
* 在数据库级别进行优化
  * 表格的结构是否合理，列的数据类型，列的多少
  * 是否有正常的索引来提高查询效率
  * 是否为每个表使用了合适的存储引擎，如事务型存储引擎（InnoDB）或非事务的存储引擎（MyISAM）
  * 每个表是否使用了适当的格式，此选择还取决于使用何种存储引擎，压缩表使用更少的磁盘空间，所以需要更少的IO，压缩表适用于InnoDB和只读的MyISAM
  * 应用程序是否使用了合适的锁定策略，应尽可能的共享访问，适当的时候独占请求
  * 合理使用缓存，缓存的内存区域是否合理，足以容纳经常访问的数据，又不会使物理内存过载
* 在硬件级别获得优化
  * 磁盘寻址
  * 磁盘读写
  * CPU周期
  * 内存带宽
* 平衡可移植性和性能
### SELECT 语句优化
> select 语句优化适用于Select声明和create table ..as select ..、insert into .. select..、where语句以及delete语句
* 让慢的select语句执行变快，首先要检查`索引`
* 尽量减少`全表扫描`次数
* 隔离的查询语句的每个部分
* 定期执行`ANALYZE TABEL`，使表的统计信息保持最新
* 了解特定的存储引擎`调优技术`、`索引技术`和`配置参数`
* 使用`InnoDB只读事务`来优化单表查询
* 复杂语句通过`explain执行计划`来查询`索引`、`where`子句、`连接子句`的内部细节
* 调整mysql的缓存大小和属性，有效使用InnoDB缓存池和Mysql查询缓存以及MyISAM键缓存
* 处理`锁问题`，查询可能被其他的会话锁定
#### where子句优化
* 删除不必要的括号
```mysql-sql
   ((a AND b) AND c OR (((a AND b) AND (c AND d))))
-> (a AND b AND c) OR (a AND b AND c AND d)
```
* 恒定折叠，有特定值，应该使用
```mysql-sql
   (a<b AND b=c) AND a=5
-> b>5 AND b=c AND a=5
```
* 恒定的条件去除
```mysql-sql
   (b>=5 AND b=5) OR (b=6 AND 5=5) OR (b=7 AND 5=6)
-> b=5 OR b=6
```
* 索引使用的常量表达式，只会计算一次
* MySQL 8.0.16 开始，对数值类型的列与常量值的比较进行检查并折叠或删除无效或超出范围的值
```mysql-sql
# CREATE TABLE t (c TINYINT UNSIGNED NOT NULL);
  SELECT * FROM t WHERE c ≪ 256;
-> SELECT * FROM t WHERE 1;
```
* count(*)作用到一个没有where的单表上，对性能没有影响
* having可以使用在不带group by或聚合函数（min、max）的where子句中
* 所有的常量表会优先其他表读取，常量表的表现形式
  * 一个空表或只有一行数据的表
  * where子句在一个`主键`(primary_kay)上或在一个`唯一键`(unique_key)上,多个表关联时，关联条件与一个常量表达式比较
```mysql-sql
SELECT * FROM t WHERE primary_key=1;
SELECT * FROM t1,t2
  WHERE t1.primary_key=1 AND t2.primary_key=t1.id;
```
* 尝试找到最佳的join组合，如果order by和group by来自同一个表，这个表示首选的joining表
* 如果order by和group by字段不同或者order by与grou by来自不同表，会产生一个临时表用来处理
* SQL_SMALL_RESULT，将会产生一个内存临时表
* 使用索引来查询结果，除非优化器认为索引效率比全表查询更有效，现在的优化器评估不再局限索引是否跨数据超过30%，也会结合行数、io等因素
* 如果使用的索引中都是数值类型，则结果会从索引树中查询
* 在输出每一行结果时，不匹配的having子句会被跳过
```mysql-sql
SELECT COUNT(*) FROM tbl_name;

SELECT MIN(key_part1),MAX(key_part1) FROM tbl_name;

SELECT MAX(key_part2) FROM tbl_name
  WHERE key_part1=constant;

SELECT ... FROM tbl_name
  ORDER BY key_part1,key_part2,... LIMIT 10;

SELECT ... FROM tbl_name
  ORDER BY key_part1 DESC, key_part2 DESC, ... LIMIT 10;
  

```
* 仅使用索引树解析以下查询案例
```mysql-sql
SELECT key_part1,key_part2 FROM tbl_name WHERE key_part1=num_var;

SELECT COUNT(*) FROM tbl_name
  WHERE key_part1=num_var1 AND key_part2=num_var2;

SELECT MAX(key_part2) FROM tbl_name GROUP BY num_var;
```
* 使用索引排序
```mysql-sql
SELECT ... FROM tbl_name
  ORDER BY key_part1,key_part2,... ;

SELECT ... FROM tbl_name
  ORDER BY key_part1 DESC, key_part2 DESC, ... ;
```
#### 范围优化
##### 单范围表达式
* 范围表达式定义
  * BTREE and HASH indexes常量值的范围定义，=, <=>, IN(), IS NULL,  IS NOT NULL
  * BTREE indexes,常量值定义范围， >, <, >=, <=, BETWEEN, !=, <>,LIKE
  * 以上通过 OR 和 AND连接的表达式
* 常量的值定义
  * 字符常量
  * 来自const或system表
  * 不相关的结果集
  * 上述表达式组成的表达式
```mysql-sql
SELECT * FROM t1
  WHERE key_col > 1
  AND key_col < 10;

SELECT * FROM t1
  WHERE key_col = 1
  OR key_col IN (15,18,20);

SELECT * FROM t1
  WHERE key_col LIKE 'ab%'
  OR key_col BETWEEN 'bar' AND 'foo';
```
##### 多范围表达式



### Mysql语句优化
* 查询数据库版本
  * select version()\G
#### 性能优化处理
* 识别性能问题
  * 查询性能问题
    * show full processlist\G,查询所有正在运行的程序
      * 如果是有运行很慢的程序，可以吧sql提取出来重复执行查看是否确实还是很慢（select），对于update和delete就需要改造为select语句执行
      * 生成执行计划（Query Execution Plan）
        * explain select * from member where member_id = xx\G,执行计划并不会执行，只是做分析
* 在未识别性能的情况下不要贸然添加索引
  * 添加索引，alter是阻塞式的，会阻塞所以向表中更新（insert delete update）操作
* 查询表结构、查询表大小
```sql
show create table member\G,查询表结构
show table status like 'member'\G,查询表大小 
```
### explain 命令
* explain select * from member where id = 1\G
  * select_type: SIMPLE
  * table: member,//输出行的表
  * `partitions`: NULL,//5.7之前需要显示explain partitions才会显示
  * type: const,//联接类型
  * `possible_keys`: PRIMARY,//可选索引
  * `key`: PRIMARY,//使用索引类型
  * key_len: 4
  * ref: const,与索引比较的列
  * rows: 1
  * `filtered`: 100.00//以前版本会显示执行 explain extended才会显示
  * Extra: NULL,//附加信息
* show warnings\G，查询上一条语句的错误信息
* show create table member\G,查询表的创建语句
* show indexes from member，查询索引
  * Table: member
  * Non_unique: 0
  * Key_name: PRIMARY
  * Seq_in_index: 1
  * Column_name: id
  * Collation: A
  * Cardinality: 59
  * Sub_part: NULL
  * Packed: NULL
  * Null:
  * Index_type: BTREE
  * Comment:
  * Index_comment:
  * Visible: YES
  * Expression: NULL
* show table status like 'member'\G，查询数据库底层表大小及结构
  * Name: member
  * Engine: InnoDB
  * Version: 10
  * Row_format: Dynamic
  * Rows: 59//数据根据引擎类型而确定，如果是InnoDB可能与实际数据不一样
  * Avg_row_length: 277
  * Data_length: 16384
  * Max_data_length: 0
  * Index_length: 0
  * Data_free: 0
  * Auto_increment: 61
  * Create_time: 2022-04-25 10:48:59
  * Update_time: NULL
  * Check_time: NULL
  * Collation: utf8_bin
  * Checksum: NULL
  * Create_options: partitioned
  * Comment: 家族成员
* show [session|global] status like 'handler_read',默认选用session，查询mysql系统内部运行状态
  * show session status like 'handler_read%';
    
|Variable_name|Value| 说明    |
|---|:---|:------|
| Handler_read_first    | 20    ||
| Handler_read_key      | 20    | 使用的索引 |
| Handler_read_last     | 0     ||
| Handler_read_next     | 0     ||
| Handler_read_prev     | 0     ||
| Handler_read_rnd      | 0     ||
| Handler_read_rnd_next | 118 ||

* show [global|session] variables,查询mysql系统变量
  * show session variables  like 'tmp_table_size'
* INFORMATION_SCHEMA,查询系统信息，为show提供了别名
  * use information_schema,数据库，内部提供了很多视图或表，记录了系统的相关信息
#### 索引
* 优化数据访问，索引可以让优化器在执行sql之前不必检索表中所有行
* 表连接，高效快捷的在相关的表上做连接
* 结果排序，索引有助于结果集排序
* 集合操作，如，在日期和账单账户上添加合适的索引就可以提高执行效率
* 索引存放在磁盘空间上
* 索引优势，降低数据库IO，降低数据库cpu消耗，劣势，占用磁盘空间，降低表的更新速率
#### 存储引擎，mysql提供插件式存储引擎引入
* 存储引擎关注点
  * 事务性和非事务性
  * 持久性和非持久性
  * 表和行锁
  * 不同索引算法，B-树，b+树，散列，R-树
  * 聚簇索引和非聚簇索引
  * 主索引和非主索引
  * 数据压缩
  * 全文搜索能力
* mysql 自带存储引擎
  * select * from information_schema.engines;
  * MyISAM
  * InnoDB(default)
  * Memory
* 索引算法
  * B-树
  * B+树（InnoDB主键使用）
  * 散列
  * R-树，MyISAM引擎使用
  * 全文本，MyISAM引擎使用
#### 单列索引
> 表上可以建立很多索引，没有要求和限制，甚至可以在一个字段上建立多个索引
* 单列索引分为，普通索引（add index），唯一索引（add unique index，允许为空），主键（唯一索引，不允许为空）
* 创建索引
  * alter table member add primary key <index_name> [column_name];//在member.id上创建主索引
  * alter table member add [unique] key <index-name> [column_name];//在member.id上创建索引或唯一索引
  * alter table artis add index(founded);在artis.founded上添加一个索引
  * `key` 和 `index`关键字可以相互替换
* explain 分析索引使用，type=ALL,or,key=NULL为无索引，type=ref，key=xx使用索引
  * explain select * from artist where founded=1942\G
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: ALL
    * possible_keys: NULL
    * key: NULL
    * key_len: NULL
    * ref: NULL
    * rows: 575149
    * filtered: 10.00
    * Extra: Using where
  * alter table artist add index(founded);//添加索引后
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: ref //关联操作
    * possible_keys: founded
    * key: founded
    * key_len: 2
    * ref: const
    * rows: 499
    * filtered: 100.00
    * Extra: NULL
* 使用索引连接表
  * explain select ar.name,ar.founded,al.name,al.first_released from artist ar inner join album al using(artist_id) where ar.name = 'Queen'\G
* 索引基数
  * 语句执行发现三个索引，选择具体的索引founded，依据就是索引基数
  * explain select * from artist where type = 'Band' and founded='1980'\G
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: ref
    * possible_keys: founded,founded_2,type
    * key: founded
    * key_len: 2
    * ref: const
    * rows: 1217
    * filtered: 50.00
    * Extra: Using where
  * show indexes from artist;
    * Table: artist
    * Key_name: founded
    * Column_name: founded
    * Cardinality: 104 //基数

    * Key_name: founded_2
    * Column_name: founded
    * Cardinality: 98  //基数
    
    * Key_name: type
    * Column_name: type
    * Cardinality: 3  //基数
  * 优秀选择性的索引，索引应该有更少的相同值，如，性别或者状态栏选择性就很差
  * 根据列信息的分布详细信息来使用不同的索引
    * explain select * from artist where founded between 1980 and 1990 and type = 'Band'\G
      * 语句中使用了founded和type两个属性作为条件
      * id: 1
      * select_type: SIMPLE
      * table: artist
      * partitions: NULL
      * type: range
      * possible_keys: founded,founded_2,type
      * key: `founded` //使用了founded来做索引
      * key_len: 2
      * ref: NULL
      * rows: 11003
      * filtered: 50.00
      * Extra: Using index condition; Using where
    * explain select * from artist where founded between 1980 and 1990 and type = 'Combination'\G
      * id: 1
      * select_type: SIMPLE
      * table: artist
      * partitions: NULL
      * type: ref
      * possible_keys: founded,founded_2,type
      * key: `type` //使用了type来做索引
      * key_len: 1
      * ref: const
      * rows: 29836
      * filtered: 1.91
      * Extra: Using where
* 根据索引来做模式匹配
  * explain select * from artist where name like 'Queen%'\G
    * 不以通配符开始，可以使用索引
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: `range`//
    * possible_keys: name
    * key: `name`
    * key_len: 257
    * ref: NULL
    * rows: 93
    * filtered: 100.00
    * Extra: Using index condition
  * explain select * from artist where name like '%Queen%'\G
    * 以通配符开始，就不会使用索引,如果经常会使用到这种通配符开头查询，如查询网址'%.com'，可以添加一列数据保存王者的倒置字符串如:'com.bpz.xxx'，就可以查询倒置字符串列使用通配符('com.%')
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: `ALL`
    * possible_keys: NULL
    * key: `NULL`
    * key_len: NULL
    * ref: NULL
    * rows: 575149
    * filtered: 11.11
    * Extra: Using where
  * explain select * from artist where upper(name) = upper('Bill jole')\G
    * 在索引自动上使用函数，无法利用到索引
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: ALL
    * possible_keys: NULL
    * key: NULL
    * key_len: NULL
    * ref: NULL
    * rows: 575149
    * filtered: 100.00
    * Extra: Using where
* 当数据是唯一的时候，添加唯一索引是很好的方式
  * alter table artist drop index name, add unique index(name);
  * 可以在未null值的列上定义unique index，此时null!=null,三态逻辑
* 结果排序
  * 索引可以用来对查询结果排序
  * explain select * from artist where name like 'AUSTRALIA%' order by founded\G
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: range
    * possible_keys: name
    * key: name
    * key_len: 257
    * ref: NULL
    * rows: 22
    * filtered: 100.00
    * Extra: Using index condition; Using `filesort`
#### 多列索引（组合索引）
> 索引可以创建在两列或多列上，多列索引被称为混合索引或链接索引
* 遵循最左前缀原则，（最左边匹配）
* 确定使用何种索引
  * 在表中创建索引alter table album add index(country_id),add index(album_type_id);
    * 合并DML（Data Manipulation Language,数据库操作语言）语句，可以使执行更加高效
    * 同时删除多个索引 alter table album drop index country_id_2,drop index country_id_3;
  * explain select * from album where country_id=22 and album_type_id=1\G
    * 根据`基数`来做出选择，也可能根据唯一值的`范围和容量`来统计信息 
    * id: 1
    * select_type: SIMPLE
    * table: album
    * partitions: NULL
    * type: ref
    * possible_keys: country_id,album_type_id
    * key: country_id
    * key_len: 3
    * ref: const
    * rows: 1
    * filtered: 50.00
    * Extra: Using where
* 多列索引语法
  * alter table table_name add primary key [index_name] (<col1>,<col2>,...)
  * alter table table_name add [unique] key [index_name] (<col1>,<col2>,...)
  * alter table album add index country_type_key (country_id,album_type_id);
    * 使用多列索引，explain select * from album where country_id=221 and album_type_id=4\G
    * id: 1
    * select_type: SIMPLE
    * table: album
    * partitions: NULL
    * type: ref
    * possible_keys: country_id,album_type_id,country_type_key
    * key: country_type_key
    * key_len: 7
    * ref: const,const
    * rows: 45342
    * filtered: 100.00
    * Extra: NULL
  * alter table album add index type_country_key (album_type_id,country_id);
    * 交换列位置，创建多列索引
  * 多列索引做左边的索引可以当做单行索引来使用，频繁使用group by或order by，最左边的列可以显著提升效率
  * 索引行宽应该尽可能的短，这样一个索引数据页面能够包含更多的索引记录。
  * 如果多列索引中没有列被使用，可以优化改索引，以减少索引宽度
  * explain中的key_len和ref两个属性的值来判断索引的列的利用率
    * alter table artist add index(type,gender,country_id);
    * explain select * from artist where type='Person' and gender='Male' and country_id=13\G
      * id: 1
      * select_type: SIMPLE
      * table: artist
      * partitions: NULL
      * type: ref
      * possible_keys: type,type_2
      * key: type_2
      * `key_len`: 6
      * `ref`: const,const,const
      * rows: 40
      * filtered: 100.00
      * Extra: NULL
      * 长度计算len=2+2+2
    * show create table artist\G
      * Table: artist
      * Create Table: CREATE TABLE `artist` (
      * `artist_id` int unsigned NOT NULL,
      * `type` enum('Band','Person','Unknown','Combination') NOT NULL,//枚举占用一个字节，为空占用一个字节，一个两个字节
      * `name` varchar(255) NOT NULL,
      * `gender` enum('Male','Female') DEFAULT NULL,//枚举占用一个字节，为空占用一个字节，一个两个字节
      * `founded` year DEFAULT NULL,
      * `country_id` smallint unsigned DEFAULT NULL,//smallint占用两个字节
      * PRIMARY KEY (`artist_id`),
      * UNIQUE KEY `name` (`name`),
      * KEY `founded` (`founded`),
      * KEY `founded_2` (`founded`),
      * KEY `type` (`type`),
      * KEY `type_2` (`type`,`gender`,`country_id`)
      * ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
* 主键应该尽可能选择较小的数据类型
  * 对于非主键索引，总有一个主键索引附在其后面

#### 查看索引
* show indexes from table_name;
#### 删除索引
* drop index index_name on table_name;
* alter table album drop index name,add index name_key (name(20));在删除的同时新建
#### 合并where 和order by
* 建立where条件和order by条件上的字段为多列索引
  * alter table album add index `name_released`(name,first_released);
    * explain select a.name,ar.name,a.first_released from album a  inner join artist ar using(artist_id)
       where a.name='Greatest Hits' order by a.first_released\G
    * id: 1
    * select_type: SIMPLE
    * table: a
    * partitions: NULL
    * type: ref
    * possible_keys: artist_id,name,name_released
    * key: `name_released`
    * key_len: 257
    * ref: const
    * rows: 660
    * filtered: 100.00
    * Extra: NULL
#### 索引合并,只能在`单表`上有效
> 一般mysql会在where ，order by， group by列中使用索引，在`单表`中只使用一个索引，在特殊情况下会使用索引合并
* 索引交集合并，对两个或多个很高基数的索引执行or操作取并集时（Using union(...)）
  * set @@session.optimizer_switch='index_merge_intersection=on';
  * explain select * from artist where name = 'Queen' or founded=1942\G
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: index_merge
    * possible_keys: name,founded,founded_2
    * key: `name`,`founded`
    * key_len: 257,2
    * ref: NULL
    * rows: 500
    * filtered: 100.00
    * Extra: Using `union`(name,founded); Using where
* 对两个少量唯一值索引取交集（Using intersect(...)）
  * explain select artist_id,name from artist where founded=1942\G
* 对两个索引取并集，但需要先排序（Using sort_union(...)）
  * explain select * from artist where name = 'Queen' or (founded between 1942 and 1950)\G
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: index_merge
    * possible_keys: name,founded,founded_2
    * key: `name`,`founded`
    * key_len: 257,2
    * ref: NULL
    * rows: 5901
    * filtered: 100.00
    * Extra: Using `sort_union(name,founded)`; Using where
* 三个索引合并实例
  * explain select * from artist where name = 'Queen' or (type='Band' or founded =1942)\G
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: index_merge
    * possible_keys: name,founded,founded_2,type,type_2
    * key: `name`,`type`,`founded`
    * key_len: 257,1,2
    * ref: NULL
    * rows: 288074
    * filtered: 100.00
    * Extra: Using `union(name,type,founded)`; Using where
* 多个单列索引和多个多列索引之间不存在谁更优势，视情况而定，建立多个单列索引更加占用内存空间，
#### 索引提示
* 用户干预优化器(优化器特殊情况下选择索引并不是最优)，对索引作出选择，有三种方式use index,ignore index,force index
  * explain select * from artist `use index(name)` where type='Person' and founded between 1942 and 1950
    and name like 'J%'\G
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: range
    * possible_keys: name
    * key: name
    * key_len: 257
    * ref: NULL
    * rows: 73916
    * filtered: 3.70
    * Extra: Using index condition; Using where
  * explain select * from artist ignore index(founded) where type='Person' and founded between 1942 and 1950 and name like 'J%'\G
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: range
    * possible_keys: name,founded_2,type,type_2
    * key: founded_2
    * key_len: 2
    * ref: NULL
    * rows: 5900
    * filtered: 6.43
    * Extra: Using index condition; Using where
  * explain select * from artist force index(type_2) where type='Person' and founded between 1942 and 1950 and name like 'J%'\G
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: ref
    * possible_keys: type_2
    * key: type_2
    * key_len: 1
    * ref: const
    * rows: 287574
    * filtered: 1.23
    * Extra: Using where
### 添加索引带来的影响
#### DML影响
* 插入、更新、删除，速率变低
* 重复索引，索引优化最简单的方式是删除重复索引，**需要通过工具来查询更加便捷**
  * show create table album\G
    * Create Table: CREATE TABLE `album` (
    * `album_id` int unsigned NOT NULL,
    * `artist_id` int unsigned NOT NULL,
    * `album_type_id` int unsigned NOT NULL,
    * `name` varchar(255) NOT NULL,
    * `first_released` year NOT NULL,
    * `country_id` smallint unsigned DEFAULT NULL,
    * PRIMARY KEY (`album_id`),
    * KEY `artist_id` (`artist_id`),
    * KEY `country_id` (`country_id`),
    * KEY `album_type_id` (`album_type_id`),
    * KEY `country_type_key` (`country_id`,`album_type_id`),
    * KEY `type_country_key` (`album_type_id`,`country_id`),
    * KEY `name` (`name`),
    * KEY `name_released` (`name`,`first_released`)
    * ) ENGINE=InnoDB DEFAULT CHARSET=latin1
  * country_type_key 索引 中 country_id 和country_id 索引中和country_id，属于重复索引，但不能被删除
* 查看索引是否被使用，**需要通过工具来查询**
  * `SHOW INDEX_STATISTICS`,目前只能在`mariaDB`上使用，Mysql没有相关功能
#### DDL影响
* 添加索引会越来越慢
  * 对磁盘空间占用越来越大，在创建索引时，mysql会为表拷贝一个副本，在副本上操作，再合并，并且副本不会被删除，而是在innoDB需要时再使用
* select * from information_schema.tables where table_name = 'artist'\G
  * 查看表空间（数据、索引）使用情况
  * 添加索引后，空间会增大，会对备份和恢复产生直接影响
### 创建更优秀的索引
> 创建覆盖索引、局部列索引
#### rowid
* rowid一直存在，
* 表中存在主键，并且是数值类型是，rowid就是这个主键，`可通过`select _rowid from t查询
* 表中没有主键，但是有union key，并且非空，并且是数值类型，rowid就是这个键,`可通过`select _rowid from t查询
* 表中没有上述两种情况，rowid会自建，隐藏，6字节，作为主键,最大2的48次方，用完就循环使用,`不能通过`select _rowid from t查询
#### 覆盖索引
* 不要使用`select *` ，这会无法准确使用覆盖索引
* 多列索引中包含了所有的在select,where,group by ,order by 中的列，称为覆盖索引,**适合在常用查询和大型查询中**，因为是针对专门的sql优化，所以有些索引字段会被称为无效索引
  * alter table artist add index founded_name_key(founded,name);
  * explain select artist_id,founded,name from artist where founded=1969\G
  * 使用了覆盖索引特性，其中artist_id是主键，包含在非主键索引的附加信息里面
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: ref
    * possible_keys: founded,founded_2,founded_name_key
    * key: `founded_name_key`
    * key_len: 2
    * ref: const
    * rows: 1036
    * filtered: 100.00
    * Extra: `Using index`
  * explain select artist_id,founded,name,type from artist where founded=1969\G
    * 与上一示例相比，多了`name`属性，不属于覆盖索引 
    * id: 1
    * select_type: SIMPLE
    * table: artist
    * partitions: NULL
    * type: ref
    * possible_keys: founded,founded_2,founded_name_key
    * key: `founded`
    * key_len: 2
    * ref: const
    * rows: 1036
    * filtered: 100.00
    * Extra: `NULL`
* 在MyISAM中使用覆盖索引，因为在MyISAM中的普通索引没有附带主键，所以在创建覆盖索引时，需要手动添加用到的索引（主键）
  * alter table artist add index founded_name_key(founded,name,artist_id);
#### 局部索引
* 在创建索引时，需要考虑物理资源，而不是仅仅考虑EQP（执行计划）
  * 主键占用使用聚合索引，不占用空间
* 局部索引实例
  1. 执行脚本source.sql查询空间占用情况
     * TABLE_NAME: artist
     * ENGINE: InnoDB
     * format: Dynamic
     * TABLE_ROWS: 575149
     * avg_row: 44
     * total_mb: 48.13
     * data_mb: 24.55
     * index_mb: 23.58
  2. 添加索引
     * alter table artist add index `(name(20))`;
     * 只在name字段的前20个长度上创建索引
* 局部索引在like模式匹配时很有用
  * explain select * from album use index(name_key) where name like 'Queen%'\G
    * id: 1
    * select_type: SIMPLE
    * table: album
    * partitions: NULL
    * type: range
    * possible_keys: name_key
    * key: name_key
    * `key_len: 22`
    * ref: NULL
    * rows: 99
    * filtered: 100.00
    * Extra: Using where
### SQL 生命周期
* 截取SQL语句
* 识别并分类有问题的SQL语句
* 确定SQL语句的当前操作
* 分析SQL信息和辅助信息
* 优化SQL语句
* 验证SQL优化结果
#### 截取SQL
##### 全面查询日志，通过mysql的配置文件来配置，/etc/my.cnf
* general_log=on
* general_log_path=/var/spool/mysql/full
* log_output=FILE
* 在配置general_log_path时，文件的权限需要`授权给mysql linux`用户
  * chown mysql:mysql /var/spool/mysql/full
* 查询日志
  * cat /var/spool/mysql/full
    * /usr/sbin/mysqld, Version: 8.0.25 (MySQL Community Server - GPL). started with:
    * Tcp port: 3306  Unix socket: /var/lib/mysql/mysql.sock
    * Time                 Id Command    Argument
    * 2022-04-28T02:20:13.891908Z         8 Connect   root@localhost on  using Socket
    * 2022-04-28T02:20:13.892170Z         8 Query     select @@version_comment limit 1
    * 2022-04-28T02:20:52.143951Z         8 Query     SELECT DATABASE()
    * 2022-04-28T02:20:52.144329Z         8 Init DB   study_mysql
    * 2022-04-28T02:20:52.145017Z         8 Query     show databases
    * 2022-04-28T02:20:52.147122Z         8 Query     show tables
    * 2022-04-28T02:20:52.148936Z         8 Field List        album
    * 2022-04-28T02:20:52.157484Z         8 Field List        album_type
    * 2022-04-28T02:20:52.158528Z         8 Field List        artist
    * 2022-04-28T02:20:52.159357Z         8 Field List        country
    * 2022-04-28T02:20:53.458679Z         8 Query     select * from artist where name like 'Queen%' limit 1
* 如果是在系统已经启动情况下可以通过在mysql程序里配置
  * set global general_log=on
  * set global general_log_file=/var/spool/mysql/full
  * set global log_output=FILE
  * 查看配置的变量
    * show variables like 'general_log%';
##### 慢查询日志配置,/etc/my.cnf
* slow_query_log=on
* slow_query_log_file=/var/spool/mysql/slow_log
* long_query_time=2,默认是秒
* log_output=FILE
* 同理，在mysql已经启动时，可以在内部配置
  * set global slow_query_log=on;
  * set global slow_query_log_file='/var/spool/mysql/slow_log';
    * 需要注意的是，配置的日志文件需要有相应的权限,通过chown mysql:mysql slow_log;
##### 配置二进制文件,my.cnf
> 二进制文件可以查询除了select语句之外的日志类型，DML，DDL，如update delete等
* 查询相关配置
  * show variables like '%binlog%'
* log_bin=on
* log_bin_basename=/var/lib/mysql/binlog
* 二进制文件被生成出来后，可以通过mysql提供的工具查看
  * mysqlbinlog binlog.xxx
* 使用场景
  * 主从复制
  * 数据恢复
##### 进程列表  
* show full processlist;
##### 引擎状态
* show engine innodb status;
### 其他性能优化
#### 索引管理优化
* 整合DDL语句
* alter table 会阻塞table，可以把多条alter语句整合为一条语句
  * alter table artist drop index name,add index name_key(name(20));
* 去除重复索引，`重复索引的查找需要使用第三方工具`比较方便[pt-duplicate-key-checker](https://www.percona.com/doc/percona-toolkit/LATEST/pt-duplicate-key-checker.html)
  * 常见是多列索引第一个字段上重复添加索引
  * show create table artist\G
    * Table: artist
    * Create Table: CREATE TABLE `artist` (
    * `artist_id` int unsigned NOT NULL,
    * `type` enum('Band','Person','Unknown','Combination') NOT NULL,
    * `name` varchar(255) NOT NULL,
    * `gender` enum('Male','Female') DEFAULT NULL,
    * `founded` year DEFAULT NULL,
    * `country_id` smallint unsigned DEFAULT NULL,
    * PRIMARY KEY (`artist_id`),
    * **KEY `name_key` (`name`),**
    * **KEY `name_founded` (`name`,`founded`)**
    * ) ENGINE=InnoDB DEFAULT CHARSET=latin1
* 删除不使用的索引
  * show index_statistics 在mariaDB中有，mysql中没有
  * mysql实践中是，QEP（执行计划）分析后遇到后再处理
* 监控无效索引
  * QEP分析key_len来找到没有使用列的索引
#### 索引列改进
* 数据类型
  * bigint 改为 int
  * datetime 改了 timestamp 
  * 使用enum类型
    * 限制数据完整性
    * 使用1个字节存储255个不同值
    * 可读性强
  * null 改为 not null
  * 避免隐含类型转换
* 列类型
  * ip地址，ipv4定义为int unsigned,占用4个字节，通过inet_aton() 和 inet_ntoa(),转换为字符串
  * ip地址，ipv6定义为binary(16)
  * md5,常见使用char(32)来存储，进而可以使用unhex(),hex()转换为16进制减少存储，单业务上需要讲求转化的效率，有些直接采用明文存储
#### 减少sql语句
* 删除重复的sql语句
  * 
* 避免重复执行的sql语句
  * 多条sql语句可以合并为一条的需要合并
* 删除不必要的sql语句
* 缓存sql结果，但是这个功能因为命中率的问题被摒弃了
* 应用程序中使用缓存，来减少与mysql的交互
#### 简化sql语句
* 改进列，不必要的列，不需要select出来
* 减少不必要的表连接
* 重写子查询，在子查询中能否改进为表连接查询
* 视图的谨慎使用
* 主从数据库读写分离
### explain 详解
* explain select * from artist\G
  * id: 1
  * select_type: SIMPLE
  * table: artist，可能是表名，临时表，子查询，派生表等
  * partitions: NULL，使用分区
  * type: ALL
  * possible_keys: NULL，优化器候选索引，太多的话，可能存在无效索引
  * key: NULL，选出使用的索引
  * key_len: NULL，sql语句连接条件的键的长度，只与where中连接条件以及join连接条件有关
  * ref: NULL
  * rows: 578048，优化器评估数据行数的值
  * filtered: 100.00，过滤值，最大100，随着过滤的数据越来越大，该值减少，rows*filtered%=与下表连接的行数
  * Extra: NULL
* select_type 类型
  * simple，不包含子查询和其他复杂查询的单表简单查询
  * primary，和union和derived查询是比较常见，外层表
  * derived，当一个表不是物理表，就叫derived，衍生表，如一个有一个子查询
  * DEPENDENT DERIVED
  * UNION，使用UNION
  * DEPENDENT UNION
  * UNION RESULT
  * SUBQUERY
  * DEPENDENT SUBQUERY
  * MATERIALIZED，实例视图，子查询结果集，	Materialized subquery
  * UNCACHEABLE SUBQUERY
  * UNCACHEABLE UNION
* Extra
[extra](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain-extra-information)
* Using where,全表扫描或使用index
* Using temporary,使用临时表
* Using filesort，order by，结果集使用
* Using indexing，使用索引
* Using join buffer，强调连接条件没有使用index，需要连接缓冲区来结果
* impossible where，where没有符合的条件
* select tables optimized away，从聚合函数得到数据，仅通过索引就得到结果
* distinct，匹配数据后就会返回
* index merges，索引合并
  * Using sort_union,索引并集排序
  * Using union，索引并集
  * Using intersect，索引交集
* type
  * const，这个表最多只有一行匹配
  * system，const的一个特例，当表中只有一行数据时会出现
  * eq_ref,这个值，有一行是为每个之前的确定的表读取的
  * ref，所有具有匹配的索引值都被使用
  * range，符合一定返回的值都被利用
  * ALL，全表扫描
### Mysql技术内核
#### mysql 体系结构
* mysql采用单进程多线程架构
* systemctl start mysqld.server
* ps -ef|grep mysqld
* mysql --help|grep my.cnf//配置文件顺序
  * /etc/my.cnf /etc/mysql/my.cnf /usr/etc/my.cnf ~/.my.cnf
* show variables like 'datadir'\G//数据文件位置
  * `/var/lib/mysql/`
  * ls -lh /var/lib/mysql/
* 插件式存储引擎，基于表
##### 存储引擎
* show engines\G查询引擎
* InnoDB
  * 支持事务、支持外键
  * 采用MVCC，多版本并发控制
  * 聚集索引组织主键，顺序存放，没有显示指定主键，每行会自动生成6字节ROWID，作为主键
  * 支持表锁、支持行锁
* MyiSIAM存储引擎
  * 不支持事务
  * 支持表锁
  * 支持全文搜索
  * 缓冲池只缓存索引文件
  * MYD（存放数据）和MYI（存放索引）组成
* Memory存储引擎
  * 数据存放在内存中
  * 不支持blob，text
  * 采用哈希索引
  * 存储临时数据的临时表
  * 只支持表级锁，并发性能差
  * 存储变长字段（varchar）按照定长字段（char）处理
  * mysql使用memory存储引擎来当做临时表，存储中间结果集
  * 中间结果集大于内存容量，或中间结果集中存在text和blob字段，会转存到MyISAM存储引擎中
* Archive 存储引擎
  * 只支持insert 和 select
  * 支持行锁，支持高并发插入，不是事务安全存储
  * 采用zlib算法压缩数据行，压缩比1：10
  * 适合用来存储日志信息
* ~~maria存储引擎~~，老版本存在，新版本已经独一为新的数据库，兼容mysql，新特性更强
##### 连接mysql
* tcp/ip
  * mysql -h 192.168.1.1 -u dev -P 3306 -p
  * 查询用户访问权限，select Host,User from mysql.user\G,Host字段是运行哪些网段可以访问当前数据库
* 命名管道连接
  * windows系统中，两个进程之间通信，需在mysql配置文件配置相应
* Unix套接字
  * show variables like 'socket'\G
    * /var/lib/mysql/mysql.sock
  * mysql -uroot -S /var/lib/mysql/mysql.sock -pJsk31456.
#### InnoDB
> 事务安全的Mysql存储引擎,第一个唯一支持ACID事务的Mysql存储引擎，支持行锁，支持MVCC，支持外键，提供一致性非锁定读，高效利用内存和CPU，
> 主要用来OLTP，（on-line transaction processing），联机事务处理，与之相对的有OLAP（ON-line，Analytical Processing）连接分析处理
##### 后台线程
* 多个后台线程
  * Master Thread，缓冲区中的数据异步刷新到磁盘
  * IO Thread,读线程和写线程都是可配置的，
    * show engine innodb status\G
      * I/O thread 0 state: waiting for completed aio requests (`insert buffer thread`)
      * I/O thread 1 state: waiting for completed aio requests (`log thread`)
      * I/O thread 2 state: waiting for completed aio requests (read thread)
      * I/O thread 3 state: waiting for completed aio requests (read thread)
      * I/O thread 4 state: waiting for completed aio requests (read thread)
      * I/O thread 5 state: waiting for completed aio requests (read thread)
      * I/O thread 6 state: waiting for completed aio requests (write thread)
      * I/O thread 7 state: waiting for completed aio requests (write thread)
      * I/O thread 8 state: waiting for completed aio requests (write thread)
      * I/O thread 9 state: waiting for completed aio requests (write thread)
    * show variables like 'innodb_%io_threads'\G
      * innodb_read_io_threads  | 4 
      * innodb_write_io_threads | 4 
* Purge Thead
> 回收已经使用并分配的undo页
  * show variables like 'innodb_purge_threads'\G


* page cleaner thread
  * 脏页刷新线程
##### 内存
* 缓冲池，做cpu和磁盘之间数据操作的缓存
  * show variables like 'innodb_buffer_pool_size';
* 缓冲池数据页类型
  * 数据页
  * 索引页
  * 重做日志缓存
  * 插入缓存
  * 自适应缓存
  * 锁信息
  * 数据字典信息
  * ...
* show variables like 'innodb_buffer_pool_instances'\G，缓冲池实例个数
* select * from information_schema.innodb_buffer_pool_stats\G，缓存池状态
* 缓存内存区域管理
  * LRU list，最近最少使用
  * Free list，
* 重做日志缓存
  * show variables like 'innodb_log_buffer_size'\G
* 额外的内存
  * 内存堆管理内存，缓存池内存不够时，会申请更多的内存
##### checkpoint技术
> 将缓冲池中的脏页刷回磁盘
* DML语句，update，insert改变了缓存池中页的记录，就会出现脏页（与磁盘中的数据不一致）
* 为避免数据丢失，采用Write Ahead Log技术，事务提交是先写redo log，再修改页，
* checkpoint技术，什么时候刷新脏页
  * 缩短数据库恢复时间
  * 缓冲池不够用时将脏页刷新到磁盘
  * 重做日志不可用时，刷新脏页
* checkpoint分类
  * sharp checkpoint，全部脏页刷新到磁盘
  * fuzzy checkpoint，部分脏页刷新到磁盘
    * Master thread checkpoint
    * Flush_LRU_List checkpoint
    * Async/sync flush checkpoint,重做日志不可用时，刷新脏页
    * Dirty Page too much checkpoint
#### innodb关键特征
##### 插入缓冲（Insert Buffer）
> 提高非union key的辅助索引的插入性能，能够合并多个插入操作为一个操作
* show engine innodb status\G
  * **INSERT BUFFER AND ADAPTIVE HASH INDEX**数据段
* change buffer
  * insert Buffer的升级，可以对insert Buffer、Purge Buffer、Delete Buffer
#### 锁
> 锁是区别文件系统的一个关键特征，锁是管理共享资源的并发访问
* InnoDB支持数据行级别上加锁，数据库内部也会有锁管理，如缓冲池LRU列表，删除添加移动LRU列表中元素
* innodb实现两个标准的`行级锁`
  * 共享锁（S Lock），允许事务读一行数据
  * 排他锁（X Lock），允许事务删除更新一行数据
* innodb允许行锁和表锁同时存在
* `意向锁`，表级别，在一个事务中揭示下一行将被请求的锁的类型，不会阻塞除全表扫描以外的任何请求
  * 意向共享锁(IS Lock),事务想要获取一个表中某几行的共享锁
  * 意向排他锁(IX Lock)，事务想要获取一个表中某几行的排它锁
* 通过查看
### mysql使用
#### mysql 表分区
* HASH 分区
  * ALTER TABLE `member`.`gift` PARTITION BY HASH(id) PARTITIONS 10 ;//更改表为分区表，按照主键id的hash值分为10个分区
#### 数据导入
1. set charset utf8//设置字符集很关键
2. source /home/bpz/mysql/data.sql
#### 查询数据表占用空间
1. 编辑脚本source.sql
```mysql-sql
set @schema = IFNULL(@schema,DATABASE());
select table_name,
	engine,
	row_format as format,
	table_rows,
	avg_row_length as avg_row,
	round((data_length+index_length)/1024/1024,2) as total_mb,
	round((data_length)/1024/1024,2) as data_mb,
	round((index_length)/1024/1024,2) as index_mb
from information_schema.tables
where table_schema=@schema
and table_name = @table
\G
```
2. 进入数据库schema,如：study_mysql
3. 设置变量table
   * set @table='artist'
4. 执行脚本
   * source /home/bpz/mysql/source.sql
#### 删除 清空表空间
* drop table table_name 立刻释放磁盘空间 ，不管是 Innodb和MyISAM 
* truncate table table_name 立刻释放磁盘空间 ，不管是 Innodb和MyISAM
* delete from table_name删除表的全部数据,对于MyISAM 会立刻释放磁盘空间 (应该是做了特别处理，也比较合理),InnoDB 不会释放磁盘空间;
* delete from table_name where xxx带条件的删除, 不管是innodb还是MyISAM都不会释放磁盘空间
* delete操作以后使用optimize table table_name 会立刻释放磁盘空间。不管是innodb还是myisam
  * 在innodb执行optimize table table_name 后，需要重启mysql服务才能生效（实现结果是这样的）
* delete from表以后虽然未释放磁盘空间，但是下次插入数据的时候，仍然可以使用这部分空间

### 资料来源
* 《Effective MySQL之SQL语句最优化.pdf》
* 《MySQL技术内幕 InnoDB存储引擎 第2版.pdf》