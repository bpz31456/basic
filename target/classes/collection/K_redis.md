## redis
> 需要启动redis服务，通过客户端程序(redis-cli)连接redis服务
### redis简介
* redis可以做什么
  * 缓存，提高访问速度，降低后端数据库压力，提供过期时间设置，淘汰策略
  * 排行系统，根据有序集合和列表
  * 计数器，视频播放数，电商网站浏览数
  * 社交网络，赞、粉丝、共同好友、共同喜好、推送、下拉刷新
  * 消息队列，业务解耦，削峰填谷
* redis不能做什么
  * 大数据量处理
  * 冷数据处理
* redis配置
  * redis 通过文件配置
  * redis 可以通过config set xxx xx 来设置环境，并可以通过config rewrite 来实现配置持久化（写到配置文件当中）
### redis的命令结构
* /usr/local/bin/redis-benchmark  //redis基准测试工具，定制化压力测试
* /usr/local/bin/redis-check-rdb  //redis持久化rdb监测和修复工具
* /usr/local/bin/redis-sentinel  //启动sentinel
* /usr/local/bin/redis-check-aof  //redis持久化aof的监测和修复工具
* /usr/local/bin/redis-cli      //redis 命令行客户端
* /usr/local/bin/redis-server //启动redis
### 启动redis
* redis-server启动服务
  * redis-server --configkey1 configvalue --configkey2 configvalue2 
  * 如：./redis-server --port 7777 --replicaof 127.0.0.1 8888 //适用于测试或开发时适用,replicaof是当前为从服务器，指向其他主服务（127.0.0.1 8888）
  * 通过配置文件启动，适用于正式环境适用，redis-server /opt/redis/redis.conf(redisconfig)[http://www.redis.cn/topics/config.html]
* docker 命令启动
  * docker run -it --name redisserver redis:3.0.0//适用于测试环境
  * docker build  -t nginx:test_3 . //Dockerfile放置在“.”上下文中，Dockerfile内配置启动配置文件并通过CMD命令启动redis
    ```Dockerfile
      FROM redis
      COPY redis.conf /usr/local/etc/redis/redis.conf
      CMD [ "redis-server", "/usr/local/etc/redis/redis.conf" ]
    ```
### 连接服务器
* redis-cli -h 127.0.0.1 -p 6379 
* 在docker中连接redis
  * docker exec -it webserver /bin/bash 在已经启动redis container上交互式连接
  * docker run -it --name -d redisserver redis /bin/bash //交互式启动redis imge并连接,-d是后台运行
### 停止服务
* redis-cli shutdown save|nosave //关闭服务器是否保存持久化文件
### 全局命令
* keys * //查询所有key
  * keys .|[]|*
* dbsize //键总数
* exists key//检查键是否存在
* del key //删除键
* expire key secs//设置键过期时间
* ttl key//监测键过期时间，没有设置返回-1，没有键返回-2
* type key //键的数据结构或none
### 单线程和I/O多路复用技术
* 单线程模型会使命令放到一个等待队列中一次执行，不会出现多线问题带来的竞态问题
* 单线程为什么快，是因为在内存中处理速度快
* 执行为什么快是使用了epoll实现了I/O多路复用
* 单线程避免了多线程的上下文切换

### 数据类型
* 内部编码
  * 数据结构是一种变现形式，而这种变现形式内部的实现可以有多种
  * object encoding key
#### 字符串类型
> 字符串本身可以是多种类型(简单字符串、xml或json等类型复制字符串、数字、二进制文件（图片文件等），大小不能超出512mb)
* 赋值与取值
```text
set key value
setnx key value //当键不存在时才会设置，用于新建，分布式锁的实现方案
setxx key value //当键存在时才会设置，用于更新
setex key 
get key //如果没有key则会返回null/nil，不会报错
mset key1 value1 [kye2 value2 ..] //批量设置值，为了节省网络开销，每个命令被执行需要经过网络传输
mget key1 key2 kye3 key4 //批量获取值
```
* 自增和自减都是从0开始，没有key会创建一个key
* 自增长
```text
incr key //初始从0开始，之后从key值开始，加上1,并返回
incrBy key value //初始从0开始，之后从key值开始，加上一个value,并返回
```
* 自减
```text
decr key //初始从0开始，之后从key值开始，减去1,并返回
decrBy key value //初始从0开始，之后从key值开始，减去value的值，并返回
```
* 基于浮点数处理增减
```text
incrByFloat key value //初始从0开始，之后从key值开始，加上一个value,并返回
没有decrByFloat 的命令
```
* 字符串尾部追加值
```text
append key value//在value处追加值，如果key不存在，就回新建一个key
```
* 获取字符串长度
```text
strlen key //返回字符串长度
```
* getset value，设置新的值并返回原来的值
* 替换字符串中的值
```text
setrange key offeset value // 从offeset开始替换原有的值为value
```
* 得到子串
```text
getrange key start end 
```
#### 字符串的内部编码
int 8个字节的长整形
embstr 大于等于39字节的字符串
raw 大于39个字节的字符串
#### 字符串常用场景，
* 缓存
```text
//伪代码获取，
UserInfo getUserInf(String userId){
    UserInfo userInfo = null;
    StringUserInfo = jedis.get("user:info:"+userId);
    if(StringUserInfo !=null){
        userInfo = deserialze(stringUserInfo)//需要对用户的信息做反序列化，能否把用户信息序列化为json？还是二进制呢？
    }
    return userInfo;
}
//伪代码设置,需要序列化，并设置过期时间
void setUserInfo(){
    jedis.setex("user:info"+userId,3600,serialize(Userinfo))
}
```
* 计数，统计
    * 实际开发考虑情况，防作弊、 按照不同维度计数， 数据持久化到底层数据源等
```text
//统计视频播放次数
long getCount(){
    long count = jedis.incr("video:playCount:"+id);
    return count;
}
```
* 分布式集中保持session//todo 如何在spring系统中控制session呢？
    *
* 限制次数
    * 网站限制访问次数等
```text
phoneNum = "138xxxxxxxx";
key = "shortMsg:limit:" + phoneNum;
// SET key value EX 60 NX
isExists = redis.set(key,1,"EX 60","NX");
if(isExists != null || redis.incr(key) <=5){
// 通过
}else{
// 限速
}
```
* 位操作
```text
setbit
getbit
bitcount
bitop
```
* 命名方式
基础属性命名，对象类型:对象ID:对象属性、业务:对象id:属性
统计属性命名，对象类型（复数形式）:count值
#### 散列类型
> 用来存储对象是不错的选择
#### 散列类型内部编码
> ziplist(压缩列表)，当hash类型元素个数小于hash-max-ziplist-entries（默认512），
>同时所有的field值小于hash-max-ziplist-value(默认64)内部更加紧凑多个元素会连续存储，不然会使用hashtable，读写速度O(1)
>
* 赋值和取值
```text
hset key field value //设置值
hget key field //得到值
hmset key field value [field2 value2 ..] //同时设置多个值
hmget key filed [filed2 ..] //同时得到多个值
hgetall key //得到所有的信息
hscan key //获取所有信息，是渐进式遍历hash类型
```
* 判断属性值长度
hstrlen key field
* 判断字段是否存在
```text
hexists key filed //判断属性是否存在
```
* 如果存在就不赋值，如果不存在就赋值
```text
hsetnx key filed //
```
* hash中属性自增长增加数字
```text
hincrby key field increment
hincrbyfloat key filed increment
```
* 删除字段
```text
hdel key field [field1 field2 ..]
```
* 查询属性
```text
hkeys key //查询key下面所有的属性
```
* 查询值
```text
hvals key //查询key下面所有属性的值
```
* 查询属性个数
```text
hlen key //查询属性个数
```
#### 散列表使用场景
* 结构化数据，如用户信息
* hash类型是稀疏的，关系型数据库是结构性数据，关系型数据库关系查询数据很复杂，hash类型不支持
```text
UserInfo getUserInfo(long id){
    // 用户id作为key后缀
    userRedisKey = "user:info:" + id;
    // 使用hgetall获取所有用户信息映射关系
    userInfoMap = redis.hgetAll(userRedisKey);
    UserInfo userInfo;
    if (userInfoMap != null) {
        // 将映射关系转换为UserInfo
        userInfo = transferMapToUserInfo(userInfoMap);
    } else {
        // 从MySQL中获取用户信息
        userInfo = mysql.get(id);
        // 将userInfo变为映射关系使用hmset保存到Redis中
        redis.hmset(userRedisKey, transferUserInfoToMap(userInfo));
        // 添加过期时间
        redis.expire(userRedisKey, 3600);
    }
        return userInfo;
}
```
#### 三种缓存方式比较
* 原生字符串类型，每个属性一个键，过多的键会占用过多内存
* 序列化字符串，每次都需要序列化反序列化，耗时，
* hash类型，结构化数据存储很适合，控制好
#### 列表类型
> 列表最多存储2的32次方-1个元素，列表中的元素是有序的，按照下标0开始偏移，列表的值是可以重复的
> 列表可以充当栈和队列的使用
> 列表的下标从左到右，0~N-1，右边到左边-1~-N
* 向列表的两端添加数据
```text
lpush key value [value1 value2 ...]
rpush key value [value1 value2 ...]
```
* 向列表的两端获取数据，并移除
```text
lpop key
rpop key
lpop key count //从左边获取count个数，并移除
rpop key count // 从右端获取count个数，并移除
blpop key [key2...] timeout//阻塞弹出,同时监听多个key，一旦有一个key有值弹出，就会返回
brpop key [key2...] timeout//阻塞弹出同时监听多个key，一旦有一个key有值弹出，就会返回
//如果多个blpop监听同一个key，最先监听的blpop操作会最先获得值
```
* 获取list 长度
```text
llen key // 获取key存储的数据长度
```
* 获取列表片段
```text
//start 和stop 可为负数，表示从右边起开始，最右边的一个数为下标-1
// lrange key -2 -1 //截取左后两个数字
lrange key start stop//下标是从0开始，start是下标开始，stop是下标结束[左闭右闭]
lrange key 0 -1//获得所有的数据
```
* 删除列表中的数据
```text
lrem key count value 在key找到value的值并删除，count>0从左往右删除最多count个数，count=0全部删除，count<0从右往左删除最多|count|
```
* 保留指定位置的元素
```text
ltrim key start stop //只保留start到stop的数据，其他的删除
```
* 获取、设置指定元素下标的值
```text
lset key index value //会覆盖原index所在位置的值
lindex key index //得到 index 位置的值
```
* 向列表中指定位置前、后插入数据
```text
linsert key before/after pivot value//在指定key的列表中查询pivot，根据befor/after，在第一个pivot的相对位置插入一个值value
```
* 转移一个list的最右边的数据(llen-1)到新的list中
```text
rpoplpush source destination //
rpoplpush list1 list2 //从list1中拿出最右边的一个数据放到list2最左边
```
#### 列表内部编码
* ziplist，压缩列表,list-max-ziplist-entries配置 （默认512个） ， 同时列表中每个元素的值都小于list-max-ziplist-value配置时
（默认64字节） ， Redis会选用ziplist来作为列表的内部实现来减少内存的使用
* linkedlist，其他情况使用链表
* quicklist，//todo 3.2提供的类型
#### 列表的使用场景
* 消息队列，lpush+rbpop可以组成消息队列，lpush为消息生产者，rbpop是消息消费者
* 用户文章列表，
  * 文章类型采用hash存储，
    hmset acticle:1 title xx timestamp 1476536196 content xxxx 
    hmset acticle:k title yy timestamp 1476512536 content yyyy
  * 用户文章列表
    lpush user:1:acticles acticle:1 ... acticle:k
  * 分页获取用户1的文章列表
    ```text
        articles = lrange user:1:articles 0 9
        for article in {articles}
            hgetall {article}
    ```
  * 上面这种分多次获取可以是用pipeline一次执行多条命令获取相应的信息
* 列表的使用
    * lpush+lpop=Stack（ 栈）
    * lpush+rpop=Queue（ 队列）
    * lpsh+ltrim=Capped Collection（ 有限集合）
    * lpush+brpop=Message Queue（ 消息队列）
    
#### 集合类型处理
* 添加删除元素
```text
sadd key value1 [value2...] //添加元素value1 value2到集合key中
srem key value1[values...]//删除集合key中的元素value1 value2
```
* 获取元素个数
```text
scard key //获取key内的元素个数
```
* 获取集合中所有元素
```text
smembers key //得到key的所有元素
```
* 判断集合中是否存在元素
```text
sismember key value //判断结合是否存在value
```
* 随机获取元素的数据
```text
srandmember key count// 在key中随机获取count个元素数据
```
* 随机获取一个数据
```text
spop key [count] //在key中随机弹出一个或[count个]数据
```
* 集合间运算
```text
sdiff key1 [key2...] //差集
sinter key1 [kye2...] //交集
sunion key1 [key2...] //并集
//集合间的运算很耗时，所以，提供可存储的
sinterstore destination key1 [key2 ...]
sunionstore destination key1 [key2 ...]
sdiffstore destination key1 [key2 ...]
```
#### 集合内部编码
* intset（整数集合） ： 当集合中的元素都是整数并且元素个数小于set-maxintset-entries配置（默认512个）时使用，节省内存
* hashtable（哈希表） ： 当集合类型无法满足intset的条件
#### 集合的典型使用场景
* 标签tag
  * 一个用户可能对娱乐、 体育比较感兴趣，另一个用户可能对历史、 新闻比较感兴趣， 这些兴趣点就是标签。 
  有了这些数据就可以得到喜欢同一个标签的人， 以及用户的共同喜好的标签， 这些数据对于用户体验以及增强用户黏度比较重要。
  1. 给用户添加标签
    sadd user:1:tags tag1 tag2 tag5
  2. 给标签添加用户
    sadd tag1:users user:1 user:3
  3. 用户共同感兴趣的标签
    sinter user:1:tags user:2:tags
* 用户和标签的关系维护应该在一个事务内执行， 防止部分命令失败造成数据不一致， 
* 使用场景
  * sadd=Tagging（标签）
  * spop/srandmember=Random item（生成随机数， 比如抽奖）
  * sadd+sinter=Social Graph（社交需求）
#### 有序集合
> 不允许重复元素，可以由重复score
> 可以实现按照时间排序分页
* 向有序集合中添加元素
```text
zadd key score value [score2 value2 ..]//给元素设置分值，并添加到有序集合key中
```
* 查询元素个数
```text
zcard key //获取元素个数
```
* 得到元素value的score值
```text
zscore key value 
```
* 获取元素在某个索引范围内的值
```text
//zrange ideal:zset:1 0 -1 获取所有元素
zrange key start stop [withscores] //
zrevrange key start stop [withscores]
//zrangebyscore ideal:zset:1 0 40 withscore limit 1 2 ,获取分值为0-40的数据并按照从0【获得的元素列表基础上偏移】到偏移量1 获取两个数据
zrangebyscore key min max [withscores] [limit offset count] // limit offset 可以获取top数据
```
* 增加某个元素的分值
```text
zincrby key increment member 
```
* 获取指定分数内的元素个数,制定分数可以设置为unix时间戳
```text
zcount  key min max
```
* 删除一个或多个元素
```text
zrem key value
```
* 按照排名范围删除元素
```text
zremrangebyrank key start stop
```
* 按照分值删除元素
```text
zremrangebyscore key min max
```
* 获取元素排名（按照score）
```text
zrank key value //获取value 在key中按照score排名情况
zrevrank key value // 反向排名情况
```
* 有序集合间的操作
  * zinterstore destination numkeys key  \[key ...\] \[weights weight \[weight ...\]\] \[aggregate sum|min|max\]
  * zunionstore destination numkeys key \[key ...\] \[weights weight \[weight ...\]\] \[aggregate sum|min|max\]
#### 有序集合的内部实现
ziplist（压缩列表） ：当有序集合的元素个数小于zset-max-ziplistentries配置（默认128个） ， 
同时每个元素的值都小于zset-max-ziplist-value配置（默认64字节）时，Redis会用ziplist来作为有序集合的内部实现， ziplist可以有效减少内存的使用
skiplist（跳跃表）：当ziplist条件不满足时， 有序集合会使用skiplist作为内部实现， 因为此时ziplist的读写效率会下降。
#### 数据操作其他命令
* 判断key是否存在
```text
exists key1 [kye2] //返回的值为存在的个数
```
#### 键的管理
* 键重命名
  * rename key newkey
  * renamenx key newkey //防止newkey已经存在
* 随机返回一个键
  * randomkey
* 键过期//todo 
* 迁移键，
  * move key db，同一个redis实例内可以存在多个数据库，在同一个redis实例内多个数据库之间的键迁移使用（不建议在生产环境使用）
  * dump+restore 
    * dump key//源目标生产RDB格式
    * restore key ttl value //在目标redis上使用restore 复原
  * migrate 将dump、 restore、 del三个命令进行组合，原子性操作
* 键的遍历，渐进式遍历不会带来阻塞，但如果在遍历的时候，键发生改变，将会遍历不到或重复遍历的问题
  * scan
  * hscan
  * sscan
  * zscan
#### 数据库管理
* 切换数据库,redis 默认提供16个数据库，不建议使用这种多个数据库，而且之后的版本在弱化这个功能
  * select dbIndex 
* 清除数据库
  * flushdb(清空当前数据库)/flushall(情况所有数据库)，操作有风险
### 性能工具
#### 慢查询处理
* 配置slowlog-log-slower-than和slowlog-max-len来配置慢查询功能属性
* 使用 slowlog get \[n\] 来获取慢查询语句
* 获取慢查询列表当前长度，slowlog len
* 慢查询日志重置，slowlog reset
* 在线上处理应该将slowlog-max-len 建议设置大于1000，
* 线上处理默认值slowlog-log-slower-than为10，高性能要求应该设置为1毫秒
* 慢查询只记录执行时间，不包括网络传输和命令排队的时间，
* 线上慢查询日志应该定期存放到数据库中持久化（如，mysql），方便后期优化
#### redis-cli
* redis-cli -r 3 ping //-r 重复执行
* redis-cli -r 5 -i 1 ping //-i 间隔时间，秒为单位可以是小数，每间隔1秒执行ping执行5次
* redis-cli -r 100 -i 1 info|grep used_memory_human //每间隔1秒查询内存使用情况
* redis-cli info //查询redis运行情况
* echo "hello redis" | redis-cli -x set hello ,把标准输出的信息当做左后一个参数

### 事务处理
> redis事务的事务支持是很弱的，redis的事务更像是批处理
#### multi
1. 打开事务multi
2. 执行命令
3. 执行事务
```text
//打开事务，并执行1，2，3的添加，执行事务
multi
sadd key 1
sadd key 2
sadd key 3
exec
```
* 事务的异常处理
  * 语法错误，事务将全部回滚
  * 执行错误，如果1，2，添加执行错误，3执行正确，那么3是不会回回滚的
* WATCH，在事务开始之间监控一个或多个key，如果有信息key发生了改变，那么事务将不会执行
#### 设置过期时间
* expire key time(s)给key设置过期时间如果，到期就会被删除
* TTL，监测key的过期时间，如果是-1则表示为永久时间，如果是-2这表示不存在key
* persist key,取消过期时间设置
* set 命令会清除过期时间，其他对值做的操作不会影响过期时间
### redis 排序处理
> multi zinterstore zrange del exec
* sort命令
> 会将参与排序的值转化为浮点类型比较，如果不能转换会把报错
* by \*//todo 还没有搞清楚这么弄，感觉是通过外部（其他key的值）的某个属性排序？
* get //与by连用构成其他属性