## linux
### 计算机概论
* 冯诺依曼体系，五大部分
  * 输入
  * 输出
  * CPU
    * 算术逻辑单元
    * 控制单元
  * 存储
    * 内存（main memory）
    * 外存（辅助存储）
* CPU架构，内部由一些微指令集
  * 精简指令集（RISC），执行时间短，效率高，任务单一，执行复制任务，需要多个指令配合执行，
  包括ARM CUP（常见路由器、手机、平板灯）、IMB PowerPC（PowerPC cell处理器）、Oracle SPARC（学术领域大型工作站，金融行业服务器）
  * 复制指令集（CISC），指令多，复杂，执行时间长，AMD，Intel，VIA等X86架构（个人电脑，微型计算机），新的X86CPU能够加速多媒体运行等
  ，加强虚拟化，减少能耗等
* 硬件设备
  * 主板（早期划分）
    * 南桥，控制速度较慢的组件，打印机，声卡，USB，网卡等距离CPU较远
    * 北桥，控制速度运算较快的CPU，内存，显卡等，与CPU距离较近，前期CPU读取内存信息需要借助北桥带宽，比较浪费，现在把内存控制放到了
    CPU中（多级缓存），CPU读取缓存数据就不需要借助北桥带宽
### LINUX 系统
#### LINUX 系统历史
* 内核，shell
* 不同的CPU架构由不同的操作系统控制，2006年以前，windows（x86 cpu架构）无法安装到mac电脑（power cpu架构）上，2006年之后mac电脑是x86架构
* linux系统有各种变种，支持x86和power cpu
* UNIX变体BSD，sun公司由BSD商用而发展，FreeBSD有BSD的x86架构改编而来，早期BSD不支持家用电脑（硬件厂商不生成家用电脑的操作系统适配硬件）
* AT&T的System V 由Unix发展而来
* 纯粹的UNIX操作系统只有System V 和BSD
* GNU计划和FSF基金会（Richard Mathew Stallman 人名）
  * GNU（GNU's Not Unix）计划，建立一个自由、开放源码的操作系统（Free Unix未实现），为了实现统一编译（C语言编译器版本众多都是商用），用C语言实现了GNU C Compiler（gcc），
  * 实现gcc之前，为方便开发，实现了Emacs程序编辑器（代码纠错等功能），
  * gcc实现之后，成立了自由软件基金会（FSF，Free Software Foundation），之后实现了GCC C library（glibc），以及控制操作系统的BASH shell
  * GNU通用公共许可（GPL）,使用者可以自由的执行、复制、再发行、学习、修改与强化自由软件，回馈，不能单纯销售，不能修改GPL许可
  可以贩卖软件以及配套服务（需要人工等资源）和相关手册，自由软件的商用途径都是出售服务，软件都可以下载使用
* LINUX基于gcc bash等GNU工具编写而来运行在Intel 386上
* LINUX数据GPLv2授权
* 开源软件（Open source）不同于GPL授权软件，GPL授权软件，只要使用部分或全部都收到GPL限制，开源软件则不同相对GPL开源软件更加开放
  * Apache License 2.0
  * BSD 3-Clause "New" or "Revised" license
  * BSD 2-Clause "Simplified" or "FreeBSD" license
  * GNU General Public License (GPL)
  * GNU Library or "Lesser" General Public License (LGPL)
  * MIT license
  * Mozilla Public License 2.0
  * Common Development and Distribution License
  * 商用这使用BSD，限制商用用GPLv2
* 专属软件（close source）
  * 只提供二进制执行文件
  * Freeware，来路不明的软件
  * Shareware，提供试用
* LINUX，早期为了兼容UNix，参照了POSIX规范（Portable Operating System Interface，由美国电器与电子工程师学会(IEEE)所发布的一项标准），修改LINUX
* LINUX发展，早期主要是硬件驱动需要集成到kernel上，先求有且能跑， 再求进一步改良
* LINUX，早期只支持386，后来有缘Open source缘故，有了其他cpu能够支持的版本
#### LINUX系统发展
* 版本（核心版本）
  * 3.10.0-123.el7.x86_64（朱版本.次版本.编译版本-修改版本）
    * 第一个组数字：3, 主版本号
    *  第二个组数字：10, 次版本号，当前为稳定版本，一般这个数字为偶数表示稳定，奇数表示在开发版本，通常这样的不做生产使用。
    *  第三个组数字：0, 发行版本
    *  第四个组数字：1127.19.1，修订版本
    *  el7：则表示我正在使用的内核是 RedHat / CentOS 系列发行版专用内核 ，centos7
    *  x86_64：采用的是适用64位的CPU的操作系统。
  * 3.0.x 之后主线版本、长期维护版本(longterm version)
  * patch，补丁
  * tarball，源码
  * Mainline：Mainline 主线树由 Linus Torvalds 维护。这个版本的内核会引入所有新功能。新的 Mainline 内核每 2-3 个月发布一次。
  * Stable：每个主线内核被发布后，即被认为是“stable”。任何对 stable 内核的 BUG 修复都会从 Mainline 主线树中回溯并由指定的 
  stable 内核维护人员使用。 stable 内核更新按需发布，通常每月 2-3 次。
  * Longterm：通常会提供几个“longterm maintenance”内核版本，用于修复旧版内核的 BUG。这些内核只会修复重大 BUG，并且不会频繁发布版本。
  * Linux distribution，Kernel + Softwares + Tools + 可完整安装程序
* 核心版本，发行商版本
  * RPM方式安装软体的系统，包括Red Hat, Fedora, SuSE
  * Debian的dpkg方式安装软体的系统，包括Debian, Ubuntu, B2D
* 关键是会得『偷』， 偷了会得改，改了会得变，变则通矣  
### linux系统命令
> 命令有两种模式，一种是直接计算出结构返回，一种是进入命令内部，进行操作，然后通过其内部的命令退出
#### 命令基础
* \[bpz@VM-0-9-centos ~\]$，\[当前用户名@计算机名称 当前所在目录名称\]，$一般用户,#root用户
  * set 命令，列出变量（环境变量、用户自定义变量、bash操作内置变量）
    *  PS1='[\u@\h \W]\$ ' 环境变量格式定义的上述名称
* exit，退出当前登录
* 登录其实是进入了shell
* 命令格式，command \[-options\] parameter1 parameter2 ...
  * command 可能是一个命令或一个批处理可执行文件
  * options 简单的是带一个-号，全称是两个--，有时候会带一个+
  * 指令，选项，参数之间的空格不论空几格，都视为一格
  * 指令太长，可以使用反斜线 \ 加 enter键来跳转到下一行
* linux 中大小写敏感
* date 当前时间,date \[OPTION\] +\[FORMAT\]
  * date +'%Y/%m/%d %H:%M:%S',
* locale 显示支持的语系
  * LANG=en_US.utf8，表示当前显示所设置的语系
  * /etc/locale.conf
  * locale的优先级,LC_all > LC_* >lang
* LANG=en_US.utf8,设置显示的语系为en_US.utf8
* export LC_ALL=en_US.utf8,更新LC_ALL语系为en_US.utf8
* cal，查看日历，
  * cal 23 10 2021,查询2021年10月23日
* bc ，简单计算器，+-*/%^，默认整数计算
  * -l，使用标准的数学库，如4*a(1) = PI
  * -q,Do not print the normal GNU bc welcome
  * scale=3，设置小数点后保留几位
  * quit，退出
  * echo "scale=2;4*((3+12)/3)+1.2"|bc -lq，通过命令的方式传递输入信息
* tab 键，命令自动补全，文档自动补全
* ctrl + c 组合键，中断当前程序
* ctrl + d 组合键，表示键盘数据结束或取代exit命令输入
* shift + upPage|downPage ,向上或下翻页查询屏幕内容
#### 文档说明
* command --help,查找简要说明文档
  * 简要说明文档
* man command ,查询详细说明文档，如man date
  * DATE(1) ,(1)，使用者在shell环境可以操作的指令和可执行文档
  * (2),系统核心可呼叫的函数或工具
  * (3),一些常用函数和库函数，大部分是C的库函数libc
  * (4),设备档案的说明，通常在/dev下面
  * (5),配置文件或某些文件的格式
  * (6),游戏
  * (7),惯例与协议等，如Linux文件系统、网络协议、ASCII代码说明
  * (8),系统管理员可用的管理命令，如ifconfg
  * (9),跟内核有关的文件
  * 关键字查找 如：/vberd 向下查找 或 ?vberd 向上查找 ，可以按n , N 向上向下查找
  * man 命令使用的文档在 /usr/share/man 中，这个地址可以在/etc/man_db.conf中修改
  * man -f command ,查找command的关联文档，可能存在多个说明文档与这个command关联
    * man 1 command,查找1号标注的说明文档
    * whatis 与 man -f command 等同
  * man -k key,按照关键字查找包含key的说明文档
    * apropos 与 man -k key 等同 
  * whatis 与 apropos的使用需要使用mandb(8)构建说明文档
  * 查询反斜杠的内容，/escape或?escape 按 n/N向下翻页，向上翻页
* info page,再linux中线上查询文档的命令，查询的命令的说明文档需要写为info格式，存放位置/usr/share/info
  * 进入info界面后，根据第一行提示，U，上一层，N，下一个节点，P，上一个节点，M，菜单（如果有）
    * 如，按下M后，Menu item: 输入菜单的名称（tab键可以补全）
    * 如，File: coreutils.info,  Node: date invocation,  Next: arch invocation,  Up: System context，根据提示来
    大写字母为可以按的导航
* 其他文档，主要是一些软件安装时的一些说明文档，主要放在/usr/share/doc中每个文件夹中都是一个软件的说明
  * 如：more /usr/share/doc/rpm-4.11.3/format,打开rpm的说明文档
#### 基础命令
1. 观察系统使用状态，系统是多人同时使用的，不建议强制关机（关电源）
  * who,目前还有谁在线
  * netstat -a，网络状态
  * ps -aux，后台应用程序的执行情况
2. 通知在线人员需要关机的信息，给别人处理后续任务的时间
  * shutdown
3. 关机指令
  * shutdown/reboot
* 资料同步指令，内存中的信息同步到磁盘上，sync
  * shutdown/reboot/halt，指令在执行之前都自动执行了sync指令
* su -
  * 回到root用户
* shutdown \[hrkc\]\[时间\]\[警告信息\]，默认1分钟时间
  * shutdown -h 10 '系统更新需要关机10分钟后重启，各位请保存好自己的信息'
  * 如果使用远端连接到linux，需要root才能执行
  * 如果本地执行使用tty1~tty7上的所有用户都能执行
  * -h 系统关闭任务后，再关机
  * -r 停掉服务后重启
  * -k 发送关机警告信息
  * -c 终止之前设置的关机命令（因为可以设置关机时间，所以可以中断之前的关机任务）
  * 自定义关机信息，发送到其他的user
* 其他关机重启命令,reboot,halt,poweroff
  * 都是去调用的systemctl的管理命令
* systemctl,所有的服务管理都用这个命令
  * systemctl reboot
  * systemctl poweroff 
* 查询系统核心版本
  * uname -a
  * cat /proc/version
* 查询发行版本信息
  * cat /etc/centos-release
  *  rpm -q centos-release
### 群组与使用者
#### 权限基础
> 权限控制，群组，使用者，其他，root不受权限限制，权限管控可以用作系统安全保护，共享资料等
* 群组，使用者，密码所在文件
  * /etc/passwd，所有用户记录的位置
  * /etc/shadow，保存密码的位置
  * /etc/group，保存群组的位置
* 么有权限会提示，Permission deny
* ls ，不支持正则表达式，只支持通配符
  * ls -l,列表形式查看文档
    * 显示的文档类型的大小都是1024的倍数，因为每个data block大小是1k,2k,4k，中的一种，所以文档内总大小都是1024b的倍数
  * ls -a,查看所有文件，包括隐藏文件（文件名以『 . 』开始）
  * ls -lh,按照人类可读的方式显示文件大小
    * du -sh,查看目录占用磁盘空间大小
  * ls -i，查看inode号码
  * drwxrwxr-x 2 bpz  bpz      4096 Jan 25 11:09 pic
  * 权限  链接数（指向同一个inode节点的链接数量，hard link） 拥有者 群组 容量 创建时间 文件名
  * date; ls -l read ;ls -l --time=atime read ;ls -l --time=ctime read
    * --time=atime ,读取时间
    * --time=ctime,状态发生改变时
    * --time=mtime,修改内容时
  * access time(atime), status time (ctime), modification time(mtime)，ls 预设显示的是mtime
    * access time  ， atime在读取文件或执行文件时会修改
    * create time  ， ctime在文件写入，更改所有者，权限。链接时文件的ctime会随之改变
    * modified time ，mtime 在文件写入时会改变。

* 权限，drwxrwxr-x
  * d,文档类型
    * d,文件夹
    * -，普通文件
    * l，链接
    * b，随机存储设备，U盘
    * c，键盘，鼠标等设备
  * rwx,文档拥有者的可读，可写，可执行
  * rwx，文档所属群组可读，可写，可执行
  * r-x，其他用户可读，不可写，可执行
  * 目录的权限，作用在改目录里面的文档列表权限
    * r，表示具有读取目录结构清单的权限，所以当你具有读取(r)一个目录的权限时，表示你可以查询该目录下的档名名称，
    其他权限符号全部为"?"，因为没有x。
      * 如：d????????? ? ? ? ?            ? test2
    * w， 因为他表示你具有修改该目录结构内文件或目录的权限
      * 建立新的档案与目录；
      * 删除已经存在的档案与目录(不论该档案的权限为何！)
      * 将已存在的档案或目录进行更名；
      * 搬移该目录内的档案、目录位置。
    * x，目录的x代表的是使用者能否进入该目录成为“工作目录”的用途
    * 是否可以删除某个文件，需要看该文件所在目录的权限是否有w权限
    * 目录的最小权限是x，可以进去，就算没有r权限，也可以对里面的文件进行操作，没有r，不知道里面的文件，但是如果知道有什么文件，
    就可以根据该文件的相关权限操作
* 链接数
  * 每个档案都会将他的权限与属性记录到档案系统的i-node
  * 我们使用的目录树却是使用档名来记录，每个文档名都会链接到一个i-node
  * 有几个文档名链接到同一个i-node，显示在这个链接数上
* 容量，Bytes
* 修改日期
  * 默认显示，日和月，如果太久只能显示年，--full-time，显示全部时间
#### 修改权限
* 改变档案所属群组
  * chgrp \[OPTION\]... GROUP FILE... 
    * OPTION,R,递归修改文档所属群组
    * 修改文档所属群组，群组必须村组/etc/group中
* 改变档案拥有者
  * chown \[OPTION]... \[OWNER\]\[:\[GROUP\]\] FILE...
  * 修改文档所属用户，用户必须存在/etc/passwd
* 什么时候使用chown和chgrp，文档复制时需要执行
  * cp，执行时，新文件会以当前执行者的权限赋值给新文件
  * 然后通过chown和chgrp修改文件所属和群组所属
* 改变档案的权限, SUID, SGID, SBIT等等的特性
  * chmod OPTION MODE\[,MODE\] FILE
    * 数字权限模式，4=r，2=w，1=x
      * chmodd 777 xx.file 修改文件权限为所有人都是可读可写可执行
      * 数字授权可以添加特殊权限SUID SGID SBIT,分别代表4，2，1
        * 如：chmod 4777 xx.file,授权SUID权限
        * chmod 6777 xx.file,授权SUID和SGID
    * 符号权限模式，chmod \[ugoa\] \[+-=\] \[rwx\] FILE
#### 文档种类
* 正规文档（regular file），-类型
  * 纯文字（ASCII），可以cat filename的文件
  * 二进制文档（binary），可执行并且不是scripts（bash脚本文件是纯文本文件）文件，如：cat本身
  * 资料文档（data），属于一种特殊格式的档案，用cat无法打开（打开是乱码），需要专门的工具打开，如last读取/var/log/wtmp文件
* 目录（directory），d
* 链接文档（link），l
* 设备文档（device）
  * b（block），区块设备，属于硬盘设备等存储介质文档，如：ll /dev/
    * brw-rw---- 1 root disk    253,   1 Sep  4 15:38 vda1
  * c（character），字符设备，键盘、滑鼠等等！这些设备的特色就是『一次性读取』的，不能够截断输出，如：ll /dev/
    * crw------- 1 root root     10,  63 Sep  4 15:38 vga_arbiter
* 数据接口文档(Sockets),通常被用在网路上的资料承接,s，如：ll /run
  * srw-rw-rw-  1 root  root              0 Sep  4 15:38 acpid.socket
* 数据输送档(FIFO, pipe)，p
  * 他主要的目的在解决多个程序同时存取一个档案所造成的错误问题，管道
* 文件扩展名，只是让你了解该档案可能的用途而已，没有其他意义
  * 文件是否可执行与权限x有关，
  * *.sh 脚本语言
  * *.Z,*.tar,*.tar.gz,*.zip,*.tgz,压缩文件
  * *.html，*.php网页文件
* 文件名最大128个中文 （255bytes）
* 文件命名避免特殊符号* ? > < ; & ! [ ] | \ ' " ` ( ) { } - +
#### linux 结构目录
> Linux目录配置的依据--FHS
* 主要目的是希望让使用者可以了解到已安装软体通常放置于那个目录下,FHS的重点在于规范每个特定的目录下应该要放置什么样子的资料
* FHS针对目录树架构仅定义出三层目录底下应该放置什么资料而已
  * / (root, 根目录)：与开机系统有关；
  * /usr (unix software resource)：与软体安装/执行有关；
  * /var (variable)：与系统运作过程有关。
* FHS 四种交互形态
  * 可分享的：可以分享给其他系统挂载使用的目录，所以包括执行档与使用者的邮件等资料， 
  是能够分享给网路上其他主机挂载用的目录；
    * /usr (软体放置处)
    * /opt (第三方协力软体)
    * /var/mail (使用者邮件信箱)
    * /var/spool/news (新闻群组)
  * 不可分享的：自己机器上面运作的装置档案或者是与程序有关的socket档案等，
  由于仅与自身机器有关，所以当然就不适合分享给其他主机了。
    * /etc (设定档)
    * /boot (开机与核心档)
    * /var/run (程序相关)
    * /var/lock (程序相关)
  * 不变的：有些资料是不会经常变动的，跟随着distribution而不变动。
    * /usr (软体放置处)
    * /opt (第三方协力软体)
    * /etc (设定档)
    * /boot (开机与核心档)
  例如函式库、文件说明档、系统管理员所管理的主机服务设定档等等；
  * 可变动的：经常改变的资料，例如登录档、一般用户可自行收受的新闻群组等。
    * /var/mail (使用者邮件信箱)
    * /var/spool/news (新闻群组)
    * /var/run (程序相关)
    * /var/lock (程序相关)
