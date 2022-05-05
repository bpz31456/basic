## spring 和 spring boot
### Spring 定义以及作用
> Spring是一个控制反转容器(IoC)，简化开发
* 基于pojo轻量级和最小侵入性
* 通过依赖注入和面向接口编程实现松耦合，依赖注入方便测试
* 通过切面和惯例进行声明式编程
* 通过切面（日志，安全，事务管理）和模板（JDBCTemplate）减少样式代码
### 应用上下文
* org.springframework.web.context.support.AnnotationConfigWebApplicationContext ,从一个或多个基于java的配置类中加载spring web上下文
* org.springframework.web.context.support.XmlWebApplicationContext，从web应用下的一个或多个xml文件加载上下文
* org.springframework.context.annotation.AnnotationConfigApplicationContext，从一个或多个基于java的配置类中加载spring的上下文
* org.springframework.context.support.ClassPathXmlApplicationContext，从类路径中一个或多个xml配置文件中加载上下文
* org.springframework.context.support.FileSystemXmlApplicationContext，从文件系统中一个或多个xml配置文件中加载上下文
### Spring模块
* Spring 核心，Bean的创建和配置，管理，Spring上下文，Spring DI，Email，JNDI，EJB等
* Spring AOP
* 数据访问与集成，数据库的连接创建使用关闭，模板处理，统一SQL异常处理，Spring提供ORM模块，是基于DAO的模块之上的，没有自己的ORM实现
* Web与远程调用，MVC框架（Model-View-Controller），Spring自己实现了一个MVC模块，比业界的（Struts2，JSF，WebWork）更方便。以及其他的RMI调度，Http invoker请求等
* 测试
* instrumentation，提供对JVM的代理功能
### Spring portfolio
> spring 对java的很多领域都提供了具体实现
* Spring web flow ，基于流程的会话式web应用提供了实现
* Spring web Service，发布web service的功能
* Spring Security. 通过Spring Aop切面和Spring Security实现的声明式安全机制 
* Spring Integration，与其他应用交互的
* Spring batch，大数据量处理时需要的框架
* Spring Data，数据库处理框架，为持久化提供了统一简单的抽象模型
* Spring Social，提供社交抽象模型
* Spring for Android
* Spring boot,简化Spring开发，简化大量Spring配置，约定由于配置的典范
* Spring mobile,支持移动web开发
### 装配Bean
> 创建对象之间的协作关系称为装配，这是依赖注入（DI）的本质
* Spring配置的方案
  * 在XML中进行显示配置
  * 在JAVA中显示配置（JAVA Config）
  * 隐式的Bean发现机制和自动装配
* Spring 自动化装配
  * 组件扫描（Component scan）,Spring 自动发现应用上下文中的创建的Bean
  * 自动装配（autowriting），Spring自动满足Bean之间的依赖
* @Component 注解标注当前类是组件类，需要被Spring实例化为Bean，可在参数列表中指定命名
* @ComponentScan ，在Spring中启动组件扫描，如果没有其他配置，默认扫描当前配置类的包以及子包，可在参数列表中指定对应的基础扫描包
  * xml配置方式<context:component-scan base-package="xxx"/>
* @ContextConfiguration,制定配置类所在位置
* @Autowired，是Spring特有的，可以用在构造器上，尝试满足方法参数上面所声明的依赖，可设置required=false，表明如果没有匹配不抛异常，可能会出现空指针异常的抛出的问题
* @Inject java中的依赖注入注解，在Spring中也被实现，桶Autowired有细微差别，同时java还提供@Named注解//TODO?
* 将第三方的类配置到当前应用中，需要通过显示配置的方式（java config或xml），无法通过@Component配置的方式自动配置处理
* @Configuration 指定该类为配置类，并且需要在该类内部实现一些配置Bean的细节
  * @Bean，配置到配置类的内部的方法上，表明该方法返回一个具体的Bean，并注册到Spring上下文中，默认bean的名称为方法名可以通过参数name指定名称如：
