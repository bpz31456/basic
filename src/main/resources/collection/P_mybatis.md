## Mybatis
> MyBatis 是一流的持久性框架，支持自定义 SQL、存储过程和高级映射。MyBatis 消除了几乎所有的 JDBC 代码和手动设置参数和检索结果。
> MyBatis 可以使用简单的 XML 或 Annotations 进行配置和映射原语、映射接口和 Java POJO（普通旧 Java 对象）到数据库记录。
### 使用Mybatis
#### mybatis 基础使用
* 引入Mybatis-xx.jar
```xml
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.9</version>
</dependency>
```
* 使用xml构建SqlSessionFactory
  * 每个 MyBatis 应用程序都以 `SqlSessionFactory` 实例为中心
  * 可以使用 `SqlSessionFactoryBuilder` 获取 `SqlSessionFactory` 实例
* 配置文件xml文件，mybatis-config.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="./mybatis/ArticleMapper.xml"/>
    </mappers>
</configuration>
```
* java 代码加载mybatis的配置文件
  * `org.apache.ibatis.io.Resources`,加载配置文件
```java
String resource = "org/mybatis/example/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream); 
```
* 使用代码加载mybatis信息,如果需要高级特性还是需要配置xml文件
```java
DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
TransactionFactory transactionFactory = new JdbcTransactionFactory();
Environment environment = new Environment("development", transactionFactory, dataSource);
Configuration configuration = new Configuration(environment);
configuration.addMapper(BlogMapper.class);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
```
* 根据SqlSessionFactory获取SqlSession 
  * `org.apache.ibatis.session.SqlSession`
  * 
```java
//sqlSession 需要释放资源，通过try-resource自动释放
try(SqlSession sqlSession =  SqlSessionFactoryInstanceFactory.sqlSessionFactoryInstance().openSession(true)){
    //todo
}
```
* 配置与mapper.xml文件匹配的mapper 接口，在MVC结构中，属于dao层
```java
@Mapper
public interface ArticleMapper {

    Article selectArticle(Article article);

}
```
* 配置mapper.xml,映射文件
  * 命名空间是必需的，其目的不仅仅是用更长的、完全限定的名称来隔离语句，需要与上文中mapper接口一致
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.ms22.mapper.ArticleMapper">

    <select id="selectArticle" resultType="cn.ms22.entity.Article">
        select id, module, label, create_date createDate, modify_date modifyDate, content
        from article
        where module = #{module}
          and label = #{label}
    </select>
</mapper>
```

* 通过sqlSession 配合Mapper.xml文件得到Mapper，并使用，隶属于MVC中Server层
```java
    public Article selectOneByKey(String module, String label) {
        try (SqlSession sqlSession = getSqlSession()) {
            Article article = new Article();
            article.setModule(module);
            article.setLabel(label);
            Article tmpArticle = sqlSession.getMapper(ArticleMapper.class).selectArticle(article);
            logger.debug("获取资源:{}", tmpArticle);
            return tmpArticle;
        }
    }
```
#### 生命周期
* `org.apache.ibatis.session.SqlSessionFactoryBuilder`,SqlSessionFactoryBuilder 实例的最佳范围是方法范围（即本地方法变量）
* `org.apache.ibatis.session.SqlSessionFactory`,SqlSessionFactory，是应用程序期间一致续存
* `org.apache.ibatis.session.SqlSession`,SqlSession，线程私有，使用后就需要关闭
#### 疑问
* 如何在系统中使用多个数据库
  * 在系统中存续多个对应不同数据库的SqlSessionFactory实例，调用哪个数据库，就使用哪个SqlSessionFactory
### 配置文件详解
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <typeAliases>
    <typeAlias alias="Article" type="cn.ms22.entity.Article"/>
  </typeAliases>
  <settings>
    <setting name="cacheEnabled" value="true"/>
    <setting name="lazyLoadingEnabled" value="true"/>
    <setting name="multipleResultSetsEnabled" value="true"/>
    <setting name="useColumnLabel" value="true"/>
    <setting name="useGeneratedKeys" value="false"/>
    <setting name="autoMappingBehavior" value="PARTIAL"/>
    <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
    <setting name="defaultExecutorType" value="SIMPLE"/>
    <setting name="defaultStatementTimeout" value="25"/>
    <setting name="defaultFetchSize" value="100"/>
    <setting name="safeRowBoundsEnabled" value="false"/>
    <setting name="mapUnderscoreToCamelCase" value="false"/>
    <setting name="localCacheScope" value="SESSION"/>
    <setting name="jdbcTypeForNull" value="OTHER"/>
    <setting name="lazyLoadTriggerMethods"
             value="equals,clone,hashCode,toString"/>
  </settings>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://42.193.45.233:3306/collection"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="Developer123."/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="./mybatis/ArticleMapper.xml"/>
        <mapper resource="./mybatis/MenuMapper.xml"/>
        <mapper resource="./mybatis/ModuleMapper.xml"/>
        <mapper resource="./mybatis/PatchMapper.xml"/>
        <mapper resource="./mybatis/SubSystemMapper.xml"/>
    </mappers>
    <properties resource=".config.properties">
      <!-- ... -->
      <property name="org.apache.ibatis.parsing.PropertyParser.enable-default-value" value="true"/> <!-- Enable this feature -->
    </properties>
</configuration>
```
* MyBatis 3.4.2 开始，可以在xml配置文件中使用占位符${jdbc.username}...
  * 需要在配置文件中打开配置`org.apache.ibatis.parsing.PropertyParser.enable-default-value`为`true`
#### 配置属性
```xml
<settings>
    <!--全局启用或禁用此配置下任何映射器中配置的任何缓存，默认是true-->
    <setting name="cacheEnabled" value="true"/>
    <!--全局启用或禁用延迟加载，默认是false-->
    <setting name="lazyLoadingEnabled" value="true"/>
    <!--允许或不允许从单个语句返回多个 ResultSet（需要兼容的驱动程序），默认是true-->
    <setting name="multipleResultSetsEnabled" value="true"/>
    <!--使用列标签而不是列名，默认是true-->
    <setting name="useColumnLabel" value="true"/>
    <setting name="useGeneratedKeys" value="false"/>
    <setting name="autoMappingBehavior" value="PARTIAL"/>
    <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
    <setting name="defaultExecutorType" value="SIMPLE"/>
    <setting name="defaultStatementTimeout" value="25"/>
    <setting name="defaultFetchSize" value="100"/>
    <setting name="safeRowBoundsEnabled" value="false"/>
    <setting name="mapUnderscoreToCamelCase" value="false"/>
    <setting name="localCacheScope" value="SESSION"/>
    <setting name="jdbcTypeForNull" value="OTHER"/>
    <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```
#### 别名
```xml
    <typeAliases>
        <typeAlias alias="Article" type="cn.ms22.entity.Article"/>
    </typeAliases>
