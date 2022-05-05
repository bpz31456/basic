## Docker
> Image、Container、repository(仓库)
> Image是类，Container是对象，Volumes是数据层
* 集中存储、分发的中央服务，Docker Registry，一个Docker Registry包含多个Repository，一个Repository包含多个tag,每个tag包含一个image
* <仓库名>:<标签>，每个标签对应一个版本号，默认标签是<latest>，如redis:latest，redis仓库，最新的版本
* 仓库名一般第两段,<用户名>/<软件名>，用户名如果不写，默认是library
### Docker 安装，配置加速器
* Docker Registry,公开服务
> 提供服务管理的开放式中央服务，提供免费，收费的的服务（提供私有镜像），常见的(Docker Hub)[https://hub.docker.com/]
> 还有(quay.io)[quay.io],国内的(阿里加速器)[https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors]
* 可以搭建私有的库
* 在window-docker设置daemon的加速地址，根据阿里加速器中的地址录入到，setting->docker engine->registry-mirrors:\[https://o833atm6.mirror.aliyuncs.com\]
### windows docker images默认位置修改
> 因为images默认位置在%LOCALAPPDATA%/Docker/wsl，所以默认在C盘，随着images越来越多，导致空间越来越大，需要将images放到其他数据盘中
> windows docker 新版本使用了wls2（）来管理
* wsl 需要安装，wsl的命令行提示 --help
* data/ext4.vhdx 是被docker-desktop-data 发行版使用，包含所有images信息
* distro/ext4.vhdx 是被 docker-desktop 发行版使用
1. 查询所有容器
  * docker ps  -a
2. 删除所有容器
  * docker container stop container_id
  * docker rm container
3. 提出而docker desktop
4. 关闭所有发行版
  * wsl --shutdown
5. 检查所有发行版是否已经关闭
  * wsl --list --verbose,如果有多个是run状态，可以多次执行 wsl --shutdown 命令，逐个关闭
6. 备份images信息到其他数据盘
  * wsl --export docker-desktop-data E:\docker\kelnerData\docker-desktop-data.tar//默认为tar格式，进行打包
  * wsl --export docker-desktop  E:\docker\kelnerData\docker-desktop.tar
7. 注销当前发行版，此步骤会将默认位置的ext4.vhdx删除
  * wsl --unregister docker-desktop-data
  * wsl --unregister docker-desktop
8. 查看是否注销成功
  * wsl --list --verbose
9. 导入备份的tar文件
  * wsl --import docker-desktop-data E:\docker\wsl\data E:\docker\kelnerData\docker-desktop-data.tar --version 2
  * wsl --import docker-desktop E:\docker\wsl\distro E:\docker\kelnerData\docker-desktop.tar --version 2
10. 查询wsl发新版信息
  * wsl --list --verbose
11. 启动wsl发行
  * wsl -d <发行版名称>,//发行版名称来自wsl --list --verbose中
  * wsl -d docker-desktop-data,启动docker 数据发行
12. 重新运行docker desktop，实质是启动docker-desktop发行
13. 重新查看wsl运行情况，wsl --list --verbose
### docker 安装kafka
1. 拉取images
  * docker pull wurstmeister/zookeeper
  * docker pull wurstmeister/kafka
2. 启动images,本机ip(192.168.2.160)
  * docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper
  * docker run  -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=192.168.2.160:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.2.160:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka
3. 启动一个生产者
  * docker exec -it kafka /bin/bash
4. 在生产者中发送消息，并启动一个主题vipsoft_kafka
  * kafka-console-producer.sh --broker-list localhost:9092 --topic vipsoft_kafka
5. 启动消费者
  * docker exec -it kafka /bin/bash
6. 消费者接受消息
  * kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic vipsoft_kafka --from-beginning
7. 在生产者交互界面中发送消息
### Docker 使用镜像
#### 从仓库获取镜像
```text
docker pull [选项] [docker repository地址[:端口]/] 仓库名[:标签]
最简化的：docker pull redis //从中央服务器获取redis的最新版本
```
#### 管理本地镜像
* 镜像是由多个层构成，是分层存储的，下载的时候也是一个层一个层的去下载，计算完整镜像的sha256值比较完整性
* 启动centos:7镜像为一个Container
```text
docker run -it --rm centos:7  bash //-i交互式，-t终端,--rm，使用后删除该容器,centos:7，软件名:版本,bash,进入容器后以bash进行交互
```
* 列出镜像
```text
docker image ls//列出镜像的Repository,tag,image id,created,size
```
* 同一个镜像可以对应多个标签
* 镜像在docker hub的体积是压缩后的体积，在这里ls查询的体积是各层解压后的体积
* 在ls的image体积也可能没有占用这么多，其中层可能和其他的image共用
```text
docker system df//查询镜像、容器、卷、缓存占用空间情况
```
* 虚悬镜像 docker image ls -f dangling=true
  * 由于更新的image占用之前的tag，导致旧版本的镜像没有tag所以就出现<none>:<none>
  * 虚悬镜像一般没有用通过命令删除，docker image prune
* 中间层镜像 docker image ls -a //查询所有的image
  * 由于多个image的层之间可能出现共用，所以也会出现<none>:<none>镜像，这些层不应该被删除
```text
docker image ls -f since=mogo:3.2 //-f是过滤器的意思，后面带的条件
docker image ls --format "{{.ID}}:{{.Repository}}" //格式化查询id:repository
docker image ls --format "table{{.ID}}\t{{.Repository}}\t{{.Tag}}"
```
```text
docker image rm $(docker image ls -f dangling=true)//配合$(xx)来组合命令
```
```text
docker run --name webserver -d -p 80:80 nginx //以80端口启动名为webserver的nginx image的容器
```
#### 镜像实现基本原理
```text
docker exec -it webserver bash //进入服务名为webserver的容器，并交互执行命令
```
```text
docker diff webserver //查询修改的信息
```
* docker 容器里面修改的东西如果不使用卷的话，会被保存到容器的存储层中，需要使用docker commit 命令把修改后的信息固话下来保存到新的image中
```text
//提交当前容器修改的信息到新的nginx:v1_test中，会新建一个nginx:v1_test的image
docker commit  --author "bpz777@163.com" --message "modify index html info" webserver nginx:v1_test
docker image ls //会列出新建的nginx:v1_test镜像
docker history nginx:v1_test //列出nginx:v1_test的修改历史
docker run --name web2 -d -p 81:80 nginx:v1_test//运行新建的image
```
* docker commit 意味着所有对镜像的操作都是黑箱操作， 生成的镜像也被称为黑箱镜像， 换句话说， 
就是除了制作镜像的人知道执行过什么命令、 怎么生成的镜像， 别人根本无从得知，而且多次使用commit命令回事image很臃肿

#### 安装配置gitlab实例
  * 在cmd中执行docker search gitlab,在hub仓库中查询gitlab的镜像
  * docker pull gitlab/gitlab-ce
  * docker run --name gitlab  -p 443:443 -p 80:80 -p 22:22 gitlab/gitlab-ce
  * 启动时间较长，需要等待
  * 启动后root的密码需要重新修改
    * 启动docker gitlab
    * 进入gitlab bash控制界面
    * 执行gitlab-rails console，需要等待片刻
    * 执行user = User.where(username:'root').first
    * 执行user.password = 'passwd'
    * 执行user.save!
  * 通过访问http://localhost进入gitlab后台控制台
#### 使用Dockerfile制作镜像
1. 新建一个文件Dockerfile
2. 在其中编写脚本
```dockerfile
FROM nginx
RUN echo '<h1>hello docker.</h1>' > /usr/share/nginx/html/index.html
```

* FROM 是必须的，就是依赖，但是如果是空白依赖的话可以使 `FROM scratch`，这样接下来所写的指令将会镜像的第一层存在
* 直接使用`FROM scratch`可能会直接把可运行文件直接复制进入镜像中，可以减小image的大小，`Golang`就是采用这种方式，所以会成为微服务的首选语言
* `RUN`命令执行
  * shell格式，RUN <命令> ,RUN echo '<h1>hello world</h1>' > /usr/share/nginx/html/index.html
  * exec格式:RUN\["可执行文件","参数1","参数2"\]
* 每一行指令会创建一层，而且image有最大层数限制，
```dockerfile
# 基于debian:jessie 构建dockerfile
# 用 \ 作分隔符 && 作连接符
# 文件最后做了rm等清理工作，image是多层存储，每一层的东西在下一层是不会删除的，为了避免臃肿，需要做清理工作
FROM debian:jessie
RUN buildDeps='gcc libc6-dev make' \
    && apt-get update \
    && apt-get install -y $buildDeps \
    && wget -O redis.tar.gz "http://download.redis.io/releases/redis-3.2.5.tar.gz" \
    && mkdir -p /usr/src/redis \
    && tar -xzf redis.tar.gz -C /usr/src/redis --strip-components=1 \
    && make -C /usr/src/redis \
    && make -C /usr/src/redis install \
    && rm -rf /var/lib/apt/lists/* \
    && rm redis.tar.gz \
    && rm -r /usr/src/redis \
    && apt-get purge -y --auto-remove $buildDeps
```
3. 在Dockerfile所在目录通过命令构建
```text
docker build -t nginx:test_3 .//-t nginx:test_3,是指定名称构建
```
#### 镜像构建上下文(Context)
> Docker 的工作原理是，客户端通过REST API（Docker remote API） 请求 Docker Engine，虽然是在本地使用命令模式与docker交互
>也是，通过这种C/S模式与Docker engine (守护进程)交互
```text
docker build -t nginx:test_3 . //最后有个"."，是指示Context所在位置，在构建执行的时候，第一步是把context内的所有文件send到daemon进程中
```
* "."表示在客户端的当前路径为context，

### Dockerfile 命令
#### COPY
> 从构建上下文目录中 <源路径> 的文件/目录复制到新的一层的镜像内的 <目标路径> 位置。
* shell方式：COPY <源路径> .. <目标路径>
* exec方式：COPY \["原路径1",... "目标路径"\]
* 源路径可以使通配符，也可以是多个路径
* 目标路径可以是绝对路径，也可以是`工作目录`的相对路径，目标路径不需要提前建立，会在复制文件之前先行建立缺失文件
* 工作目录可以通过`WORKDIR`来指定
* COPY会保留源文件元数据，包括权限

#### ADD
> 与COPY一样从上下文中把文件/目录复制到新的一层镜像内的目标路径，但是仅在需要自动解压的时候推荐使用这个命令

#### CMD
> 用于指定默认的容器主进程的启动命令的。
* shell 格式： CMD <命令>
*  exec 格式： CMD \["可执行文件", "参数1", "参数2"..\]

### 操作容器
#### 启动,停止，删除
1. docker run nginx:v_test 
2. docker run -t -i nginx:v_test//-t 分配伪终端，-i是交互式
3. docker run ubuntu:17.10 /bin/sh -c "xxx"
4. docker container start container_name //启动一个已经存在的容器
5. docker container stop container_id //关闭一个已经启动的容器
6. docker container restart container_id // 重启一个容器
7. docker rm container_id,//删除容器
#### docker 连接到已经启动的container
> docker exec -it container_id /bin/bash //交互式连接到容器里面

#### 日志查看
1. 查看容器运行日志
> docker container logs container_id 