```text
@Bean(name="SgtPepper")
public CompactDisc getPeppers(){
  return new SgtPepper();
}
```
* 使用Xml来配置规范
  * @Configuration同spring xml配置文件一致（其中以beans为根目录）
* @Import，在java config 的配置类上标注@Import(xx)，将引入xx中的配置信息，也可以用一个统一的config类在头顶上引入所有的配置类
* @ImportResource，在javaConfiig的配置类上通过@importResource可以引入xml配置的beans
  * 如：@ImportResource("classpath:cd-config.xml")
* 在xml中可以通过<import> 来引入其他xml的配置文件，可以通过<bean>来引入其他的Java config配置类
### 装配特性
* 配置profile bean
  * @Profile("dev")，配置到javaconfig 的Configuration类上，标明为dev分支上，也可以放到带有@Bean的方法上，表明当前Bean处在dev反之上
  * 没有指定profile的Bean不敢哪个分支被激活，就会被创建并管理
  * xml中配置Profile，在<beans profile="dev">...</beans>
* 激活profile，有多种方式，实质是两个独立的属性，spring.profiles.active 和 spring.profiles.default
  * 作为DispatcherServlet的初始化参数传入
  * 作为Web应用的上下文参数
  * 作为JNDI的条目
  * 作为环境变量
  * 作为JVM的系统参数
  * 在集成测试环境上，使用@ActiveProfiles注解设置
  * 在spring boot的配置文件中指定
* 条件创建Bean
  * @Condition(实现了Condition接口的类)，注解配置到@Bean的方法上，如果返回true则创建这个Bean，这里的返回是否为true的是实现了`org.springframework.context.annotation.Condition`的match方法
  * org.springframework.context.annotation.Condition#matches(
  org.springframework.context.annotation.ConditionContext, //是一个接口，包内含一些条件上下文
  org.springframework.core.type.AnnotatedTypeMetadata) //是在@Bean修饰的方法上还有其他什么注解类型
  * @Profile是通过@Condition配置的matchs来实现的（是否为激活状态）
* 消除注入时的歧义性
  * @Primary，可以配合@Companent 或@Bean使用，指定当前类型为首选类型，如果遇到如法判别时，首选当前对象
  * @Qualifier,可以与@Autowrite 或 @Inject连用，指定具体的类型ID（或在其他地方定义的ID），当然需要和@Companent 或@Bean连用的时候进行设置自定义的ID
* Bean的作用域
  * Singleton，默认是单例模式
  * Prototype，每次都会创建新的对象
  * Session，在web的应用中，会话
    * 定义一个会话作用域的组件
    * 这里的proxyMode，很有讲究，当前时一个session作用域，当需要注入到server的实现（通常情况下是一个singleton）中时，
    其实质是注入了一个代理类型到server单例中，在具体的调用的时候，再根据具体的类型执行相应的代理类，如果代理类是一个接口，就设置为
    proxyMode=ScopedProxyMode.INTERFACES，如果代理类型为具体的类，则设置为proxyMode = ScopedProxyMode.TARGET_CLASS
```text
  @Component
  @Scope(value=WebApplicationContext.SCOPE_SESSION,
  proxyMode=ScopedProxyMode.INTERFACES)
  public ShoppingCar car(){
    ...
  }
```
  * Request，在web的应用中，请求
  * @Scope，在@Bean和@Component连用是设置作用域/在xml配置的时候在<bean scope="">..</bean>设置