* 根目录(/)
  * 根目录(/)所在分割槽应该越小越好， 且应用程式所安装的软体最好不要与根目录放在同一个分割槽内，根目录所在的档案系统也较不容易发生问题。
    * 所有的目录都是由根目录衍生出来的，同时根目录也与开机/还原/系统修复等动作有关
  * /bin，系统执行文件放置，单人维护系统时也能够执行的指令，root和一般账户都能执行的指令，cat,chmod,mkdir,cd等
  * /boot，开机引导时使用的，Linux核心档案以及开机选单与开机所需设定档，linux kernel文件：vmlinuz，grab2启动项/boot/grab2
    * Linux系统启动时使用initramfs (initram file system), initramfs可以在启动早期提供一个用户态环境，借助它可以完成一些内核在启动阶段不易完成的工作。
  * /dev,linux设备，字符设备，块设备，伪设备
    * /dev/null, /dev/zero, /dev/tty , /dev/loop*, /dev/sd*,/dev/console
     * /dev/null,在类Unix系统中，/dev/null，或称空设备，是一个特殊的设备文件，它丢弃一切写入其中的数据（但报告写入操作成功），读取它则会立即得到一个EOF。
       * 通常被用于丢弃不需要的输出流，或作为用于输入流的空文件
       * cat $filename 2>/dev/null >/dev/null,没有输出，只测试程序是否能正常运行
       * cat /dev/null > /var/log/messages，清空日志
     * /dev/zero,当你读它的时候，它会提供无限的空字符(NULL, ASCII NUL, 0x00),
       * /dev/zero主要的用处是用来创建一个指定长度用于初始化的空文件，像临时交换文件。
     * /dev/tty*,/dev/console,控制台终端
  * /etc，系统主要的配置文件放置位置
    * FHS建议不要放置可执行档(binary)在这个目录中喔
    * /etc/modprobe.d/, /etc/passwd, /etc/fstab, /etc/issue
    * /etc/opt(必要)：这个目录在放置第三方协力软体/opt 的相关设定档
  * /lib，/lib放置的则是在开机时会用到的函式库， 以及在/bin或/sbin底下的指令会呼叫的函式库而已
    * /lib/modules/：这个目录主要放置可抽换式的核心相关模组(驱动程式)
  * /media，/media底下放置的就是可移除的装置，软盘、光盘、DVD等等装置都暂时挂载
    * /media/floppy, /media/cdrom
  * /mnt，想要暂时挂载某些额外的装置，一般建议妳可以放置到这个目录中
  * /opt
    * 第三方软件安装的位置，以前的Linux系统中，我们还是习惯放置在/usr/local目录下
  * /run，系统运行时信息，早期的FHS /var/run 目录下，新版的FHS 则规范到/run，有内存映射而来，效率更高
  * /sbin，设定系统环境，只能root使用，放在/sbin底下的为开机过程中所需要的，里面包括了开机、修复、还原系统所需要的指令
    * 服务器软件程序放在,/usr/sbin
    * 本机自行安装的软体所产生可执行文件，/usr/local/sbin/
    * fdisk, fsck, ifconfig, mkfs
  * /srv，service缩写，网路服务启动之后，这些服务所需要取用的资料目录，WWW, FTP
    * WWW伺服器需要的网页资料就可以放置在/srv/www/
  * /tmp，一般使用者或者是正在执行的程序暂时放置档案的地方。这个目录是任何人都能够存取的，所以你需要定期的清理一下
    * HFS,建议在开机的时候清空
  * /usr(Unix soft resource)，系统安装时占用较大空间
    * 系统默认安装程序路径,类似windows的 C:/Program files
    * /usr/bin/,所有一般用户的指令放置的位置，centos7之后，这个目录映射到了/bin
    * /usr/lib,centos7后，目录映射到了/lib
    * /usr/local,自己安装软件建议安装在这里，方便管理
    * /usr/sbin,与/sbin映射
    * /usr/share,放置只读文件，默认都是文本文件
      * /usr/share/man,线上说明文档
      * /usr/share/doc,软件杂项说明
    * /usr/games/，游戏相关
    * /usr/include/，c/c++头文件位置，使用tarball编译生产的软件（*.tar.gz），使用的文件
    * /usr/libexec/，不常用的脚本文件，如x window指令
    * /usr/lib<qual>/，与/lib<qual> 映射
    * /usr/src/，源码放置位置
  * /var，系统运行时占用较大空间，缓存，日志，mysql数据文件等经常变动的文件
    * /var/cache，应用程序的产生的缓存
    * /var/lib,程序执行的时候用的文件，如mysql,/var/lib/mysql
    * /var/lock,程序独占时加锁的文件
    * /var/log，日志文件，如/var/log/message,/var/log/wtmp登录时候的日志文件
    * /var/mail,邮件所在地方，与/var/spool/mail关联
    * /var/run,与/run类似，两者相关联，存放pid
    * /var/spool,队列数据，如：待发送邮件数据/var/spool/mail，执行计划(crontab)/var/spool/cron
  * /home，系统预设的使用者家目录(home directory)。
    * ~，当前用户的家目录
    * ~bpz，bpz用户的家目录
  * /lib<qual>，存放与/lib 不同的格式的二进位函式库，例如支援64 位元的/lib64 函式库等
    * 如：/lib64
  * /root，系统管理员(root)的家目录
    * 进入单人维护模式而仅挂载根目录时，root的家目录与根目录放置在同一个分割槽
  * /lost+found，使用标准ext2/ext3/ext4，系统发生错误的时候，将遗失的片段放在这个位置，使用xfs就不会有这个文件
  * /proc，虚拟档案系统（virtual filesystem），在内存中的映射，系统核心，process，网络状态等
    * /proc/cpuinfo, /proc/dma, /proc/interrupts, /proc/ioports, /proc/net/*
  * /sys，与/proc非常类似
* 目录树
  * 都开始于/
  * 每个目录可以与分区系统关联也可以是网络上的文件系统如，Network File System (NFS) 挂载到特定目录上
  * 每个文档都是独一无二的
  * 本目录.,上级目录..，在本目录执行可执行文件./run.sh
* LSB标准（Linux Standard Base）
### linux档案和目录管理
* 相对路径和绝对路径
  * 编写脚本是建议使用绝对路径
* 路径操作
  * . 当前路径
  * .. 上一级路径
  * - 前一个操作路径
  * ~ 当前用户主目录
  * ~bpz 用户bpz的主目录
  * /路径下的.和..都代表当前路径
#### 文件目录操作命令
* cd，(change directory)
  * cd 代表cd ~
* pwd（Print Working Directory），显示当前所在工作目录
  * pwd -P 显示真实路径，消除符号链接
* mkdir（make directory）
  * mkdir -p /home/bpz/test1/test2，递归建立父子目录
  * mkdir -m 755 /home/bpz/test,带权限建立目录
* rmdir，删除空目录
  * rmdir -p /home/bpz/test1/test2/test3,递归删除依次的空目录，如果遇到某一目录非空，则停止删除
  * rm -r ./test1/test2/test3,删除test3，如果里面包含文件也会删除
* ls
  * -a ,查询所有文件，包括隐藏文件
  * -l,列表形式显示
  * -d，仅仅列出目录
  * 在 /目录下，ls -lid . ./ ../ ,三个路径执行同一个inode的值，说明在更目录里，上级目录就是自己
* cp，需要有r权限才能赋值
  * -i,提示是否覆盖
  * -a，连带着文档权限一起赋值
  * -p，连带着文档权限一起赋值
  * -r，递归赋值
  * -l，硬链接，与源文件属性一样
  * -s，符号链接，快捷方式
  * -u，目标文件与源文件存在差异才会复制，用于备份
  * 复制slink文件时候，会直接赋值原始文件
  * -d，复制slink文件时，会复制slink链接
  * cp sour1 sour2 dis/同时将多个文件复制到一个文件夹中
* rm
  * f,强制删除
  * i，删除前提示
  * r，递归删除
  * 删除带-的文件，rm ./-aaa-
* mv,移动或更名
  * f，强制覆盖
  * i，覆盖会提示
  * u，更新
* 获取文件名和目录名
  * basename，获取文件的基础名称
  * dirname，获取文件所在目录的名称
#### 环境变量$PATH
* $PATH
  * echo $PATH
  * /usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin，有顺序的
  * PATH="${PATH}:/bpz/bin",添加环境变量
  * 为了安全，不建议把.添加到$PATH中
  * 不同身份使用者预设的PATH不同，预设能够随意执行的指令也不同
  * 使用绝对路径或相对路径直接指定某个指令的档名来执行，会比搜寻PATH来的正确，PATH有顺序查找
#### 查看文件内容
* cat
  * -n ,显示行号，空白行有行号
  * -b，显示行号，空白行没有行号
  * -A，会显示特殊符号，如：回车($)，tab(^I)等
* tac
  * 与cat相反，反向现实最后一行先显示
* nl，添加行号显示内容，行号可以自动补位，默认6位
  * -ba,空行也显示行号
  * -bt,空行不显示，预设值
  * -n ln :行号在左边
  * -n rn : 行号在右边
* more，翻页查询，只能向下翻页
  * :f,显示文档文档名
  * 空格，翻页
  * enter，下一行
  * d，下翻页
* less，一页一页翻看，可以向后翻页
  * 与more类似操作，提供/ ?查找
  * -N ,显示行号
  * b，上翻一页
  * d，向下翻页
* head，获取头几行
  * -n num,num可以正负数，
* tail，获取末尾几行
  * -n num,num可以正负数
* 获取第11到20行的数据，显示原始行号
  * cat -n filename|head -n 20 |tail -n 10
* od,非纯文档打开方式，以多进位打开
  *  //
* touch ,修改文档时间，新建文件
  * -a ,修改访问时间
  * -c ，修改建立时间
  * -m ，修改更新时间
#### 文档权限相关
* umask,默认文档或目录的权限
  * -S,用符号的方式显示默认权限
  * 默认最大权限，文件，-rw-rw-rw-，默认没有x可执行权限
  * 默认最大权限，目录, -drwxrwxrwx
  * 查询的值为需要向减的值
  * 如查询结果是0022
    * 第一位0是特殊权限，SUID, SGID, SBIT，分别代表4，2，1
    * 后三位0，表示当前用户新建文件为-rw-r--r--,目录为drwxr-xr-x
  * 设置遮掩码值，umask 022
* 文档隐藏属性设置chattr，在ext2/ext3/ext4文档系统中有隐藏属性
  * chattr \[+-=\]\[ASacdistu\] 档案或目录名称
  * i,不能被删除、改名、设定连结也无法写入或新增资料！ 对于系统安全性有相当大的助益！只有root 能设定此属性
  * a,当设定a 之后，这个档案将只能增加资料，而不能删除也不能修改资料，只有root 才能设定这属性
  * 
* 文档隐藏属性设置查看lsattr
  * lsattr \[-adR\] 档案或目录
  * 
* 档案特殊权限： SUID, SGID, SBIT
  * SUID，s权限在x位置上时候，临时获取该文件所有者（owner）的权限
    * ls -l /usr/bin/passwd 
    * -rwsr-xr-x 1 root root 27856 Apr  1  2020 /usr/bin/passwd
    * SUID 权限仅对二进位程式(binary program)有效， 不能够用在shell script 上面，目录也无效
    * 执行者对于该程式需要具有x 的可执行权限；
    * 本权限仅在执行该程式的过程中有效(run-time)；
    * 执行者将具有该程式拥有者(owner) 的权限。
    * 如：修改密码使用的passwd命令
      * dmtsai 对于/usr/bin/passwd 这个程式来说是具有x 权限的，表示dmtsai 能执行passwd；
      * passwd 的拥有者是root 这个帐号；
      * dmtsai 执行passwd 的过程中，会『暂时』获得root 的权限；
      * /etc/shadow 就可以被dmtsai 所执行的passwd 所修改。
  * SGID ，s 在群组的x上，临时获取改文件所属群的权限
    * ls -l /usr/bin/locate 
    * -rwx--s--x 1 root slocate 40520 Apr 11  2018 /usr/bin/locate
    * SGID 对二进位程式有用；
    * 程式执行者对于该程式来说，需具备x 的权限；
    * 执行者在执行的过程中将会获得该程式群组的支援！
      * 使用者若对于此目录具有r 与x 的权限时，该使用者能够进入此目录；
      * 使用者在此目录下的有效群组(effective group)将会变成该目录的群组；
      * 用途：若使用者在此目录下具有w 的权限(可以新建档案)，则使用者所建立的新档案，该新档案的群组与此目录的群组相同
  * Sticky Bit，SBIT 目前只针对目录有效，t
    * ls -ld /tmp ; 
    * drwxrwxrwt. 15 root root 4096 Feb 11 16:45 /tmp
    * 当使用者对于此目录具有w, x 权限，亦即具有写入的权限时；
    * 当使用者在该目录下建立档案或目录时，仅有自己与root 才有权力删除该档案
  * 查看特殊文档位置,只要包含三种特殊权限中某一个就可以
    * find /bin -perm /7000,7=4(SUID)+2(GUID)+1(SBIT)
#### 文档查找
* file ,查询文件类型，查询tar文件压缩类型时很有用（用特定的解压程序）
  * file ~/.bashrc      
* which 查询命令（PATH中查找binary命令）
  * -a，找到PATH中的所有符合要求的命令
  * which history 指令无法找到，因为which是查找PATH中的命令，history是属于bash内建指令所以查询不到，可以用type查询
    * type history，区分命令是内部命令还是外部命令
      * history is a shell builtin
* stat filename，查看文件详情
  * stat create_file.sh
* whereis，在特定目录中查找文件
  * whereis \[-bmsu\] 文件名
  * -l ,列出whereis搜索的特殊文件目录位置
  * -b，找二进制文件
  * -s，源文件中找
  * -m，man文档
  * -u ，其他类型文件
* locate/updatedb，在内建资料库中查找结果，不精准，内建库每天更新一次
  * locate \[-ir\] keyword
  * -i，忽略大小写
  * -r，后面可接正规表示法的显示方式
  * -l，仅输出几行的意思，例如输出五行则是-l 5
  * -c，输出查找到的文件个数
  * updatedb：根据/etc/updatedb.conf 的设定去搜寻系统硬碟内的档名
  * 配合ls查询文档的权限
    * ls -ld $(locate crontab),查询crontab内容中配置的权限列表
* find，强大的搜索工具，很费硬盘
  * find PATH option action
  * 与时间有关的选项
    * -mtime n ：n 为数字，意义为在n 天之前的『一天之内』被更动过内容的档案；
    * -mtime +n ：列出在n 天之前(不含n 天本身)被更动过内容的档案档名；
    * -mtime -n ：列出在n 天之内(含n 天本身)被更动过内容的档案档名。
    * -newer file ：file 为一个存在的档案，列出比file 还要新的档案档名
    * +4代表大于等于5天前的档名：ex> find /var -mtime +4
    * -4代表小于等于4天内的档案档名：ex> find /var -mtime -4
    * 4则是代表4-5那一天的档案档名：ex> find /var -mtime 4
  * 与使用者或群组名称有关的参数
    * find /home -user dmtsai ，搜寻/home 底下属于dmtsai 的档案
      * 利用这个指令将属于某个使用者的所有档案都找出来喔！
    * find / -nouser ，查找不属于任何人的文档
      * 透过这个指令，可以轻易的就找出那些不太正常的档案，有时候是正常的～尤其是你曾经以原始码自行编译软体时
    * find /-nogroup ,查找不属于任何群组的文档
  * 与档案权限及名称有关的参数
    * -name filename：搜寻档案名称为filename 的档案；
    * find / -name "*passwd*"，包含passwd关键字的文档
    * -size \[+-\]SIZE：搜寻比SIZE 还要大(+)或小(-)的档案。这个SIZE 的规格有：
                   c: 代表byte， k: 代表1024bytes。所以，要找比50KB
                   还要大的档案，就是『 -size +50k 』
      * find /etc/ -size +90k -a -size -1500k -a ! -user bpz -exec ls -l --block-size=K -S {} \;//查找不属于bpz用户的大于90k小于1500k的文件
    * -type TYPE ：搜寻档案的类型为TYPE 的，类型主要有：一般正规档案(f), 装置档案(b, c),
                   目录(d), 连结档(l), socket (s), 及FIFO (p) 等属性。
       * find /run -type s ,socket 与FIFO 档案，可以用find /run -type p 或-type s 来找
    * -perm mode ：搜寻档案权限『刚好等于』 mode 的档案，这个mode 为类似chmod
                 的属性值，举例来说， -rwsr-xr-x 的属性为4755 ！
    * -perm -mode ：搜寻档案权限『必须要全部囊括mode 的权限』的档案，举例来说，
                 我们要搜寻-rwxr--r-- ，亦即0744 的档案，使用-perm -0744，
                 当一个档案的权限为-rwsr-xr-x ，亦即4755 时，也会被列出来，
                 因为-rwsr-xr-x 的属性已经囊括了-rwxr--r-- 的属性了。
    * -perm /mode ：搜寻档案权限『包含任一mode 的权限』的档案，举例来说，我们搜寻
                 -rwxr-xr-x ，亦即-perm /755 时，但一个档案属性为-rw-------
                 也会被列出来，因为他有-rw.... 的属性存在！
      * find / -perm /7000 ,搜寻档案当中含有SGID 或SUID 或SBIT 的属性
      * find /usr/bin /usr/sbin -perm /6000,具有SUID 或SGID 就列出来该档案
        * 所谓的7000 就是---s--s--t ，那么只要含有s 或t 的就列出
        * -7000 表示要同时含有---s--s--t 的所有三个权限
        * /7000 表示只要包含一个就可以
   * 额外可进行的动作
     * -exec command ：command 为其他指令，-exec 后面可再接额外的指令来处理搜寻到的结果。
       * find /usr/bin /usr/sbin -perm /7000 -exec ls -l {} \; 那个-exec 后面的ls -l 就是额外的指令，指令不支援命令别名,
       如：ls -l 不可以使用ll 
       * {} 代表的是『由find 找到的内容』，如上图所示，find 的结果会被放置到{} 位置中；
       * -exec 一直到\; 是关键字，代表find 额外动作的开始(-exec) 到结束(\;) ，在这中间的就是find 指令内的额外动作。在本例中就是『 ls -l {} 』啰！
       * 因为『 ; 』在bash 环境下是有特殊意义的，因此利用反斜线来跳脱。
     * -print ：将结果列印到萤幕上，这个动作是预设动作！
   * 其他操作
     * find -inum 279007 -delete，根据inum（inode number） 查找文件并且删除
#### 总结
1. 让使用者能进入某目录成为『可工作目录』的基本权限为何：
  * 可使用的指令：例如cd 等变换工作目录的指令；
  * 目录所需权限：使用者对这个目录至少需要具有x 的权限
  * 额外需求：如果使用者想要在这个目录内利用ls 查阅档名，则使用者对此目录还需要r 的权限。
2. 使用者在某个目录内读取一个档案的基本权限为何？
  * 可使用的指令：例如本章谈到的cat, more, less等等
  * 目录所需权限：使用者对这个目录至少需要具有x 权限；
  * 档案所需权限：使用者对档案至少需要具有r 的权限才行！
3. 让使用者可以修改一个档案的基本权限为何？
  * 可使用的指令：例如nano或未来要介绍的vi编辑器等；
  * 目录所需权限：使用者在该档案所在的目录至少要有x 权限；
  * 档案所需权限：使用者对该档案至少要有r, w 权限
4. 让一个使用者可以建立一个档案的基本权限为何？
  * 目录所需权限：使用者在该目录要具有w,x 的权限，重点在w 啦！
5. 让使用者进入某目录并执行该目录下的某个指令之基本权限为何？
  * 目录所需权限：使用者在该目录至少要有x 的权限；
  * 档案所需权限：使用者在该档案至少需要有x 的权限
### 磁盘与档案系统
#### 磁盘组成
* 磁盘每个扇区512b或4k
* 磁盘分区表分为，MBR方式开机引导区占用446b（Master boot record），分区信息占用64b，放在第一个扇区（512b），
* GPT分区方式，可以容纳2TB容量硬盘
* /dev/sd\[ap\][1-128]：为实体磁碟的磁碟档名
* /dev/vd\[ad\][1-128]：为虚拟磁碟的磁碟档名
* 磁盘分区后，通过格式化（其中有权限/属性区别的不同格式化文档系统），通过不同filesystem
* linux 正式文件系统是ext2，windows中的文件系统（fat，fat16，ntfs）不认识ext2文件系统
* 一个分区可以被格式化为多种文件格式（LVM），多个分区可以合并为一个文件系统（LVM，RAID）
* 一个可被挂载的数据为一个文件系统
* superblock：记录此filesystem 的整体资讯，包括inode/block的总量、使用量、剩余量， 以及档案系统的格式与相关资讯等；
* inode：记录档案的属性（权限），一个档案占用一个inode，同时记录此档案的资料所在的block 号码；
* block：实际记录档案的内容，若档案太大时，会占用多个block 。
* 索引式档案系统（indexed allocation），通过inode记录当前文件的block编号列表，从而找到lock中的数据
* FAT,格式，inode找到一个一个block，然后链表查找后续block
* 磁盘整理，多个block可能不在一个柱面，离散比较厉害，查找需要多次读取（磁头移动），就需要通过磁盘整理把文件的block放到一起，方便读取
#### ext2，索引式文档系统
* 包含多个block groups，每个block group 包含自己的inode/block/superblock
* 在文档系统之前有个开机引导区域（boot sector），可以安装开机管理程序，可以制作多系统
* data block，存放实际的date，支持1k，2k，4k，格式化时会固定大小，每个block有编号，
  * 1k block
    * 最大单体文件大小16G
    * 系统最大容量2TB
  * 2k block
    * 最大单体文件256GB
    * 系统最大容量8TB
  * 4k block
    * 最大单体文件2TB
    * 系统最大容量16TB
  * 一旦格式化，大小就不能更改，除非重新格式化
  * 每个block最大放一个文件
  * 每个文件可以使用多个block
  * 一个block未使用完全，就会被浪费
  * 实际现在的文件都比较大，所以都会选择4k的容量格式化
  * 存放文档名称
* inode table
  * 该档案的存取模式(read/write/excute)；
  * 该档案的拥有者与群组(owner/group)；
  * 该档案的容量；
  * 该档案建立或状态改变的时间(ctime)；
  * 最近一次的读取时间(atime)；
  * 最近修改的时间(mtime)；
  * 定义档案特性的旗标(flag)，如SetUID...；
  * 该档案真正内容的指向(pointer)；  
  * 每个inode 大小均固定为128 bytes (新的ext4 与xfs 可设定到256 bytes)；
  * 每个档案都仅会占用一个inode 而已；
  * 承上，因此档案系统能够建立的档案数量与inode 的数量有关；
  * 系统读取档案时需要先找到inode，并分析inode 所记录的权限与使用者是否符合，若符合才能够开始实际读取 block 的内容
* Superblock (超级区块)，大小为1024bytes
  * block 与inode 的总量；
  * 未使用与已使用的inode / block 数量；
  * block 与inode 的大小(block 为1, 2, 4K，inode 为128bytes 或256bytes)；
  * filesystem 的挂载时间、最近一次写入资料的时间、最近一次检验磁碟(fsck) 的时间等档案系统的相关资讯；
  * 一个valid bit 数值，若此档案系统已被挂载，则valid bit 为0 ，若未被挂载，则valid bit 为1 。
* block bitmap (区块对照表)
* inode bitmap (inode 对照表)
* Filesystem Description (档案系统描述说明)
* dumpe2fs： 查询Ext 家族superblock 资讯的指令 
  * blkid,查询系统中被格式化的设备
  * dump2fs /dev/sda1
* 挂载
  * 挂载点一定是目录，是文件系统的入口 
#### 文件系统
* 文件系统类型
  * 传统档案系统：ext2 / minix / MS-DOS / FAT (用vfat 模组) / iso9660 (光碟)等等；
  * 日志式档案系统： ext3 /ext4 / ReiserFS / Windows' NTFS / IBM's JFS / SGI's XFS / ZFS
  * 网路档案系统： NFS / SMBFS
* 查询当前linux系统支持哪种文件系统
  * ls -l /lib/modules/$(uname -r)/kernel/fs
* 查看当前加载到内存中的文件系统类型
  * cat /proc/filesystems
* Linux VFS (Virtual Filesystem Switch),Linux 认识的filesystem 其实都是VFS 在进行管理
* EXT家族，文件系统，格式化缓慢，逐渐被XFS替换
* XFS，规划为三个部份，资料区(data section)，档案系统活动登录区(log section)，即时运作区(realtime section)。
  * 资料区(data section)，与ext 家族一样，包括inode/data block/superblock 等资料
  * 档案系统活动登录区(log section)，纪录档案系统的变化，其实有点像是日志区
  * 即时运作区(realtime section)，
* df，查询文件系统使用情况
  * df -T，查询文件系统类型，以及挂载点
  * df -i ，查看inode编号
* xfs_info ，查询xfs文件系统信息
  * xfs_info /dev/vda2 
* du，查询文档容量情况
  * du -sm ./*,查询当前目录下文件占用容量大小以mb显示
* hard link，硬链接，与源文件（目录地址）指向同一个block文件，源文件被删除后，该文件依然存在（依然指向block文件）
  * 硬链接不占用大小
  * 不能跨Filesystem
  * 不能link 目录
    * herd link 目录，目录内需要产生./ ../，容易产生是循环
* Symbolic Link，符号链接，快捷方式，有新的inode产生，所以是一个新文件，直接指向源文件（目录地址）
  * 原始文件（目录地址）被删除后，符号链接就失效
  * 文件大小是指向源文件的路径名称字符长度，
    * lrwxrwxrwx. 1 root root   12 Jun 23 22:31 /root/crontab2 -> /etc/crontab，文件大小（/etc/crontab）12个长度
* ln 
  * -s,符号链接
* link 数量，当前目录新增目录时，新增的目录自己的link 数为2 ，而当前目录的link 数则会增加1
#### 磁盘管理
* 观察分区状态
  * lsblk ，list block devices
    * lsblk -fp,列出带路径的文件系统设备
  * blkid，blkid 列出装置的UUID 等参数
  * parted ,高级分区操作程序，可以左mbr和GPT格式分区
    * parted /dev/sda1 print，打印分区信息
#### 分区程序
  * fdisk，MBR分区程序
  * gdisk，GPT分区程序
  * cat /proc/partitions //打印操作系统分区信息
  * partprobe ，更新分区信息
    * partprobe -s
#### 格式化，make filesystem, mkfs
  * mkfs.xfs
  * mkfs.ext4
  * 其他档案系统mkfs
  * xfs_repair，修复
  * fsck.ext4
#### 挂载 /解挂载
  * 单一档案系统不应该重复挂载到不同的挂载点（目录）中
  * 一个挂载点（目录）不应该挂载多个档案系统
  * 要作为挂载的目录应该是空的
    * 如果挂载点目录不为空，那么挂载档案系统后，目录里面的内容会临时消失
  * mount
    * -l，列出挂载信息
  * umount unmount file systems,解挂载文件系统
  * 开机自动挂载
    * cat /etc/fstab ，开机时，系统检测该文件，自动进行挂载
  * 挂载光盘信息ISO
    * mount -o loop /tmp/CentOS-7.0-1406-x86_64-DVD.iso /data/centos_dvd 
  * swap 交换分区
    * 通过分区建立交换分区
      1. 硬盘分区
      2. 分区格式化，mkswap 设备名（set up a Linux swap area）
      3. 启动交换分区，swapon 设备名
      4. free（Display amount of free and used memory in the system）和swapon -s（Display  swap  usage summary by device）观察，
    * 通过大文件来建立交换分区
      * 利用/dev/zero 来建立大文件
        * dd if=/dev/zero of=/tmp/swap bs=1M count=128
      * 通过mkswap 文件位置，格式化为swap类型
        * mkswap /tmp/swap
      * swapon，启动交换分区
        * swapon /tmp/swap
      * swapon -s 查看交换分区
  * 关闭交换分区,swapoff /tmp/swap  
### 压缩/解压缩
> 常见压缩格式，*.tar, *.tar.gz, *.tgz, *.gz, *.Z, *.bz2, *.xz
#### 压缩
* 打包和压缩
  * *.tar，通过tar程序进行打包，解决每次解压后有很多文件难以管理
* gzip，可以解压compress,zip,gzip软件压缩的文档，可以压缩文件为*.gz格式，为了替代compress压缩程序
  * gzip -v file 压缩file，同时删除原文件
  * zcat,zmore,zless，可以尝试打开文本压缩文档
  * gzip -d file.gz 解压file文件，同时删除*.gz文件
  * gzip -9 -c file > file.gz ,保留原文件，并且压缩file输出到file.gz文件
* bzip2是为了替代gzip的压缩比而来，与gzip用法一致
  * bzip2 -v file,压缩的文件比gzip文件小
  * bzip2 -d file.bz2,解压file.bz2
  * bzip2 -9 -c file > file.gz2 用最高压缩比压缩文件file保留文件file
* xz是替换bzip2的压缩比而诞生的
  *用法和gzip，bzip2一致
* 压缩软件，主要针对文件压缩，如果需要对目录压缩的话，需要打包软件的支持
#### 打包
  * tar 
    * -c ：建立打包档案，可搭配-v 来察看过程中被打包的档名(filename)
    * -t ：察看打包档案的内容含有哪些档名，重点在察看『档名』就是了；
    * -x ：解打包或解压缩的功能，可以搭配-C (大写) 在特定目录解开
    *       特别留意的是， -c, -t, -x 不可同时出现在一串指令列中。
    * -z ：透过gzip 的支援进行压缩/解压缩：此时档名最好为*.tar.gz
    * -j ：透过bzip2 的支援进行压缩/解压缩：此时档名最好为*.tar.bz2
    * -J ：透过xz 的支援进行压缩/解压缩：此时档名最好为*.tar.xz
    *       特别留意， -z, -j, -J 不可以同时出现在一串指令列中
    * -v ：在压缩/解压缩的过程中，将正在处理的档名显示出来！
    * -f filename：-f 后面要立刻接要被处理的档名！建议-f 单独写一个选项啰！(比较不会忘记)
    * -C 目录：这个选项用在解压缩，若要在特定目录解压缩，可以使用这个选项。
    * -p(小写) ：保留备份资料的原本权限与属性，常用于备份(-c)重要的设定档
    * -P(大写) ：保留绝对路径，亦即允许备份资料中含有根目录存在之意；
    * --exclude=FILE：在压缩的过程中，不要将FILE 打包！
  * tar -czvp -f /home/bpz/etc.tar.gz /etc,压缩目录sourcefilepath到descfile使用gzip方式，显示压缩详细信息，保留原始权限
  * tar -xzv -f /home/bpz/etc.tar.gz ~bpz/etc
  * tar -tzv -f /home/bpz/etc.tar.gz 查看文件内的文件内容
  * tarfile（只进行了打包的源文件）, tarball（进行了打包并且压缩了的源文件）
* basename -a filename1 filename2 ，获取文件的文件名
  
#### 其他常用压缩软件
* dd，
* cpio
### Bash
> 程序运行：软件-》kernel -》 硬件
* shell，提供一个调用系统的一个界面，狭义的shell程序是指令列方面的软件包括bash，广义的定义包括图形界面的软件
* linux shell 程序有很多，包括Bourne SHell (sh)，CShell，KShell，TCSH，Bourne Again SHell (简称bash) 是根据GNU发展而来
* cat /etc/shells,查询linux系统中的Shell   
* cat /etc/passwd,查看每个用户默认的shell
  * bpz:x:1000:1000::/home/bpz:/bin/bash
  * nobody:x:99:99:Nobody:/:/sbin/nologin
* cat -A filename，查询文件内充，符号用特殊符号显示（如windows系统下的一些特殊字符）
  * cat -A /etc/man_db.conf
#### 常见命令
* histroy功能，可以在bash界面按上下键来查询历史命令
  * ~/.bash_history 记录登录成功后的所有命令默认1000个
* 自动补全tab
  * 命令自动补全
  * 文档名自动补全
* 命令别名设置
  * alias lm='ls -al'
* 工作控制，前端程序后端程序
  * job control, foreground, background
* shell scripts
  * 
* 通配符
  * ls -l /usr/bin/X*
* 为了方便shell操作，bash内建了很多内置指令
  * cd，umask
* type，查询指令是否为bash内置指令，只能查找指令，不能查找普通文档
  * type cd，cd is a shell builtin
  * type bash，bash is /usr/bin/bash
  * type -a ls，显示所有ls命令信息
* 指令太长可以使用反斜线\+enter来跳转一行继续写，\和enter之间没有一个空格
  * ctrl+u，删除光标所在之前的编辑内容
  * ctrl+k，删除光标所在之后的编辑内容
  * ctrl+a，移动光标到命令行最前
  * ctrl+e，移动光标到命令行最后
* 命令别名
  * alias
    * alias lm='ls -al |more'
    * alias rm='rm -i'
  * unalias
    * unalias lm,删除别名lm
* id ，查询用户的gid uid 等
  * id bpz
* 历史命令 
  * history
    * !number,执行number的历史命令
    * !!，执行最近的历史命令
    * !command，执行最近的command历史命令
* 系统命令执行顺序
  1. 以相对/绝对路径执行指令，例如『 /bin/ls 』或『 ./ls 』；
  2. 由alias 找到该指令来执行；
  3. 由bash 内建的(builtin) 指令来执行；
  4. 透过$PATH 这个变数的顺序搜寻到的第一个指令来执行。
* xargs ，参数处理
  * -0 ：如果输入的stdin 含有特殊字元，例如`, \, 空白键等等字元时，这个-0 参数
        可以将他还原成一般字元。这个参数可以用于特殊状态喔！
  * -e ：这个是EOF (end of file) 的意思。后面可以接一个字串，当xargs 分析到这个字串时，
         就会停止继续工作！
  * -p ：在执行每个指令的argument 时，都会询问使用者的意思；
  * -n ：后面接次数，每次command 指令执行时，要使用几个参数的意思。
  * 当xargs 后面没有接任何的指令时，预设是以echo 来进行输出喔！
  * cat /etc/passwd|cut -d ':' -f1|head -n 3|xargs -n 1 id，每次以一个参数输入后续命令
  * cut -d ':' -f 1 /etc/passwd | xargs -e'sync' -n 1 id，只要分析道sync字符串就会停止分析后面的内容
  * cat users.config|grep -v "^$"|grep -v "#"|xargs -n1 id
#### 变量
  * set 查看变量，包括环境变量，自定义变量，与bash程序相关的变量
    * set
  * 环境变量和Shell内置变量为了与自定义变量区分，通常使用(纯粹是为了方便人类自己识别)大写字母，PATH、HOME、MAIL、SHELL
  * 获取变量，$HOME或${HOME}
  * 显示变量，echo $HOME，如果是bash中echo不存在的变量会显示空格，其他的shell可能会报错
  * 设置变量，=
    * vbir=hell
    * 等号两边不能有空格
    * 不能用数字开头表示变量名
    * 变量内容如果有空格需要使用单引号或双引号括起来
      * 单引号表示为纯粹字符串
      * 双引号中的$等特殊字符保留原有功能
    * 使用\作为转义字符，包括\、空格、$等
    * 变量需要设置其他命令生成的数据使用\`命令\`或$\(指令\)
      * vir=$(uname -r),echo $vir 或 vir=\`uname -r\` echo $vir
      * 3.10.0-1127.19.1.el7.x86_64
      * cd /lib/modules/$(uname -r)/kernel
    * append变量内容，使用$变量名或${变量名}
      * PATH=${PATH}:/home/bpz/bin
    * 变量设置为环境变量,export
      * export 变量名，这个只能让后续的程序知道变量，如果后续程序exit（如子程序bash退出后，在当前bash是无法获取到变量的）
      * export 会列出去所有的环境变量
      * 环境变量在启动bash时候，在内存中存在，当使用export时，系统会把变量放到这块存储空间中
  * 取消变量，unset，环境变量也可以取消
    * unset vbir
  * 环境变量
    * env，现实系统中的环境变量
      * LANG，语系
      * HOME,家目录
      * PATH，命令环境变量
      * RANDOM，随机数，0-32767，declare -i number=${RANDOM}*10/32768 ; echo $number
* 直接使用bash命令，表示进入一个子程序bash中，如果需要退出需要使用exit，进入一个子程序后无法使用外围的变量，需要在外围使用
export命令设置为环境变量后，才能再子程序中使用
  * bash
  * exit
* set 命令，列出变量（环境变量、shell内置变量，用户自定义变量）
  * PS1，命令提示符，内置的一些特殊符号，与环境变量设置无关
    * \d ：可显示出『星期月日』的日期格式，如："Mon Feb 2"
    * \H ：完整的主机名称。举例来说，鸟哥的练习机为『study.centos.vbird』
    * \h ：仅取主机名称在第一个小数点之前的名字，如鸟哥主机则为『study』后面省略
    * \t ：显示时间，为24 小时格式的『HH:MM:SS』
    * \T ：显示时间，为12 小时格式的『HH:MM:SS』
    * \A ：显示时间，为24 小时格式的『HH:MM』
    * \@ ：显示时间，为12 小时格式的『am/pm』样式
    * \u ：目前使用者的帐号名称，如『dmtsai』；
    * \v ：BASH 的版本资讯，如鸟哥的测试主机版本为4.2.46(1)-release，仅取『4.2』显示
    * \w ：完整的工作目录名称，由根目录写起的目录名称。但家目录会以~ 取代；
    * \W ：利用basename 函数取得工作目录名称，所以仅会列出最后一个目录名。
    * \# ：下达的第几个指令。
    * \$ ：提示字元，如果是root 时，提示字元为# ，否则就是$ 啰～
  * echo $$,显示当前bash 的pid
  * ?,上次命令执行的回传值，如果成功是0，错误就是非零
    * echo $?
  * OSTYPE, HOSTTYPE, MACHTYPE,系统烈性，主机类型，机器类型
    * echo $OSTYPE,linux-gnu
    * echo $HOSTTYPE,x86_64
    * echo $MACHTYPE,x86_64-redhat-linux-gnu）
* locale,影响结果字符显示的变量
  * locale -a
  * cat /etc/locale.conf
* read，从键盘中读取字符，通常被用在shell scripts中
  -p，带提示符的等待输入
  -t，阻塞时间等待
* declare/typese,定义类型变量
  * -a，定义数组
  * -i，定义整形
    declare -i number=10+20+30
  * -x，将后面的变量定义为环境变量
    * export 可以列出来设置为x的环境变量
    * +x，取消环境变量
    * declare +x number,取消number 的环境变量变为自定义变量
  * -r，定义自读变量，不能被修改，也不能被unset
  * -p，显示定义的变量
* 数组
  * var[idx]="content"
  * name[1]=lisi 
  * name[2]=wangwu
  * echo ${name[1]} ,${name[2]}
* ulimit ,限制资源使用
  * ulimit -f 10240，限制文件大小10m上限
* 对变量内容的删除，修改，替换，脚本程序设计的时候用到变量判断
  * 删除部分内容
    * \# ：从左往右符合取代文字的『最短的』那一个；
    * \##：从左往右符合取代文字的『最长的』那一个
    * ${变数%关键字}，从右往左
    * ${变数%%关键字}，从右往左
    * ${ variable#/*content },删除变量variable内，包含content以及之前最短的内容
      * echo ${path#*:}，=》/usr/local/bin:/usr/sbin:/usr/bin:/root/bin
    * ${ variable##/*content }，删除变量内，包含content以及之前最长的内容
    * echo ${path##*:}，=》/root/bin
  * 替换部分内容
    * echo ${variable/old/new},有new 替换变量内第一个old的值
  * 如果变量存在值就使用，如果不存在就替换为新值
    * echo ${variable-newvalue},如果存在variable的值，就打印，如果不存在就打印newvalue
  * 如果变量值为空（""）或不存在就替换为新值
    * echo ${variable:-newvalue},如果存在variable的值，就打印，如果不存在就打印newvalue
* bash环境设置
  * bash的欢迎界面
    * cat /etc/issue,/etc/motd（登录后提示信息）,/etc/issue.net（远程登录时显示）
      * man issue,查看特殊信息标识符 
  * login和nologin shell
    * login shell,取得shell之前需要完整的登录流程
      * /etc/profile：这是系统整体的设定，你最好不要修改这个档案；
      * ~/.bash_profile 或~/.bash_login 或~/.profile：属于使用者个人设定
      * source 配置文件（.bashrc），重新切换配置文件（可以在同一个工作目录下实现多个环境切换）
    * nologin shell，取得shell时不需要完整的登录流程
      * ~/.bashrc (non-login shell 会读)
      * /etc/bashrc
        * 依据不同的UID 规范出umask的值；
        * 依据不同的UID 规范出提示字元(就是PS1 变数)；
        * 呼叫/etc/profile.d/*.sh 的设定
* 终端环境设置
  * stty，set
    * stty，设置按键列表（快捷键）
* 通配符
  * \* 0个和多个
  * ? 一个或多个
  * [] 某一个
  * [-] 范围，[0-9],0-9 
  * [^ ],任何一个非，[^abcd],任何一个非abcd字符
* 特殊符号
  * \#,注释
  * \ ,转义字符
  * | ，管道
  * ; ，连续命令分隔符
  * ~ ，home
  * $ ,取变量
  * & ，背景任务
  * ! , 非
  * / ，目录分隔符
  * \> ,\>> 重定向输出
    
  * \< ,\<< 重定向输入
  * '' ，纯文本
  * `` , $() 一致
  * () ,子shell开始结束
  * {} ， 命令块组合
* 输入、输出、从定向
  * 标准输入(stdin) ：代码为0 ，使用< 或<< ；
  * 标准输出(stdout)：代码为1 ，使用> 或>> ；
  * 标准错误输出(stderr)：代码为2 ，使用2> 或2>>
  * 1> ：以覆盖的方法将『正确的资料』输出到指定的档案或装置上；
  * 1>>：以累加的方法将『正确的资料』输出到指定的档案或装置上；
  * 2> ：以覆盖的方法将『错误的资料』输出到指定的档案或装置上；
  * 2>>：以累加的方法将『错误的资料』输出到指定的档案或装置上；
  * &> ：正确和错误的消息都从定向
  * /dev/null ，黑洞
  * find /home -name .bashrc > list 2>&1，将正确错误的输出合并到list文件中
  * id bpz > /dev/null 2>&1 && echo "用户存在" || echo "用户不存在"，检查用户是否存在
  * cat > catfile < ./.bash_profile
  * tee，双重从定向，输出到文件和标准输出
    * last |grep [a-zA-Z]|grep -v 'reboot'|grep -v 'wtmp'|tee last.list|wc -l，有效登录从定向到文件last.list
* 命令执行判断依据
  * cmd;cmd，命令依次执行
  * cmd1&&cmd2,两个命令依次执行，cmd1执行成功($?=0),会执行cmd2，cmd1执行失败($?!=0),不会执行cmd2
  * cmd1||cmd2,两个命令依次执行，cmd1执行失败($?!=0),会执行cmd2，cmd1执行成功($?!=0),不会执行cmd2
  * command1 && command2 || command3，常见命令关联格式
* 管道，标准输出到下一个命令的输入，会忽略标准错误
  * ls -al /etc|less
#### 文字处理工具
* 截取命令，一行一行分析
  * cut -d'分隔字元' -f fields，截取一段信息
    * echo $PATH|cut -d ':' -f 5，获取第5个数据
    * cut -d ':' -f 3 ./filename
    * export | cut -c 12- ，截取第12个字符之后的值
    * export | cut -c 12-20 ，截取第12个字符到20个字符的信息
    * export | cut -c -11 ，截取1-11个字符的信息
    
    * -d ：后面接分隔字元。与-f 一起使用；
    * -f ：依据-d 的分隔字元将一段讯息分割成为数段，用-f 取出第几段的意思；
    * -c ：以字元(characters) 的单位取出固定字元区间；
* grep 分析一段信息，如果有所需要的信息就列出来
  * -a ：将binary 档案以text 档案的方式搜寻资料
  * -c ：计算找到'搜寻字串' 的次数
  * -i ：忽略大小写的不同，所以大小写视为相同
  * -n ：顺便输出行号
  * -v ：反向选择，亦即显示出没有'搜寻字串' 内容的那一行！
  * --color=auto ：可以将找到的关键字部分加上颜色的显示喔！
* 排序， sort, wc, uniq
  * sort [-fbMnrtuk] [file or stdin]
    * -f ：忽略大小写的差异，例如A 与a 视为编码相同；
    * -b ：忽略最前面的空白字元部分；
    * -M ：以月份的名字来排序，例如JAN, DEC 等等的排序方法；
    * -n ：使用『纯数字』进行排序(预设是以文字型态来排序的)；
    * -r ：反向排序；
    * -u ：就是uniq ，相同的资料中，仅出现一行代表；
    * -t ：分隔符号，预设是用[tab] 键来分隔；
    * -k ：以那个区间(field) 来进行排序的意思
    * cat /etc/passwd | sort -t ':' -k 3,3 -n，排序passwd第三列，纯数字
  * uniq ，去重
    * -i ：忽略大小写字元的不同；
    * -c ：进行计数
    * last | cut -d ' ' -f1 | sort | uniq -c
  * wc，统计行、字符数、字节数
    * last |grep [a-zA-Z]|grep -v 'reboot'|grep -v 'wtmp'|wc -l，查询有效登录系统的次数
* 字符替换, tr, col, join, paste, expand
  * tr
    * -d ：删除讯息当中的SET1 这个字串；
    * -s ：取代掉重复的字符
    * last | tr -s [a-z] [A-Z] 所有的小写替换为大写
    * cat /etc/passwd | tr -d ':'，删除所有":"冒号
  * col， 替换tab键为空格键
    * -x ：将tab 键转换成对等的空白键
    * cat /etc/man_db.conf | col -x | cat -A | more 
  * join ,按照指定列分析多个文件的相同点，进行合并，按照文件的先后顺序合并行
    * -t ：join 预设以空白字元分隔资料，并且比对『第一个栏位』的资料，
           如果两个档案相同，则将两笔资料联成一行，且第一个栏位放在第一个！
    * -i ：忽略大小写的差异；
    * -1 ：这个是数字的1 ，代表『第一个档案要用那个栏位来分析』的意思；
    * -2 ：代表『第二个档案要用那个栏位来分析』的意思。
    * join -t ':' /etc/passwd /etc/shadow | head -n 3 
    * join -t ':' -1 4 /etc/passwd -2 3 /etc/group | head -n 3 
  * paste ，直接将两行粘贴到一起，中间用tab键分割,没有比对
    * paste /etc/passwd /etc/group
  * expand ，将tab 换成空格
    * expand [-t] file
#### 文件处理工具
* split ，文件分割命令
  * split [-bl] file PREFIX ，
    * -b ：后面可接欲分割成的档案大小，可加单位，例如b, k, m 等；
    * -l ：以行数来进行分割。
    * PREFIX ：代表前置字元的意思，可作为分割档案的前导文字。
    * split -b 300k services serv_，services文件按照每个300k分割，并且分割后的文件名以serv_开始
      * cat serv_* >> services_bat 恢复文件
### 正则表达式（Regular Expression）
> 正规表示法就是处理字串的方法，他是以行为单位来进行字串的处理行为， 正规表示法透过一些特殊符号的辅助，
>可以让使用者轻易的达到『搜寻/删除/取代』某特定字串的处理程序！
* 正规表示法基本上是一种『表示法』， 只要工具程式支援这种表示法，那么该工具程式就可以用来作为正规表示法的字串处理之用
* 基础正规表示法与延伸正规表示法
* 正则表达式和通配符不是一个概念
* 语系对正则表达式有影响
  * LANG=C 时：0 1 2 3 4 ... ABCD ... Z abcd ...z
  * LANG=zh_TW 时：0 1 2 3 4 ... a A b B c C d D ... z Z
* POSIX标准使用C语系
  * [:alpha:]	代表任何英文大小写字元，亦即AZ, az
  * [:blank:]	代表空白键与[Tab] 按键两者
  * [:cntrl:]	代表键盘上面的控制按键，亦即包括CR, LF, Tab, Del.. 等等
  * [:digit:]	代表数字而已，亦即0-9
  * [:graph:]	除了空白字元(空白键与[Tab] 按键) 外的其他所有按键
  * [:lower:]	代表小写字元，亦即az
  * [:print:]	代表任何可以被列印出来的字元
  * [:punct:]	代表标点符号(punctuation symbol)，亦即：" ' ? ! ; : # $...
  * [:upper:]	代表大写字元，亦即AZ
  * [:space:]	任何会产生空白的字元，包括空白键, [Tab], CR 等等
  * [:xdigit:]	代表16 进位的数字类型，因此包括： 0-9, AF, af 的数字与字元
#### grep，行匹配筛选
  * grep [-A] [-B] [--color=auto] '搜寻字串' filename
    * -An，之后几行
    * -Bn，之前几行
  * 筛选基础文本信息
    * grep -n 'the' regular_express.txt,包含the的行
    * grep -nv 'the' regular_express.txt,反选，不包含the的行
    * grep -in 'the' regular_express.txt ，忽略大小写的the
  * 利用\[\]来筛选，代表任意一个，在[]内添加^表示反选
    * grep -n 'th[ea]' regular_express.txt 
    * grep -n '[^g]oo' regular_express.txt ，不以g开始接oo的行
    * grep -n '[^[:lower:]]oo' regular_express.txt 
    * grep -n '[[:digit:]]' regular_express.txt，包含数字
  * 行首与行尾字元^ $，^没在[]内表示行首
    * grep -n '^T' regular_express.txt，行首以T开始
    * grep -n '\.$' regular_express.txt，行尾以.结束
    * grep -n '^[^a-zA-Z]' regular_express.txt，行首不以字母开始
    * grep -n '^$' regular_express.txt，查询空行
    * grep -v '^$' /etc/rsyslog.conf |grep -nv '#'，筛选掉空白行和#开始的注释行
  * 正则表达式中的.和*
    * .，表示任意一个
      * grep -n 'g..d' regular_express.txt
    * \*，表示0个或多个与之相邻的前一个字符
      * grep -n 'oo*' regular_express.txt ,表示包含一个o以上的行
      *  grep -n 'g.*g' regular_express.txt ,表示包含两个g的行
  * 正在表达式中的{},在bash中，需要添加转义字符，不然会识别为变量的特殊符号
    * grep -n  'o\{3,5\}' regular_express.txt ,
    * ls -l /etc/|grep '^d'，查询文件夹
#### sed,line-oriented text editor
> 资料进行取代、删除、新增、撷取特定行
  * 选项与参数：
    * -n ：使用安静(silent)模式。在一般sed 的用法中，所有来自STDIN 的资料一般都会被列出到萤幕上。
            但如果加上-n 参数后，则只有经过sed 特殊处理的那一行(或者动作)才会被列出来。
    *  -e ：直接在指令列模式上进行sed 的动作编辑；
    *  -f ：直接将sed 的动作写在一个档案内， -f filename 则可以执行filename 内的sed 动作；
    *  -r ：sed 的动作支援的是延伸型正规表示法的语法。(预设是基础正规表示法语法)
    *  -i ：直接修改读取的档案内容，而不是由萤幕输出。
  
  *  动作说明： [n1[,n2]]function
    *  n1, n2 ：不见得会存在，一般代表『选择进行动作的行数』，举例来说，如果我的动作
    *           是需要在10 到20 行之间进行的，则『 10,20[动作行为] 』
    *  
    *  function 有底下这些咚咚：
    *  a ：新增， a 的后面可以接字串，而这些字串会在新的一行出现(目前的下一行)～
    *  c ：取代， c 的后面可以接字串，这些字串可以取代n1,n2 之间的行！
    *  d ：删除，因为是删除啊，所以d 后面通常不接任何咚咚；
    *  i ：插入， i 的后面可以接字串，而这些字串会在新的一行出现(目前的上一行)；
    *  p ：列印，亦即将某个选择的资料印出。通常p 会与参数sed -n 一起运作～
    *  s ：取代，可以直接进行取代的工作哩！通常这个s 的动作可以搭配正规表示法！
            例如1,20s/old/new/g 就是啦！
* 行处理
  * nl regular_express.txt |sed '2,5d'，删除2-5行
  * $表示到结尾
    * nl regular_express.txt |sed '2,$d'，删除2-结尾
  * nl regular_express.txt |sed '2a hello' 在2行后添加内容
  * nl regular_express.txt |sed '2,3c hello',替换
  * nl regular_express.txt |sed -n '2,3p'，只显示2，3行
* 行内数据处理
  * sed命令详情需要通过info sed查看
  * sed 's/要被取代的字串/新的字串/g'，内容替换，g表示行内所有匹配都需要替换
    * /sbin/ifconfig eth0|grep inet|sed 's/^[[:blank:]]*inet[6[:blank:]]*//g'，筛选ip地址
    * cat /etc/man_db.conf |sed 's/^#.*//g'|sed '/^$/d'，第一个管道的sed采用's///'替换模式，第二个管道采用在行内匹配正则表达式/xx/的方式处理
