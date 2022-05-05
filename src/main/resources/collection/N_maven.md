## Maven
### maven项目构建生命周期
> 每个过程都是交给插件来实现，每个步骤都绑定一个或多个插件，大部分步骤都绑定了默认的插件实现，如编译阶段默认插件maven-compiler-plugin
>测试由maven-surefire-plugin为默认插件实现
* 清理
* 初始化
* 编译
* 测试
* 打包
* 集成测试
* 验证
* 部署
* 站点生成
### 插件查找以及插件配置项
* 官方插件[maven插件](https://maven.apache.org/plugins/index.html)
### 插件配置
* maven官方插件可以省略`<groupId>org.apache.maven.plugins</groupId>`
* 插件类型分为多种，`核心插件`，`打包插件`，`报表插件`，`工具插件`
* 打包插件可以配置一些非标志配置项，不是所有的插件都支持这些配置项[打包插件非标准配置](https://maven.apache.org/shared/maven-archiver/)
* 以下是打包插件maven-jar-plugin，指定mainclass的配置
  * 执行命令 java -jar springmvc-1.0-SNAPSHOT.jar
  * 如果没有配置mainClass，执行效果,D:\study\springcode\target\springmvc-1.0-SNAPSHOT.jar中没有主清单属性
```xml
<build>
    <plugins>
        <plugin>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <encoding>utf-8</encoding>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.2.0</version>
            <configuration>
                <!--使用了非标准的-->
                <archive>
                    <manifest>
                        <mainClass>
                            cn.ms22.Main
                        </mainClass>
                    </manifest>
                </archive>
            </configuration>
        </plugin>

    </plugins>
</build>
```