```
* 内置别名，可以在mapper的xml中使用，基础数据类型，列表，键值对，迭代器，容器

| 内置类型| java类型 |   类型名称    |
|----------|:----------:|:---------:|
|_byte |byte|    字节     |
|_long |long|    长整形    |
|_short |short|    短整形    |
|_int |int|    整形     |
|_integer |int|    整形     |
|_double |double|  双精度浮点数   |
|_float |float|  单进度浮点数   |
|_boolean |boolean|   布尔类型    |
|string |String|    字符串    |
|byte |Byte|    字节     |
|long |Long|    长整形    |
|short |Short|    短整形    |
|int |Integer|    整形     |
|integer |Integer|    整形     |
|double |Double|  双精度浮点数   |
|float |Float|  单进度浮点数   |
|boolean |Boolean|   布尔类型    |
|date |Date|    日期     |
|decimal |BigDecimal|   十进制类型   |
|bigdecimal |BigDecimal|   十进制类型   |
|object |Object|    对象     |
|map |Map|    映射     |
|hashmap |HashMap| hash键值对映射 |
|list |List|    列表     |
|arraylist |ArrayList|   数组列表    |
|collection |Collection|    集合     |
|iterator |Iterator|    迭代器    |
#### 类型处理程序
> MyBatis 在 PreparedStatement 上设置参数或从 ResultSet 检索值时，TypeHandler 用于以适合 Java 类型处理
* 自带类型处理，基础数据类型、字节输入流、字符输入流、字节数组、日期、时间

| 类型处理程序                     | java类型 | JDBC 类型 |
|----------------------------|:----------:|:----------:|
| BooleanTypeHandler         |java.lang.Boolean,boolean |任何兼容的BOOLEAN|
 | ByteTypeHandler            |java.lang.Byte,byte |任何兼容NUMERIC或BYTE|
 | ShortTypeHandler           |java.lang.Short,short |任何兼容NUMERIC或SMALLINT|
 | IntegerTypeHandler         |java.lang.Integer,int |任何兼容NUMERIC或INTEGER|
 | LongTypeHandler            |java.lang.Long,long |任何兼容NUMERIC或BIGINT|
 | FloatTypeHandler           |java.lang.Float,float |任何兼容NUMERIC或FLOAT|
 | DoubleTypeHandler          |java.lang.Double,double |任何兼容NUMERIC或DOUBLE|
 | BigDecimalTypeHandler      |java.math.BigDecimal |任何兼容NUMERIC或DECIMAL|
 | StringTypeHandler          |java.lang.String |CHAR,VARCHAR|
 | ClobReaderTypeHandler      |java.io.Reader |-|
 | ClobTypeHandler            |java.lang.String |CLOB,LONGVARCHAR|
 | NStringTypeHandler         |java.lang.String |NVARCHAR,NCHAR|
 | NClobTypeHandler           |java.lang.String |NCLOB|
 | BlobInputStreamTypeHandler |java.io.InputStream |-|
 | ByteArrayTypeHandler       |byte[] |任何兼容的字节流类型|
 | BlobTypeHandler            |byte[] |BLOB,LONGVARBINARY|
 | DateTypeHandler            |java.util.Date |TIMESTAMP|
 | DateOnlyTypeHandler        |java.util.Date |DATE|
 | TimeOnlyTypeHandler        |java.util.Date |TIME|
 | SqlTimestampTypeHandler    |java.sql.Timestamp |TIMESTAMP|
 | SqlDateTypeHandler         |java.sql.Date |DATE|
 | SqlTimeTypeHandler         |java.sql.Time |TIME|
 | ObjectTypeHandler          |任何 |OTHER, 或未指定|
 | EnumTypeHandler            |枚举类型 |VARCHAR任何字符串兼容类型，因为代码被存储（不是索引）。|
 | EnumOrdinalTypeHandler     |枚举类型 |任何兼容的NUMERICor DOUBLE，作为存储位置（不是代码本身）。|
 | SqlxmlTypeHandler          |java.lang.String |SQLXML|
 | InstantTypeHandler         |java.time.Instant |TIMESTAMP|
 | LocalDateTimeTypeHandler   |java.time.LocalDateTime |TIMESTAMP|
 | LocalDateTypeHandler       |java.time.LocalDate |DATE|
 | LocalTimeTypeHandler       |java.time.LocalTime |TIME|
 | OffsetDateTimeTypeHandler  |java.time.OffsetDateTime |TIMESTAMP|
 | OffsetTimeTypeHandler      |java.time.OffsetTime |TIME|
 | ZonedDateTimeTypeHandler   |java.time.ZonedDateTime |TIMESTAMP|
 | YearTypeHandler            |java.time.Year |INTEGER|
 | MonthTypeHandler           |java.time.Month |INTEGER|
 | YearMonthTypeHandler       |java.time.YearMonth |VARCHAR或者LONGVARCHAR|
 | JapaneseDateTypeHandler    |java.time.chrono.JapaneseDate |DATE|
* 自定义类型处理器,实现`org.apache.ibatis.type.TypeHandler`接口
```java
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ExampleTypeHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i,
    String parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, parameter);
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName)
    throws SQLException {
    return rs.getString(columnName);
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex)
    throws SQLException {
    return rs.getString(columnIndex);
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex)
    throws SQLException {
    return cs.getString(columnIndex);
  }
}
```
* 配置进入mapper-config.xml
```xml
<!-- mybatis-config.xml -->
<typeHandlers>
  <typeHandler handler="org.mybatis.example.ExampleTypeHandler"/>
  <typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler" javaType="java.math.RoundingMode"/>
</typeHandlers>
```
* xml配置文件中的类型处理,通过自定义的数据配置到resultMap中
```xml
    <resultMap type="org.apache.ibatis.submitted.rounding.User" id="usermap2">
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="roundingMode" property="roundingMode" typeHandler="org.apache.ibatis.type.EnumTypeHandler"/>
    </resultMap>
```
* 对象工厂，每次 MyBatis 创建结果对象的新实例时，它都会使用 ObjectFactory 实例来执行此操作
  * 继承`org.apache.ibatis.reflection.factory.DefaultObjectFactory`类
  * 可以自定义对象工厂，在创建结果对象新实例时做业务处理
* mybatis-config.xml配置自定义的对象创建类
```xml
<objectFactory type="org.mybatis.example.ExampleObjectFactory">
  <property name="someProperty" value="100"/>