#### egrep采用扩展正则表达式处理

#### 文件格式化处理
* printf ，格式化打印
* awk，资料处理工具， awk 则比较倾向于一行当中分成数个栏来处理
  * awk '条件类型1{动作1} 条件类型2{动作2} ...' filename
    * last -n 5|awk '{print $1 "\t" $3}'
    * 每一行的每个栏位都是有变数名称的，那就是$1, $2... 等变数名称，$0,整行内容
  * 执行流程
    1. 读入第一行，并将第一行的资料填入$0, $1, $2.... 等变数当中；
    2. 依据"条件类型" 的限制，判断是否需要进行后面的"动作"；
    3. 做完所有的动作与条件类型；
    4. 若还有后续的『行』的资料，则重复上面1~3 的步骤，直到所有的资料都读完为止。
  * 内置变量
    * NF	每一行($0) 拥有的栏位总数
    * NR	目前awk 所处理的是『第几行』资料
    * FS	目前的分隔字元，预设是空白键
  * 条件类型
    * \>	大于
    * <	小于
    * \>=	大于或等于
    * <=	小于或等于
    * ==	等于
    * !=	不等于
  * cat /etc/passwd|awk '{FS=":"} $3 > 100 {print $1 "\t" $3}',设置份额符为":"
  * cat /etc/passwd |awk 'BEGIN{FS=":"} $3>100{print $1 "\t" $3}',在开始之前就设置分隔符  
  * cat play.txt |awk 'NR==1{printf "%10s %10s %10s %10s %10s\n",$1,$2,$3,$4,"Total" } \
  NR>=2{total = $2 + $3 + $4;  printf "%10s %10d %10d %10d %10.2f\n", $1, $2, $3, $4, total}'
    * 在第二个{}内，多个命令通过;分割
    * awk中使用变量，直接使用变量名，不需要 $符号
