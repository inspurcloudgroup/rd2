#linux任务计划crontab#

##crontab 的使用##

crontab 命令常见于 Unix 和类 Unix 的操作系统之中（Linux 就属于类 Unix 操作系统），用于设置周期性被执行的指令。

crontab 命令从输入设备读取指令，并将其存放于 crontab 文件中，以供之后读取和执行。通常，crontab 储存的指令被守护进程激活，crond 为其守护进程，crond 常常在后台运行，每一分钟会检查一次是否有预定的作业需要执行。

通过 crontab 命令，我们可以在固定的间隔时间执行指定的系统指令或 shell　script 脚本。时间间隔的单位可以是分钟、小时、日、月、周的任意组合。

这里我们看一看crontab 的格式

```
Example of job definition:
.---------------- minute (0 - 59)
|  .------------- hour (0 - 23)
|  |  .---------- day of month (1 - 31)
|  |  |  .------- month (1 - 12) OR jan,feb,mar,apr ...
|  |  |  |  .---- day of week (0 - 6) (Sunday=0 or 7) OR sun,mon,tue,wed,thu,fri,sat
|  |  |  |  |
*  *  *  *  * user-name command to be executed
```

##crontab 准备##

crontab 在本实验环境中需要做一些特殊的准备，首先我们会启动 rsyslog，以便我们可以通过日志中的信息来了解我们的任务是否真正的被执行了（在本实验环境中需要手动启动，而在自己本地中 Ubuntu 会默认自行启动不需要手动启动）
```
sudo apt-get install -y rsyslog
sudo service rsyslog start
service-rsyslog-start
```
![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190527-1558965082432)

在本实验环境中 crontab 也是不被默认启动的，同时不能在后台由 upstart 来管理，所以需要我们来启动它:
```
sudo cron －f &
```

##crontab 正式使用##

下面将开始 crontab 的使用了，我们通过下面一个命令来添加一个计划任务
```
crontab -e
```
第一次启动会出现这样一个画面，这是让我们选择编辑的工具，选择第二个基本的 vim 就可以了

![图片描述](https://doc.shiyanlou.com/document-uid600404labid6124timestamp1523941985569.png/wm)

而选择后我们会进入这样一个画面，这就是添加计划的地方了，与一般的配置文档相同，以#号开头的都是注释

详细的格式可以使用上一节中学习到的 man 命令查看：
```
man crontab
```
在了解命令格式之后，我们通过这样的一个例子来完成一个任务的添加，在文档的最后一排加上这样一排命令,该任务是每分钟我们会在/home/shiyanlou目录下创建一个以当前的年月日时分秒为名字的空白文件
```
*/1 * * * * touch /home/shiyanlou/$(date +\%Y\%m\%d\%H\%M\%S)
```
>注意 “ % ” 在 crontab 文件中，有结束命令行、换行、重定向的作用，前面加 ” \ ” 符号转义，否则，“ % ” 符号将执行其结束命令行或者换行的作用，并且其后的内容会被做为标准输入发送给前面的命令。

添加成功后我们会得到最后一排 installing new crontab 的一个提示

当然我们也可以通过这样的一个指令来查看我们添加了哪些任务
```
crontab -l 
```


虽然我们添加了任务，但是如果 cron 的守护进程并没有启动，它根本都不会监测到有任务，当然也就不会帮我们执行，我们可以通过以下2种方式来确定我们的 cron 是否成功的在后台启动，默默的帮我们做事，若是没有就得执行上文准备中的第二步了
```
ps aux | grep cron

or

pgrep cron
```
![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190527-1558965677724)

通过下图可以看到任务在创建之后，执行了几次，生成了一些文件，且每分钟生成一个：

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190527-1558965780533)

我们通过这样一个命令可以查看到执行任务命令之后在日志中的信息反馈
```
sudo tail -f /var/log/syslog
```
从图中我们可以看到在 shiyanlou 用户的家目录下创建了文件

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190527-1558965904556)

当我们并不需要这个任务的时候我们可以使用这么一个命令去删除任务
```
crontab -r
```
删除之后再查看任务列表，系统已经显示该用户没有任务


##crontab 的深入##

每个用户使用 crontab -e 添加计划任务，都会在 /var/spool/cron/crontabs 中添加一个该用户自己的任务文档，这样目的是为了隔离。

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190527-1558966063874)

如果是系统级别的定时任务，应该如何处理？只需要以 sudo 权限编辑 /etc/crontab 文件就可以。

cron 服务监测时间最小单位是分钟，所以 cron 会每分钟去读取一次 /etc/crontab 与 /var/spool/cron/crontabs 里面的內容。

在 /etc 目录下，cron 相关的目录有下面几个：

![](https://dn-simplecloud.shiyanlou.com/1135081468206856712)

每个目录的作用：

/etc/cron.daily，目录下的脚本会每天执行一次，在每天的6点25分时运行；
/etc/cron.hourly，目录下的脚本会每个小时执行一次，在每小时的17分钟时运行；
/etc/cron.monthly，目录下的脚本会每月执行一次，在每月1号的6点52分时运行；
/etc/cron.weekly，目录下的脚本会每周执行一次，在每周第七天的6点47分时运行；
系统默认执行时间可以根据需求进行修改。


##总结##
本节讲解了 crontab 的一些简单的应用和一些简单的概念。crontab 是 Linux 系统中添加计划任务，定时执行一些必要的脚本所必不可少的工具










