## spring cloud
> 新版的Spring Cloud更多倾向于做各种组件版本的依赖处理，不在大量使用默认的一些组件，会弃用一些集成式的注解
* Eureka [jʊ'ri:kə]
* Ribbon [ˈrɪbən]
* Feign [fen]
* Hystrix [hɪst'rɪks]
* Zuul [zulu]
* Sleuth [sluθ]
* Turbine [ˈtɜ:rbaɪn]
### spring cloud 基础知识
* 微服务
  * 微服务是一种架构风格，服务之家基于RESTful风格通信
  * 为了解决传统架构带来的臃肿更新，部署难以维护等问题
  * 带来的问题
    * 运维新挑战，维护需要更多自动化程序
    * 接口的一致性，为了保证完整的通信依赖，需要做更完善的接口和版本控制
    * 分布式的复杂性，服务之间的调用带来了网络、分布式事务、异步消息等问题
  * 微服务的架构指导
    * 服务组件化
    * 按照业务组织团队
    * 做产品的态度，小团队对组件的生命周期负责
    * 智能端点和哑管道
      * MQ之类的异步消息中间件，管道根本不关心具体传送的数据，所以与其叫哑管道，与服务总线（ESB）的区别，所有的请求都需要发送到总线，由总线来分发任务
      * 智能端点需要根据服务调用者的需求提供多种类型的服务以适应业务发展，比如报文转换，比如数据转换等等统统是在服务提供端实现
    * 去中心化治理
    * 去中心化管理数据，每个服务自己管理自己的数据
    * 基础设施自动化
      * 自动化测试
      * 自动化部署
    * 容错设计
    * 演进式设计
* spring cloud有一套完整的微服务解决方案
  * 一些企业微服务实践都是在某几方面上处理
    * 服务治理，阿里巴巴Dubbo
    * 分布式配置管理，百度的Disconf,SpringCloud的 Config
    * 批量任务，当当的Elastic-job
    * 服务跟踪，京东的Hydra
### 服务治理
> 微服务框架中，实现各个微服务之间自动注册和发现
* 微服务发展子初，服务较少可以通过静态配置的方式配置各个服务，为了便于维护，微服务框架主要通过服务自动注册和服务发现机制来实现自动化管理
* 服务注册，
  * 各个服务把自己的实例提交到服务注册中心，
  * 统一记录，
  * 服务注册中心与之有心跳线监测，
  * 不健康就剔除（剔除的策略问题）
* 服务发现，
  * 服务调用不在指向具体的各个实例，而是通过服务名来调用,
  * 调用方向服务注册中心发起询问，根据服务名获取服务清单列表（可能有缓存等方式减少网络请求），
  * 调用方得到清单后，轮询（客户端负载均衡）调用服务具体实例
* spring cloud Eureka 使用Netflix 的Eureka来实现服务发现和注册，采用java语言编写
   * 包含了服务端组件和客户端组件
  * 适用于java的分布式或是JVM语系的系统
  * 采用RESTfull API
  * 其他语言需要自己实现相应的客户端
  * 服务端也称为注册中心，支持高可用配置，支持集群
  * 客户端处理服务注册和发现，通过注解和参数配置的方式嵌入到客户端程序代码中，同时从服务端查询当前注册服务中心的服务兵缓存到本地，周期性刷新
#### 搭建注册中心，单体
* 普通spring boot项目引入依赖spring-cloud-starter-netflix-eureka-server
* Eureka,采用了CAP原则中的AP原则，即，可用性和可靠性，并不一定要保证一致性，为了保证尽可能一致，采用了重试机制
* 在启动类上打开@EnableEurekaServer注解
* 在配置文件application.properties配置不发现当前注册中心服务
```properties
server.port=1111
# 当前实例的hostname
eureka.instance.hostname=localhost
# 关闭通过ip查找地址
eureka.instance.prefer-ip-address=false
# 防止自己注入进入
eureka.client.register-with-eureka=false 
# 防止自己发现其他注册中心
eureka.client.fetch-registry=false
# 关闭自我保护，开发阶段防止缓存
eureka.server.enable-self-preservation=false
# 默认注册中心服务节点位置，是个魔数，官方默认
eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/

```
#### 搭建注册中心，集群
* 启动命令java -jar xxx.0.1-SNAPSHOT.jar --spring.profiles.active=sec
* 搭建高可用（提供副本）,根据profile特性，建立两个配置文件，application.first.properties,application.sec.properties
  * application.first.properties
```properties
spring.application.name=eureka-server
server.port=1111
# 当前实例的per1
eureka.instance.hostname=per1
# 关闭通过ip查找地址
eureka.instance.prefer-ip-address=false
# 自己成为一个服务注册
eureka.client.register-with-eureka=true 
# 自己成为服务，可以被其他服务看到
eureka.client.fetch-registry=true
# 指向其他副本
eureka.client.service-url.defaultZone=http://per2:1112/eureka/
```
  * application.sec.properties
```properties
spring.application.name=eureka-server
server.port=1112
# 关闭通过ip查找地址
eureka.instance.prefer-ip-address=false
# 自己成为一个服务注册
eureka.client.register-with-eureka=true
# 自己成为服务，可以被其他服务看到
eureka.client.fetch-registry=true
# 当前实例的per2
eureka.instance.hostname=per2
# 服务指向其他副本，让其他副本可以发现
eureka.client.service-url.defaultZone=http://per1:1111/eureka/
```
#### 搭建客户端
* 普通spring boot项目引入依赖spring-cloud-starter-netflix-eureka-client
* 在启动类上打开@EnableEurekaClient或@EnableDiscoveryClient
  * @EnableEurekaClient，在使用eureka时
  * @EnableDiscoveryClient，使用其他注册中心时
* 在配置文件application.properties配置不发现当前注册中心服务
```properties
spring.application.name=hello-server
server.port=8088
eureka.instance.prefer-ip-address=false
eureka.instance.hostname=localhost
# 服务注册到http://per1:1111/eureka第一个注册中心
eureka.client.service-url.defaultZone=http://per1:1111/eureka
management.endpoints.web.exposure.include=*
eureka.client.healthcheck.enabled=true
```
  * 通过一个Controller供其他服务使用
```properties
server.port=9001
spring.application.name=customer-server
#服务注册到第二个注册中心
eureka.client.service-url.defaultZone=http://per2:1112/eureka
management.endpoints.web.exposure.include=*
```
  * customer-server服务通过服务自带的`org.springframework.web.client.RestTemplate`构造为Bean,并使用
```text
    @RequestMapping("/product")
    public String hellProduct(){
        return restTemplate.getForEntity("http://hello-server/product/info",String.class).getBody();
    }
```

#### eureka总结
* 三个角色
  * 服务注册中心
  * 服务提供者
  * 服务消费者，//TODO?会不会导致循环调
* eureka.client.service-url.defaultZone,defaultZone默认的Zone
  * com.netflix.discovery.EurekaClientConfig#getAvailabilityZones
    * com.netflix.discovery.DefaultEurekaClientConfig#getAvailabilityZones
### 客户端负载均衡Ribbon
* 负载均衡，对网络的压力缓解、系统高可用、处理能力的扩展的重要手段
* 负载均衡，硬件F5或软件nginx，服务类都下挂一个服务清单，通过心跳线来判断下挂服务是否正常，
  * 当客户端请求到负载均衡设备，负载均衡设备会通过算法（轮询、权重、流量）给出一个服务地址
* 负载均衡注入到客户端如discoveryClient就是在客户端注入Ribbon负载均衡组件
  * 也需要通过心跳线监测服务列表中的服务是否正常
  * 在spring cloud中使用
    * 注册中心启动
    * @LoadBanaced注解修饰RestTemplate
* Ribbon就是一个客户端http RESTfull风格请求的工具
  * 提供了GET、POST、DELETE、PUT等请求
### Spring Cloud Hystrix,熔断保护
> 微服务结构中存在服务依赖情况，若一个服务发生故障，很容易因为依赖关系而导致服务故障蔓延，为了解决这个问题，引入了熔断机制（断路器）
* 断路器本身来自生活，如切断故障电路
* 通过断路器故障监控，返回给调用方一个错误响应，而不是长时间等待
* spring cloud 熔断的实现由多种
```text
Hystrix - org.springframework.cloud:spring-cloud-starter-netflix-hystrix
Resilience4J - org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j
Reactive Resilience4J - org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j
Spring Retry - org.springframework.cloud:spring-cloud-starter-circuitbreaker-spring-retry
Sentinal - org.springframework.cloud:spring-cloud-starter-circuitbreaker-sentinal
```
#### 具体实现
  * 1. 在服务中引入jar包支持
  org.springframework.cloud:spring-cloud-starter-netflix-hystrix:2.2.10.RELEASE
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        <version>2.2.10.RELEASE</version>
    </dependency>
```
  * 2. 在服务的启动类打开@EnableHystrix
  * 3. 在服务的server层的方法调用上打开@HystrixCommand(fallbackMethod = "helloFallback")
  * 4. 在服务的server层编写回调函数helloFallback
  * 5. server层通过RestTemplate调用其他服务时，如果超时或返回错误，将会直接返回helloFacllbak的结果
### 声明式服务调用 spring cloud feign
>  Feign creates a dynamic implementation of an interface decorated with JAX-RS or Spring MVC annotations
* spring cloud feign 是一个服务调用工具，在Spring Cloud Ribbon和RestTemplate上做了进一步封装，减少了模板化代码的编写
  * spring cloud feign，只需要创建一个接口，通过注解的方式来配置，即可完成对服务提供方的接口绑定
  * spring cloud feign，支持，可插拔的注解，包括feign和jax-rs注解
#### 具体实现
1. 引入jar包
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
```
2. 在启动类上注解@EnableFeignClients
3. 在Server接口类上@FeignClient("hello-server")
4. 在Server类方法中@RequestMapping("/hello/info"), Server接口方法 和 服务提供者的Controller定义类似，带参数也是如此
```java
@FeignClient("hello-server")
public interface FeignServer {
    @RequestMapping("/hello/info")
    String hello();

    @RequestMapping("/getUser")
    User getUser(@RequestParam String name,
                        @RequestHeader String sex,
                        @RequestBody String age);
}
```
5. 在Controller中直接注入Server，系统会直接根据接口来够着相应的类
#### 在调用接口涉及到一些需要传递的类（序列化，反序列）等问题
* 将服务和客户端提取到公共类提供为统一接口
  * 打包为不带可执行的jar包，替换为一下打包插件
```xml
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
```
* 此方法有个问题，就是接口的开闭原则，如果修改接口就会修改相关的实现
### API网关服务 spring cloud zuul，新版已经改变或是spring-cloud-starter-gateway
* zuul与Eureka整合，将自身注册为eureka的应用，同时获取其他微服务的实例
* zuul提供了一套前置过滤机制可以处理签名校验登录验证的问题

### 分布式配置中心，spring Cloud Config
> 微服务系统中集中化的外部配置，spring cloud config分为服务端和客户端,外部配置文件存储到git等版本控制服务器中
#### spring cloud config 服务端
* spring-cloud-config-server
* 采用支持git或svn等版本控制作为服务
* 实现步骤
  * 1. 引入spring cloud config server，spring-cloud-config-server
  * 2. 打开注解@EnableConfigServer
  * 3. 在application.properties中配置git仓库信息和服务信息
    * 其中在配置spring.cloud.config.server.git.uri=http://127.0.0.1/root/ms22config.git时，直接通过复制项目所在git服务器的clone按键地址
```properties

spring.application.name=config-server
server.port=7001

spring.cloud.config.server.git.uri=http://127.0.0.1/root/ms22config.git
spring.cloud.config.server.git.search-paths=config-repo

spring.cloud.config.server.git.username=root
spring.cloud.config.server.git.password=jsk31456.
```
* 在配置文件所在服务器中（可能是gitlab，github，svn服务器），配置文件与访问地址映射关系
  * 访问地址格式/{application}/{profile}[/{label}],项目名称/profile/git项目中分支名称
  * git项目中配置文件命名格式:/{application}-{profile}.yml
  * git项目中配置文件命名格式:/{application}-{profile}.properties
  * git项目中配置文件命名格式:/{label}/{application}-{profile}.yml
  * git项目中配置文件命名格式:/{label}/{application}-{profile}.properties
* 实质是spring config server 项目通过git clone 命令通过配置的git地址信息获取文件到本地，然后在读取返回到前端
#### spring cloud config 客户端
> 在上述spring cloud config 服务端启动后，通过启动本服务来获取其中的配置信息
* 引入pom,spring-cloud-starter-config
* 配置文件bootstrap.properties,必须是bootstrap配置文件
  * spring.application.name=didispace //与上面config server中的配置项对应
  * spring.cloud.config.uri=http://127.0.0.1:7001/ //与上文中对应
  * spring.cloud.config.label=main
  * spring.cloud.config.profile=dev
  * spring.cloud.config.name=didispace
  * server.port=7002
* 在启动时，可能加载不到bootstrap.properties文件，这是spring cloude的版本更新所致，需要引入相关包
```xml
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-bootstrap</artifactId>
			<version>3.0.2</version>
		</dependency>
```
* 在客户端引用配置到config server中的信息
  * @Value 注入信息 @Value("${xx}")
  * org.springframework.core.env.Environment 注入@Autowired
* spring cloud config中通过spring-boot-starter-actuator来做心跳线监测
  * http://localhost:7002/actuator/refresh 刷新才会重新获取config server中修改的新值
* 网络波动导致的失败重试，引入一下包，自动处理
```xml
		<dependency>
			<groupId>org.springframework.retry</groupId>
			<artifactId>spring-retry</artifactId>
			<version>1.3.2</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
			<version>2.6.4</version>
		</dependency>
```
### Spring cloud bus 消息总线
#### 消息中线简介
* 消息代理（message broker）,是一种消息验证、传输、路由的架构模式，消息代理是一种中间件产品，核心是一种消息路由程序，
用来实现消息的接受和分发
* 消息代理的场景
  * 将消息路由到一个或多个目的地
  * 消息转化为其他表现形式
  * 执行消息的聚合、分解，将结果转发到其他目的地
  * 调用web服务来检索数据
  * 响应事件和错误
  * 使用消息的订阅和发布或基于主题的消息路由
* 已有的消息开源产品，目前spring cloud bus支持的两种消息中间件如下
  * kafka
  * RabbitMQ
* RabbitMQ采用高级消息队列协议（AMQP）来实现
* AMQP协议，开放式标准应用层协议，要求使用HTTP、FTP、SMTP等方式来时间消息传输
  * 消息方向
  * 消息队列
  * 消息路由（包括点到点和订阅发布模式）
  * 可靠性
  * 安全性
* kafka消息中间件，提供订阅发布模式做消息路由
  * 消息持久化
  * 高吞吐率
  * 分布式
  * 跨平台
  * 实时性
  * 伸缩性
* kafka基本概念
  * broker，kafka集群包含一个或多个服务器，这些服务器统称为broker
  * topic，主题，与queue队列类似，逻辑上一个topic可以跨多个服务器
  * partition，一个topic可以包含一个或多个partition，提高吞吐率，一个partition，对应一个文件夹
  * producer，生产者
  * consumer，消费者
  * consumer group，每个consumer属于一个组，如果没有指定，属于默认组
#### spring cloud config 集成kafka做配置集中更新
1. spring cloud config server 服务端集成eureka，做到服务可以发现，称为集群中的一个服务
  * spring-cloud-starter-netflix-eureka-client
  * 打开@EnableEurekaClient注解
  * 配置服务集群节点，eureka.client.service-url.defaultZone=http://per1:1111/eureka
2. spring cloud config server 集成消息总线以及kafka
  * kafka默认端口9092，zookeeper默认端口2181在默认情况下，引入包，系统自动注入相关topics（springCloudBus）
    * 可通过在kafka中执行 kafka-topics.sh --list --zookeeper 192.168.2.160:2181 ，查询topics
```xml
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-bus</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-stream-binder-kafka</artifactId>
		</dependency>
```
3. spring cloud config server 集成actuator,actuator作为服务状态监测工具
  * 配置management.endpoints.web.exposure.include=busrefresh,busrefresh-destinations,mappings//因为版本文件导致很可能endpoints不一样
    * 可通过先配置“*”，再访问mappings后查看检查点信息，之后在逐渐配置为busrefresh，
  * 刷新配置访问地址 http://localhost:7001/actuator/busrefresh
    * 可通过http://localhost:7001/actuator/mappings查询相关访问接口
```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
```
4. spring cloud config client集成eureka
  * @EnableEurekaClient 注解
  * spring.cloud.config.discovery.enabled=true
  * spring.cloud.config.discovery.service-id=config-server
  * eureka.client.service-url.defaultZone=http://per1:1111/eureka
```xml
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
```
5. spring cloud config client集成actuator
  * 配置management.endpoints.web.exposure.include=busrefresh
```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
```
6. spring cloud config client集成bus
  * 引入包后，默认直接连接到springCloudBus
  * 在配置文件中可以配置kafka相关信息，如：
    * spring.cloud.stream.kafka.binder.brokers=localhost
```xml
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-bus</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-stream-binder-kafka</artifactId>
		</dependency>
```
7. spring cloud config client内需要更新配置信息的类添加@RefreshScope注解 
### spring cloud stream
* 是微服务应用中的消息驱动框架
* 支持消息中间件
  * apache kafka
  * kafka streams
  * rabbitmq
  * Google PubSub
  * ...
* 创建步骤
  1. 引入对应包
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-kafka</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream</artifactId>
    <scope>test</scope>
    <classifier>test-binder</classifier>
    <type>test-jar</type>
</dependency>
  
```
  2. 配置绑定器（binder）	
    * @EnableBinding(Sink.class),配置到类上
    * @StreamListener(Sink.INPUT)，配置到方法上
* @EnableBinding，绑定器绑定是个通道（channel），通道连接着一个主题
  * channel的配置，spring cloud stream内部默认input和output的通道，可以自己重新定义
  * Sink.class,org.springframework.cloud.stream.messaging.Sink，spring cloud stream内部定义的接受消息的接口
  * Source.class,org.springframework.cloud.stream.messaging.Source,spring cloud stream内部定义的发送消息的接口
  * Processor.class,org.springframework.cloud.stream.messaging.Processor,同时接受消息和发送消息
* channel连接的主题配置
  * spring.cloud.stream.bindings.input.destination=vipsoft_kafka
  * spring.cloud.stream.bindings.input.content-type=text/plain
#### binder,绑定器
* 一个绑定器绑定到对应的一个消息中间件实例
  * 在同一个应用中可以配置多个绑定器，就是一个应用程序中可以配置多个绑定器
* org.springframework.cloud.stream.binder.Binder
  * spring-cloud-stream-binder-kafka中实现的绑定器
    * org.springframework.cloud.stream.binder.kafka.KafkaMessageChannelBinder
* 绑定器抽象消息层，上层对应spring application，下层具体的消息处理，由各个厂家自行处理，就相当于一个门户
* 向应用程序暴露统一的channel
* application中stream通用配置
  * spring.cloud.stream.instance-count=1,实例数量
  * spring.cloud.stream.instance-index=0，kafka中的分区偏移
  * spring.cloud.stream.dynamic-destinations=，动态目标，只能在列表中的目标地址才会生效
  * spring.cloud.stream.default-binder=kafka，默认绑定器，当存在多个绑定器时使用
* 绑定通道，在绑定器与消息中间件连接后需要配置消息通道（每个消息中间件中可以配置多个通道【理解为一个消息队列】）
  * spring.cloud.stream.bindings.<channelName>.<property>=<value>
    * <channelName>代表着Source中的output通道,Sink中的input通道
    * 消息通道有输入、输出类型，所有配置分为通用配置，生产者配置，消费者配置
  * 通用配置,spring.cloud.stream.bindings.<channelName>.<property>=<value>
    * spring.cloud.stream.bindings.output_1.destination=vipsoft_kafka_output_1，消息的目标topic
    * spring.cloud.stream.bindings.output_1.content-type=text/plain，通道使用的消息格式
    * spring.cloud.stream.bindings.output_1.binder=kafka1,当存在多个绑定器时，指定具体的绑定器
    * spring.cloud.stream.bindings.input_1.group=,保证每个组里面的消息只会被一个实例接收和处理
  * 消费者配置，spring.cloud.stream.bindings.<channelName>.consumer.<property>=<value>
    * spring.cloud.stream.bindings.input_1.consumer.partitioned=true,分区
    * spring.cloud.stream.bindings.input_1.consumer.concurrency=1,并发实例
    * spring.cloud.stream.bindings.input_1.consumer.max-attempts=3,重试次数
  * 生产者配置spring.cloud.stream.bindings.<channelName>.producer.<property>=<value>
     * spring.cloud.stream.bindings.output_1.producer.partition-count=,配置消息分区数量
* 绑定器配置，消息中间件自身带有的特殊性做配置
  * 通用配置,spring.cloud.stream.kafka.binder.<property>=<value>
    * spring.cloud.stream.kafka.binder.brokers=localhost
  * 消费者配置，spring.cloud.stream.kafka.bindings.<channelName>.consumer.<property>=<value>
  * 生产者配置，spring.cloud.stream.kafka.bindings.<channelName>.producer.<property>=<value>
#### 消息处理
* 消息分区
  * 保证相同特征的消息被同一个实例消费
    * spring.cloud.stream.bindings.input_1.consumer.partitioned=true
* 消息类型
  * spring.cloud.stream.bindings.input_1.content-type=text/plain
  * spring.cloud.stream.bindings.input_1.content-type=application/json
  * JSON与POJO互相转换
  * String 与 byte[]
  * MIME类型
### 链路追踪，Spring Cloud Sleuth
* 在注册到注册中心的应用中引入pom坐标
```xml
		<!--链路追踪-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-sleuth</artifactId>
		</dependency>
```
* 在请求日志中会发现INFO [customer-server,d65e8e336bb7d946,d65e8e336bb7d946]，追踪路径
  * INFO [hello-server,d65e8e336bb7d946,d059a67c7339b9ff]
* 原理，每个应用都会添加一个trace Id，请求路径上都会带上，在内部是不会变的
* 在默认情况下，sleuth会在restTemplate中构建请求所需数据
  * 在请求头中添加一些参数
  * x-b3-traceid:当前链路唯一标识
  * x-b3-parentspanid:上一个工作单元
#### 日志收集，logStash
* 分布式应用的日志都是在各个主机上，所以需要通过专门的工具来收集整理分析
* 引入pom
```xml
<dependency>
  <groupId>net.logstash.logback</groupId>
  <artifactId>logstash-logback-encoder</artifactId>
  <version>7.0.1</version>
</dependency>
```
* 启动logstash应用，默认端口9600
  *  
#### ELK平台
* LogStash，日志收集过滤
* ElasticSearch搜索引擎
* kibana，web界面
* 工作流程，sprng-> logback-> logstash->elasticSearch->kibana
## mybatis
### MyBatis 如何权衡代码的易用性、性能和灵活性
* JdbcTemplate 提供的功能最简单，易用性最差，性能损耗最少，用它编程性能最好。
* Hibernate 提供的功能最完善，易用性最好，但相对来说性能损耗就最高了。
* MyBatis 介于两者中间，在易用性、性能、灵活性三个方面做到了权衡。
* 过度封装，提供过于简化的开发方式，也会丧失开发的灵活性。
### 如何利用职责链与代理模式实现 MyBatis Plugin
* MyBatis Plugin 跟 Servlet Filter、Spring Interceptor 的功能是类似的，都是在不需要修改原有流程代码的情况下，
拦截某些方法调用，在拦截的方法调用的前后，执行一些额外的代码逻辑。
* MyBatis Plugin 主要拦截的是 MyBatis 在执行 SQL 的过程中涉及的一些方法。
* 除了统计 SQL 的执行耗时，利用 MyBatis Plugin，可以分库分表、自动分页、数据脱敏、加密解密等等。
### MyBatis 框架中用到的十几种设计模式
* BaseExecutor：模板模式跟普通的继承有什么区别？
* 动态 SQL 的语法规则，解释器模式
* DynamicSqlSource.getBoundSql()
* ErrorContext：如何实现一个线程唯一的单例模式
* PropertyTokenizer：如何利用迭代器模式实现一个属性解析器
* Cache：为什么要用装饰器模式而不设计成继承子类
* Log：如何使用适配器模式来适配不同的日志框架