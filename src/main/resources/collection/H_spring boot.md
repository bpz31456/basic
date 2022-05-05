## Spring Boot 
Spring Boot 的设计初衷是快速启动一个项目，利用它可以快速地实现一个项目的开发、部署和运行。
* 策略模式的典型应用场景，一般是通过环境变量、状态值、计算结果等动态地决定使用哪个策略。对应到 Spring 源码中，
我们可以参看刚刚给出的 DefaultAopProxyFactory 类中的 createAopProxy() 函数的代码实现。
* （解释器模式） SpEL，全称叫 Spring Expression Language，是 Spring 中常用来编写配置的表达式语言。它定义了一系列的语法规则。
* spring framework是轻量级框架，但是配置是重量级的，spring2.5引入基于注解的自动组件扫描，消除了大量xml配置，spring3.0基于java
的配置，替代了xml配置
* Spring Boot CLI，（Command Line Interface， CLI）
* 利用了Spring 4的条件化配置特性，以及Maven和Gradle提供的传递依赖解析，以此实现Spring应用程序上下文里的自动配置
### springboot基础
* 自动配置
  * Spring boot，会对常见（各种Template的javaBean config）的配置起到自动配置作用，Spring boot在classpath中发现了JdbcTemplate，会自动配置进入Bean
  * Spring boot启动时，自动配置时，会做很多判断，在classpath中寻找可自动配置的功能，涵盖安全、 集成、持久化、 Web开发等诸多方面
  * spring-boot-autoconfigure jar包做了很多自动化配置监测，自动化配置的配置类注解
  * 各种自动配置类中需要的配置文件参数，通过以下代码获得
    * Environment environment = context.getEnvironment();
    * environment.containsProperty("spring.datasource.url")
  * @ConditionalOnBean 配置了某个特定Bean
  * @ConditionalOnMissingBean 没有配置特定的Bean
  * @ConditionalOnClass Classpath里有指定的类
  * @ConditionalOnMissingClass Classpath里缺少指定的类
  * @ConditionalOnExpression 给定的Spring Expression Language（ SpEL）表达式计算结果为true
  * @ConditionalOnJava Java的版本匹配特定值或者一个范围值
  * @ConditionalOnJndi 参数中给定的JNDI位置必须存在一个，如果没有给参数，则要有JNDI
  * InitialContext
  * @ConditionalOnProperty 指定的配置属性要有一个明确的值
  * @ConditionalOnResource Classpath里有指定的资源
  * @ConditionalOnWebApplication 这是一个Web应用程序
  * @ConditionalOnNotWebApplication 这不是一个Web应用程序
* 起步依赖，需要什么库，springboot就会引入
* 命令行界面,无需传统构建项目
* Actuator，Spring 内部监测
* Spring initializr初始化spring boot项目
* Maven项目布局//TODO 出处?
#### 基础配置
* @SpringBootApplication，项目启动类配置项，最少配置，开启组件自动扫描和自动配置
  * @SpringBootConfiguration，@Configuration，标注为一个配置类
  * @EnableAutoConfiguration，打开自动扫描
  * @ComponentScan ，组件扫描
* 如果需要自动配置以外的配置，也需要自己编写java Configuration 类
* spring boot 配置文件application.properties
* Spring boot 构建过程，maven项目通过pom.xml文件配置
  * 默认配置打包为jar
  * spring-boot-starter-：Artifact ID前缀
  * 依赖项目以功能为导向
  * 不需要确定的version，会根据spring-boot-starter-parent的版本自动适配
  * 可以排除掉默认的starter中默认的依赖项目
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>com.fasterxml.jackson.core</groupId><!--排除掉包中的jackson包-->
        </exclusion>
    </exclusions>
</dependency>
```
  * 可以添加自己的其他依赖
#### 自动化配置
* 默认自动化配置可以最大程度减少配置带来的工作量
* 覆盖自动配置，使用特定的自定义配置
  * 显示配置自己的同类型配置类，spring boot就会默认使用自定义的配置类，随后降低自动配置的优先级
  * @ConditionalOnMissingBean，是覆盖自动化配置的关键
* 读取配置信息
  * 环境变量、 Java系统属性、 JNDI（ Java Naming and Directory Interface）、命令行参数或者属性文件里进行指定,优先级逐个降低
    * (1) 命令行参数
    * (2) java:comp/env里的JNDI属性
    * (3) JVM系统属性
    * (4) 操作系统环境变量
    * (5) 随机生成的带random.*前缀的属性（在设置其他属性时，可以引用它们，比如${random.long}）
    * (6) 应用程序以外的application.properties或者appliaction.yml文件
    * (7) 打包在应用程序内的application.properties或者appliaction.yml文件
    * (8) 通过@PropertySource标注的属性源
    * (9) 默认属性
  * application.properties、application.yml文件放置位置，优先级逐个降低
    * (1) 外置，在相对于应用程序运行目录的/config子目录里
    * (2) 外置，在应用程序运行的目录里。
    * (3) 内置，在config包内。
    * (4) 内置，在Classpath根目录
    * application.yml的优先级高于application.properties
    * 可以通过配置开启嵌入中间件tomcat等的https
  * @ConfigurationProperties(prefix="amazon")，注解表示，当前类的属性（带有setter方法）通过配置文件或其他环境变量等注入（id前缀为：amazon.xx）
    * 配置信息和java属性名，自动驼峰替换如amazon.member_name替换为memberName属性对应
    * 一种优雅的方案是单独一个java Configuration bean加上@ConfigurationProperties，专门收集某一类配置信息
* Profile属性
  * application-{profile}.properties，配置文件遵循命名方式
#### spring boot 程序部署
* 构建WAR文件
  * 修改jar为war
  * 定义子类继承`org.springframework.boot.web.servlet.support.SpringBootServletInitializer`,重写`org.springframework.boot.web.servlet.support.SpringBootServletInitializer#configure`
  方法。
  * 通过部署到tomcat中可以访问，也可以通过java -jar xx.war启动
```java
public class GenealogyWarApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GenealogyApplication.class); //这里指向启动class
    }
}
```