#### 档案比对
  * diff，文档比较
    * diff regular_txt regular_txt.old，比较文档内容
    * diff /etc/rc0.d/ /etc/rc5.d/，比较目录下文件差异 
  * cmp，比对文档找出第一个不相同的字符位置
    * cmp  regular_txt regular_txt.old
  * patch ，通过diff制作差异补丁，patch用于升级，还原
    * patch -pN < patch_file，升级
      *  patch -p0 < regular.patch 
    * patch -R -pN < patch_file,还原
      *  patch -p0 -R < regular.patch 
### bash script
> shell script 是利用shell 的功能所写的一个『程式 (program)』，这个程式是使用纯文字档，
>将一些shell 的语法与指令(含外部指令)写在里面， 搭配正规表示法、管线命令与资料流重导向等功能，以达到我们所想要的处理目的。
* 包括命令，条件控制，列表，循环，逻辑控制等功能，不需要编译，直接在linux上运行
* 可以实现自动化管理
* 追踪和管理系统的重要工作
* 简单资料处理
* 连续指令单一化
#### 学习方法
> 多看、多模仿、并加以修改成自己的样式
#### 脚本语言的一些基础概念
* 指令的执行是从上而下、从左而右的分析与执行；
* 指令、选项与参数间的多个空白都会被忽略掉；
* 空白行也将被忽略掉，并且[tab] 按键所推开的空白同样视为空白键；
* 如果读取到一个Enter 符号(CR) ，就尝试开始执行该行(或该串) 命令；
* 至于如果一行的内容太多，则可以使用『 \[Enter] 』来延伸至下一行；
* 『 # 』可做为注解！任何加在# 后面的资料将全部被视为注解文字而被忽略
* 脚本执行
  * 直接执行，具有rx权限
  * 通过bash xx.sh执行
  * source 方式执行
  * 执行方式具有各种差异
    * 直接执行（绝对路径或相对路径）以及通过bash命令来执行，都会重新启动一个子程序，所以内部的变量不会被父进程看到
    * 通过source方式执行的话，父进程可以看到这个变量，并使用
* 头部信息包含一些说明信息
  * script 的功能；
  * script 的版本资讯；
  * script 的作者与联络方式；
  * script 的版权宣告方式；
  * script 的History (历史纪录)；
  * script 内较特殊的指令，使用『绝对路径』的方式来下达；
  * script 运作时需要的环境变数预先宣告与设定。
* var=$((运算内容))，表示运算
* echo "123.123*55.9" | bc，包含小数
* test -e /etc && echo "file  exsit"||echo "file not exist"，判断文件是否存在
  * 判断文档本身
    * -e	该『档名』是否存在？(常用)
    * -f	该『档名』是否存在且为档案(file)？(常用)
    * -d	该『档名』是否存在且为目录(directory)？(常用)
    * -b	该『档名』是否存在且为一个block device 装置？
    * -c	该『档名』是否存在且为一个character device 装置？
    * -S	该『档名』是否存在且为一个Socket 档案？
    * -p	该『档名』是否存在且为一个FIFO (pipe) 档案？
    * -L	该『档名』是否存在且为一个连结档？
  * 判断文档权限
    * -r	侦测该档名是否存在且具有『可读』的权限？
    * -w	侦测该档名是否存在且具有『可写』的权限？
    * -x	侦测该档名是否存在且具有『可执行』的权限？
    * -u	侦测该档名是否存在且具有『SUID』的属性？
    * -g	侦测该档名是否存在且具有『SGID』的属性？
    * -k	侦测该档名是否存在且具有『Sticky bit』的属性？
    * -s	侦测该档名是否存在且为『非空白档案』？
  * 判断文档新旧
    * -nt	(newer than)判断file1 是否比file2 新
    * -ot	(older than)判断file1 是否比file2 旧
    * -ef	判断file1 与file2 是否为同一档案，可用在判断hard link 的判定上。主要意义在判定，两个档案是否均指向同一个inode 哩！
  * 判断数字
    * -eq	两数值相等(equal)
    * -ne	两数值不等(not equal)
    * -gt	n1 大于n2 (greater than)
    * -lt	n1 小于n2 (less than)
    * -ge	n1 大于等于n2 (greater than or equal)
    * -le	n1 小于等于n2 (less than or equal)
  * 字符串判断
    * test -z string	判定字串是否为0 ？若string 为空字串，则为true
    * test -n string	判定字串是否非为0 ？若string 为非空字串，则为true。
    * 注： -n 亦可省略
    * test str1 == str2	判定str1 是否等于str2 ，若相等，则回传true
    * test str1 != str2	判定str1 是否不等于str2 ，若相等，则回传false
  * 多个条件同时判断
    * -a	(and)两状况同时成立！例如test -r file -a -x file，则file 同时具有r 与 x 权限时，才回传true。
    * -o	(or)两状况任何一个成立！例如test -r file -o -x file，则file 具有r 或 x 权限时，就可回传true。
    * !	反相状态，如test ! -x file ，当file 不具有x 时，回传true 
* 利用判断符号[],
  * [ "$HOME" == "$MAIL" ],中括号的前后必须有一个空格
  * 在中括号[] 内的每个元件都需要有空白键来分隔；
  * 在中括号内的变数，最好都以双引号括号起来；
  * 在中括号内的常数，最好都以单或双引号括号起来。
* 接受参数
  * $# ：代表后接的参数『个数』，以上表为例这里显示为『 4 』；
  * "$@" ：代表『 "$1" "$2" "$3" "$4" 』之意，每个变数是独立的(用双引号括起来)；
  * "$*" ：代表『 "$1 c $2 c $3 c $4" 』，其中c为分隔字元，预设为空白键， 所以本例中代表『 "$1 $2 $3 $4" 』之意。 
  * shift：造成参数变数号码偏移
#### 条件判断
* if
  * if []; then fi
  * && || ,在[]之外的连接符号，-o -a ! 在[]方括号之内
  * if []; then elif[]; then elif[]; then fi
* case
```shell script
case $变量名称 in    #==关键字为case ，还有变数前有钱字号
  "第一个变数内容" )    #==每个变数内容建议用双引号括起来，关键字则为小括号)
	程式段
	;;             #==每个类别结尾使用两个连续的分号来处理！
  "第二个变数内容" )
	程式段
	;; 
    *)                   #==最后一个变数内容都会用* 来代表所有其他值
	不包含第一个变数内容与第二个变数内容的其他程式执行段
	exit 1
	;; 
esac                   #==最终的case 结尾！『反过来写』思考一下！
```
* 循环，loop
```shell script
while [ condition ]   #==中括号内的状态就是判断式
do             #==do 是回圈的开始！
	程式段落
done           #==done 是回圈的结束
```

```shell script
until [ condition ]
do
	程式段落
done
```

* for
  * 文字列表处理
```shell script
for var in con1 con2 con3 ...
 do
	程式段
done
```
  * 数值处理
```shell script
for ((初始值;限制值;执行步阶))
do
	程式段
done
```

#### 函数
* 函数应该在bash script 的前面，因为脚本是通过解释执行从上至下，从左到右
```shell script
function fname () {
	程式段
}
```
* 函数内部由自己的变量${1}、${2}、${3}、${4}与shell脚本无关
#### 程序语法诊断（debug）
* sh [ -nvx ] scripts.sh
  * -n ：不要执行script，仅查询语法的问题；
  * -v ：在执行sccript 前，先将scripts 的内容代码输出到屏幕；
  * -x ：逐步答应执行结果与代码同步debug
---

### 账号管理
* UID，GID，表示用户ID和群ID
* /etc/passwd ,/etc/group
* id username ，查询用户id信息
* 登录过程，1.获得/etc/passwd，查询用户信息，2.获取/etc/group信息3.获取/etc/shadow密码信息，最后shell接管程序
* /etc/passwd信息结构，都可以读取
  * bpz:x:1000:1000::/home/bpz:/bin/bash
  * 用户名:密码:UID:GID:说明信息:HOME:Shell
    * 密码放到了/etc/shadow，都不能读取
    * UID为0的是系统管理员，有些系统没有root账号也行，只要把uid改为0
    * UID,账号区分，1-200，发行版系统内置账号编码
    * UID，账号201-999，系统账号需求
    * UID，账号1000-60000，一般账户使用
* /etc/shadow，详细信息,9个栏位
  * bpz:$1$bJhfPAAh$1.9fa3fCvsn4M7IfOb2vE.:18766:0:99999:7:::
  * 用户名:密码（加密后）:最近更新日期:密碼不可被更動的天數:密码需要重新变更的天数:密码需要变更期限前的警告天数:密码过期后的帐号宽限时间(密码失效日):帐号失效日期:保留
  * 最近更新日期，从1970年1月1日，如果更新过密码，这个数值会发生变化，后续以此为基准的实际数值也会发生改变
  * 密碼不可被更動的天數，(與第 3 欄位相比)，如果是0，可以随时修改
  * 密码需要重新变更的天数，(与第3 栏位相比)
  * 密码需要变更期限前的警告天数，（与第5 栏位相比），每次登陆都有警告信息
  * 密码过期后的帐号宽限时间(密码失效日)，（与第5 栏位相比），宽限期内登陆系统，系统会出现强制修改密码界面
  * 帐号失效日期，使用1970 年以来的总日数设定
  * 如：dmtsai:$6$M4IphgNP2TmlXaSS$B418YFroYxxmm....:16559:5:60:7:5:16679:
* 密码相关
  * 普通用户密码丢失，需要root用户使用passwd密码处理
  * root用户丢失密码，需要单机维护模式进入后使用paaswd修改密码，或挂载live cd挂载根目录修改/etc/shadow，将root的密码栏清空
  * 查看密码加密方式
    * authconfig --test|grep hashing
* /etc/group
  * bpz:x:1000:
  * 组名:密码:GID:所属群组账号列表
  * 密码，/etc/gshadow，很少使用，一般不用设置
  * 所属群组账号列表，这个列表中列出的是次要群组，在/etc/passwd中的GID属于初始设置
* groups，获得群组，第一个是有效群组，有效群组在新建文件时有用（新建文件，当中所属群组ID）
* newgrp，在账号所属群组中选其中一个群组为有效群组
  * newgrp group，修改自己的有限群组，log in to a new group
* /etc/gshadow
  群组名称:密码栏:群组管理员的帐号:有加入该群组支援的所属帐号
  * 群组名称
  * 密码栏，同样的，开头为! 表示无合法密码，所以无群组管理员
  * 群组管理员的帐号(相关资讯在gpasswd中介绍)
  * 有加入该群组支援的所属帐号(与/etc/group 内容相同！)
#### 账号管理
* 添加用户
  * useradd [-u UID] [-g 初始群组] [-G 次要群组] [-mM]  [-c 说明栏] [-d 家目录绝对路径] [-s shell] 使用者帐号名
    * -r，创建系统账号，系统不会创建home目录
    * -D，查看useradd默认值
      * 信息来自 cat /etc/default/useradd 信息
      * GROUP=100，centos不会参考，会为每个用户建立与用户名同名的私有群组，建立自己的私有home(centos)\加入公共group=100群组的有(SuSE)
      * INACTIVE=-1：密码过期后是否会失效的设定值
      * EXPIRE=：帐号失效的日期,通常不会设置
      * SKEL=/etc/skel：使用者家目录参考基准目录（包括.bash_profile、.bashrc、.bash_logout）
    * /etc/login.defs ,其他默认权限配置文件
      * cat /etc/login.defs |grep -i umask 
        * UMASK 077,默认home权限 700
  * passwd ,设置密码，锁定账号，解锁账号等
    * 主要操作对应到/etc/passwd文件
    * --stdin ：可以透过来自前一个管线的资料，作为密码输入，对shell script 有帮助！
      * echo "abc235abc" |passwd --stdin bpz,不需要再次输入账号就直接充值账号bpz的密码为"abc235abc"，
        * 缺点就是账号密码信息在.history中，不安全，仅用在shell script 的大量建立使用者帐号当中
    * -l ：是Lock 的意思，会将/etc/shadow 第二栏最前面加上! 使密码失效；
    * -u ：与-l 相对，是Unlock 的意思！
    * -S ：列出密码相关参数，亦即shadow 档案内的大部分资讯。
      * passwd -S bpz
      * bpz PS 2022-02-19 0 99999 7 -1 (Password set, MD5 crypt.)
    * -n ：后面接天数，shadow 的第4 栏位，多久不可修改密码天数
    * -x ：后面接天数，shadow 的第5 栏位，多久内必须要更动密码
    * -w ：后面接天数，shadow 的第6 栏位，密码过期前的警告天数
    * -i ：后面接天数，shadow 的第7 栏位，密码失效天数        
    * 密码的校验配置文件，/etc/pam.d/passwd，会取代/etc/login.defs 内的PASS_MIN_LEN 设置
  *  chage，change user password expiry information
    * -l ：列出该帐号的详细密码参数；
    * -d ：后面接日期，修改shadow 第三栏位(最近一次更改密码的日期)，格式YYYY-MM-DD
    * -E ：后面接日期，修改shadow 第八栏位(帐号失效日)，格式YYYY-MM-DD
    * -I ：后面接天数，修改shadow 第七栏位(密码失效日期)
    * -m ：后面接天数，修改shadow 第四栏位(密码最短保留天数)
    * -M ：后面接天数，修改shadow 第五栏位(密码多久需要进行变更)
    * -W ：后面接天数，修改shadow 第六栏位(密码过期前警告日期)
    * 使用者在第一次登入时， 强制她们一定要更改密码后才能够使用系统资源
      1. useradd agetest 
      2. echo "agetest" | passwd --stdin agetest 
      3. chage -d 0 agetest ,最近一次更改密码的日期设置为0，账号登录时需要强制修改密码才能登录
      4. chage -l agetest | head -n 3
* usermod， modify a user account
  * -c ：后面接帐号的说明，即/etc/passwd 第五栏的说明栏，可以加入一些帐号的说明。
  * -d ：后面接帐号的家目录，即修改/etc/passwd 的第六栏；
  * -e ：后面接日期，格式是YYYY-MM-DD 也就是在/etc/shadow 内的第八个栏位资料啦！
  * -f ：后面接天数，为shadow 的第七栏位。
  * -g ：后面接初始群组，修改/etc/passwd 的第四个栏位，亦即是GID 的栏位！
  * -G ：后面接次要群组，修改这个使用者能够支援的群组，修改的是/etc/group 啰～
  * -a ：与-G 合用，可『增加次要群组的支援』而非『设定』喔！
  * -l ：后面接帐号名称。亦即是修改帐号名称， /etc/passwd 的第一栏！
  * -s ：后面接Shell 的实际档案，例如/bin/bash 或/bin/csh 等等。
  * -u ：后面接UID 数字啦！即/etc/passwd 第三栏的资料；
  * -L ：暂时将使用者的密码冻结，让他无法登入。其实仅改/etc/shadow 的密码栏。
  * -U ：将/etc/shadow 密码栏的! 拿掉，解冻啦
* userdel,delete a user account and related files，删除用户和lock用户是不一样的
  * -r,删除用户的home目录，以及mail_dir，定义在/etc/login.defs中
    * locate -i login.defs,查找文件login.defs所在位置
* id，可以用来判断用户是否存在
  * id bpz > /dev/null 2>&1 && echo " exists" || echo "not exists."
* finger -s  username
  * finger -s bpz,查找账户bpz的相关信息
  * -s ：仅列出使用者的帐号、全名、终端机代号与登入时间等等；
  * -m ：列出与后面接的帐号相同者，而不是利用部分比对(包括全名部分)
  * 直接使用finger，不带参数查询系统目前登录的用户    
* chfn，修改finger的相关信息，bbs中的个人属性信息修改
* chsh，修改passwd中的shell
  * -l ：列出目前系统上面可用的shell ，其实就是/etc/shells 的内容！
  * -s ：设定修改自己的Shell
* linux创建大量账号
  * 检查使用者的home、密码等问题
    * pwck
  * 检查group相关信息
    * grpck
  * chpasswd,修改密码
    * echo "bpz:123456" |grep chpasswd,会把123456自动加密处理（读取/etc/login.defs 预制信息）
#### 群组管理
* groupadd
  * -g ：后面接某个特定的GID ，用来直接给予某个GID ～
  * -r ：建立系统群组啦！与/etc/login.defs 内的GID_MIN 有关。
* groupmod
  * -g ：修改既有的GID 数字；
  * -n ：修改既有的群组名称
* groupdel
* gpasswd：群组管理员功能，管理群的成员进入或移除
  *     ：若没有任何参数时，表示给予groupname 一个密码(/etc/gshadow)
  * -A ：将groupname 的主控权交由后面的使用者管理(该群组的管理员)
  * -M ：将某些帐号加入这个群组当中！
  * -r ：将groupname 的密码移除
  * -R ：让groupname 的密码栏失效
  * -a : 通过群管理员添加用户到群里面
  * -d : 通过群管理员从群里面移除用户
#### ACL，
> ACL 是Access Control List 的缩写，主要的目的是在提供传统的 owner,group,others 的read,write,execute 权限之外的细部权限设定。
>ACL 可以针对单一使用者，单一档案或目录来进行r,w,x 的权限规范，对于需要特殊权限的使用状况非常有帮助。
* 使用者(user)：可以针对使用者来设定权限；
* 群组(group)：针对群组为对象来设定其权限；
* 预设属性(mask)：还可以针对在该目录下在建立新档案/目录时，规范新资料的预设权限；
* 大部分linux都预设了acl程序，
  * dmesg | grep -i acl ，查询系统是否支持acl
  * getfacl：取得某个档案/目录的ACL 设定项目；
  * setfacl：设定某个目录/档案的ACL 规范。
    * setfacl [-bkRd] [{-m|-x} acl参数] 目标档名
      * -m ：设定后续的acl 参数给档案使用，不可与-x 合用；
      * -x ：删除后续的acl 参数，不可与-m 合用；
      * -b ：移除『所有的』 ACL 设定参数；
      * -k ：移除『预设的』 ACL 参数，关于所谓的『预设』参数于后续范例中介绍；
      * -R ：递回设定acl ，亦即包括次目录都会被设定起来；
      * -d ：设定『预设acl 参数』的意思！只对目录有效，在该目录新建的资料会引用此预设
* setfacl ,设置权限
  * setfacl -mu:bpz:r-w acl_test,单独给文件上面设置bpz用户的r-w权限
  * setfacl -mg:test1:rw- acl_test ，给文件设置群的权限
  * setfacl -mm:r-- acl_test，给文件设置mask值，设置文件允许的最大权限
  * setfacl -md:u:bpz:rw- ./acl_dir/,让文件夹内部的文件可以继承这种acl设置，根据设置的用户权限
  * setfacl -md:g:test1:r-- ./acl_dir/，让文件夹内部的文件可以继承这种acl设置，根据设置的群组权限
  * setfacl -mu:bpz:- 2，设置用户不具备这个文件的rxw权限
  * 
* getfacl ,查看文件acl权限
  * getfacl acl_test
   * \# file: acl_test
   * \# owner: bpz
   * \# group: bpz
   * user::rwx
   * user:bpz:r-x                    #effective:r--
   * user:xp:rw-                     #effective:r--
   * group::rw-                      #effective:r--
   * group:test1:rw-                 #effective:r--
   * mask::r--      //mask设置就会和其他权限做#effective比较，避免权限设置过多
   * other::r--
   
#### 其他权限 
* 使用者身份切换
  * 使用一般帐号：系统平日操作的好习惯
  * 用较低权限启动系统服务，比如apache用户可以专门启动相关应用
  * 软体本身的限制
  * su -,切换到root用户，需要录入root用户的密码
  * sudo ,暂时拥有root用户权限操作，需要实现设置sudo相关信息，需要录入当前账号密码
* su [-lm] [-c 指令] [username]
  * - ：单纯使用- 如『 su - 』代表使用login-shell 的变数档案读取方式来登入系统；若使用者名称没有加上去，则代表切换为root 的身份。
  * -l ：与- 类似，但后面需要加欲切换的使用者帐号！也是login-shell 的方式。
  * -m ：-m 与-p 是一样的，表示『使用目前的环境设定，而不读取新使用者的设定档』
  * -c ：仅进行一次指令，所以-c 后面可以加上指令喔！
* sudo [-b] [-u 新使用者帐号]
  * -b ：将后续的指令放到背景中让系统自行执行，而不与目前的shell 产生影响
  * -u ：后面可以接欲切换的使用者，若无此项则代表切换身份为root 。
  * 执行顺序
    1. 当使用者执行sudo 时，系统于/etc/sudoers 档案中搜寻该使用者是否有执行sudo 的权限；
    2. 若使用者具有可执行sudo 的权限后，便让使用者『输入使用者自己的密码』来确认；
    3. 若密码输入成功，便开始进行sudo 后续接的指令(但root 执行sudo 时，不需要输入密码)；
    4. 若欲切换的身份与执行者身份相同，那也不需要输入密码。
* visudo 与/etc/sudoers
  * 通过visudo 修改 /etc/sudoers文件，sudoers有特定语法，所以需要通过visudo工具修改，在退出的时候，工具会校验语法，
  避免出现配置文件无法使用
#### 特殊权限
* shell, /sbin/nologin，表示账号不需要登录linux 系统
  *  /etc/nologin.txt ，文件设置nologin尝试登录时候的提示信息，默认没有这个文件
* PAM模组设定语法
  * 执行passwd命令流程
    * 使用者开始执行/usr/bin/passwd 这支程式，并输入密码；
    * passwd 呼叫PAM 模组进行验证；
    * PAM 模组会到/etc/pam.d/ 找寻与程式(passwd) 同名的设定档；
    * 依据/etc/pam.d/passwd 内的设定，引用相关的PAM 模组逐步进行验证分析；
    * 将验证结果(成功、失败以及其他讯息) 回传给passwd 这支程式；
    * passwd 这支程式会根据PAM 回传的结果决定下一个动作(重新输入新密码或者通过验证！)
  * cat /etc/pam.d/passwd,，每一行都是一个独立的验证流程， 每一行可以区分为三个栏位，分别是验证类别(type)、控制标准(flag)、PAM的模组与该模组的参数
    * \#%PAM-1.0
    * auth       include      system-auth
    * account    include      system-auth
    * password   substack     system-auth
    * -password   optional    pam_gnome_keyring.so use_authtok
    * password   substack     postlogin
  * 验证类别(Type)
    * auth,认证，需要密码校验使用者身份
    * account，授权，检验使用者是否具有正确的使用权限
    * session，使用者在这次登入(或使用这个指令) 期间，PAM 所给予的环境设定
    * password 主要在提供验证的修订工作，举例来说，就是修改/变更密码
  * 验证的控制标志（control flag)
    * required，失败会继续后续的验证流程
    * requisite，失败则立刻回报原程式failure 的标志，终止后续的验证流程
    * sufficient，成功则立刻回传success 给原程式，并终止后续的验证流程，验证失败则带有failure 标志并继续后续的验证流程
    * optional，显示讯息，不验证。
  * 常用验证类型，cat /etc/pam.d/login，cat /etc/pam.d/system-auth等内部的一些*.so
* vim /etc/security/limits.conf 
  * ulimit -a ,查询用户限制信息
* /var/log/secure, /var/log/messages，系统发生错误时，产生的信息
  * 
#### 主机上的使用者信息传递
* w, who, last, lastlog，查询使用者
* w
* last
* who
* lastlog,读取/var/log/lastlog文件，得知账号最近登录时间
* write 使用者帐号[使用者所在终端介面]
  * write bpz pts/1，之后录入信息，在ctrl+d结束录入
  *  mesg n ，接收方通过这个命令关闭接收
  * mesg y,接收方开启接收
* well "msg",给所有用户发送广播，自己也会收到
* email，给离线用户发送邮件 //TODO 还有问题，报错
  * mail -s "邮件标题" username@localhost
  * mail -s "bashrc file content" dmtsai < ~/.bashrc，
  * ls -al ~ | mail -s "myfile" root
---

### 磁盘配额（quota）  
> 针对用户，群组，目录做磁盘空间大小使用限制
* ext文件系统，只能针对文件系统做空间限制，无法针对某个目录做大小使用限制
* linux内核需要支持quota，centos内置quato
* 只能针对普通用户，无法针对root
* SElinux只能针对/home做大小限制
#### 针对XFS
* 针对用户、群组、目录
* 限制类型（block和inode）
  * inode针对文件数量
  * block针对容量
* 限制方式
  * hard，强制要求
  * soft，达到soft后，有提示，默认7天，7天过后没有处理，会直接以soft容量为空间大小限制
* 查看文件系统是否支持quota
  * df -hT /home,查看文件系统类型
    * /dev/vda1      ext3   50G   12G   36G  25% /，为ext3类型
#### RAID,磁盘阵列

#### 逻辑卷管理(Logical Volume manager)   
> LVM 的重点在可以弹性的调整filesystem 的容量
> LVM 可以整合多个实体partition 在一起， 让这些partitions 看起来就像是一个磁碟一样！
而且，还可以在未来新增或移除其他的实体partition 到这个LVM 管理的磁碟当中


### 计划任务（crotab）
* 类型cron、at
* at，一次性任务，必须atd服务支持，centos默认安装并启动
* crontab，工作可以循环执行，可编辑/etc/crontab 来支持任务
  * /etc/crontab
```shell script
     1  SHELL=/bin/bash
     2  PATH=/sbin:/bin:/usr/sbin:/usr/bin
     3  MAILTO=root，系统执行的结果传递给root，错误的和正确的，也可以设置为自己的邮箱
       
     4  # For details see man 4 crontabs
       
     5  # Example of job definition:
     6  # .---------------- minute (0 - 59)
     7  # |  .------------- hour (0 - 23)
     8  # |  |  .---------- day of month (1 - 31)
     9  # |  |  |  .------- month (1 - 12) OR jan,feb,mar,apr ...
    10  # |  |  |  |  .---- day of week (0 - 6) (Sunday=0 or 7) OR sun,mon,tue,wed,thu,fri,sat
    11  # |  |  |  |  |
    12  # *  *  *  *  * user-name  command to be executed
```
* crontab 常见工作任务
  * 执行日志文件轮询（log rotate），将就得日志文件做压缩归档
  * 登录日志分析（logwatch），软件问题、硬件问题、安全问题，都会记录到日志文件
  * 建立locate资料库，文件名快照需要更新，更新命令updatedb，/var/lib/mlocate
  * 更新man page资料库，命令说明文档，更新命令mandb，
  * RPM日志文件建立
  * 移除缓存，指令tmpwatch
  * 网络服务分析有关的操作，如分析网络凭证是否过期等
  * 其他，通过软件自带的分析工具，结合执行计划做任务编排，通过脚本做计划编排
#### atd和at
* 查看adt服务是否启动
  * systemctl status atd
  * systemctl start atd
  * systemctl stop atd
  * systemctl enable atd #让服务自动开启
  * systemctl restart atd
* /var/spool/at/ ，记录任务信息，std服务会读取里面的任务，并加以执行
* /etc/at.allow 与/etc/at.deny 限制于安全
  * 两个文件存在与否，和发行版本有关
  * 如果存在/etc/at.allow,只能在at.allow中存在的用户才能使用，其他人无法使用
  * 如果存在/etc/at.deny,存在at.deny中的用户不能使用，其他用户可以使用，一般发行版本使用这种方式，比较松散
  * 如果都不存在，那么只能root用户才能使用
* at [-mldv] TIME 
  * -m ：当at 的工作完成后，即使没有输出讯息，亦以email 通知使用者该工作已完成。
  * -l ：at -l 相当于atq，列出目前系统上面的所有该使用者的at 排程；
  * -d ：at -d 相当于atrm ，可以取消一个在at 排程中的工作；
  * -v ：可以使用较明显的时间格式列出at 排程中的工作列表；
  * -c ：可以列出后面接的该项工作的实际指令内容。
  
  * TIME：时间格式，这里可以定义出『什么时候要进行at 这项工作』的时间，格式有：
  *   HH:MM ex> 04:00
  * 	在今日的HH:MM 时刻进行，若该时刻已超过，则明天的HH:MM 进行此工作。
  *   HH:MM YYYY-MM-DD ex> 04:00 2015-07-30
  * 	强制规定在某年某月的某一天的特殊时刻进行该工作！
  *   HH:MM[am|pm] [Month] [Date] ex> 04pm July 30
  * 	也是一样，强制在某年某月某日的某时刻进行！
  *   HH:MM[am|pm] + number [minutes|hours|days|weeks]
  * 	ex> now + 5 minutes ex> 04pm + 3 days
  * 	就是说，在某个时间点『再加几个时间后』才进行。
  * 实际执行命令格式
    * at 10:57 2022-02-21 
    \at> 录入具体命令
  * at 的执行与终端机环境无关，而所有standard output/standard error output 都会传送到执行者的mailbox 
  * 系统会将该项at 工作独立出你的bash 环境中， 直接交给系统的atd 程式来接管，剩下的工作就完全交给Linux 管理
  * atq,查询当前用户的at任务，和at -l一样
  * atrm number，删除命令 number，每个at任务都有一个number
* batch，系统有空时才执行的任务
  * 利用at来下达任务，要在cpu的负载小于0.8时才会执行
* uptime，查询系统负载情况
* jobs ,查询系统背景执行任务
* kill，杀死进程，kill -9 %1 %2 %3,这里的%1 %2 %3 就是后台任务

#### crontab
* /etc/cron.allow,/etc/cron.deny，与at一样，/etc/cron.allow 比/etc/cron.deny 要优先，建议只保留一个文件
  * /etc/cron.allow,在其中的user才能使用
  * /etc/cron.deny,在其中的user不能使用