</objectFactory> 
```
#### 插件,在映射语句的执行过程中拦截调用，添加业务逻辑
> MyBatis 允许您在映射语句的执行过程中拦截对某些点的调用。
* Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
* ParameterHandler (getParameterObject, setParameters)
* ResultSetHandler (handleResultSets, handleOutputParameters)
* StatementHandler (prepare, parameterize, batch, update, query)

* `org.apache.ibatis.plugin.Interceptor`,实现拦截器做拦截处理
```java
// ExamplePlugin.java
@Intercepts({@Signature(
  type= Executor.class,
  method = "update",
  args = {MappedStatement.class,Object.class})})
public class ExamplePlugin implements Interceptor {
  private Properties properties = new Properties();

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    // implement pre-processing if needed
    Object returnObject = invocation.proceed();
    // implement post-processing if needed
    return returnObject;
  }

  @Override
  public void setProperties(Properties properties) {
    this.properties = properties;
  }
}
```
* mybatis-config.xml配置插件
```xml
<plugins>
  <plugin interceptor="org.mybatis.example.ExamplePlugin">
    <property name="someProperty" value="100"/>
  </plugin>
</plugins>
```
#### 环境配置，environments
> 多个数据库处理，可以配置多个环境
```xml
<environments default="development">
  <environment id="development">
    <transactionManager type="JDBC">
      <property name="..." value="..."/>
    </transactionManager>
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>
  <environment id="collection">
    <transactionManager type="JDBC">
      <property name="..." value="..."/>
    </transactionManager>
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>
</environments>
```
* The default Environment ID (e.g. default="development").
* The Environment ID for each environment defined (e.g. id="development").
* The TransactionManager configuration (e.g. type="[JDBC|MANAGED]")
  * JDBC事务管理，直接使用JDBC提交和回滚，依赖database检索的连接来管理事务范围
  * MANAGED，不做处理，不提交和回滚，让容器自己管理事务
* The DataSource configuration (e.g. type="POOLED")
* mybatis与spring一起使用，不需要配置TransactionManager，spring会接管事务管理
* 可以自定义事务管理
  * 实现`TransactionFactory`接口，org.apache.ibatis.transaction.TransactionFactory
  * 实现`Transaction `接口，org.apache.ibatis.transaction.Transaction
#### dataSource配置
* 内置数据源类型，`type="[UNPOOLED|POOLED|JNDI]"`
  * UNPOOLED，不使用数据库连接池，每次连接使用后都会关闭连接
    * driver,JDBC驱动程序
    * url，数据库实例的URL
    * username，用户名
    * password，密码
    * defaultTransactionIsolationLevel，默认的事务隔离级别
    * defaultNetworkTimeout，默认网络超时时间
    * driver.encoding=UTF8，在配置文件mybatis.properties中配置，将按照UTF-8字符集连接数据库
  * POOLED，使用数据库连接池来连接数据库，缓存与数据库的连接
    * poolMaximumActiveConnections，最大活跃连接
    * poolMaximumIdleConnections，最大空闲连接
    * poolMaximumCheckoutTime
    * poolTimeToWait，使池有机会打印日志状态并在连接异常长的情况下重新尝试获取连接
    * poolMaximumLocalBadConnectionTolerance
    * poolPingQuery，ping自检连接状态
    * poolPingEnabled，是否打开ping，默认是false
    * poolPingConnectionsNotUsedFor，这配置了 poolPingQuery 的使用频率
  * JNDI，与容器一起使用，这些容器可以集中或外部配置 DataSource 并将对它的引用放置在 JNDI 上下文中
    * initial_context
    * data_source
    * env.encoding=UTF8
* 自定义数据源
  1. 实现`DataSourceFactory`,org.apache.ibatis.transaction.TransactionFactory
  2. 或者继承`DataSourceFactory`的子类，如`UnpooledDataSourceFactory`,org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory
  3. 把自定义的数据源配置进入dataSource配置项中，如下
```xml
<dataSource type="org.myproject.C3P0DataSourceFactory">
  <property name="driver" value="org.postgresql.Driver"/>
  <property name="url" value="jdbc:postgresql:mydb"/>
  <property name="username" value="postgres"/>
  <property name="password" value="root"/>
</dataSource>
```
#### databaseIdProvider，提供在多种数据库类型中选择
* 在映射xml文件中，有个属性`databaseId`需要
```xml
<databaseIdProvider type="DB_VENDOR">
  <property name="SQL Server" value="sqlserver"/>
  <property name="DB2" value="db2"/>
  <property name="Oracle" value="oracle" />
</databaseIdProvider>
```
#### mappers配置，定义我们映射的 SQL 语句文件位置
* Using classpath relative resources
```xml 
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
```
* Using url fully qualified paths
```xml
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
```
* Using mapper interface classes
```xml
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
```
* Register all interfaces in a package as mappers
```xml
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```
### 映射文件配置详解
> MyBatis 的真正强大之处在于 Mapped Statements
* xml文件中第一类元素
  * cache – 给定命名空间的缓存配置。
  * cache-ref – 从另一个命名空间引用缓存配置。
  * resultMap – 描述如何从数据库结果集中加载对象的最复杂和最强大的元素。
  * ~~parameterMap~~ – 已弃用！映射参数的老式方法。内联参数是首选，将来可能会删除此元素。此处未记录。
  * sql – 可以被其他语句引用的可重用 SQL 块。
  * insert – 映射的 INSERT 语句。
  * update – 映射的 UPDATE 语句。
  * delete – 映射的 DELETE 语句。
  * select – 映射的 SELECT 语句。
#### select
> 查询数据库中的数据，使用的标签

| 属性| 描述                                                                                                                      |
|----------|:----------------------------------------------------------------------|
|id | 此命名空间中的唯一标识符，可用于引用此语句。                                                                                                  |
|parameterType | 将传递到此语句中的参数的完全限定类名或别名。这个属性是可选的，因为 MyBatis 可以从传递给语句的实际参数中计算出要使用的 TypeHandler。默认为unset。 |
|~~parameterMap~~ | 这是一种不推荐使用的方法来引用外部parameterMap. 使用内联参数映射和parameterType属性。|
|resultType | 将从该语句返回的预期类型的​​完全限定类名或别名。请注意，在集合的情况下，这应该是集合包含的类型，而不是集合本身的类型。使用resultTypeOR resultMap，而不是两者。                             |
|resultMap | 对外部的命名引用resultMap。结果映射是 MyBatis 最强大的特性，对它有很好的理解，可以解决很多困难的映射情况。使用resultMapOR resultType，而不是两者。  |
|flushCache | 将此设置为 true 将导致在调用此语句时刷新本地和二级缓存。默认值：false用于选择语句。|
|useCache | 将此设置为 true 将导致此语句的结果缓存在二级缓存中。默认值： true用于选择语句。|
|timeout | 这设置了驱动程序在抛出异常之前等待数据库从请求返回的秒数。默认为unset（取决于驱动程序）。|
|fetchSize | 这是一个驱动程序提示，它将尝试使驱动程序以大小等于此设置的行数的批次返回结果。默认为unset（取决于驱动程序）。|
|statementType | `STATEMENT`, `PREPARED` or `CALLABLE`。这会导致 MyBatis 使用`Statement`，`PreparedStatement` ，`CallableStatement` 。默认值：PREPARED。|
|resultSetType |FORWARD_ONLY,SCROLL_SENSITIVE,SCROLL_INSENSITIVE,DEFAULT。默认为unset（取决于驱动程序）。|
|databaseId | 如果配置了 databaseIdProvider，MyBatis 将加载所有没有databaseId 属性或databaseId匹配当前属性的语句。如果发现有和没有databaseId后者的相同语句，则将丢弃后者。             |
|resultOrdered | 这仅适用于嵌套结果选择语句：如果这是真的，则假定嵌套结果被包含或组合在一起，这样当返回新的主结果行时，将不再出现对先前结果行的引用。这允许填充嵌套结果对内存更加友好。默认值： false。                          |
|resultSets | 这仅适用于多个结果集。它列出了语句将返回的结果集，并为每个结果集命名。名称用逗号分隔。                                                                             |
#### insert, update and delete
```xml
<insert
  id="insertAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  keyProperty=""
  keyColumn=""
  useGeneratedKeys=""
  timeout="20">

