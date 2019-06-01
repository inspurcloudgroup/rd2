#Linux下软件安装#

介绍 Ubuntu 下软件安装的几种方式，及 apt，dpkg 工具的使用。

实验知识点

通常 Linux 上的软件安装主要有四种方式：

在线安装
从磁盘安装deb软件包
从二进制软件包安装
从源代码编译安装
这几种安装方式各有优劣，而大多数软件包会采用多种方式发布软件，所以我们常常需要全部掌握这几种软件安装方式，以便适应各种环境。下面将介绍前三种安装方式，从源码编译安装将在 Linux 程序设计中学习到。

在不同的linux发行版上面在线安装方式会有一些差异包括使用的命令及它们的包管理工具，因为我们的开发环境是基于ubuntu的，所以这里我们涉及的在线安装方式将只适用于ubuntu发行版，或其它基于ubuntu的发行版如国内的ubuntukylin(优麒麟)，ubuntu又是基于debian的发行版，它使用的是debian的包管理工具dpkg，所以一些操作也适用与debian。而在一些采用其它包管理工具的发行版如redhat，centos,fedora等将不适用(redhat和centos使用rpm)。


##先体验一下##

比如我们想安装一个软件，名字叫做 w3m(w3m是一个命令行的简易网页浏览器)，那么输入如下命令：
```
$ sudo apt-get install w3m
```
这样的操作你应该在前面的章节中看到过很多次了，它就表示将会安装一个软件包名为w3m的软件

命令执行后的效果：

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190601-1559397575111)

$ w3m www.shiyanlou.com/faq

**注意**:如果在安装一个软件之后，无法立即使用Tab键补全这个命令，可以尝试先执行source ~/.zshrc，然后就可以使用补全操作。

##apt 包管理工具介绍##


>APT是Advance Packaging Tool（高级包装工具）的缩写，是Debian及其派生发行版的软件包管理器，APT可以自动下载，配置，安装二进制或者源代码格式的软件包，因此简化了Unix系统上管理软件的过程。APT最早被设计成dpkg的前端，用来处理deb格式的软件包。现在经过APT-RPM组织修改，APT已经可以安装在支持RPM的系统管理RPM包。这个包管理器包含以 apt- 开头的多个工具，如 apt-get apt-cache apt-cdrom 等，在Debian系列的发行版中使用。
当你在执行安装操作时，首先apt-get 工具会在本地的一个数据库中搜索关于 w3m 软件的相关信息，并根据这些信息在相关的服务器上下载软件安装，这里大家可能会一个疑问：既然是在线安装软件，为啥会在本地的数据库中搜索？要解释这个问题就得提到几个名词了：

- 软件源镜像服务器
- 软件源

我们需要定期从服务器上下载一个软件包列表，使用 sudo apt-get update 命令来保持本地的软件包列表是最新的（有时你也需要手动执行这个操作，比如更换了软件源），而这个表里会有软件依赖信息的记录，对于软件依赖，我举个例子：我们安装 w3m 软件的时候，而这个软件需要 libgc1c2 这个软件包才能正常工作，这个时候 apt-get 在安装软件的时候会一并替我们安装了，以保证 w3m 能正常的工作。


##apt-get##


apt-get 是用于处理 apt包的公用程序集，我们可以用它来在线安装、卸载和升级软件包等，下面列出一些apt-get包含的常用的一些工具：

工具	说明
install	其后加上软件包名，用于安装一个软件包
update	从软件源镜像服务器上下载/更新用于更新本地软件源的软件包列表
upgrade	升级本地可更新的全部软件包，但存在依赖问题时将不会升级，通常会在更新之前执行一次update
dist-upgrade	解决依赖关系并升级(存在一定危险性)
remove	移除已安装的软件包，包括与被移除软件包有依赖关系的软件包，但不包含软件包的配置文件
autoremove	移除之前被其他软件包依赖，但现在不再被使用的软件包
purge	与remove相同，但会完全移除软件包，包含其配置文件
clean	移除下载到本地的已经安装的软件包，默认保存在/var/cache/apt/archives/
autoclean	移除已安装的软件的旧版本软件包
下面是一些apt-get常用的参数：

参数	说明
-y	自动回应是否安装软件包的选项，在一些自动化安装脚本中使用这个参数将十分有用
-s	模拟安装
-q	静默安装方式，指定多个q或者-q=#,#表示数字，用于设定静默级别，这在你不想要在安装软件包时屏幕输出过多时很有用
-f	修复损坏的依赖关系
-d	只下载不安装
--reinstall	重新安装已经安装但可能存在问题的软件包
--install-suggests	同时安装APT给出的建议安装的软件包