* 运行时注入值
  * 通过配置读取配置文件获取值 @PropertySource("xxx")与@Configuration连用,如@PropertySource("classpath:/static/jdbc.properties")，注入外部参数到配置类中的Environment类中
  `org.springframework.core.env.Environment`,通过@Autowrite注解接受解析好的配置信息如（jdbc连接信息等等）
  * 通过使用属性占位符获取配置文件中的值，在xml中是用${x.y},其中${x.y}是属于配置文件的信息，在xml可以使用在java config中也可以使用，需要使用@Value("${x.y}")做参数值注入
  * 在java config中使用占位符需要配置一个`org.springframework.context.support.PropertySourcesPlaceholderConfigurer`Bean，通过@Bean修饰，
    如果在xml中需要使用占位符的话，需要配置<context:property-placeholder />
  * SpEL,通过一种表达式将值注入到bean属性和构造器中
* SpEL，需要放到#{...}中，可以注入到@Value中，如@Value("#{systemProperties\[\]}")
  * 使用Bean的Id是使用Bean
  * 调用方法和访问对象的属性
  * 对值进行算术、关系和逻辑运算
  * 正则表达式匹配
  * 集合操作
  * 获取系统参数，如#{systemProperties\['disc.title'\]}
```text
@Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Qualifier("toOther")
    public Other iniOther(@Value("${disc.next}") String nx,//属性站位符
@Value("#{systemProperties['disc.title']}") String nx2) { //SpEL表达式
        String property = environment.getProperty("disc.title");
        System.out.println(property);
        return new Other() {
        };
    }
```
* 总结
  * java config 类，需要标注@Configuration，需要添加@ComponentScan扫描
```text
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = Audience.class)
public class Config {
}
```
  * java Bean，可以通过@Component单独标注，并被java Config 类扫描，也可以在java Config 类中被@Bean修饰
  * 注入，可以通过@Value注入参数，@Autowired，可修饰属性，方法参数（修饰到方法上）@Inject
  
### 切面编程AOP
#### Spring AOP基础定义
> 抽象出相同关注点组成一个特殊的模块，进行处理//TODO?
* 通知（advice），定义了切面执行时机以及如何执行
  * 前置通知（before），在目标方法调用之前
  * 后置通知（after），在目标方法调用之后
  * 返回通知（after-return），方法成功返回时执行
  * 异常通知（after-throwing），方法抛出异常时执行
  * 环绕通知（around），通知包裹了被通知的方法，在被通知方法调用之前和调用之后执行
* 连接点（join point），应用执行过过程中，能够插入切面的一个点
* 切点（poincut），定义了匹配通知所要织入的一个或多个连接点，定义了执行的地方
  * 通常使用明确的类和方法名，或者，利用正则表达式来指定匹配的类和方法名来指定切点，有些aop框架允许动态建立切点
* 切面（Aspect），有通知和切点共同构成切面，定了在何时何地如何执行的方法
* 引入（Introduction），向现有类中引入新的方法或属性
* 织入（Weaving），织入是把切面应用到目标对象上生成代理对象的过程，目标对象的生命周期内有多个点可进行织入
  * 编译期，AspectJ框架是这种方式
  * 类加载期，AspectJ 5 支持
  * 运行期，SpringAOP支持的方式
* Spring提供了4种类型的AOP支持，Spring是基于运行期动态代理的基础上，只支持方法拦截
  * 基于代理的经典Spring AOP支持
  * 纯POJO切面
  * @AspectJ注解驱动切面
  * 注入式AspectJ切面（适用于所有版本的Spring）
* Spring AOP中通知是JAVA类，切点使用注解或XML来定义，AspectJ通过特殊的语言来处理切面编程
* Spring通过代理类包裹切面，在运行期把切面织入到Spring管理的bean中 
* Spring只支持方法级别的连接点，AspectJ可以支持构造器和属性连接点
* Spring通过AspectJ切点表达式来定义Spring切面，一下都为指示器
  * arg(),限制连接点匹配参数为指定类型的执行方法
  * @args()，限制连接点匹配参数由指定注解标注的执行方法
  * execution()，用于匹配连接点的执行方法，只有execution指示器是实际执行匹配，其他表达式是做条件限定的
  * this()，限制连接点匹配AoP代理的Bean引用为指定类型
  * target()，限制连接点匹配目标对象为指定类型的类
  * @target()，限制连接点匹配特定的对象，该对象应该具有指定类型的注解
  * within()，限制连接点匹配指定类型
  * @within()，限制连接点匹配指定注解所标注的类型
  * @annotation，限制连接点匹配指定注解
  * bean(xx),Spring特有指示器
