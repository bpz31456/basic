## 错误误日志
1. Failed to execute goal org.apache.maven.plugins:maven-resources-plugin:3.2.0
  * 引入资源打包插件
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-resources-plugin</artifactId>
            <version>3.2.0</version>
        </plugin>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```
2. Unrecognized option: --profile=sec 
  * 在spring boot jar中带参数启动 java -jar xx.jar --spring.profiles.active=sec
3. @SpringCloudApplication和@EnableCircuitBreaker已经弃用
  * 取而代之的是，EnableHystrix
  * 老版本的@SpringCloudeApplicaton注解包含了@EnableCircuitBreaker @EnableDiscoveryClient @SpringBootApplication
    * 新版本弃用了@SpringCloudeApplication，需要自己添加，在熔断中也取代了@EnableCircuitBreake
4. spring cloud 不会整体配置，只提供最佳的版本依赖，不在默认使用某种组件如熔断老版本默认使用@EnableHytrix
5. The packaging for this project did not assign a file to the build artifact
  * 通过idea打包的方式有误导致，需要选择Lifecycle->install的方式安装到本地	
6. Execution repackage of goal org.springframework.boot:spring-boot-maven-plugin:2.6.4:repackage failed: Unable to find main class
  * 通过spring boot的方式打包为jar包，必须包含可执行的main方法，所以需要替换为maven自带的打包程序
  * maven-compiler-plugin
7.  class path resource [org/springframework/boot/autoconfigure/web/ServerPropertiesAutoConfiguration.class] cannot be opened because it does not exist
  * spring cloud 项目因为发展导致了一些包前后不一致，导致了内容有修改，
  * org.springframework.cloud:spring-cloud-starter-netflix-ribbon:2.1.1.RELEASE
  * org.springframework.cloud:spring-cloud-starter-ribbon:1.4.2.RELEASE
8. gitlab docker root用户不知道密码处理办法
  * 启动docker gitlab
  * 进入gitlab bash控制界面
  * 执行gitlab-ralis console
  * 执行user = User.where(username: ‘root’).first
  * 执行user.password = ‘password’
  * 执行user.save!
9. spring cloud config配置客户端启动时报错 No spring.config.import property has been defined
  * bootstrap.properties比application.properties的优先级要高
  * 在SpringCloud 2020.* 版本把bootstrap禁用了，导致在读取文件的时候读取不到而报错，所以我们只要把bootstrap从新导入进来就会生效了。
  * <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bootstrap</artifactId>
            <version>3.0.2</version>
        </dependency>
10. Error invoking remote method 'docker-start-container': Error: (HTTP code 500) server error - Ports are not available: listen tcp 0.0.0.0:2181: bind: An attempt was made to access a socket in a way forbidden by its access permissions.
  * docker 启动zk时报错，予以提示为2181端口无效
  * 网卡重置后再启动
11. windows上查询端口
  * netstat -aon |findstr :2181
12. 网卡重置
  * netsh winsock reset
### mysql
1. 碎片优化optimize table artist，提示Table does not support optimize, doing recreate + analyze instead
   * 内部做了alter table artist force操作，等于是做了重建和更新索引统计信息