##安装软件包##

关于安装，如前面演示的一样你只需要执行apt-get install <软件包名>即可，除了这一点，你还应该掌握的是如何重新安装软件包。 很多时候我们需要重新安装一个软件包，比如你的系统被破坏，或者一些错误的配置导致软件无法正常工作。
可以使用如下方式重新安装：
```
$ sudo apt-get --reinstall install w3m
```

另一个需要掌握的是，如何在不知道软件包完整名的时候进行安装。通常我们是使用Tab键补全软件包名，后面会介绍更好的方法来搜索软件包。有时候需要同时安装多个软件包，你还可以使用正则表达式匹配软件包名进行批量安装

##软件升级##

```
 # 更新软件源
 $ sudo apt-get update
 # 升级没有依赖问题的软件包
 $ sudo apt-get upgrade
 # 升级并解决依赖关系
 $ sudo apt-get dist-upgrade
```

## 卸载软件 ##
如果你现在觉得 w3m 这个软件不合自己的胃口，或者是找到了更好的，你需要卸载它，那么简单！同样是一个命令加回车 sudo apt-get remove w3m ，系统会有一个确认的操作，之后这个软件便“滚蛋了”。

或者，可以执行
```
# 不保留配置文件的移除
$ sudo apt-get purge w3m
# 或者 sudo apt-get --purge remove
# 移除不再需要的被依赖的软件包
$ sudo apt-get autoremove
```

##软件搜索##

当自己刚知道了一个软件，想下载使用，需要确认软件仓库里面有没有，就需要用到搜索功能了，命令如下：

sudo apt-cache search softname1 softname2 softname3……
apt-cache 命令则是针对本地数据进行相关操作的工具，search 顾名思义在本地的数据库中寻找有关 softname1 softname2 …… 相关软件的信息。


#使用 dpkg#

##dpkg 介绍##

>dpkg 是 Debian 软件包管理器的基础，它被伊恩·默多克创建于 1993 年。dpkg 与 RPM 十分相似，同样被用于安装、卸载和供给和 .deb 软件包相关的信息。
dpkg 本身是一个底层的工具。上层的工具，像是 APT，被用于从远程获取软件包以及处理复杂的软件包关系。"dpkg"是"Debian Package"的简写。
我们经常可以在网络上见到以deb形式打包的软件包，就需要使用dpkg命令来安装。

dpkg常用参数介绍：

参数	说明
-i	安装指定deb包
-R	后面加上目录名，用于安装该目录下的所有deb安装包
-r	remove，移除某个已安装的软件包
-I	显示deb包文件的信息
-s	显示已安装软件的信息
-S	搜索已安装的软件包
-L	显示已安装软件包的目录信息

##使用dpkg安装deb软件包##

我们先使用apt-get加上-d参数只下载不安装，下载emacs编辑器的deb包，下载完成后，我们可以查看/var/cache/apt/archives/目录下的内容，如下图：

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190601-1559399766637)

然后我们将第一个deb拷贝到 /home/shiyanlou 目录下，并使用dpkg安装

```
$ cp /var/cache/apt/archives/emacs24_24.5+1-6ubuntu1.1_amd64.deb ~
# 安装之前参看deb包的信息
$ sudo dpkg -I emacs24_24.5+1-6ubuntu1.1_amd64.deb
```

这个包还额外依赖了一些软件包，这意味着，如果主机目前没有这些被依赖的软件包，直接使用dpkg安装可能会存在一些问题，因为dpkg并不能为你解决依赖关系。

# 使用dpkg安装
$ sudo dpkg -i emacs24_24.5+1-6ubuntu1.1_amd64.deb
跟前面预料的一样，这里你可能出现了一些错误：

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190601-1559399866970)

我们将如何解决这个错误呢？这就要用到apt-get了，使用它的-f参数了，修复依赖关系的安装
```
$ sudo apt-get update
$ sudo apt-get -f install
没有任何错误，这样我们就安装成功了，然后可以运行emacs程序
```
![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190601-1559400149564)

##查看已安装软件包的安装目录##

使用dpkg -L查看deb包目录信息

```
$ sudo dpkg -L emacs24
```

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190601-1559400265554)

## 从二进制包安装 ##

二进制包的安装比较简单，我们需要做的只是将从网络上下载的二进制包解压后放到合适的目录，然后将包含可执行的主程序文件的目录添加进PATH环境变量即可

## 作业 ##

这一节是本课程的最后一节，所以这里我们给大家介绍一个很有趣的命令。

安装：

$ sudo apt-get update
$ sudo apt-get install bb
$ /usr/games/bb

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190601-1559400434220)














