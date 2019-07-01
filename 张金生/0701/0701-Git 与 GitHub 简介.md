# Git 与 GitHub 简介 #

## Git 与 GitHub 简介 ##

Linux 之父 Linus 在 1991 年创建开源的 Linux 操作系统之后，多年来依靠全世界广大热心志愿者的共同建设，经过长足发展，现已成为世界上最大的服务器系统。系统创建之初，代码贡献者将源码文件发送给 Linus，由其手动合并。这种方式维持多年后，代码量已经庞大到人工合并难以为继，于是深恶集中式版本控制系统的 Linus 选择了一个分布式商业版本控制系统 BitKeeper，不过 Linux 社区的建设者们可以免费使用它。BitKeeper 改变了 Linus 对版本控制的认识，同时 Linus 发现 BitKeeper 有一些不足，而且有个关键性的问题使之不能被广泛使用，就是不开源。

在 2005 年，BitKeeper 所在公司发现 Linux 社区有人企图破解它，BitKeeper 决定收回 Linux 社区的免费使用权。Linus 对此事调节数周无果，找遍了当时已知的各种版本控制系统，没有一个看上眼的，一怒之下决定自己搞一个。Linus 花了十天时间用 C 语言写好了一个开源的版本控制系统，就是著名的 Git。

2007 年旧金山三个年轻人觉得 Git 是个好东西，就搞了一个公司名字叫 GitHub，第二年上线了使用 Ruby 编写的同名网站 GitHub，这是一个基于 Git 的免费代码托管网站（有付费服务）。十年间，该网站迅速蹿红，击败了实力雄厚的 Google Code，成为全世界最受欢迎的代码托管网站。2018 年 6 月，GitHub 被财大气粗的 Microsoft 收购。2019 年 1 月 GitHub 宣布用户可以免费创建私有仓库。根据 2018 年 10 月的 GitHub 年度报告显示，目前有 3100 万开发者创建了 9600 万个项目仓库，有 210 万企业入驻。

本课程将以图文的形式逐步讲解 GitHub 的使用以及 Git 实现版本控制。

## 在 GitHub 上创建仓库 ##

先，打开 GitHub 注册个人账户并登录。登录后，在个人主页的右上角点击 New repository 创建新的仓库：

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755508075.png/wm)

打开页面如下图所示，填入相关信息。注意下图紫色框中有两个下拉按钮，左边的用来选择忽略文件，右边的用来选择所属协议，这两项可以不选，后面的课程会讲到。

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755552253.png/wm)

点击绿色按钮创建新的仓库，成功后自动跳转到新建仓库的主页面，如下图所示：

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755564537.png/wm)


## 安装 Git、升级 Git 到最新版 ##

接下来，我们就要尝试使用这个仓库。

首先，打开实验环境。实验环境中内置了 Git 版本控制器，无需下载。打开终端使用 git --version 命令查看版本：

![](![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190701-1561989450223))

这个版本有点儿低，先给它升升级，Ubuntu 系统中依次执行以下命令即可升级 Git 到最新版。

注意，升级版本这部分操作完全可以略过，升级不是必须的。我如果不是为了写课程，自己用实验环境的话，就不会升级，当前版本与最新版本区别不大。非会员用户不能成功升级。

首先，在终端执行如下命令下载安装 Git 所需的秘钥：
```
sudo apt update  # 更新源
sudo apt install software-properties-common # 安装 PPA 需要的依赖
sudo add-apt-repository ppa:git-core/ppa    # 向 PPA 中添加 git 的软件源
```
![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755586114.png/wm)

出现此报错是因为实验环境中 Python 版本的问题，执行如下命令选择 Python3.4 即可：
```
sudo update-alternatives --install /usr/bin/python3 python3 /usr/bin/python3.4 1
sudo update-alternatives --display python3   # 查看可选版本
sudo update-alternatives --config python3   # 选择 python3.4
```
![](https://doc.shiyanlou.com/document-uid600404labid7166timestamp1549869587753.png/wm)

重复前一个命令下载秘钥：

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755610411.png/wm)

执行 sudo apt update 更新源：

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755621433.png/wm)

执行 sudo apt install -y git 重新安装 Git：

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755631787.png/wm)

再次查看 Git 版本，升级成功（目前升级成功后版本会高于下图所示）：

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755641324.png/wm)

在 Windows 系统中可以安装 Git for Windows 客户端

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548673848562.png/wm)

## 克隆 GitHub 上的仓库到本地 ##

现在克隆前面我们在 GitHub 上创建的仓库，使用 git clone + [仓库地址] 命令即可，这是标准的克隆仓库命令。

点击下图绿色按钮，再点击紫色框中的按钮即可复制仓库地址，当然复制上面地址栏中的内容也是一样的。

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755659143.png/wm)

克隆仓库到本地：

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755672465.png/wm)

进入仓库主目录，如下图所示，仓库主目录中有个 .git 隐藏目录，它里面包含了仓库的全部信息，删掉这个目录，仓库就变成普通的目录了。进入到仓库目录中，命令行前缀发生了一些变化，出现了红色的 master ，它就是当前所在的分支名：

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755685917.png/wm)

当我们在 GitHub 上创建一个仓库时，同时生成了仓库的默认主机名 origin，并创建了默认分支 master。GitHub 可以看成是免费的 Git 服务器，在 GitHub 上创建仓库，会自动生成一个仓库地址，主机就是指代这个仓库，主机名就等于这个仓库地址。克隆一个 GitHub 仓库（也叫远程仓库）到本地，本地仓库则会自动关联到这个远程仓库，执行 git remote -v 命令可以查看本地仓库所关联的远程仓库信息：

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755698081.png/wm)

Git 要求对本地仓库关联的每个远程主机都必须指定一个主机名（默认为 origin），用于本地仓库识别自己关联的主机，git remote 命令就用于管理本地仓库所关联的主机，一个本地仓库可以关联任意多个主机（即远程仓库）。

克隆远程仓库到本地时，还可以使用 -o 选项修改主机名，在地址后面加上一个字段作为本地仓库的主目录名，举例如下：

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755710736.png/wm)

另一个在其它 Git 教程中常见的命令 git init ，它会把当前所在目录变成一个本地仓库，因为有 GitHub 的存在，这个命令在我们的生产生活中用到的次数应该是零，除非你想费时费力自己搭建服务器。操作截图如下：

![](https://doc.shiyanlou.com/document-uid310176labid7166timestamp1548755742879.png/wm)