```text
package connection;
public interface Performance{
  public void perform(){
   ...
  }
}
//1、AspectJ切点表达式选择perform()方法
//执行方法时触发，任意返回值，方法所在类，方法名，使用任意参数
//execution括号内指定方法
execution(* connection.Performance.perform(..))
//2、限定具体在某个包下面
//关系表达式&& || ! and  or not，都可使用
execution(* connection.Performance.perform(..))
  && within(connection.*)
//3.使用bean指示器来指定bean id
execution(* connection.Performance.perform(..)) and bean('workBean')
```
#### 使用注解定义切面
1. spring引入AspectJ 5 注解定义包，支持aop功能，并且支持aspect注解
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<!--其中包含
spring-aop 包
org.aspectj.aspectjweaver 包
-->
```
2. 定义切面，在切面类标注@Aspect，以下为生命通知的方法注解
  * @After，在方法执行之后
  * @AfterReturning，在返回数据之后
  * @AfterThrowing，在抛出异常后
  * @Around，环绕，会将目标方法包装起来
  * @Before，在方法执行之前
3. 通知方法使用@Pointcut注解修饰，表明为当前切面公共方法，可以被其他注解引用并且本身应该是空方法，只做为一个标记使用
```text
@Aspect
@Component
public class Audience {
    //不带参数的切点
    @Pointcut("execution(* cn.ms22.study.server.UserServer.allInfo(..))")
    public void comment(){}
    //带参数的切点，args中的参数名需要和方法中的参数名一致
    @Pointcut("execution(* cn.ms22.study.server.UserServer.userInfo(String)) && args(username)")
    public void argsComment(String username){}
    //表演前
    @Before("comment()")
    public void silenceCellPhones(){
        System.out.println("silence Cell Phones");
    }

    //表演前
    @Before("comment()")
    public void takeSeats(){
        System.out.println("take seat");
    }
    
    //表演后
    @After("comment()")
    public void applause(){
        System.out.println("clap clap clap");
    }
    //执行完毕并返回值后
    @AfterReturning("comment()")
    public void end(){
        System.out.println("After Return。");
    }
    
    //表演失败
    @AfterThrowing("comment()")
    public void demandRefund(){
        System.out.println("表演失败");
    }
    //环绕，包括连接点之前之后异常都可以在一个方法中处理，必须ProceedingJoinPoint这个参数
    @Around("comment()")
    public Object round(ProceedingJoinPoint joinPoint) {
        Object o = null;
        try {
            //之前
            o = joinPoint.proceed();
            //之后
        } catch (Throwable throwable) {
            //异常
        }
        return o;
    }
}
```
4. 在切面类添加@Component修饰为组件，有Spring 上下文管理声明周期
  * 同时也可以在java config 类中，通过@Bean方法修饰为需Spring上下文管理的类
5. 在java Config 类中标注@EnableAspectJAutoProxy，启用AspectJ标注
6. 环绕@Around，以ProceedingJoinPoint作为参数，同时处理执行前，执行后，异常等问题
7. 通过注解新增新功能，@DeclareParents
  * @DeclareParents中value，表示那种类型的Bean需要引入接口
  * defaultImpl，表示引用功能实现的类
  * @DeclareParents表明需要引入的接口类型
```text
    //在切面类中添加@DeclareParents标注，修饰静态属性，表示为cn.ms22.study.server.UserServer所有子类添加NewFunction接口的
    //默认实现NewFunctionImpl.class
    @DeclareParents(value = "cn.ms22.study.server.UserServer+",
            defaultImpl = NewFunctionImpl.class)
    public static NewFunction newFunction;