* /var/spool/cron/，任务编排后会按照用户名建立文件夹，任务会写进其中
* /var/log/cron,任务执行后所有的信息都会记录到这里面
* crontab [-u username] [-l|-e|-r]
  * -u ：只有root 才能进行这个任务，亦即帮其他使用者建立/移除crontab 工作排程；
  * -e ：编辑crontab 的工作内容
  * -l ：查阅crontab 的工作内容
  * -r ：移除所有的crontab 的工作内容，若仅要移除一项，请用-e 去编辑。
  
  * 如：crotab -e
  * 0 12 * * * mail -s "at 12:00" bpz < /home/dmtsai/.bashrc ，每天12点发送邮件给bpz
  * 系统任务配置
    *  /etc/crontab, /etc/cron.d/*
* 系统执行crontab会到三个文件中去读取配置信息
  * /etc/crontab 系统级别任务配置
  * /etc/cron.d/* 系统级别任务配置
  * /var/spool/cron/* 个人任务配置
* anacron，时间已经过了但是还没有执行的任务，由这个任务执行（并非一个服务，而是借助crontab执行），每个小时被crontab执行一次
  * 任务放在/etc/cron.hourly里面 ，
  * anacron [-sfn] [job].. 
    * -s ：开始一连续的执行各项工作(job)，会依据时间记录档的资料判断是否进行；
    * -f ：强制进行，而不去判断时间记录档的时间戳记；
    * -n ：立刻进行未进行的任务，而不延迟(delay) 等待时间；
    * -u ：仅更新时间记录档的时间戳记，不进行任何工作。
    * job ：由/etc/anacrontab 定义的各项工作名称。
### 进程管理
  * 程序，由使用者触发，放在硬盘上，出发后生成一个进程放到内存中，进程给予使用者的权限是不一样的，每个进程启动后会有一个PID,系统根据这个PID来判断权限
  * 用户登录系统获得一个bash,就会得到一个相应权限的PID，通过这个bahs启动的程序，一般情况也会沿用这个pid的权限
  * 父子进程
  * 程序调动流程（fork-and-exec）
  * 系统或者网络服务：常驻内存的进程（daemon，守护进程，所以如atd等服务后面会带一个d）
  * 通过在命令后面添加 & ,提交后台进程
#### 任务管理(job control)
* 在单一终端下同时执行多个任务的操作管理
* 无法以job control的方式在一个终端如tty1中管理tty2终端中的程序
  * 在/etc/security/limits.conf 中可以配置一个用户的最大连接数（如可以控制一个用户只能打开 一个终端）
* 前台程序（foreground）和后端程序(background)，后端程序无法交互,所以，vi无法以后端程序方式运行，在后台程序无法通过ctrl+c来中断
* 将程序提交后端运行，&
  * tar -zpcf /tmp/etc.tar.gz /etc & 
  * 后端运行的程序也会把stdout 及stderr 输出到屏幕上，所以需要通过从定向把输出从定向到其他地方
  * tar -zpcvf /tmp/etc.tar.gz /etc > /tmp/log.txt 2>&1 &  //正确错误都从定向到/tmp/log.txt中
* 快捷键ctrl+z,把当前程序提交到后端，如在vim中ctrl+z提交这个vim到后台处于暂停状态
* jobs -l，查询目前所有的后端程序
  * [1]- 14566 Stopped vim ~/.bashrc，减号，-表示第二个提交到后台的程序
  * [2]+ 14567 Stopped find / -print,+,表示第一个提交到后台的程序
  * 如果是加号（+），则fg命令会默然把这个程序提交到前台
  * 如果超过两个后端程序，就没有+/-号
* ctrl+z,提交到后台暂停运行
* fg %jobnumber,提交程序到前台
  * %jobnumber ：jobnumber 为工作号码(数字)。注意，那个% 是可有可无的！
* bg %jobnumber,提交到后台处于运行状态
* kill -signal %jobnumber ，管理进程
  * -l ：这个是L 的小写，列出目前kill 能够使用的讯号(signal) 有哪些？
  * signal ：代表给予后面接的那个工作什么样的指示啰！用man 7 signal 可知：
      * -1 ：重新读取一次参数的设定档(类似reload)；
      * -2 ：代表与由键盘输入[ctrl]-c 同样的动作；
      * -9 ：立刻强制删除一个工作；
      * -15：以正常的程序方式终止一项工作。与-9 是不一样的。默认自带
  * kill -9 %2，强制终止%2的进程
* nohup，提交系统后台运行，与&不一样，&只是提交到当前控制台的后端运行，如果推出当前登录，就会结束
  * nohup ./sleep500.sh &，程序提交到系统后台运行，所有的输出会从定向到~/nohup.out
* pstree，以树形结构显示进程
#### 进程管理
* ps aux,静态列出所有的进程
* ps -lA  <==也是能够观察所有系统的资料
* ps -axjf  <==连同部分程序树状态
  * -A ：所有的process 均显示出来，与-e 具有同样的效用；
  * -a ：不与terminal 有关的所有process ；
  * -u ：有效使用者(effective user) 相关的process ；
  * x ：通常与a 这个参数一起使用，可列出较完整资讯。
  * 输出格式规划：
  * -l ：较长、较详细的将该PID 的的资讯列出；
  * -j ：工作的格式(jobs format)
  * -f ：做一个更为完整的输出。

* ps -l ,列出自己的所有程序
```shell script
F S   UID   PID  PPID  C PRI  NI ADDR SZ WCHAN  TTY          TIME CMD
0 R  1000 29936 29989  0  80   0 - 38330 -      pts/1    00:00:00 ps
0 S  1000 29989 29988  0  80   0 - 29133 do_wai pts/1    00:00:00 bash
```
  * F,表示权限
    * 4,表示root
    * 1，若为1 则表示此子程序仅进行复制(fork)而没有实际执行(exec)
  * S，状态
    * R (Running)：该程式正在运作中；
    * S (Sleep)：该程式目前正在睡眠状态(idle)，但可以被唤醒(signal)。
    * D ：不可被唤醒的睡眠状态，通常这支程式可能在等待I/O 的情况(ex>列印)
    * T ：停止状态(stop)，可能是在工作控制(背景暂停)或除错(traced) 状态；
    * Z (Zombie)：僵尸状态，程序已经终止但却无法被移除至记忆体外。
  * UID/PID/PPID：代表『此程序被该UID 所拥有/程序的PID 号码/此程序的父程序PID 号码』
  * C：代表CPU 使用率，单位为百分比
  * PRI/NI：Priority/Nice 的缩写，代表此程序被CPU 所执行的优先顺序，数值越小代表该程序越快被 CPU 执行
  * ADDR,都内存有关，ADDR 是kernel function，指出该程序在内存的哪个部分，如果是个running 的程序，一般就会显示『 - 』
  * SZ，程序用掉多少内存
  * WCHAN： 目前程序是否运作中，若为- 表示正在运作中。
  * TTY：登入者的终端机位置，若为远端登入则使用动态终端介面(pts/n)
  * TIME：使用掉的CPU 时间，注意，是此程序实际花费CPU 运作的时间，而不是系统时间
  * CMD：就是command 的缩写，造成此程序的触发程式之指令为何
* ps aux,没有-号
```shell script
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         2  0.0  0.0      0     0 ?        S     2021   0:05 [kthreadd]
```
  * VSZ ：该process 使用掉的虚拟记忆体量(Kbytes)
  * RSS ：该process 占用的固定的记忆体量(Kbytes)
  * TTY ：该process 是在那个终端机上面运作，若与终端机无关则显示?，另外， tty1-tty6 是本机上面的登入者程序，若为pts/0 等等的，则表示为由网路连接进主机的程序。
* ps aux | egrep '(cron|rsyslog)',查询与cron，syslog有关的进程
* 系统不稳定的时候就容易造成所谓的僵尸程序

* top：动态观察程序的变化
  * top [-d 数字] | top [-bnp]
  * 选项与参数：
  * -d ：后面可以接秒数，就是整个程序画面更新的秒数。预设是5 秒；
  * -b ：以批次的方式执行top ，还有更多的参数可以使用喔！
  *       通常会搭配资料流重导向来将批次的结果输出成为档案。
  * -n ：与-b 搭配，意义是，需要进行几次top 的输出结果。
  * -p ：指定某些个PID 来进行观察监测而已。
    * top -d 4 -p 29989
  * 在top 执行过程当中可以使用的按键指令：
  * 	? ：显示在top 当中可以输入的按键指令；
  * 	P ：以CPU 的使用资源排序显示；
  * 	M ：以Memory 的使用资源排序显示；
  * 	N ：以PID 来排序喔！
  * 	T ：由该Process 使用的CPU 时间累积(TIME+) 排序。
  * 	k ：给予某个PID 一个讯号(signal)
  * 	r ：给予某个PID 重新制订一个nice 值。
  * 	q ：离开top 软体的按键。
* 查询当前bash pid
  * echo $$

* pstree
  * pstree [-A|U] [-up]
  * -A ：各程序树之间的连接以ASCII 字元来连接；
  * -U ：各程序树之间的连接以万国码的字元来连接。在某些终端介面下可能会有错误；
  * -p ：并同时列出每个process 的PID；
  * -u ：并同时列出每个process 的所属帐号名称。
  * pstree -Aup|egrep 'java|mysql'
```shell script
        ├─java(bpz)───27*[{java}]
        ├─mysqld(mysql)───49*[{mysqld}]
```
  * systemd ,是1号程序，有内核调用的第一个程序
* nice [-n 数字] command，系统调用优先级，新执行的指令即给予新的nice 值
* renice [number] PID，已存在程序的nice 重新调整
* free，内存使用情况监控
  * -b ：直接输入free 时，显示的单位是Kbytes，我们可以使用b(bytes), m(Mbytes)
          k(Kbytes), 及g(Gbytes) 来显示单位喔！也可以直接让系统自己指定单位(-h)
  * -t ：在输出的最终结果，显示实体记忆体与swap 的总量。
  * -s ：可以让系统每几秒钟输出一次，不间断的一直输出的意思！对于系统观察挺有效！
  * -c ：与-s 同时处理～让free 列出几次的意思～
  * free -m,如果swap的空间使用超过20%,就是内存不够
* uname：查阅系统与核心相关资讯
  * -a ：所有系统相关的资讯，包括底下的资料都会被列出来；
  * -s ：系统核心名称
  * -r ：核心的版本
  * -m ：本系统的硬体名称，例如i686 或x86_64 等；
  * -p ：CPU 的类型，与-m 类似，只是显示的是CPU 的类型！
  * -i ：硬体的平台(ix86)
* uptime，查询的是top第一行的信息
* netstat
  * -a ：将目前系统上所有的连线、监听、Socket 资料都列出来
  * -t ：列出tcp 网路封包的资料
  * -u ：列出udp 网路封包的资料
  * -n ：不以程序的服务名称，以埠号(port number) 来显示；
  * -l ：列出目前正在网路监听(listen) 的服务；
  * -p ：列出该网路服务的程序PID
  * netstat -tulnp, 查询系统存在监听的进程
* dmesg ：分析核心产生的讯息
* vmstat [-a] [延迟[总计侦测次数]]  <==CPU/记忆体等资讯
* vmstat [-fs]                       <==记忆体相关
* vmstat [-S 单位]                   <==设定显示数据的单位
* vmstat [-d]                        <==与磁碟有关
* vmstat [-p 分割槽]                 <==与磁碟有关
  * vmstat 1 3，监控cpu信息，间隔1s，总共3s
  
* 其他文件相关
  * /proc/* 代表的意义，内存中的信息
    * cat /proc/1/cmdline，查询pid为1的cmdline 的参数，就是systemd启动的参数
    * cat /proc/cmdline,kener启动时候的参数
    * cat /proc/cpuinfo，本机的CPU 的相关资讯
    * cat /proc/filesystems,载入的文件系统
    * cat /proc/meminfo,内存
* fuser，借由文件找出正在使用该文件的进程
  * fuser -mvu .
   * -u  ：除了程序的 PID 之外，同時列出該程序的擁有者；
   * -m  ：後面接的那個檔名會主動的上提到該檔案系統的最頂層，對 umount 不成功很有效！
   * -v  ：可以列出每個檔案與程序還有指令的完整相關性！
   * -k  ：找出使用該檔案/目錄的 PID ，並試圖以 SIGKILL 這個訊號給予該 PID；
   * -i  ：必須與 -k 配合，在刪除 PID 之前會先詢問使用者意願！
   * -signal：例如 -1 -15 等等，若不加的話，預設是 SIGKILL (-9) 囉！
```shell script
                     USER        PID ACCESS COMMAND
/home/bpz/workspace/scripts:
                     root     kernel swap  (root)/tmp/swap
                     root     kernel mount (root)/
                     bpz        5459 Frce. (bpz)java
                     bpz       29989 .rce. (bpz)bash
```
    * c ：此程序在當前的目錄下(非次目錄)；
    * e ：可被觸發為執行狀態；
    * f ：是一個被開啟的檔案；
    * r ：代表頂層目錄 (root directory)；
    * F ：該檔案被開啟了，不過在等待回應中；
    * m ：可能為分享的動態函式庫；
    * 如： fuser -uvm genealogy-0.0.1-SNAPSHOT.jar 查询当前文件被哪个进程使用
* lsof,列出被进程使用的文件
  *  lsof [-aUu] [+d]
    * -a  ：多項資料需要『同時成立』才顯示出結果時！
    * -U  ：僅列出 Unix like 系統的 socket 檔案類型；
    * -u  ：後面接 username，列出該使用者相關程序所開啟的檔案；
    * +d  ：後面接目錄，亦即找出某個目錄底下已經被開啟的檔案！
  * lsof -u bpz -a -U
```shell script
COMMAND  PID USER   FD   TYPE             DEVICE SIZE/OFF      NODE NAME
java    5459  bpz   16u  unix 0xffff9b47668eb740      0t0 159619563 socket
java    5459  bpz   17u  unix 0xffff9b47668ea640      0t0 159619561 socket
```
* pidof,根据程序名称查询pid
  * pidof -sx java
### 系统服务
> daemon程序，守护进程
#### 早期的系统服务
* System V 的init 管理行为中daemon主要分类，所有的服务启动脚本通通放置于/etc/init.d/ 底下，都是使用bash shell script 所写成的脚本程序
  * 启动：/etc/init.d/daemon start
  * 关闭：/etc/init.d/daemon stop
  * 重新启动：/etc/init.d/daemon restart
  * 状态观察：/etc/init.d/daemon status
* 服务启动分类
  * 独立启动模式（stand alone），服务独立启动，常驻内存，反应速度快
  * 总管程序（super daemon），有xinetd或inetd两个总管程序提供socket或port对应管理，没有用户使用socket或port，服务是关闭的，
  有人使用才会通过xinetd或inetd去启动，并提供服务，速度有延迟，方便管理
* 服务的依赖性，a服务需要b服务时，需要管理员去启动b服务，在启动a服务
* 执行等级，init 是开机后核心主动调用，init根据管理员定制区调用相应等级linux提供7中等级（0，1，2...6），1)单人维护模式、3)纯文字模式、5)文字加图形界面
启动脚本放在/etc/rc.d/rc[0-6]/SXXdaemon 连结到/etc/init.d/daemon，连结档名(SXXdaemon) 的功能为： S为启动该服务，XX是数字，为启动的顺序，
因此在开机时可以『依序执行』所有需要的服务， 同时也能解决相依服务的问题
* 制定执行等级预设要启动的服务
  * 预设要启动： chkconfig daemon on
  * 预设不启动： chkconfig daemon off
  * 观察预设为启动否： chkconfig --list daemon
* 执行等级的切换行为
  * 从纯文字界面(runlevel 3) 切换到图形界面(runlevel 5)，只需要，init 5
#### systemd daemon服务管理
* centos 7以后，改用了systemd启动服务管理
  * 平行处理所有服务，加速开机流程，不会像init程序一样需要一项一项启动
  * systemd 全部就是仅有一只systemd 服务搭配systemctl 指令来处理，无须其他额外的指令来支援，
    * system v需要init，chkconfig，service等命令配合使用
  * 服务相依性的自我检查，a调用b，b没有启动，程序自动启动b
  * 依daemon 功能分类
    * init仅分stand alone和super daemon
    * systed定义了服务为服务单元（unit），unit分为很多类型（type），如service, socket, target, path, snapshot, timer
  * 将多个daemons 集合成为一个群组
    * systemV 的init 里头有个runlevel 的特色
    * systemd 亦将许多的功能集合成为一个所谓的target 项目，启动一个target会执行多个daemon
  * 向下兼容init，systemd能够管理/etc/init.d脚本
* systemd的配置文件
  * /usr/lib/systemd/system/：每个服务最主要的启动脚本设定，有点类似以前的/etc/init.d 底下的档案；
  * /run/systemd/system/：系统执行过程中所产生的服务脚本，这些脚本的优先序要比/usr/lib/systemd/system/ 高！
  * /etc/systemd/system/：管理员依据主机系统的需求所建立的执行脚本，其实这个目录有点像以前/etc/rc.d/rc5.d/Sxx 之类的功能！
  执行优先序又比/run/systemd/system/ 高喔！
* unit类型
  *  ll /usr/lib/systemd/system |egrep '(vsftpd|multi|cron)'
  * .service	一般服务类型(service unit)：主要是系统服务，包括伺服器本身所需要的本机服务以及网路服务都是！比较经常被使用到的服务大多是这种类型！所以，这也是最常见的类型了！
  * .socket	  内部程序资料交换的插槽服务(socket unit)：主要是IPC (Inter-process communication) 的传输讯息插槽档(socket file) 功能。这种类型的服务通常在监控讯息传递的插槽档，当有透过此插槽档传递讯息来说要连结服务时，
  就依据当时的状态将该用户的要求传送到对应的daemon， 若daemon 尚未启动，则启动该daemon 后再传送用户的要求。使用socket 类型的服务一般是比较不会被用到的服务，因此在开机时通常会稍微延迟启动的时间 (因为比较没有这么常用嘛！)。一般用于本机服务比较多，例如我们的图形界面很多的软体都是透过socket 来进行本机程序资料交换的行为。(这与早期的xinetd 这个super daemon 有部份的相似喔！)
  * .target	执行环境类型(target unit)：其实是一群unit 的集合，例如上面表格中谈到的multi-user.target 其实就是一堆服务的集合～也就是说， 选择执行multi-user.target 就是执行一堆其他.service 或/及.socket 之类的服务就是了！
  * .mount
  * .automount	档案系统挂载相关的服务(automount unit / mount unit)：例如来自网路的自动挂载、NFS 档案系统挂载等与档案系统相关性较高的程序管理。
  * .path	侦测特定档案或目录类型(path unit)：某些服务需要侦测某些特定的目录来提供伫列服务，例如最常见的列印服务，就是透过侦测列印伫列目录来启动列印功能！这时就得要.path 的服务类型支援了！
  * .timer	循环执行的服务(timer unit)：这个东西有点类似anacrontab 喔！不过是由systemd 主动提供的，比anacrontab 更加有弹性！
#### systemctl命令
* systemctl,服务统一通过systemctl来管理
  *  systemctl [command] [unit] 
  * start ：立刻启动后面接的unit
  * stop ：立刻关闭后面接的unit
  * restart ：立刻关闭后启动后面接的unit，亦即执行stop 再start 的意思
  * reload ：不关闭后面接的unit 的情况下，重新载入设定档，让设定生效
  * enable ：设定下次开机时，后面接的unit 会被启动
  * disable ：设定下次开机时，后面接的unit 不会被启动
  * status ：目前后面接的这个unit 的状态，会列出有没有正在执行、开机预设执行否、登录等资讯等！
  * is-active ：目前有没有正在运作中
  * is-enabled：开机时有没有预设要启用这个unit
  * systemctl status atd.service，查看状态
    * active (running)：正有一只或多只程序正在系统中执行的意思，举例来说，正在执行中的vsftpd 就是这种模式。
    * active (exited)：仅执行一次就正常结束的服务，目前并没有任何程序在系统中执行。举例来说，开机或者是挂载时才会进行一次的quotaon 功能，就是这种模式！quotaon 不须一直执行～只须执行一次之后，就交给档案系统去自行处理啰！通常用bash shell 写的小型服务，大多是属于这种类型(无须常驻记忆体)。
    * active (waiting)：正在执行当中，不过还再等待其他的事件才能继续处理。举例来说，列印的伫列相关服务就是这种状态！虽然正在启动中，不过，也需要真的有伫列进来(列印工作) 这样他才会继续唤醒印表机服务来进行下一步列印的功能。
    * inactive：这个服务目前没有运作的意思。
    * 既然daemon 目前的状态就有这么多种了，那么daemon 的预设状态有没有可能除了enable/disable 之外，还有其他的情况呢？当然有！
      * enabled：这个daemon 将在开机时被执行
      * disabled：这个daemon 在开机时不会被执行
      * static：这个daemon 不可以自己启动(enable 不可)，不过可能会被其他的enabled 的服务来唤醒(相依属性的服务)
      * mask：这个daemon 无论如何都无法被启动！因为已经被强制注销(非删除)。可透过systemctl unmask 方式改回原本状态
  * 不应该通过kill来关闭一个服务，不然systemctl无法继续管理
* systemctl,列出系统中已经启动的服务
* systemctl list-unit-files,系统已经安装的服务（已经有配置文件），可能并没有启动
  * systemctl list-unit-files|grep mysql
  * systemctl list-units --type=service --all | grep cpu //查询所有.server，关于cpu的服务
  * systemctl list-units 是systemctl的默认参数
* centos 7.1内置26种unit.target
* systemctl get-default,返回默认的引导信息，操作界面（multi-user.target 文字操作界面）
* systemctl isolate multi-user.target，切换操作界面为文本形式
* 精简操作指令
  * systemctl reboot
  * systemctl poweroff
  * systemctl suspend,暂停，暂停模式会将系统的状态资料保存到记忆体中，然后关闭掉大部分的系统硬体，电脑不会关机
  * systemctl hibernate,休眠，休眠模式则是将系统状态保存到硬碟当中，保存完毕后，将电脑关机
  * systemctl rescue,强制进入救援模式
  * systemctl emergency，强制进去紧急救援模式
* 查看服务之间的依赖性
  * systemctl list-dependencies [unit] [--reverse]
  * systemctl list-dependencies ,查询默认target依赖的unit列表
  * systemctl list-dependencies --reverse，查询哪个会依赖defalut.target
  * systemctl list-dependencies mysqld.service,查询mysqld.service依赖哪些unit
* 服务脚本放置位置
  * /usr/bin/systemd/system,官方centos安装的服务脚本
  * /run/systemd/system,系统运行期间建立的执行脚本，优先级高于/usr/bin/systemd/system
  * /etc/sysconfig/*,所有的服务脚本的启动参数放置位置，如：/etc/systemconfig/network-scripts,放置网络相关脚本
  * /var/lib,生产数据的服务，会把数据放置这个位置，如mysqld服务
  * /run/放置daemon服务的缓存，包括lock和PID
* systemctl list-sockets --all，正在监听本地服务需求的socket文件所在位置
* 针对server类型建立服务
  * 需要修改官方的服务，不建议修改/usr/bin/systemd/system里面的脚本
    * /etc/systemd/system/ vsftpd.service.d /custom.conf，在/etc/systemd/system 底下建立与设定档相同档名的目录，但是要加上.d 的副档名。然后在该目录下建立设定档即可
      * 设定档最好附档名取名为.conf 较佳！在这个目录下的档案会『累加其他设定』进入/usr/lib/systemd/system/vsftpd.service 内喔
    * /etc/systemd/system/ vsftpd.service.wants /*，此目录为连接文件，设置依赖服务的连接，意思在启动vsftpd.service之前应该启动哪个服务
    * /etc/systemd/system/ vsftpd.service.requires /*，此目录为连接文件，设置依赖服务的链接，意思是在启动 vsftpd.service 之前，需要事先启动哪些服务的意思
  * sshd示例
```shell script
[Unit] # unit解释，执行服务依赖
Description=OpenSSH server daemon
Documentation=man:sshd(8) man:sshd_config(5)
After=network.target sshd-keygen.service
Wants=sshd-keygen.service

[Service] # 项目实际执行指令
Type=notify
EnvironmentFile=/etc/sysconfig/sshd
ExecStart=/usr/sbin/sshd -D $OPTIONS
ExecReload=/bin/kill -HUP $MAINPID
KillMode=process
Restart=on-failure
RestartSec=42s

[Install] # 挂载在哪个target下面
WantedBy=multi-user.target
```
    * Description	就是当我们使用systemctl list-units 时，会输出给管理员看的简易说明！当然，使用systemctl status 输出的此服务的说明，也是这个项目！
    * Documentation	这个项目在提供管理员能够进行进一步的文件查询的功能！提供的文件可以是如下的资料：
    * Documentation=http://www....
    * Documentation=man:sshd(8)
    * Documentation=file:/etc/ssh/sshd_config
    * After	说明此unit 是在哪个daemon 启动之后才启动的意思！基本上仅是说明服务启动的顺序而已，并没有强制要求里头的服务一定要启动后此unit 才能启动。以sshd.service 的内容为例，该档案提到After 后面有network.target 以及sshd-keygen.service，但是若这两个unit 没有启动而强制启动sshd.service 的话， 那么sshd.service 应该还是能够启动的！这与Requires 的设定是有差异的喔！
    * Before	与After 的意义相反，是在什么服务启动前最好启动这个服务的意思。不过这仅是规范服务启动的顺序，并非强制要求的意思。
    * Requires	明确的定义此unit 需要在哪个daemon 启动后才能够启动！就是设定相依服务啦！如果在此项设定的前导服务没有启动，那么此unit 就不会被启动！
    * Wants	与Requires 刚好相反，规范的是这个unit 之后最好还要启动什么服务比较好的意思！不过，并没有明确的规范就是了！主要的目的是希望建立让使用者比较好操作的环境。因此，这个Wants 后面接的服务如果没有启动，其实不会影响到这个unit 本身！
    * Conflicts	代表冲突的服务！亦即这个项目后面接的服务如果有启动，那么我们这个unit 本身就不能启动！我们unit 有启动，则此项目后的服务就不能启动！反正就是冲突性的检查啦！
    
    * Type	说明这个daemon 启动的方式，会影响到ExecStart 喔！一般来说，有底下几种类型
        * simple：预设值，这个daemon 主要由ExecStart 接的指令串来启动，启动后常驻于记忆体中。
        * forking：由ExecStart 启动的程序透过spawns 延伸出其他子程序来作为此daemon 的主要服务。原生的父程序在启动结束后就会终止运作。传统的unit 服务大多属于这种项目，例如httpd 这个WWW 服务，当httpd 的程序因为运作过久因此即将终结了，则systemd 会再重新生出另一个子程序持续运作后， 再将父程序删除。据说这样的效能比较好！！
        * oneshot：与simple 类似，不过这个程序在工作完毕后就结束了，不会常驻在记忆体中。
        * dbus：与simple 类似，但这个daemon 必须要在取得一个D-Bus 的名称后，才会继续运作！因此设定这个项目时，通常也要设定BusName= 才行！
        * idle：与simple 类似，意思是，要执行这个daemon 必须要所有的工作都顺利执行完毕后才会执行。这类的daemon 通常是开机到最后才执行即可的服务！
        * 比较重要的项目大概是simple, forking 与oneshot 了！毕竟很多服务需要子程序(forking)，而有更多的动作只需要在开机的时候执行一次(oneshot)，例如档案系统的检查与挂载啊等等的。
    *  EnvironmentFile	可以指定启动脚本的环境设定档！例如sshd.service 的设定档写入到/etc/sysconfig/sshd 当中！你也可以使用Environment= 后面接多个不同的Shell 变数来给予设定！
    *  ExecStart	就是实际执行此daemon 的指令或脚本程式。你也可以使用ExecStartPre (之前) 以及ExecStartPost (之后) 两个设定项目来在实际启动服务前，进行额外的指令行为。但是你得要特别注意的是，指令串仅接受『指令参数参数...』的格式，不能接受<, >, >>, |, & 等特殊字符，很多的bash 语法也不支援喔！所以，要使用这些特殊的字符时，最好直接写入到指令脚本里面去！不过，上述的语法也不是完全不能用，亦即，若要支援比较完整的bash 语法，那你得要使用Type=oneshot 才行喔！其他的Type 才不能支援这些字符。
    *  ExecStop	与systemctl stop 的执行有关，关闭此服务时所进行的指令。
    *  ExecReload	与systemctl reload 有关的指令行为
    *  Restart	当设定Restart=1 时，则当此daemon 服务终止后，会再次的启动此服务。举例来说，如果你在tty2 使用文字界面登入，操作完毕后登出，基本上，这个时候tty2 就已经结束服务了。但是你会看到萤幕又立刻产生一个新的tty2 的登入画面等待你的登入！那就是Restart 的功能！除非使用systemctl 强制将此服务关闭，否则这个服务会源源不绝的一直重复产生！
    *  RemainAfterExit	当设定为RemainAfterExit=1 时，则当这个daemon 所属的所有程序都终止之后，此服务会再尝试启动。这对于Type=oneshot 的服务很有帮助！
    *  TimeoutSec	若这个服务在启动或者是关闭时，因为某些缘故导致无法顺利『正常启动或正常结束』的情况下，则我们要等多久才进入『强制结束』的状态！
    *  KillMode	可以是process, control-group, none 的其中一种，如果是process 则daemon 终止时，只会终止主要的程序(ExecStart 接的后面那串指令)，如果是control-group 时， 则由此daemon 所产生的其他control-group 的程序，也都会被关闭。如果是none 的话，则没有程序会被关闭喔！
    *  RestartSec	与Restart 有点相关性，如果这个服务被关闭，然后需要重新启动时，大概要sleep 多少时间再重新启动的意思。预设是100ms (毫秒)。
    
    * WantedBy	这个设定后面接的大部分是*.target unit ！意思是，这个unit 本身是附挂在哪一个target unit 底下的！一般来说，大多的服务性质的unit 都是附挂在multi-user.target 底下！
    * Also	当目前这个unit 本身被enable 时，Also 后面接的unit 也请enable 的意思！也就是具有相依性的服务可以写在这里呢！
    * Alias	进行一个连结的别名的意思！当systemctl enable 相关的服务时，则此服务会进行连结档的建立！以multi-user.target 为例，这个家伙是用来作为预设操作环境default.target 的规划， 因此当你设定用成default.target 时，这个/etc/systemd/system/default.target 就会连结到/usr/lib/systemd/system/multi-user.target 啰！
 * 重复启动相同服务，如agetty,agetty.target
   * cat /usr/lib/systemd/system/agetty.target,查看agetty.unit,其中的ExecStart命令
     * ExecStart=-/sbin/agetty --noclear %I $TERM
       * %I,规范命名
   1. 修改启动配置文件
     * systemctl show getty.target|grep -i after
     * 先看/usr/lib/systemd/system/, /etc/systemd/system/ 有没有getty@tty1.service 的设定，若有就执行，若没有则执行下一步；
     * 找getty@.service 的设定，若有则将@ 后面的资料带入成%I 的变数，进入getty@.service 执行！
       * 原始档案：执行服务名称@.service
       * 执行档案：执行服务名称@范例名称.service
     * vim /etc/systemd/logind.config
```shell script
[Login]
NAutoVTs=3  #修改这个为3个tty节点
ReserveVT=0
#KillUserProcesses=no
#KillOnlyUsers=
#KillExcludeUsers=root
#InhibitDelayMaxSec=5
#HandlePowerKey=poweroff
#HandleSuspendKey=suspend
#HandleHibernateKey=hibernate
#HandleLidSwitch=suspend
#HandleLidSwitchDocked=ignore
#PowerKeyIgnoreInhibited=no
#SuspendKeyIgnoreInhibited=no
#HibernateKeyIgnoreInhibited=no
#LidSwitchIgnoreInhibited=yes
#IdleAction=ignore
#IdleActionSec=30min
#RuntimeDirectorySize=10%
#RemoveIPC=no
#UserTasksMax=
```
  2. 修改服务启动文件参数
    * cat /usr/lib/systemd/system/vsftpd@.service 
      * ExecStart=/usr/sbin/vsftpd /etc/vsftpd/%i.conf 这里的启动参数配置文件在“/etc/vsftpd/%i.conf”，所以去新增一个/etc/vsftpd/%i.conf文件
    * cp /etc/vsftpd/vsftpd.conf /etc/vsftpd/vsftpd3.conf
      * listen_port=2121 ,添加配置
    * systemctl status vsftpd@vsftpd3.service ，查询服务（程序回去查询对应的服务）   
      *  Loaded: loaded (/usr/lib/systemd/system/vsftpd@.service; disabled; vendor preset: disabled)
    * systemctl start vsftpd@vsftpd3.service，根据自己的配置，启动新的服务
```shell script
vsftpd@vsftpd3.service - Vsftpd ftp daemon
           Loaded: loaded (/usr/lib/systemd/system/vsftpd@.service; disabled; vendor preset: disabled)
           Active: active (running) since Tue 2022-02-22 15:15:00 CST; 2s ago
          Process: 29964 ExecStart=/usr/sbin/vsftpd /etc/vsftpd/%i.conf (code=exited, status=0/SUCCESS)
         Main PID: 29965 (vsftpd)
           CGroup: /system.slice/system-vsftpd.slice/vsftpd@vsftpd3.service
                   └─29965 /usr/sbin/vsftpd /etc/vsftpd/vsftpd3.conf
        
        Feb 22 15:15:00 VM-0-9-centos systemd[1]: Starting Vsftpd ftp daemon...
        Feb 22 15:15:00 VM-0-9-centos systemd[1]: Started Vsftpd ftp daemon.
```
* 新建自己的服务
  * 编写执行脚本
```shell script
#!/bin/bash
# 备份文件
basepath=/home/bpz/workspace/scripts/
files=$(basename -a $(find ${basepath} -mtime 0 -type f|grep ".sh"))
filecount=$(echo "${files}"|wc -w)
if [ "${filecount}" -eq 0 ];then
  echo "没有发现文件."
  exit 0
fi
echo "start tar gzip."
tar -czvp -f ${basepath}$(date +%Y-%m-%d).tar.gz ${files}
if [ $? -ne 0 ];then
  echo "备份文件失败."
  exit 1
fi
echo "备份文件成功"
exit 0
```
  * 编写服务脚本,/etc/systemd/system/backupbpzscripts.service
```shell script
[Unit]
Description=backup my server
Requires=atd.service

[Service]
Type=simple
ExecStart=/bin/bash -c " echo /home/bpz/workspace/scripts/tar_v.sh | at now"

[Install]
WantedBy=multi-user.target 
```
  * 加载服务脚本到systemctl中
    * systemctl daemon-reload
  * 查询服务
    * systemctl list-unit-files|grep backupbpzscripts.service
  * 启动服务
    * systemctl start backupbpzscripts.service
  * 查询服务状态
    * systemctl status backupbpzscripts.service
  * 查看服务的内部运行情况
    * systemctl show backupbpzscripts.service
#### 使用timers.target
* systemd.timer优势
  * 由于所有的systemd 的服务产生的资讯都会被纪录(log)，因此比crond 在debug 上面要更清楚方便的多；
  * 各项timer 的工作可以跟systemd 的服务相结合；
  * 各项timer 的工作可以跟control group (cgroup，用来取代/etc/secure/limit.conf 的功能) 结合，来限制该工作的资源利用
* 前置条件
  * 系统的timer.target 一定要启动
  * 要有个xx.service 的服务存在(xx 是你自己指定的名称)
  * 要有个xx.timer 的时间启动服务存在
* xx.timer配置()
  * /etc/systemd/system 底下去建立这个xx.timer文件,这个xx名称要与xx.service同名
  * 配置xx.timer(man systemd.timer & man systemd.time)
```shell script
OnActiveSec	当timers.target 启动多久之后才执行这只unit
OnBootSec	当开机完成后多久之后才执行
OnStartupSec	当systemd 第一次启动之后过多久才执行
OnUnitActiveSec	这个timer 设定档所管理的那个unit 服务在最后一次启动后，隔多久后再执行一次的意思
OnUnitInactiveSec	这个timer 设定档所管理的那个unit 服务在最后一次停止后，隔多久再执行一次的意思。
OnCalendar	使用实际时间(非循环时间) 的方式来启动服务的意思！至于时间的格式后续再来谈。
Unit	一般来说不太需要设定，因此如同上面刚刚提到的，基本上我们设定都是sname.server + sname.timer，那如果你的sname 并不相同时，那在.timer 的档案中， 就得要指定是哪一个service unit 啰！
Persistent	当使用OnCalendar 的设定时，指定该功能要不要持续进行的意思。通常是设定为yes ，比较能够满足类似anacron 的功能喔！
```
[配置文档中文翻译](http://www.jinbuguo.com/systemd/systemd.index.html)
```shell script
[Unit]
Description=backup my server timer

[Timer]
OnStartupSec=2minutes
OnUnitActiveSec=1day

[Install]
WantedBy=multi-user.target 
```
  * systemctl daemon-reload,重新加载服务
  * systemctl start xx.timer,启动定时服务
  *  systemctl status xx.timer，查看定时服务是否正常启动
  * systemctl show xx.timer|grep Next，查询服务的内部运行情况，
  * systemctl show xx.service|grep Exec,查看内部运行结果
  * 监测运行结果
* 常见内置服务
  * atd
  * crond
  * firewalld，防火墙
    * centos7.1以前有iptables 与ip6tables 等防火墙机制
    * 新的firewalld 搭配 firewall-cmd 指令，可以快速的建置好你的防火墙系统
  * rsyslog,系统)这个服务可以记录系统所产生的各项讯息， 包括/var/log/messages 内的几个重要的登录档
  * rc-local,(系统)相容于/etc/rc.d/rc.local 的呼叫方式,你必须要让/etc/rc.d/rc.local 具有x 的权限后， 
  这个服务才能真的运作！否则，你写入/etc/rc.d/rc.local 的脚本还是不会运作的喔！
  * ModemManager ,(系统/网路)主要就是数据机、网路设定等服务，建议的是使用NetworkManager 搭配nmcli 指令来处理网路设定
    network
    NetworkManager*
[常见内置服务](https://linux.vbird.org/linux_basic/centos7/0560daemons.php)
---
### 日志分析
> 解决系统错误，解决网络服务问题，过往事件记录
#### 常见日志
* /var/log/boot.log：记录这次开机的硬件监测结果
* /var/log/cron：crontab 任务执行信息
* var/log/dmesg，开机内核监测信息
* /var/log/lastlog，所有账号最后一次登录系统时候的信息
* /var/log/maillog 或/var/log/mail/*，SMTP 是发信所使用的通讯协定， POP3 则是收信使用的通讯协定
* /var/log/messages，几乎系统发生的错误讯息(或者是重要的资讯) 都会记录在这个档案中
* /var/log/secure，只要牵涉到『需要输入帐号密码』的软体，那么当登入时(不管登入正确或错误) 都会被记录在此档案中
  * ssh，telnet，su，sudo，gdm等
* /var/log/wtmp, /var/log/faillog,记录正确登入系统者的帐号资讯(wtmp) 与错误登入时所使用的帐号资讯
* /var/log/httpd/*, /var/log/samba/*,不同的网路服务会使用它们自己的登录档案来记载它们自己产生的各项讯息

### 软件管理
* linux认识的可执行文件是二进制文件，至于脚本文件script,是利用了bash程序做的解释执行
* 二进制可执行文件的产生，源码+可利用的库函数-》通过gcc编译器编译-》二级制可执行文件
* make和configure
* 源码方式安装程序
  * 将Tarball 由厂商的网页下载下来；
  * 将Tarball 解开，产生很多的原始码档案；
  * 开始以gcc 进行原始码的编译(会产生目标档object files)；
  * 然后以gcc 进行函式库、主、副程式的连结，以形成主要的binary file；
  * 将上述的binary file 以及相关的设定档安装至自己的主机上面。
* 主程序、子程序链接，子程序编译
  * make，把gcc的任务组合在一起，通过makefile链接在一起
#### tarball安装相关
> 源码安装已经很少被使用，在编程时需要用到，使用已有软件可以通过EPEL计划，就是RPM安装（CentOS）
* 标的(target): 目标档1 目标档2 
  <tab> gcc -o 欲建立的执行档目标档1 目标档2
  * 在makefile 当中的# 代表注解；
  * <tab> 需要在命令行(例如gcc 这个编译器指令) 的第一个字元；
  * 标的(target) 与相依档案(就是目标档)之间需以『:』隔开。
```shell script
main: main.o head.o sin_value.o cos_value.o
        gcc -o main main.o head.o sin_value.o cos_value.o -lm 
clean:
        rm -f main main.o head.o sin_value.o cos_value.o 
```
  * -lm 指的是libm.so 或libm.a 这个函式库档案；
  * -L 后面接的路径是刚刚上面那个函式库的搜寻目录；
  * -I 后面接的是原始码内的include 档案之所在目录。
  * 每个makefile可以由多个target
  * make main,构建main
  * make clean,清理中间文件
  * make clean main，先清理在构建
* 与bash script语法有所不同
  * 变数与变数内容以『=』隔开，同时两边可以具有空格；
  * 变数左边不可以有<tab> ，例如上面范例的第一行LIBS 左边不可以是<tab>；
  * 变数与变数内容在『=』两边不能具有『:』；
  * 在习惯上，变数最好是以『大写字母』为主；
  * 运用变数时，以${变数} 或$(变数) 使用；
  * 在该shell 的环境变数是可以被套用的，例如提到的CFLAGS 这个变数！
  * 在指令列模式也可以给予变数。
```shell script
LIBS = -lm
OBJS = main.o head.o sin_value.o cos_value.o
main:${OBJS} 
        #gcc -o main ${OBJS} ${LIBS} 
         gcc -o $@ ${OBJS} ${LIBS} 
clean:
        rm -f main ${OBJS}
```
* 不同的系统环境导致需要建立makefile有很多不同，需要自动建立makefile的autoconfig辅助工具
* 库函数包含在内核当中，kernel-source 或kernel-header，所以需要提前安装这些核心
* linux发行版已经偏向桌面应用，gcc等基础工具很多没有内置安装，centos中系统安装的时候提示Development Tools 需要勾选安装
* 源码安装软件流程
  * 取得原始档：将tarball 档案在/usr/local/src 目录下解压缩；
  * 取得步骤流程：进入新建立的目录底下，去查阅INSTALL 与README 等相关档案内容(很重要的步骤！)；
  * 相依属性软体安装：根据INSTALL/README 的内容察看并安装好一些相依的软体(非必要)；
  * 建立makefile：以自动侦测程式(configure 或config) 侦测作业环境，并建立Makefile 这个档案；
  * 编译：以make 这个程式并使用该目录下的Makefile 做为他的参数设定档，来进行make (编译或其他) 的动作；
  * 安装：以make 这个程式，并以Makefile 这个参数设定档，依据 install 这个标的(target) 的指定来安装到正确的路径！
* ./configure，自动生产makefile
* make clean，清除历史编译文件
* make，执行默认构建行为
* make install，安装软件到相应的目录
* 软件安装目录
  * 系统内置软件安装在/usr/中
  * 用户安装软件应该安装在/usr/local/中
  * 源码放置在/usr/local/src/中
  * 如： Apache WWW服务器为例
    * 系统默认安装在一下目录
      * /etc/httpd,配置文件
      * /usr/lib,软件需要的库函数
      * /usr/bin,软件生产的二进制可执行文件
      * /usr/share/man,软件的说明文档
    * 用户安装的软件放置默认位置
      * /usr/local/etc
      * /usr/local/bin
      * /usr/local/lib
      * /usr/local/man 
    * 为方便管理，可以为每个软件建立一个文件夹管理
       * /usr/local/apache/etc
       * /usr/local/apache/bin
       * /usr/local/apache/lib
       * /usr/local/apache/man 
       * 为了方便使用和卸载，需要将bin添加到path里面，man说明文档配置（man_db.conf中配置映射）到man_db.conf里，可以方便搜索到
```shell script
    # man_db.conf中配置映射
    24  # set up PATH to MANPATH mapping
    25  # ie. what man tree holds man pages for what binary directory.
    26  #
    27  #               *PATH*        ->        *MANPATH*
    28  #
    29  MANPATH_MAP     /bin                    /usr/share/man
    30  MANPATH_MAP     /usr/bin                /usr/share/man
    31  MANPATH_MAP     /sbin                   /usr/share/man
    32  MANPATH_MAP     /usr/sbin               /usr/share/man
    33  MANPATH_MAP     /usr/local/bin          /usr/local/man
        MANPATH_MAP     /usr/local/apche/bin    /usr/local/apche/man #新添加的配置文件
```
* 通过tarball方式安装ntp（时钟）服务
  * 阅读README
  * ./configure --prefix=/usr/local/ntp --enable-all-clocks --enable-parse-clocks 
    * prefix=/usr/local/ntp，软件安装路径，默认是安装在/usr/local中
  * make clean; make 
  * make check 
  * make install 
* 更新tarball编译的软件，需要通过diff 和patch命令进行替换，之后在通过make install自动编译修改后的文件，然后达到更新软件的目的
#### 库函数管理
* 静态函数库
  * 文档名为,文件.a
  * 编译后会直接放置到软件中，所以软件会大一些
  * 可以直接执行
  * 升级难度大
* 动态库，/lib /lib64,以及kernel提供的内核动态库/lib/modules
  * 文件名,文件.so
  * 编译后，软件通过指针导向，编译后软件会小一些
  * 不能单独执行，需有相应的动态库做支持
  * 升级要方便些，只需要更新动态库就能实现
* 提高动态库读取效率，利用缓存机制，把常用动态库加载到内存中
  * dconfig 与/etc/ld.so.conf.d/
  * /etc/ld.so.conf.d/，记录需要加载到缓存的动态库目录
  * dconfig,读取配置的加载目录
  * dconfig,把资料记录到/etc/ld.so.cache中
* ldconfig [-f conf] [ -C cache] 
  * ldconfig [-p]
  * -f conf ：那个conf 指的是某个档案名称，也就是说，使用conf 作为libarary
    	  函式库的取得路径，而不以/etc/ld.so.conf 为预设值
  *  -C cache：那个cache 指的是某个档案名称，也就是说，使用cache 作为快取暂存
    	  的函式库资料，而不以/etc/ld.so.cache 为预设值
  * -p ：列出目前有的所有函式库资料内容(在/etc/ld.so.cache 内的资料！)
* ldd [-vdr] [filename],查看文件调用了哪些动态库
  * -v ：列出所有内容资讯；
  * -d ：重新将资料有遗失的link 点秀出来！
  * -r ：将ELF 有关的错误内容秀出来！  
* 文档校验
  * md5sum / sha1sum / sha256sum
  * md5sum/sha1sum/sha256sum [-bct] filename
    * -b ：使用binary 的读档方式，预设为Windows/DOS 档案型态的读取方式；
    * -c ：检验档案指纹；
    * -t ：以文字型态来读取档案指纹。
  * 通过不可以函数生产的hashcode,监测是否被修改过
* rpm（RedHat Package Manager）、 dpkg、yum、apt
  * centos：rpm、yum（线上）
  * ubuntu：dpkg、apt（线上）
* rpm
  * 由于已经编译完成并且打包完毕，所以软体传输与安装上很方便(不需要再重新编译)；
  * 由于软体的资讯都已经记录在Linux 主机的资料库上，很方便查询、升级与反安装
  * 软体档案安装的环境必须与打包时的环境需求一致或相当；
  * 需要满足软体的相依属性需求；
  * 反安装时需要特别小心，最底层的软体不可先移除，否则可能造成整个系统的问题！
  * rpm安装会校验依赖软件的相关性，如果不存在就不能安装
* rpm，xxxxxxxxx.rpm,是已经编译的软件，必须相同的环境才能安装
* srpm，xxxxx.src.rpm,是未编译的源码文件，包含rpm管理信息，可以定制编译
* rp-pppoe - 3.11 - 5 .el7.x86_64 .rpm
  * 软件名-软件版本信息-发布版本次数-操作硬件平台
  * 硬件平台:x86_64,64位cpu，noarch，没有硬件平台要求
* 为了解决rpm依赖软件的问题，引入了yum，线上
* yum机制
  1. centos开发软件放置到yum服务器
  2. 分析软件依赖性，记录下软件的记录信息，header，生成软件相关性列表，这些列表和软件所在位置（网络或本地）都是一个repository
  3. 客户端通过yum 命令来查找相关信息，安装所需依赖软件
  4. 用户需要更新或安装时，需要更新软件清单到/var/cache/yum/中，再通过yum文件描述下载新的软件依赖，再通过rpm安装软件
  * yum直接能够安装软件，rpm只剩查询和校验
* rpm 安装软件
  * rpm -ivh xxx.rpm
  * --nodeps,软件发生属性依赖性问题，任要安装
  * --replacefiles，覆盖之前的版本的文件
  * --replacepkgs，覆盖之前的package
  * --force，--replacefiles和--replacepkgs
  * --test，测试是否能成功安装
  * --justdb，只安装说明文档
  * --nosignature，跳过数字签名监测
  * --prefix 新路径，设置安装路径
  * --noscripts，跳过一些指令
  * rpm -Uvh 更新软件，如果软件不存在，会新安装
  * rpm -Fvh 更新软件，如果软件不存在，不会安装
* rpm  查询功能，
  * rpm -qa，查询已经安装的软件，其实是查询/var/lib/rpm/ 文件
  * -q ：仅查询，后面接的软体名称是否有安装；
  * -qa ：列出所有的，已经安装在本机Linux 系统上面的所有软体名称；
  * -qi ：列出该软体的详细资讯(information)，包含开发商、版本与说明等；
  * -ql ：列出该软体所有的档案与目录所在完整档名(list)；
  * -qc ：列出该软体的所有设定档(找出在/etc/ 底下的档名而已)
    * rpm -qc nginx
  * -qd ：列出该软体的所有说明档(找出与man 有关的档案而已)
  * -qR ：列出与该软体有关的相依软体所含的档案(Required 的意思)
  * -qf ：由后面接的档案名称，找出该档案属于哪一个已安装的软体；
    * rpm -qf /bin/sh
    * rpm -qf /etc/my.cnf
  * -q --scripts：列出是否含有安装后需要执行的脚本档，可用以debug 喔！
    查询某个RPM 档案内含有的资讯：
  * -qp[icdlR]：注意-qp 后面接的所有参数以上面的说明一致。但用途仅在于找出某个RPM 档案内的资讯，而非已安装的软体资讯！注意！
    * rpm -qpR filename.i386.rpm ,查询filename的依赖文件
* 通过rpm查询命令所属软件
  1. type -a man ,查询man 命令类型，列出路径
  2. rpm -qf /usr/bin/man ,根据路径查询man所属软件
* rpm 验证与数字签名
  * rpm -V 查询软件文档是否被修改过
  * rpm -Va 查询系统中可能被修改的文件
  * rpm -Vp software,该软件哪个文档可能被修改过
  * rpm -Vf software,查询该软件是否被修改过
    * S ：(file Size differs) 档案的容量大小是否被改变
      * M ：(Mode differs) 档案的类型或档案的属性(rwx) 是否被改变？如是否可执行等参数已被改变
      * 5 ：(MD5 sum differs) MD5 这一种指纹码的内容已经不同
      * D ：(Device major/minor number mis-match) 装置的主/次代码已经改变
      * L ：(readLink(2) path mis-match) Link 路径已被改变
      * U ：(User ownership differs) 档案的所属人已被改变
      * G ：(Group ownership differs) 档案的所属群组已被改变
      * T ：(mTime differs) 档案的建立时间已被改变
      * P ：(caPabilities differ) 功能已经被改变
      * c ：设定档(config file)
      * d ：文件资料档(documentation)
      * g ：鬼档案～通常是该档案不被某个软体所包含，较少发生！(ghost file)
      * l ：授权档案(license file)
      * r ：读我档案(read me)
* 电子签名
  * 
* 卸载软件
  * rpm -e softwarename
* 重建/var/lib/rpm/
  * rpm --rebuilddb
* INSTALLING, UPGRADING, AND REMOVING PACKAGES:
  * rpm {-i|--install} [install-options] PACKAGE_FILE ...
  * rpm {-U|--upgrade} [install-options] PACKAGE_FILE ...
  * rpm {-F|--freshen} [install-options] PACKAGE_FILE ...
  * rpm {-e|--erase} [--allmatches] [--justdb] [--nodeps] [--noscripts] [--notriggers] [--test] PACKAGE_NAME ...
#### yum 安装更新卸载卸载查询
> 基于rpm的交互式软件管理
* 查询相关
  * yum [list|info|search|provides|whatprovides] 参数
  * yum search java,查询java软件相关，在repo配置文件中配置的源中查找（在线查找）
  * yum info software，软件详情，（安装或未安装都能查询）
  * yum list，查询服务器上提供的软件名称
    *  yum list nginx*,查询linux 相关软件
      * Available Packages，表示还未安装或需要升级
      * Installed Packages，表示已经安装
  * yum list updates,列出服务器上可供本机更新的软件程序
  * yum provides xxfile,列出文件所属软件信息
* yum [install|update] 软体，安装软件
  * yum install software
  * yum update software
* yum [群组功能] [软体群组]，安装软件集合
  * grouplist ：列出所有可使用的『软体群组组』，例如Development Tools 之类的；
  * groupinfo ：后面接group_name，则可了解该group 内含的所有软体名；
  * groupinstall：这个好用！可以安装一整组的软体群组，相当的不错用！
    * 由于大部分软件群安装都属于可选（非mandatory类型），系统不会把groupinfo 列出来的依赖软件一起安装所有需要修改/etc/yum.conf文件
      * distroverpkg=centos-release    # 找到这一行，底下新增一行！
      * group_package_types=default, mandatory, optional 
      
  * groupremove ：移除某个软体群组；
* yum remove software,卸载软件
* yum 配置文件
  * vim /etc/yum.repos.d/nginx.repo
```shell script
[nginx-stable]
name=nginx stable repo
baseurl=http://nginx.org/packages/centos/$releasever/$basearch/
gpgcheck=1
enabled=1
gpgkey=https://nginx.org/keys/nginx_signing.key
module_hotfixes=true    
```
    * [nginx-stable]：代表软体库的名字！中括号一定要存在，里面的名称则可以随意取。但是不能有两个相同的软体库名称， 否则yum 会不晓得该到哪里去找软体库相关软体清单档案。
    * name：只是说明一下这个软体库的意义而已，重要性不高！
    * mirrorlist=：列出这个软体库可以使用的映射站台，如果不想使用，可以注解到这行；
    * baseurl=：这个最重要，因为后面接的就是软体库的实际网址！mirrorlist 是由yum 程式自行去捉映射站台， baseurl 则是指定固定的一个软体库网址！我们刚刚找到的网址放到这里来啦！
    * enable=1：就是让这个软体库被启动。如果不想启动可以使用enable=0 喔！
    * gpgcheck=1：还记得RPM 的数位签章吗？这就是指定是否需要查阅RPM 档案内的数位签章！
    * gpgkey=：就是数位签章的公钥档所在位置！使用预设值即可
  * yum repolist all ，列出yum server所有的软件库信息
  * yum 会下载远程服务的软件清单到本地缓存/var/cache/yum，可能会导致清单列表有遗留信息，需要清理
    * yum clean [packages|headers|all]
      *  packages：将已下载的软体档案删除
      *  headers ：将下载的软体档头删除
      *  all ：将所有软体库资料都删除！
* 扩展软件计划，自定义配置文件（EPEL/ELRepo）
  * epel,官方提供的扩展软件仓库，https://fedoraproject.org/wiki/EPEL等
  * ELRepo ，社区提供的扩展软件仓库，http://elrepo.org/linux/kernel/el7/x86_64等
  * 编辑repo配置文件
* echo '10 1 * * * root /usr/bin/yum -y --enablerepo=epel update' > /etc/cron.d/yumupdate 
  * 通过定时任务，配置自动升级，--enablerepo=epel，打开扩展epel
* 通用软件选择rpm/yum来安装，方便，特殊软件选择用tarball来安装
#### SRPM，包含源码文件的SRPM
*  rpmbuild (Optional)，SRPM使用的命令，使用源码来构建、打包为rpm文件，再通过rpm来安装
  * --rebuild，编译和打包
  * --recompile，编译，打包，安装
  * 这两个选项都没有修改过SRPM 内的设定值，仅是透过再次编译来产生RPM 可安装软体档案而已,成功会清除中间文件，失败会保留中间文件
* 软件名 xx.src.rpm
  * 通过 rpmbuild --rebuild xx.src.rpm
  * 如果缺少前置软件，会提示，然后用yum安装依赖文件
  * 再次rpmbuild --rebuild xx.src.rpm构建软件
  * 再用rpm -ivh xxx.rpm安装
* SRPM,使用需要的路径和软件,centos6之后，普通用户可以自己安装软件，之前只能root安装
  * /home/{user_name}/rpmbuild/SPECS	这个目录当中放置的是该软体的设定档，例如这个软体的资讯参数、设定项目等等都放置在这里；
  * /home/{user_name}/rpmbuild/SOURCES	这个目录当中放置的是该软体的原始档(*.tar.gz 的档案) 以及config 这个设定档；
  * /home/{user_name}/rpmbuild/BUILD	在编译的过程中，有些暂存的资料都会放置在这个目录当中；
  * /home/{user_name}/rpmbuild/RPMS	经过编译之后，并且顺利的编译成功之后，将打包完成的档案放置在这个目录当中。里头有包含了 x86_64, noarch.... 等等的次目录。
  * /home/{user_name}/rpmbuild/SRPMS	与RPMS 内相似的，这里放置的就是SRPM 封装的档案啰！有时候你想要将你的软体用SRPM 的方式释出时， 你的SRPM 档案就会放置在这个目录中了。
  * 如果需要修改源码文件和配置信息，需要make gcc c c++等软件的支持，才能修改后编译
* SRPM，软件需要修改后再编译的方法
  * rpm -ivh xxx.src.rpm 
    * 安装后会生成上述目录
      *  /home/{user_name}/rpmbuild/SPECS	
      *  /home/{user_name}/rpmbuild/SOURCES	
      *  /home/{user_name}/rpmbuild/BUILD	
      *  /home/{user_name}/rpmbuild/RPMS	
      *  /home/{user_name}/rpmbuild/SRPMS	
    * 修改配置文件 cd /home/{user_name}/rpmbuild/SPECS
    * vim xxx.spec ,里面包括配置安装等命令
    * rpmbuild -ba ntp.spec ，编译并同时产生RPM 与SRPM 档案
    * rpmbuild -bb ntp.spec ，仅编译成RPM 档案
      * 之后会在当前文件产生打包后的文件
#### 通过tarbal构建自己的rpm文件
* 主要通过编辑/home/{user_name}/rpmbuild/SPECS/xxx.spec文件
```shell script
Name: main 
Version: 0.1 
Release: 1%{?dist} 
Summary: Shows sin and cos value. 
Group: Scientific Support 
License: GPLv2 
URL: http://linux.vbird.org/ 
Source0: main-0.1.tgz              # 这两个档名要正确喔！
Patch0: main_0.1_to_0.2.patch 

%description 
This package will let you input your name and calculate sin cos value. 

%prep 
%setup -q 
%patch0 -p1                               # 要用来作为patch 的动作！

%build
make clean main                           # 编译就好！不要安装！

%install 
mkdir -p %{buildroot}/usr/local/bin 
install -m 755 main %{buildroot}/usr/local/bin # 这才是顺利的安装行为！

%files 
/usr/local/bin/main 

%changelog 
* Wed Sep 09 2015 VBird Tsai <vbird@mail.vbird.idv.tw> 0.2 
- build the program
```
  * 必要配置
    * Summary	本软体的主要说明，例如上表中说明了本软体是针对NTP 的软体功能与工具等啦！
    * Name	本软体的软体名称(最终会是RPM 档案的档名构成之一)
    * Version	本软体的版本(也会是RPM 档名的构成之一)
    * Release	这个是该版本打包的次数说明(也会是RPM 档名的构成之一)。由于我们想要动点手脚，所以请将『 19%{?dist}.1 』 修改为『 20.vbird 』看看
    * License	这个软体的授权模式，看起来涵盖了所有知名的Open source 授权啊！！
    * Group	这个软体在安装的时候，主要是放置于哪一个软体群组当中(yum grouplist 的特点！)；
    * URL	这个原始码的主要官方网站；
    * SourceN	这个软体的来源，如果是网路上下载的软体，通常一定会有这个资讯来告诉大家这个原始档的来源！此外，如果有多个软体来源，就会以Source0, Source1... 来处理原始码喔！
    * PatchN	就是作为补丁的patch file 啰！也是可以有好多个！
    * BuildRoot	设定作为编译时，该使用哪个目录来暂存中间档案(如编译过程的目标档案/连结档案等档)。
  * 上述为必须要存在的项目，底下为可使用的额外设定值
    * Requires	如果你这个软体还需要其他的软体的支援，那么这里就必需写上来，则当你制作成RPM 之后，系统就会自动的去检查啦！这就是『相依属性』的主要来源啰！
    * BuildRequires	编译过程中所需要的软体。Requires 指的是『安装时需要检查』的，因为与实际运作有关，这个BuildRequires 指的是『编译时』所需要的软体，只有在SRPM 编译成为RPM 时才会检查的项目。
* rpmbuild -ba xx.spec 编译为自己的rpm文件和src.rpm文件
*  yum install /home/bpz/rpmbuild/RPMS/x86_64/main-0.1-1.el7.centos.x86_64.rpm 通过yum安装，会安装一些依赖文件

### linux kernel编译与管理
> 编译linux kernel的可能目的，让系统更加稳定
 * 新功能的需求
 * 原本核心太过臃肿
 * 与硬体搭配的稳定性
 * 其他需求(如嵌入式系统)
* 下载kernel到/usr/src/kernels/
  * jar -Jxv -f /usr/src/kernels/xxx.tar.xz
* 执行make mrproper
* 配置核心功能列表，类似文件（/boot/config-xxx）
* 
###  网络服务
* 查看服务与端口对应
  * cat /etc/services
* 会产生监听网络端口的服务就是网络服务
* netstat -tulnp，查询主机打开的网络服务端口信息
  * -t, tcp信息
  * -u，udp信息
  * -l，listening信息，监听
  * -n，numeric信息，数字形式显示
  * -p，--program|-p
* 常见服务端口
  * 80: WWW
  * 22: ssh
  * 21: ftp
  * 25: mail
  * 111: RPC(远端程序呼叫)
  * 631: CUPS(列印服务功能)
* ping -c 1 -w 1 192.168.1.1 ping 192.168.1.1地址1次，超时1s