<update
  id="updateAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  timeout="20">

<delete
  id="deleteAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  timeout="20">
```
| 属性| 描述 | 
|----------|:----------|
|id |此命名空间中的唯一标识符，可用于引用此语句。|
|parameterType |将传递到此语句中的参数的完全限定类名或别名。这个属性是可选的，因为 MyBatis 可以从传递给语句的实际参数中计算出要使用的 TypeHandler。默认为unset。|
|~~parameterMap~~ |这是一种不推荐使用的引用外部参数映射的方法。使用内联参数映射和 parameterType 属性。|
|flushCache |将此设置为 true 将导致在调用此语句时刷新二级缓存和本地缓存。默认值：true用于插入、更新和删除语句。|
|timeout |这设置了驱动程序在抛出异常之前等待数据库从请求返回的最大秒数。默认为unset（取决于驱动程序）。|
|statementType |STATEMENT, PREPARED or CALLABLE任何一个。这会导致 MyBatis 分别使用, 或。默认值：。 PREPARED|
|useGeneratedKeys |（仅插入和更新）这告诉 MyBatis 使用 JDBC `getGeneratedKeys`方法来检索由数据库内部生成的键（例如，像 MySQL 或 SQL Server 等 RDBMS 中的自动递增字段）。默认值：false。|
|keyProperty |（仅限插入和更新）标识一个属性，MyBatis 将在其中设置由`getGeneratedKeys`或`selectKey`插入语句的子元素返回的键值。默认值：unset。如果需要生成多个列，则可以是逗号分隔的属性名称列表。|
|keyColumn |（仅限插入和更新）使用生成的键设置表中列的名称。仅当键列不是表中的第一列时，某些数据库（如 PostgreSQL）才需要这样做。如果需要生成多个列，则可以是逗号分隔的列名列表。|
|databaseId |如果配置了 databaseIdProvider，MyBatis 将加载所有没有databaseId 属性或databaseId匹配当前属性的语句。如果发现有和没有databaseId后者的相同语句，则将丢弃后者|
* 多行插入，自带主键生成，需要自定义主键生成
```xml
<!--自带主键生成-->
<insert id="insertAuthor" useGeneratedKeys="true" keyProperty="id">
    insert into Author (username, password, email, bio) values
    <foreach item="item" collection="list" separator=",">
        (#{item.username}, #{item.password}, #{item.email}, #{item.bio})
    </foreach>
</insert>
<!--手动生成主键-->
<insert id="insertAuthor2">
<selectKey keyProperty="id" resultType="int" order="BEFORE">
    select CAST(RANDOM()*1000000 as INTEGER) a from SYSIBM.SYSDUMMY1
</selectKey>
insert into Author
(id, username, password, email,bio, favourite_section)
values
(#{id}, #{username}, #{password}, #{email}, #{bio}, #{favouriteSection,jdbcType=VARCHAR})
</insert>
```
    * selectKey
```xml
<selectKey
  keyProperty="id"
  resultType="int"
  order="BEFORE"
  statementType="PREPARED">
```
| 属性| 描述 | 
|----------|:----------|
|keyProperty |selectKey应设置语句结果的目标属性。如果需要生成多个列，则可以是逗号分隔的属性名称列表。|
|keyColumn |返回的结果集中与属性匹配的列名。如果需要生成多个列，则可以是逗号分隔的列名列表。|
|resultType |结果的类型。MyBatis 通常可以解决这个问题，但添加它以确保它并没有什么坏处。MyBatis 允许使用任何简单类型作为键，包括字符串。如果您期望生成多个列，则可以使用包含预期属性的 Object 或 Map。|
|order |这可以设置为BEFORE或AFTER。如果设置为BEFORE，那么它将首先选择键，设置keyProperty然后执行插入语句。如果设置为AFTER，它将运行插入语句，然后运行该selectKey语句——这在 Oracle 等数据库中很常见，这些数据库可能在插入语句中嵌入了序列调用。|
|statementType |同上，MyBatis supports `STATEMENT`, `PREPARED` and `CALLABLE` statement types that map to `Statement`, `PreparedStatement` and `CallableStatement` respectively.|

#### sql,sql代码片段，可以引入到其他的声明当中
```xml
<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>
```
```xml
<select id="selectUsers" resultType="map">
  select
    <include refid="userColumns"><property name="alias" value="t1"/></include>,
    <include refid="userColumns"><property name="alias" value="t2"/></include>
  from some_table t1
    cross join some_table t2
</select>
```
* 在sql片段中可以使用外部定义的属性和配置
```xml
<sql id="sometable">
    ${prefix}Table
</sql>

<sql id="someinclude">
from
<include refid="${include_target}"/>
</sql>

<select id="select" resultType="map">
select
field1, field2, field3
<include refid="someinclude">
    <property name="prefix" value="Some"/>
    <property name="include_target" value="sometable"/>
</include>
</select> 
```

#### Parameters,参数处理
> 在所有的声明中，参数的配置可以精确确定参数的具体处理形似
* 默认情况下，mybatis会自动的处理基础类型，pojo的参数映射，特殊情况需要指定具体的参数配置
```xml
<!--User的属性名与参数的名称一致-->
<insert id="insertUser" parameterType="User">
    insert into users (id, username, password)
    values (#{id}, #{username}, #{password})
</insert> 
```
* 配置详细参数
```text
//指定具体的类型对应关系
#{property,javaType=int,jdbcType=NUMERIC} 
//指定具体的类型对应关系以及类型转换处理器
#{age,javaType=int,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
//数值类型指定具体小数精确度
#{height,javaType=double,jdbcType=NUMERIC,numericScale=2}
//游标使用，在jdbcType=CURSOR时，必须要有一个resultMap去接受游标的返回值
#{department, mode=OUT, jdbcType=CURSOR, javaType=ResultSet, resultMap=departmentResultMap}
<!-- public void getPageByProcedure();
1、使用select标签定义调用存储过程
2、statementType="CALLABLE":表示要调用存储过程
3、{call procedure_name(params)}
-->
<select id="getPageByProcedure" statementType="CALLABLE" databaseId="oracle">
    {call hello_test(
        #{start,mode=IN,jdbcType=INTEGER},
        #{end,mode=IN,jdbcType=INTEGER},
        #{count,mode=OUT,jdbcType=INTEGER},
        #{emps,mode=OUT,jdbcType=CURSOR,javaType=ResultSet,resultMap=PageEmp}
    )}
</select>
<resultMap type="com.atguigu.mybatis.bean.Employee" id="PageEmp">
    <id column="EMPLOYEE_ID" property="id"/>
    <result column="LAST_NAME" property="email"/>
    <result column="EMAIL" property="email"/>
</resultMap>
```
* 空值处理，在有些时候，传递的参数为null时，需要指定具体的jdbcType，由处理器自动处理为null时候的问题
#### 字符串替换
* 默认情况下使用`#{}`语法将导致 MyBatis 生成PreparedStatement属性并根据PreparedStatement参数安全地设置值，会替换为?设置的值
* 也可以直接使用`${}`来做字符串替换
* #{}比${}更安全，可以防止sql注入

#### Result Maps，结果映射
##### 实例
> 主要处理列明不匹配问题
* 默认情况下更好的方案是直接把结果集映射到pojo领域中，列明不一致可以使用数据库字段别名处理`as`
```xml
<select id="selectUsers" resultType="User">
  select
    user_id             as "id",
    user_name           as "userName",
    hashed_password     as "hashedPassword"
  from some_table
  where id = #{id}
</select>
```
* 极端情况下需要使用resultMap
```xml
<resultMap id="userResultMap" type="User">
    <id property="id" column="user_id" />
    <result property="username" column="user_name"/>
    <result property="password" column="hashed_password"/>
</resultMap> 
```
* 处理复制的java对象
```xml
<select id="selectBlogDetails" resultMap="detailedBlogResultMap">
  select
       B.id as blog_id,
       B.title as blog_title,
       B.author_id as blog_author_id,
       A.id as author_id,
       A.username as author_username,
       A.password as author_password,
       A.email as author_email,
       A.bio as author_bio,
       A.favourite_section as author_favourite_section,
       P.id as post_id,
       P.blog_id as post_blog_id,
       P.author_id as post_author_id,
       P.created_on as post_created_on,
       P.section as post_section,
       P.subject as post_subject,
       P.draft as draft,
       P.body as post_body,
       C.id as comment_id,
       C.post_id as comment_post_id,
       C.name as comment_name,
       C.comment as comment_text,
       T.id as tag_id,
       T.name as tag_name
  from Blog B
       left outer join Author A on B.author_id = A.id
       left outer join Post P on B.id = P.blog_id
       left outer join Comment C on P.id = C.post_id
       left outer join Post_Tag PT on PT.post_id = P.id
       left outer join Tag T on PT.tag_id = T.id
  where B.id = #{id}
</select>

<resultMap id="detailedBlogResultMap" type="Blog">
<constructor>
    <idArg column="blog_id" javaType="int"/>
</constructor>
<result property="title" column="blog_title"/>
<association property="author" javaType="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
    <result property="favouriteSection" column="author_favourite_section"/>
</association>
<collection property="posts" ofType="Post">
    <id property="id" column="post_id"/>
    <result property="subject" column="post_subject"/>
    <association property="author" javaType="Author"/>
    <collection property="comments" ofType="Comment">
        <id property="id" column="comment_id"/>
    </collection>
    <collection property="tags" ofType="Tag" >
        <id property="id" column="tag_id"/>
    </collection>
    <discriminator javaType="int" column="draft">
        <case value="1" resultType="DraftPost"/>
    </discriminator>
</collection>
</resultMap>
```
* constructor- 用于在实例化时将结果注入类的构造函数
    * idArg- ID 参数；将结果标记为 ID 将有助于提高整体性能
    * arg- 注入构造函数的正常结果
* id– 识别结果；将结果标记为 ID 将有助于提高整体性能
* result– 注入字段或 JavaBean 属性的正常结果
* association– 关联；关联的其他类型
  * 嵌套结果映射——关联resultMap本身就是 s，或者可以引用一个
* collection– 集合类型
  * 嵌套结果映射——集合resultMap本身就是 s，或者可以引用一个
* discriminator– 使用结果值来确定resultMap使用 哪个
  * case– 案例是基于某个值的结果图 
    * 嵌套结果映射——一个案例本身也是一个结果映射，因此可以包含许多相同的元素，或者它可以引用外部结果映射。
* 最佳实践是一步一步构建结构集的复杂映射
* id & result，都是与结果集的属性相匹配的基础属性，但是id是对象的标识符，尤其是在结果集缓存方面使用会提高性能
##### constructor
> 通过bean的构造函数来注入属性，一些不可变属性需要这种方式处理
```xml
<constructor>
   <idArg column="id" javaType="int"/>
   <arg column="username" javaType="String"/>
   <arg column="age" javaType="_int"/>
</constructor>
```
| 属性| 描述 | 
|----------|:----------|
|column |数据库中的列名，或者是列的别名。一般情况下，这和传递给 resultSet.getString(columnName) 方法的参数一样。|
|javaType |一个 Java 类的完全限定名，或一个类型别名（关于内置的类型别名，可以参考上面的表格）。 如果你映射到一个 JavaBean，MyBatis 通常可以推断类型。然而，如果你映射到的是 HashMap，那么你应该明确地指定 javaType 来保证行为与期望的相一致。|
|jdbcType |JDBC 类型，所支持的 JDBC 类型参见这个表格之前的“支持的 JDBC 类型”。 只需要在可能执行插入、更新和删除的且允许空值的列上指定 JDBC 类型。这是 JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC 编程，你需要对可能存在空值的列指定这个类型。|
|typeHandler |我们在前面讨论过默认的类型处理器。使用这个属性，你可以覆盖默认的类型处理器。 这个属性值是一个类型处理器实现类的完全限定名，或者是类型别名。|
|select |用于加载复杂类型属性的映射语句的 ID，它会从 column 属性中指定的列检索数据，作为参数传递给此 select 语句。具体请参考关联元素。|
|resultMap |结果映射的 ID，可以将嵌套的结果集映射到一个合适的对象树中。 它可以作为使用额外 select 语句的替代方案。它可以将多表连接操作的结果映射成一个单一的 ResultSet。这样的 ResultSet 将会将包含重复或部分数据重复的结果集。为了将结果集正确地映射到嵌套的对象树中，MyBatis 允许你 “串联”结果映射，以便解决嵌套结果集的问题。想了解更多内容，请参考下面的关联元素。|
|name | 构造方法形参的名字。从 3.4.3 版本开始，通过指定具体的参数名，你可以以任意顺序写入 arg 元素。参看上面的解释。                                                                                                                                      |
##### association
> 关联（association）元素处理“有一个”类型的关系。如一个博客有一个用户
* 需要告诉 MyBatis 如何加载关联
  * 嵌套 Select 查询：通过执行另外一个 SQL 映射语句来加载期望的复杂类型。
  * 嵌套结果映射：使用嵌套的结果映射来处理连接结果的重复子集。

| 属性| 描述 | 
|----------|:----------|
|property |映射到列结果的字段或属性。如果用来匹配的 JavaBean 存在给定名字的属性，那么它将会被使用。否则 MyBatis 将会寻找给定名称的字段。 无论是哪一种情形，你都可以使用通常的点式分隔形式进行复杂属性导航。 比如，你可以这样映射一些简单的东西：“username”，或者映射到一些复杂的东西上：“address.street.number”。|
|javaType |一个 Java 类的完全限定名，或一个类型别名（关于内置的类型别名，可以参考上面的表格）。 如果你映射到一个 JavaBean，MyBatis 通常可以推断类型。然而，如果你映射到的是 HashMap，那么你应该明确地指定 javaType 来保证行为与期望的相一致。|
|jdbcType |JDBC 类型，所支持的 JDBC 类型参见这个表格之前的“支持的 JDBC 类型”。 只需要在可能执行插入、更新和删除的且允许空值的列上指定 JDBC 类型。这是 JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC 编程，你需要对可能存在空值的列指定这个类型。|
|typeHandler |我们在前面讨论过默认的类型处理器。使用这个属性，你可以覆盖默认的类型处理器。 这个属性值是一个类型处理器实现类的完全限定名，或者是类型别名。|
###### 关联的嵌套 Select 查询
> 在关联嵌套中，值可能来之一个具体的对象，需要通过其他select语句查询出来，在查询出结果后，再次关联查询
```xml
<resultMap id="blogResult" type="Blog">
  <association property="author" column="author_id" javaType="Author" select="selectAuthor"/>
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  SELECT * FROM BLOG WHERE ID = #{id}
</select>

<select id="selectAuthor" resultType="Author">
  SELECT * FROM AUTHOR WHERE ID = #{id}
</select>
```

| 属性| 描述 | 
|----------|:----------|
|column |数据库中的列名，或者是列的别名。一般情况下，这和传递给 resultSet.getString(columnName) 方法的参数一样。 注意：在使用复合主键的时候，你可以使用 column="{prop1=col1,prop2=col2}" 这样的语法来指定多个传递给嵌套 Select 查询语句的列名。这会使得 prop1 和 prop2 作为参数对象，被设置为对应嵌套 Select 语句的参数。|
|select |用于加载复杂类型属性的映射语句的 ID，它会从 column 属性指定的列中检索数据，作为参数传递给目标 select 语句。 具体请参考下面的例子。注意：在使用复合主键的时候，你可以使用 column="{prop1=col1,prop2=col2}" 这样的语法来指定多个传递给嵌套 Select 查询语句的列名。这会使得 prop1 和 prop2 作为参数对象，被设置为对应嵌套 Select 语句的参数。|
|fetchType |可选的。有效值为 lazy 和 eager。 指定属性后，将在映射中忽略全局配置参数 lazyLoadingEnabled，使用属性的值。|
* 只会导致N+1问题，对列表返回的每条记录，你执行一个 select 查询语句来为每条记录加载详细信息（就是“N”）。
###### 关联的嵌套结果映射
> 在关联查询中，结果集映射到一个定义的resultMap中，而不是通过select声明来处理，一次性查询出结构，在java中拼接避免N+1问题
```xml
<resultMap id="authorResult" type="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
</resultMap>
<resultMap id="blogResult" type="Blog">
    <id property="id" column="blog_id" />
    <result property="title" column="blog_title"/>
    <association property="author"
                 resultMap="authorResult" />
    <association property="coAuthor"
                 resultMap="authorResult"
                 columnPrefix="co_" />
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  select
    B.id            as blog_id,
    B.title         as blog_title,
    A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio,
    CA.id           as co_author_id,
    CA.username     as co_author_username,
    CA.password     as co_author_password,
    CA.email        as co_author_email,
    CA.bio          as co_author_bio
  from Blog B
  left outer join Author A on B.author_id = A.id
  left outer join Author CA on B.co_author_id = CA.id
  where B.id = #{id}
</select>

```
| 属性| 描述 | 
|----------|:----------|
|resultMap |结果映射的 ID，可以将此关联的嵌套结果集映射到一个合适的对象树中。 它可以作为使用额外 select 语句的替代方案。它可以将多表连接操作的结果映射成一个单一的 ResultSet。这样的 ResultSet 有部分数据是重复的。 为了将结果集正确地映射到嵌套的对象树中, MyBatis 允许你“串联”结果映射，以便解决嵌套结果集的问题。使用嵌套结果映射的一个例子在表格以后。|
|columnPrefix |当连接多个表时，你可能会不得不使用列别名来避免在 ResultSet 中产生重复的列名。指定 columnPrefix 列名前缀允许你将带有这些前缀的列映射到一个外部的结果映射中。 详细说明请参考后面的例子。|
|notNullColumn |默认情况下，在至少一个被映射到属性的列不为空时，子对象才会被创建。 你可以在这个属性上指定非空的列来改变默认行为，指定后，Mybatis 将只在这些列非空时才创建一个子对象。可以使用逗号分隔来指定多个列。默认值：未设置（unset）。|
|autoMapping |如果设置这个属性，MyBatis 将会为本结果映射开启或者关闭自动映射。 这个属性会覆盖全局的属性 autoMappingBehavior。注意，本属性对外部的结果映射无效，所以不能搭配 select 或 resultMap 元素使用。默认值：未设置（unset）。|

###### 返回多个结果集处理
> 在存储过程中可能返回多个结果集
```xml
<resultMap id="blogResult" type="Blog">
    <id property="id" column="id" />
    <result property="title" column="title"/>
    <association property="author" javaType="Author" resultSet="authors" column="author_id" foreignColumn="id">
        <id property="id" column="id"/>
        <result property="username" column="username"/>
        <result property="password" column="password"/>
        <result property="email" column="email"/>
        <result property="bio" column="bio"/>
    </association>
</resultMap>
<select id="selectBlog" resultSets="blogs,authors" resultMap="blogResult" statementType="CALLABLE">
  {call getBlogsAndAuthors(#{id,jdbcType=INTEGER,mode=IN})}
</select>

```

| 属性| 描述 | 
|----------|:----------|
|column |当使用多个结果集时，该属性指定结果集中用于与 foreignColumn 匹配的列（多个列名以逗号隔开），以识别关系中的父类型与子类型。|
|foreignColumn |指定外键对应的列名，指定的列将与父类型中 column 的给出的列进行匹配。|
|resultSet |指定用于加载复杂类型的结果集名字。|
##### collection
> 在bean中具有集合属性属性的结果集构造
```xml
<collection property="posts" ofType="domain.blog.Post">
  <id property="id" column="post_id"/>
  <result property="subject" column="post_subject"/>
  <result property="body" column="post_body"/>
</collection>
```
###### 集合的嵌套 Select 查询,
> N+1问题
```xml
<resultMap id="blogResult" type="Blog">
  <collection property="posts" javaType="ArrayList" column="id" ofType="Post" select="selectPostsForBlog"/>
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  SELECT * FROM BLOG WHERE ID = #{id}
</select>

<select id="selectPostsForBlog" resultType="Post">
  SELECT * FROM POST WHERE BLOG_ID = #{id}
</select>
```
###### 集合的嵌套结果映射
```xml
<resultMap id="blogResult" type="Blog">
    <id property="id" column="blog_id" />
    <result property="title" column="blog_title"/>
    <collection property="posts" ofType="Post">
        <id property="id" column="post_id"/>
        <result property="subject" column="post_subject"/>
        <result property="body" column="post_body"/>
    </collection>
</resultMap>
<select id="selectBlog" resultMap="blogResult">
  select
  B.id as blog_id,
  B.title as blog_title,
  B.author_id as blog_author_id,
  P.id as post_id,
  P.subject as post_subject,
  P.body as post_body,
  from Blog B
  left outer join Post P on B.id = P.blog_id
  where B.id = #{id}
</select>
```
###### 集合的多结果集（ResultSet）
```xml
<resultMap id="blogResult" type="Blog">
    <id property="id" column="id" />
    <result property="title" column="title"/>
    <collection property="posts" ofType="Post" resultSet="posts" column="id" foreignColumn="blog_id">
        <id property="id" column="id"/>
        <result property="subject" column="subject"/>
        <result property="body" column="body"/>
    </collection>
</resultMap>
<select id="selectBlog" resultSets="blogs,posts" resultMap="blogResult">
  {call getBlogsAndPosts(#{id,jdbcType=INTEGER,mode=IN})}
</select>
```
##### discriminator(鉴别器)
> 它很像 Java 语言中的 switch 语句。
* 鉴别器的定义需要指定 column 和 javaType 属性。
  * column 指定了 MyBatis 查询被比较值的地方。 
  * javaType 用来确保使用正确的相等测试（虽然很多情况下字符串的相等测试都可以工作)
```xml
<resultMap id="vehicleResult" type="Vehicle">
  <id property="id" column="id" />
  <result property="vin" column="vin"/>
  <result property="year" column="year"/>
  <result property="make" column="make"/>
  <result property="model" column="model"/>
  <result property="color" column="color"/>
  <discriminator javaType="int" column="vehicle_type">
    <case value="1" resultMap="carResult"/>
    <case value="2" resultMap="truckResult"/>
    <case value="3" resultMap="vanResult"/>
    <case value="4" resultMap="suvResult"/>
  </discriminator>
</resultMap>

<resultMap id="vehicleResult" type="Vehicle">
<id property="id" column="id" />
<result property="vin" column="vin"/>
<result property="year" column="year"/>
<result property="make" column="make"/>
<result property="model" column="model"/>
<result property="color" column="color"/>
<discriminator javaType="int" column="vehicle_type">
    <case value="1" resultType="carResult">
        <result property="doorCount" column="door_count" />
    </case>
    <case value="2" resultType="truckResult">
        <result property="boxSize" column="box_size" />
        <result property="extendedCab" column="extended_cab" />
    </case>
    <case value="3" resultType="vanResult">
        <result property="powerSlidingDoor" column="power_sliding_door" />
    </case>
    <case value="4" resultType="suvResult">
        <result property="allWheelDrive" column="all_wheel_drive" />
    </case>
</discriminator>
</resultMap>
```

#### Auto-mapping
> 混合使用这自动映射和手动构建映射
* 在结果集映射中配置是否启用自动映射，
* 在mybatis.config中配置`mapUnderscoreToCamelCase`=true,驼峰标识和数据库中表、字段自动映射
* 
```xml
<resultMap id="userResultMap" type="User" autoMapping="false">
  <result property="password" column="hashed_password"/>
</resultMap>
```
#### cache（缓存）
> 对结果集缓存
```xml
<!--开启全局缓存-->
<cache/>
<!--或-->
<cache
eviction="FIFO"
flushInterval="60000"
size="512"
readOnly="true"/>
```
* select 结果集被缓存
* insert update delete 会刷新缓存
* 最近最少使用（LRU，Least Recently Used）清除缓存
* 不会自动刷新缓存
* 1024个缓存引用
* 清除缓存策略（LRU、FIFO、SOFT、WEAK）
  * LRU，最近最少使用
  * FIFO,先进先出
  * SOFT，弱引用，和GC有关
  * WEAK，虚引用，和GC有关
* 自定义缓存，实现接口Cache,`org.apache.ibatis.cache.Cache`
* 配置自定义缓存
```xml
<cache type="com.domain.something.MyCustomCache">
    <property name="cacheFile" value="/tmp/my-custom-cache.tmp"/>
</cache> 
```
### 动态SQL
> 由mybatis动态拼接SQL
* if
* choose (when, otherwise)
* trim (where, set)
* foreach
#### if
```xml
<select id="findActiveBlogLike"
        resultType="Blog">
    SELECT * FROM BLOG WHERE state = ‘ACTIVE’
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
</select>
```
#### choose when otherwise
```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
  </choose>
</select>
```
#### trim where set
> 消除在动态拼接sql时的语法问题
* 使用where来消除 where中的语法问题
```sql
SELECT * FROM BLOG
WHERE
AND title like ‘someTitle’
//或则
SELECT * FROM BLOG
WHERE
```
```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG
  <where>
    <if test="state != null">
         state = #{state}
    </if>
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>
```
* trim
  * 和where类似
  * 和set类似
```xml
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ...
</trim>
```
```xml
<trim prefix="SET" suffixOverrides=",">
  ...
</trim>
```
* set，set 元素可以用于动态包含需要更新的列，忽略其它不更新的列
```xml
<update id="updateAuthorIfNecessary">
    update Author
    <set>
        <if test="username != null">username=#{username},</if>
        <if test="password != null">password=#{password},</if>
        <if test="email != null">email=#{email},</if>
        <if test="bio != null">bio=#{bio}</if>
    </set>
    where id=#{id}
</update> 
```
#### foreach
> 处理参数集合遍历
```xml
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  <where>
    <foreach item="item" index="index" collection="list"
        open="ID in (" separator="," close=")" nullable="true">
          #{item}
    </foreach>
  </where>
</select>
```
#### script，在mapper映射器接口上使用
```java
   @Update({"<script>",
      "update Author",
      "  <set>",
      "    <if test='username != null'>username=#{username},</if>",
      "    <if test='password != null'>password=#{password},</if>",
      "    <if test='email != null'>email=#{email},</if>",
      "    <if test='bio != null'>bio=#{bio}</if>",
      "  </set>",
      "where id=#{id}",
      "</script>"})
    void updateAuthorValues(Author author);
```
#### 多数据库支持
> 在配置了`databaseIdProvider`属性时，各个数据库语法可能不同，可以使用`_databaseId`来处理
```xml
<insert id="insert">
  <selectKey keyProperty="id" resultType="int" order="BEFORE">
    <if test="_databaseId == 'oracle'">
      select seq_users.nextval from dual
    </if>
    <if test="_databaseId == 'db2'">
      select nextval for seq_users from sysibm.sysdummy1"
    </if>
  </selectKey>
  insert into users values (#{id}, #{name})
</insert>
```
### mybatis API
> 当 Mybatis 与一些依赖注入框架（如 Spring 或者 Guice）搭配使用时，SqlSession 将被依赖注入框架创建并注入，
> 所以你不需要使用 SqlSessionFactoryBuilder 或者 SqlSessionFactory
#### SqlSessionFactoryBuilder
> 构造一个SqlSessionFactory构建器
```java
String resource = "org/mybatis/builder/mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
SqlSessionFactory factory = builder.build(inputStream);
```
#### SqlSessionFactory
* 事务处理：你希望在 session 作用域中使用事务作用域，还是使用自动提交（auto-commit）？（对很多数据库和/或 JDBC 驱动来说，等同于关闭事务支持）
* 数据库连接：你希望 MyBatis 帮你从已配置的数据源获取连接，还是使用自己提供的连接？
* 语句执行：你希望 MyBatis 复用 PreparedStatement 和/或批量更新语句（包括插入语句和删除语句）吗？
* `org.apache.ibatis.session.SqlSessionFactory`
```java
public interface SqlSessionFactory {

    SqlSession openSession();

    SqlSession openSession(boolean autoCommit);

    SqlSession openSession(Connection connection);

    SqlSession openSession(TransactionIsolationLevel level);

    SqlSession openSession(ExecutorType execType);

    SqlSession openSession(ExecutorType execType, boolean autoCommit);

    SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);

    SqlSession openSession(ExecutorType execType, Connection connection);

    Configuration getConfiguration();

}
```
* autoCommit,是否自动提交
* TransactionIsolationLevel,  
  * `NONE`(Connection.TRANSACTION_NONE),
  * `READ_COMMITTED`(Connection.TRANSACTION_READ_COMMITTED),
  * `READ_UNCOMMITTED`(Connection.TRANSACTION_READ_UNCOMMITTED),
  * `REPEATABLE_READ`(Connection.TRANSACTION_REPEATABLE_READ),
  * `SERIALIZABLE`(Connection.TRANSACTION_SERIALIZABLE),
* ExecutorType, `SIMPLE`, `REUSE`, `BATCH`
#### SqlSession
* 语句执行方法
* 立即批量更新方法
  * ExecutorType.BATCH时，调用flushStatements()立即执行批处理中缓存的提交
* 事务控制方法
  * commit()
  * commit(boolean force)
  * rollback()
  * rollback(boolean force)
* 确保 SqlSession 被关闭
  * close()
* 使用映射器
  * getMapper()
* 映射器注入，通过注解的方式直接在接口上注入sql语法
  * @Insert
  * @Update
  * @Delete
  * @Select
  * ...
### SQL语法构建器
> 在java语法中构建SQL语句
* new 一个 SQL对象就开始编写sql语句，`org.apache.ibatis.jdbc.SQL`

### 日志
> sql日志委托给其他日志框架处理，如果在classpath未找到相应的日志框架，就会仅用日志
* 默认发现日志框架顺序
  * SLF4J
  * Apache Commons Logging
  * Log4j 2
  * Log4j (deprecated since 3.5.9)
  * JDK logging
* 手动配置日志框架,mysql-config.xml中，配置`logImpl`
  * `logImpl`，的可选值为`SLF4J`、`LOG4J`、`LOG4J2`、`JDK_LOGGING`、`COMMONS_LOGGING`、`STDOUT_LOGGING`、`NO_LOGGING`
  * 或者,实现了`org.apache.ibatis.logging.Log`接口的类的完全限定名
```xml
<configuration>
  <settings>
    ...
    <setting name="logImpl" value="LOG4J"/>
    ...
  </settings>
</configuration>
```
### mybatis整体架构
#### 基础支持层
* 反射模块
* 类型转换模块
* 日志模块
* 资源加载模块
##### 解析器模块
> 解析xml，DOM（Document Object model）树，SAX（Simple API for xml），StAX（stream api for xml）
* DOM,基于树形结构解析xml方式，会将xml读入内存构建一颗DOM树，基于树的各个NODE节点操作
* SAX，基于事件模型解析xml，每解析一个节点就会处理一个回调函数，推模式
* SaAX，拉模式，
* Xpath，结合DOM树，在节点之间做节点查询处理
* XpathParser，封装Xpath、Document、EntityResolver
* 数据源模块
* 事务管理缓存
* Biding模块
#### 核心处理层
* 配置解析
* SQL解析与scripting模块
* SQL执行
* 插件
#### 接口层（SqlSession）
