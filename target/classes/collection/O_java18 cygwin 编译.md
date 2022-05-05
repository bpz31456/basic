## java18 cygwin 编译
> cygwin是在windows系统上运行的linux环境的软件，方便一些需用linux环境编译的程序使用，如编译windows版本的jdk
1. 下载cygwin软件[cygiwin](http://www.cygwin.com/install.html)，下载安装文件名为setup-x86_64.exe
2. 全部下一步安装
3. [openjdk](https://github.com/openjdk)路径中查找Repositories中选定版本如（jdk18u）
4. git clone https://github.com/openjdk/jdk18u.git
5. 打开源码文件中的README，打开doc/building.md，查看编译须知
6. 通过上文编译文件须知，通过cygwin安装需要安装一些软件
7. 跳转到第一步中的setup-x86_64.exe所在目录，执行命令setup-x86_64.exe -q -P autoconf -P make -P unzip -P zip
8. 通过cygwin窗口跳转到/cygdrive/e/java18u源码所在位置执行编译命令
9. 执行 bash configure ，命令会构建configure文件，但是如果机器上缺少Microsoft Visual Studio（用来编译构建的工具），就会保存
### 须知
* 通过cygwin图标进入窗口化后，计算机上的c、d、e盘等都会挂载到/cygdrive中
* 在windows系统上编译jdk需要使用到visual studio来编译源码中的c/c++代码
* 在使用cygwin软件通过bash configure构建配置文件时，会因为包含空格命名而出现很多问题（visual studio中安装的c++ sdk目录中有空格），暂时没有解决