```
### SPRING MVC
#### Spring MVC 流程
1. Spring MVC 的所有请求都会被一个前端Servlet控制器拦截并处理（DispatcherServlet）
2. DispatcherServlet把请求发送给Spring MVC控制器（Controller）,在发送给控制器之前，Spring会查询所有的映射（handler mapping），并确定发送给哪个控制器
3. 控制器完成逻辑处理，之后需要返回给请求方
4. 在返回请求之前需要将一些业务信息（Model）封装渲染为视图（View）可能是jsp页面等
5. 控制器在返回给请求方的时候，会经过DispatcherServlet，其中包含了打包好的模型和视图
6. DispatcherServlet接收到Controller返回的打包信息，然后做视图解析器（view resolve），查找真正的视图，可能是个jsp页面
7. DispatcherServlet定位到具体的视图实现（如：jsp页面），视图实现会根据数据模型渲染输出，通过请求响应返回给客户端
#### 搭建Spring MVC
* 配置DispatcherServlet，传统的配置方法是需要配置web.xml，同时打包到war中，得益于Servlet 3和Spring 3.1的功能增强，有其他的配置方法，使用java
将DispatcherServlet配置到Servlet容器中
* 启动DispatcherServlet时，会创建Spring上下文，加载配置文件和其中配置的Bean
* 配置上下文
1. AbstractAnnotationConfigDispatcherServletInitializer的子类会自动配置DispatcherServlet 和 Spring 应用上下文
2. 在Servlet3.0环境中，容器会在classpath中查找实现javax.servlet.ServletContainerInitializer接口的类如果能发现就会用它来配置Servlet容器
3. spring-web包中org.springframework.web.SpringServletContainerInitializer实现了javax.servlet.ServletContainerInitializer接口，该类处理
org.springframework.web.WebApplicationInitializer，org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer
* 继承自WebApplicationInitializer，所以会被servlet3.0容器自动发现
* spring的应用上下文位于应用程序的Servlet上下文中
* 通过继承AbstractAnnotationConfigDispatcherServletInitializer类是传统web.xml的替代方案，两种方式可以共存
* 当前这种方式需要在servlet3.0以后的版本才行tomcat7之后
#### 控制器、数据层、视图层
* @Controller
* @Repository
* @Server
* @RequestMapping
* @RequestParam
* RequestParam
  * @RequestMapping("/title")
  * public String aTitle(@RequestParam("name") String name){
* @PathVariable
  * @RequestMapping("/{id}") 
  * public String title(@PathVariable String id){...}
* 接受表单数据，接受参数对象的属性名对应
* org.springframework.web.servlet.view.InternalResourceViewResolver，解析器页面跳转方式
  * 重定向"redirect:/xxx/xx",重新请求你新的地址，有两次请求，地址会发生转变
  * 前往"forward:/xxx/xx"，一次请求，直接跳转，请求地址没有转变
* 数据校验，spring对java数据校验支持基于[JSR303](https://jcp.org/aboutJava/communityprocess/final/jsr303/index.html)的一些实现
  * 需要具体实现的包，如Hibernate Validator，pom引入hibernate-validator[Hibernate Validator](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#_validating_constraints)
  * jakarta.validation.constraints，包中提供了相应的注解词条
  * 在接受参数的类上添加相关的注解
  * 在Spring的参数接受处添加@Valid注解，校验不会阻塞程序，Spring中的Errors对象会把相关信息存储起来
    * public class Title {
          @NotNull
          private Long id;
    * @RequestMapping(value = "/add",method = RequestMethod.POST)
          public String register(@Valid Title title, Errors errors){
              if(errors.hasErrors()){

#### Thymeleaf
* JSP在html中添加java代码，并且有JSP自己定义的标准标签，Spring也定义了一些JSP的标签
* Thymeleaf没有新增标签，只是在html标签中定义了一些新的属性
* 在项目中需要配置Thymeleaf来解析视图
  * java config中配置@Bean 
#### Spring 处理文件上传
* @RequestPart修饰controller的参数
#### Spring 处理异常
* @ResponseStatus，异常返回特定的HTTP 状态码
* @ExceptionHandler，在一个控制器中，单独一个方法处理特定异常的注解
* 统一异常处理很有用，控制器通知，@ControllerAdvice注解，修饰特定的类，类中包含特定的一些注解，会运用到整个程序中带有@RequestMapping中
  * @ExceptionHandler注解方法
  * @InitBinder注解方法
  * @ModelAttribute注解方法
### Spring 框架中蕴含的经典设计思想或原则
* 约定大于配置、低侵入松耦合、模块化轻量级
* 解耦业务和非业务开发、让程序员聚焦在业务开发上
* 隐藏复杂实现细节、降低开发难度、减少代码 bug
* 实现代码复用、节省开发时间
* 实现代码复用、节省开发时间；规范化标准化项目开发、降低学习和维护成本
### 约定优于配置
* 通过约定的代码结构或者命名来减少配置。说直白点，就是提供配置的默认值，优先使用默认值
* 属性名默认跟表字段名相同，String 类型对应数据库中的 varchar 类型，long 类型对应数据库中的 bigint 类型
* 基于约定来配置，在没有牺牲配置灵活性的前提下，节省了我们大量编写配置的时间，省掉了很多不动脑子的纯体力劳动，提高了开发效率。
### 低侵入、松耦合
* 请求日志、数据采点、安全校验、事务
* 基于 AOP 这种开发模式，将非业务代码集中放到切面
### 模块化、轻量级
* Spring 的模块化设计思想，模块之间关系，仅有上层对下层的依赖关系，而同层之间以及下层对上层，几乎没有依赖和耦合。
* 每个模块都非常轻量级，都可以单独拿来使用。
### 再封装、再抽象
* Spring Cache，它定义了统一、抽象的 Cache 访问接口，这些接口不依赖具体的 Cache 实现（Redis、Guava Cache、Caffeine 等）
* Spring 提供了 spring-data-redis 模块，对 Redis Java 开发类库（比如 Jedis、Lettuce）做了进一步的封装，
适配 Spring 的访问方式，让编程访问 Redis 更加简单
* Spring 对 JDBC 异常也做了进一步的封装。封装的数据库异常继承自 DataAccessException 运行时异常。
这类异常在开发中无需强制捕获，从而减少了不必要的异常捕获和处理。
* JdbcTemplate,性能损耗比较少,它的缺点也比较明显，那就是 SQL 与代码耦合在一起，而且不具备 ORM 的功能，
需要自己编写代码，解析对象跟数据库中的数据之间的映射关系
* Hibernate,全自动化的 ORM 框架,性能方面，这样得到的 SQL 可能没有程序员编写得好
### Spring Security
> 基于Spring应用程序提供的声明式安全保护的安全框架，能够提供web请求级别和方法调用级别的处理身份认证和授权
* csrf，spring security默认开始csrf限制，防止跨域请求

### Spring 持久化
#### 配置数据源
* 配置数据源的Bean，使用@Profile选择各种环境上处理的数据源
  * 通过JDBC确定定义数据源
  * 通过JNDI查找数据源，正式环境中，通过应用服务器提供JNDI数据源服务
  * 连接池的数据源
* 使用JDBC的原因，以及缺点
  * JDBC使用SQL语句直接访问数据库
  * JDBC能够对数据访问的性能调优
  * JDBC允许使用数据库的所有特性，框架（mybatis、hibernate、JPA）就不会全面
  * JDBC能够在更低曾是处理数据
  * JDBC访问数据，需要处理大量重复工作，如连接，异常
* Spring使用JDBC模板访问数据库
  * JdbcTemplate
  * NamedParameterJdbcTemplate
  * SimpleJdbcTemplate
#### JPA
> 通过EntityManagerFactory实现类类获取EntityManager实例
* 基于模板的JPA和纯粹的JPA方式
* org.springframework.orm.jpa.LocalEntityManagerFactoryBean,使用应用程序管理类型的EntityManagerFactory
* org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean，生成容器管理类型的EntityManagerFactory

#### Repository 和 Spring data JPA之间的关系
* Repository 是与数据库交互的类接口
* Spring data JPA则是操作接口，如，保存、删除、根据Id查询等接口
* Spring data JPA 的打开，@EnableJpaRepositories，会扫描继承自Spring data JPA Repository接口的所有接口，并在调用的时候自我反射实现内置的18个遍历方法
* SPring data 提供的domain-specific language函数命名方式，系统可以据此来实例化改接口类，并且也提供了自定义方法名的处理方式，
  * 如果无法解析方法名，会报错
  * 如果需要自定义，需要在方法名上添加@Query("sql")
  * @Query功能非常简单，如果需要复杂的功能，需要回退到JPA使用Repository的方式，无法使用Spring Data Repository的方式，使用混合模式
  * Spring Data Repository 的混合模式，默认系统会生成xxxImpl的实现，如果已经有xxxImpl实现，就会合并
### Spring 启用缓存支持
* 开启缓存管理器
  * @Configuration @EnableCaching
* 缓存管理器org.springframework.cache.CacheManager,在java Configuration bean中配置一个CacheManager的具体实现,内置缓存管理器
  * org.springframework.cache.support.SimpleCacheManager
  * org.springframework.cache.support.NoOpCacheManager
  * org.springframework.cache.support.CompositeCacheManager
* 第三方包支持的cacheManager
  * org.springframework.data.redis.cache.RedisCacheManager 
* 缓存使用到的注解，注解只能标注到方法上
  * @Cacheable，表明获取数据会首选到缓存中查找，如果没有则重新执行方法并缓存和返回,在save方法时，很合适使用
  * @CachePut，获取的时候不会从缓存中获取，返回得只需要回到缓存中，在save方法时合适使用
  * @CacheEvict，Spring应该在缓存中清除一个或多个条目，在delete/remove方法时，合适使用，清除缓存
  * Caching，一个分组注解，可以同时应用到多个其他的缓存上
* 在缓存中使用SpEL表达式
  * 如自定义缓存的KEY

### REST
> 将资源的状态以最合适客户端或服务端的形式从服务端转移到客户端或从客户端转移到服务端
* 表述性，REST资源可以用各种形式进行表述，如JSON，XML，甚至是HTML，使用资源的使用形式
* 状态，使用REST时，需要关注资源的状态
* 转移，某种表达形式从一个应用转移到另一个应用
* 在REST中资源通过URL进行识别和定位
  * REST通过HTTP方法来识别行为，如GET、POST、PUT、DELETE、PATCH等
  * create，POST，read，GET，update，PUT或PATCH，delete，DELETE
* Spring 对REST的支持
  * 控制器，可以处理REST的方法，GET，POST，DELETE，PUT等
  * 借助@PathVariable来识别URL中的参数
  * 借助Spring视图和视图解析器，将资源包装为多种数据模型进行渲染，JSON、XML、Atom、RSS等View
  * 可以使用ContentNegotiatingViewResolver来选择最适合客户端的表述
  * 借助@ResponseBody注解和各种HttpMethodConvert实现，能够替换视图的渲染方式
  * 借助@RequestBody注解和各种HttpMethodCoverter实现将传入的HTTP数据转化为控制器能够识别的JAVA对象
  * 借助RestTemplate，Spring应用能够方便的使用REST资源
* Spring 提供两种方式将资源的java形式转换为发送给客户端的表达形式
  * 内容协商，选择一个视图，它能够将模型转换为呈现给客户端的表达形式,org.springframework.web.servlet.view.ContentNegotiatingViewResolver
    * 确定请求的媒体类型
    * 找到适合媒体类型的最佳视图
    * ContentNegotiatingViewResolver的解析步骤,一次如下
      1. 请求URL的结尾，如果是.json,就返回application/json等
      2. 查看accept头部的mime类型
      3. 如果没有accept，就默认客户端可以接受任意类型视图
    * ContentNegotiatingViewResolver不会解析视图，而是转发给相应的视图解析器解析
    * ContentNegotiatingViewResolver，提供内容协商解决问题，有部分限制//TODO
  * 消息转换，通过一个消息转换器将控制器返回的对象转换为客户端的表达形式
    * @ResponseBody，将返回对象作为资源发送给客户端，并将其转换为客户端可接受的形式，DispatcherServlet会考虑Accept头部信息，
    * @RestController 自带注解了@ResponseBody
    并查找能够给客户端提供所需的消息转换器（需要类路径中有相应的转换器包）
    * @RequestBody，在接受参数的时候，会组装为java对象
* 处理返回消息，包含错误提示，状态码等资源以外的信息
  * 使用@ResponseStatus注解指定消息状态码
  * 控制器方法返回ResponseEntity对象，该对象能够响应更多的元数据
  * 异常信息能够应对错误场景，   
    
* 通过配置org.springframework.web.accept.ContentNegotiationManager来控制ContentNegotiatingViewResolver的具体行为的三种方式
  * 直接创建（在java config bean中），已经弃用，配置型太多
  * 通过org.springframework.web.accept.ContentNegotiationManagerFactoryBean简介创建Bean，通过XML方式配置比较方便
  * 继承org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter来实现（已经被弃用）
  org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureContentNegotiation方法
  * 实现org.springframework.web.servlet.config.annotation.WebMvcConfigurer接口中的
  org.springframework.web.servlet.config.annotation.WebMvcConfigurer#configureContentNegotiation方法
### Spring 消息支持
#### ActiveMQ
* 启动activemq服务器（docker启动）
  * docker pull rmohr/activemq
  * docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
* 进入activemq服务器监控页面
  * http://localhost:8161/admin
  * 用户名/密码:admin/admin
* 引入activemq支持
  * <dependency>
  *     <groupId>org.springframework.boot</groupId>
  *     <artifactId>spring-boot-starter-activemq</artifactId>
  *     <version>2.6.3</version>
  * </dependency>
* 配置java Configuration，引入Bean
  * @Bean javax.jms.ConnectionFactory
  * @Bean org.apache.activemq.command.ActiveMQQueue
  * @Bean ActiveMQTopic
  * @Bean org.springframework.jms.core.JmsOperations
  * @Bean org.springframework.jms.support.converter.MessageConverter
* 在server中使用
  * 发送异步消息
    * 在Server中引入jmsOperations，使用jmsOperations.convertAndSend(Object object);
  * 接收同步消息
  * 接收异步消息
#### Spring使用WebSocket
> 解决浏览器与服务器全双工通信，比轮询更加高效自然
* Spring支持Websocket
  * 发送和接受消息的底层API
    * 引入spring WebSocket的包
    * 服务器端实现org.springframework.web.socket.WebSocketHandler或
    继承org.springframework.web.socket.handler.AbstractWebSocketHandler或
    继承org.springframework.web.socket.handler.TextWebSocketHandler或其他的类型处理器
    * 配置WebSocket到java Configuration bean中，配置类实现org.springframework.web.socket.config.annotation.WebSocketConfigurer
  * 发送和接受消息的高层API
  * 用来发送消息的模板
  * 支持sockJS，用来解决浏览器端、服务器端以及代理不支持WebSocket的问题
  