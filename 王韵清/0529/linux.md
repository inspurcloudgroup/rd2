# ifconfig

配置和显示Linux系统网卡的网络参数

## 补充说明

**ifconfig命令** 被用于配置和显示Linux内核中网络接口的网络参数。用ifconfig命令配置的网卡信息，在网卡重启后机器重启后，配置就不存在。要想将上述的配置信息永远的存的电脑里，那就要修改网卡的配置文件了。

### 语法

```shell
ifconfig(参数)
```

### 参数

```shell
add<地址>：设置网络设备IPv6的ip地址；
del<地址>：删除网络设备IPv6的IP地址；
down：关闭指定的网络设备；
<hw<网络设备类型><硬件地址>：设置网络设备的类型与硬件地址；
io_addr<I/O地址>：设置网络设备的I/O地址；
irq<IRQ地址>：设置网络设备的IRQ；
media<网络媒介类型>：设置网络设备的媒介类型；
mem_start<内存地址>：设置网络设备在主内存所占用的起始地址；
metric<数目>：指定在计算数据包的转送次数时，所要加上的数目；
mtu<字节>：设置网络设备的MTU；
netmask<子网掩码>：设置网络设备的子网掩码；
tunnel<地址>：建立IPv4与IPv6之间的隧道通信地址；
up：启动指定的网络设备；
-broadcast<地址>：将要送往指定地址的数据包当成广播数据包来处理；
-pointopoint<地址>：与指定地址的网络设备建立直接连线，此模式具有保密功能；
-promisc：关闭或启动指定网络设备的promiscuous模式；
IP地址：指定网络设备的IP地址；
网络设备：指定网络设备的名称。
```

### 实例

**显示网络设备信息（激活状态的）：**

```shell
[root@localhost ~]# ifconfig
eth0      Link encap:Ethernet  HWaddr 00:16:3E:00:1E:51  
          inet addr:10.160.7.81  Bcast:10.160.15.255  Mask:255.255.240.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:61430830 errors:0 dropped:0 overruns:0 frame:0
          TX packets:88534 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000
          RX bytes:3607197869 (3.3 GiB)  TX bytes:6115042 (5.8 MiB)

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:16436  Metric:1
          RX packets:56103 errors:0 dropped:0 overruns:0 frame:0
          TX packets:56103 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:0
          RX bytes:5079451 (4.8 MiB)  TX bytes:5079451 (4.8 MiB)
```

说明：

**eth0** 表示第一块网卡，其中`HWaddr`表示网卡的物理地址，可以看到目前这个网卡的物理地址(MAC地址）是`00:16:3E:00:1E:51`。

**inet addr** 用来表示网卡的IP地址，此网卡的IP地址是`10.160.7.81`，广播地址`Bcast:10.160.15.255`，掩码地址`Mask:255.255.240.0`。

**lo** 是表示主机的回坏地址，这个一般是用来测试一个网络程序，但又不想让局域网或外网的用户能够查看，只能在此台主机上运行和查看所用的网络接口。比如把 httpd服务器的指定到回坏地址，在浏览器输入127.0.0.1就能看到你所架WEB网站了。但只是您能看得到，局域网的其它主机或用户无从知道。

- 第一行：连接类型：Ethernet（以太网）HWaddr（硬件mac地址）。
- 第二行：网卡的IP地址、子网、掩码。
- 第三行：UP（代表网卡开启状态）RUNNING（代表网卡的网线被接上）MULTICAST（支持组播）MTU:1500（最大传输单元）：1500字节。
- 第四、五行：接收、发送数据包情况统计。
- 第七行：接收、发送数据字节数统计信息。

**启动关闭指定网卡：**

```shell
ifconfig eth0 up
ifconfig eth0 down
```

`ifconfig eth0 up`为启动网卡eth0，`ifconfig eth0 down`为关闭网卡eth0。ssh登陆linux服务器操作要小心，关闭了就不能开启了，除非你有多网卡。

**为网卡配置和删除IPv6地址：**

```shell
ifconfig eth0 add 33ffe:3240:800:1005::2/64    #为网卡eth0配置IPv6地址
ifconfig eth0 del 33ffe:3240:800:1005::2/64    #为网卡eth0删除IPv6地址
```

**用ifconfig修改MAC地址：**

```shell
ifconfig eth0 hw ether 00:AA:BB:CC:dd:EE
```

**配置IP地址：**

```shell
[root@localhost ~]# ifconfig eth0 192.168.2.10
[root@localhost ~]# ifconfig eth0 192.168.2.10 netmask 255.255.255.0
[root@localhost ~]# ifconfig eth0 192.168.2.10 netmask 255.255.255.0 broadcast 192.168.2.255
```

**启用和关闭arp协议：**

```shell
ifconfig eth0 arp    #开启网卡eth0 的arp协议
ifconfig eth0 -arp   #关闭网卡eth0 的arp协议
```

**设置最大传输单元：**

```shell
ifconfig eth0 mtu 1500    #设置能通过的最大数据包大小为 1500 bytes
```

**其它实例**

```shell
ifconfig   #处于激活状态的网络接口
ifconfig -a  #所有配置的网络接口，不论其是否激活
ifconfig eth0  #显示eth0的网卡信息
```

# netstat

查看Linux中网络系统状态信息

## 补充说明

**netstat命令** 用来打印Linux中网络系统的状态信息，可让你得知整个Linux系统的网络情况。

### 语法

```shell
netstat(选项)
```

### 选项

```shell
-a或--all：显示所有连线中的Socket；
-A<网络类型>或--<网络类型>：列出该网络类型连线中的相关地址；
-c或--continuous：持续列出网络状态；
-C或--cache：显示路由器配置的快取信息；
-e或--extend：显示网络其他相关信息；
-F或--fib：显示FIB；
-g或--groups：显示多重广播功能群组组员名单；
-h或--help：在线帮助；
-i或--interfaces：显示网络界面信息表单；
-l或--listening：显示监控中的服务器的Socket；
-M或--masquerade：显示伪装的网络连线；
-n或--numeric：直接使用ip地址，而不通过域名服务器；
-N或--netlink或--symbolic：显示网络硬件外围设备的符号连接名称；
-o或--timers：显示计时器；
-p或--programs：显示正在使用Socket的程序识别码和程序名称；
-r或--route：显示Routing Table；
-s或--statistice：显示网络工作信息统计表；
-t或--tcp：显示TCP传输协议的连线状况；
-u或--udp：显示UDP传输协议的连线状况；
-v或--verbose：显示指令执行过程；
-V或--version：显示版本信息；
-w或--raw：显示RAW传输协议的连线状况；
-x或--unix：此参数的效果和指定"-A unix"参数相同；
--ip或--inet：此参数的效果和指定"-A inet"参数相同。
```

### 实例

**列出所有端口 (包括监听和未监听的)**

```shell
netstat -a     #列出所有端口
netstat -at    #列出所有tcp端口
netstat -au    #列出所有udp端口                             
```

**列出所有处于监听状态的 Sockets**

```shell
netstat -l        #只显示监听端口
netstat -lt       #只列出所有监听 tcp 端口
netstat -lu       #只列出所有监听 udp 端口
netstat -lx       #只列出所有监听 UNIX 端口
```

**显示每个协议的统计信息**

~~~shell
netstat -s   显示所有端口的统计信息
netstat -st   显示TCP端口的统计信息
netstat -su   显示UDP端口的统计信息

```shell

 **在netstat输出中显示 PID 和进程名称** 

```shell
netstat -pt
~~~

`netstat -p`可以与其它开关一起使用，就可以添加“PID/进程名称”到netstat输出中，这样debugging的时候可以很方便的发现特定端口运行的程序。

**在netstat输出中不显示主机，端口和用户名(host, port or user)**

当你不想让主机，端口和用户名显示，使用`netstat -n`。将会使用数字代替那些名称。同样可以加速输出，因为不用进行比对查询。

```shell
netstat -an
```

如果只是不想让这三个名称中的一个被显示，使用以下命令:

```shell
netsat -a --numeric-ports
netsat -a --numeric-hosts
netsat -a --numeric-users
```

**持续输出netstat信息**

```shell
netstat -c   #每隔一秒输出网络信息
```

**显示系统不支持的地址族(Address Families)**

```shell
netstat --verbose
```

在输出的末尾，会有如下的信息：

```shell
netstat: no support for `AF IPX' on this system.
netstat: no support for `AF AX25' on this system.
netstat: no support for `AF X25' on this system.
netstat: no support for `AF NETROM' on this system.
```

**显示核心路由信息**

```shell
netstat -r
```

使用`netstat -rn`显示数字格式，不查询主机名称。

**找出程序运行的端口**

并不是所有的进程都能找到，没有权限的会不显示，使用 root 权限查看所有的信息。

```shell
netstat -ap | grep ssh
```

找出运行在指定端口的进程：

```shell
netstat -an | grep ':80'
```

**通过端口找进程ID**

```bash
netstat -anp|grep 8081 | grep LISTEN|awk '{printf $7}'|cut -d/ -f1
```

**显示网络接口列表**

```shell
netstat -i
```

显示详细信息，像是ifconfig使用`netstat -ie`。

**IP和TCP分析**

查看连接某服务端口最多的的IP地址：

```shell
netstat -ntu | grep :80 | awk '{print $5}' | cut -d: -f1 | awk '{++ip[$1]} END {for(i in ip) print ip[i],"\t",i}' | sort -nr
```

TCP各种状态列表：

```shell
netstat -nt | grep -e 127.0.0.1 -e 0.0.0.0 -e ::: -v | awk '/^tcp/ {++state[$NF]} END {for(i in state) print i,"\t",state[i]}'
```

查看phpcgi进程数，如果接近预设值，说明不够用，需要增加：

```shell
netstat -anpo | grep "php-cgi" | wc -l
```

# telnet

登录远程主机和管理(测试ip端口是否连通)

## 补充说明

**telnet命令** 用于登录远程主机，对远程主机进行管理。telnet因为采用明文传送报文，安全性不好，很多Linux服务器都不开放telnet服务，而改用更安全的ssh方式了。但仍然有很多别的系统可能采用了telnet方式来提供远程登录，因此弄清楚telnet客户端的使用方式仍是很有必要的。

### 语法

```shell
telnet(选项)(参数)
```

### 选项

```shell
-8：允许使用8位字符资料，包括输入与输出；
-a：尝试自动登入远端系统；
-b<主机别名>：使用别名指定远端主机名称；
-c：不读取用户专属目录里的.telnetrc文件；
-d：启动排错模式；
-e<脱离字符>：设置脱离字符；
-E：滤除脱离字符；
-f：此参数的效果和指定"-F"参数相同；
-F：使用Kerberos V5认证时，加上此参数可把本地主机的认证数据上传到远端主机；
-k<域名>：使用Kerberos认证时，加上此参数让远端主机采用指定的领域名，而非该主机的域名；
-K：不自动登入远端主机；
-l<用户名称>：指定要登入远端主机的用户名称；
-L：允许输出8位字符资料；
-n<记录文件>：指定文件记录相关信息；
-r：使用类似rlogin指令的用户界面；
-S<服务类型>：设置telnet连线所需的ip TOS信息；
-x：假设主机有支持数据加密的功能，就使用它；
-X<认证形态>：关闭指定的认证形态。
```

### 参数

- 远程主机：指定要登录进行管理的远程主机；
- 端口：指定TELNET协议使用的端口号。

### 实例

```shell
$ telnet 192.168.2.10
Trying 192.168.2.10...
Connected to 192.168.2.10 (192.168.2.10).
Escape character is '^]'.

    localhost (Linux release 2.6.18-274.18.1.el5 #1 SMP Thu Feb 9 12:45:44 EST 2012) (1)

login: root
Password:
Login incorrect
```

一般情况下不允许root从远程登录，可以先用普通账号登录，然后再用su -切到root用户。

```shell
$ telnet 192.168.188.132
Trying 192.168.188.132...
telnet: connect to address 192.168.188.132: Connection refused
telnet: Unable to connect to remote host
```

处理这种情况方法：

1. 确认ip地址是否正确？
2. 确认ip地址对应的主机是否已经开机？
3. 如果主机已经启动，确认路由设置是否设置正确？（使用route命令查看）
4. 如果主机已经启动，确认主机上是否开启了telnet服务？（使用netstat命令查看，TCP的23端口是否有LISTEN状态的行）
5. 如果主机已经启动telnet服务，确认防火墙是否放开了23端口的访问？（使用iptables-save查看）

**启动telnet服务**

```shell
service xinetd restart
```

配置参数，通常的配置如下：

```shell
service telnet
{
    disable = no #启用
    flags = REUSE #socket可重用
    socket_type = stream #连接方式为TCP
    wait = no #为每个请求启动一个进程
    user = root #启动服务的用户为root
    server = /usr/sbin/in.telnetd #要激活的进程
    log_on_failure += USERID #登录失败时记录登录用户名
}
```

如果要配置允许登录的客户端列表，加入

```
only_from = 192.168.0.2 #只允许192.168.0.2登录
```

如果要配置禁止登录的客户端列表，加入

```
no_access = 192.168.0.{2,3,4} #禁止192.168.0.2、192.168.0.3、192.168.0.4登录
```

如果要设置开放时段，加入

```
access_times = 9:00-12:00 13:00-17:00 # 每天只有这两个时段开放服务（我们的上班时间：P）
```

如果你有两个IP地址，一个是私网的IP地址如192.168.0.2，一个是公网的IP地址如218.75.74.83，如果你希望用户只能从私网来登录telnet服务，那么加入

```
bind = 192.168.0.2
```

各配置项具体的含义和语法可参考xined配置文件属性说明（man xinetd.conf）

配置端口，修改services文件：

```shell
# vi /etc/services
```

找到以下两句

```shell
telnet 23/tcp
telnet 23/udp
```

如果前面有#字符，就去掉它。telnet的默认端口是23，这个端口也是黑客端口扫描的主要对象，因此最好将这个端口修改掉，修改的方法很简单，就是将23这个数字修改掉，改成大一点的数字，比如61123。注意，1024以下的端口号是internet保留的端口号，因此最好不要用，还应该注意不要与其它服务的端口冲突。

启动服务：

```
service xinetd restart
```

# ftp

用来设置文件系统相关功能

## 补充说明

**ftp命令** 用来设置文件系统相关功能。ftp服务器在网上较为常见，Linux ftp命令的功能是用命令的方式来控制在本地机和远程机之间传送文件，这里详细介绍Linux ftp命令的一些经常使用的命令，相信掌握了这些使用Linux进行ftp操作将会非常容易。

### 语法

```shell
ftp(选项)(参数)
```

### 选项

```shell
-d：详细显示指令执行过程，便于排错或分析程序执行的情况；
-i：关闭互动模式，不询问任何问题；
-g：关闭本地主机文件名称支持特殊字符的扩充特性；
-n：不使用自动登录；
-v：显示指令执行过程。
```

### 参数

主机：指定要连接的FTP服务器的主机名或ip地址。

### 实例

```shell
ftp> ascii  # 设定以ASCII方式传送文件(缺省值) 
ftp> bell   # 每完成一次文件传送,报警提示. 
ftp> binary # 设定以二进制方式传送文件. 
ftp> bye    # 终止主机FTP进程,并退出FTP管理方式. 
ftp> case   # 当为ON时,用MGET命令拷贝的文件名到本地机器中,全部转换为小写字母. 
ftp> cd     # 同UNIX的CD命令. 
ftp> cdup   # 返回上一级目录. 
ftp> chmod  # 改变远端主机的文件权限. 
ftp> close  # 终止远端的FTP进程,返回到FTP命令状态, 所有的宏定义都被删除. 
ftp> delete # 删除远端主机中的文件. 
ftp> dir [remote-directory] [local-file] # 列出当前远端主机目录中的文件.如果有本地文件,就将结果写至本地文件. 
ftp> get [remote-file] [local-file] # 从远端主机中传送至本地主机中. 
ftp> help [command] # 输出命令的解释. 
ftp> lcd # 改变当前本地主机的工作目录,如果缺省,就转到当前用户的HOME目录. 
ftp> ls [remote-directory] [local-file] # 同DIR. 
ftp> macdef                 # 定义宏命令. 
ftp> mdelete [remote-files] # 删除一批文件. 
ftp> mget [remote-files]    # 从远端主机接收一批文件至本地主机. 
ftp> mkdir directory-name   # 在远端主机中建立目录. 
ftp> mput local-files # 将本地主机中一批文件传送至远端主机. 
ftp> open host [port] # 重新建立一个新的连接. 
ftp> prompt           # 交互提示模式. 
ftp> put local-file [remote-file] # 将本地一个文件传送至远端主机中. 
ftp> pwd  # 列出当前远端主机目录. 
ftp> quit # 同BYE. 
ftp> recv remote-file [local-file] # 同GET. 
ftp> rename [from] [to]     # 改变远端主机中的文件名. 
ftp> rmdir directory-name   # 删除远端主机中的目录. 
ftp> send local-file [remote-file] # 同PUT. 
ftp> status   # 显示当前FTP的状态. 
ftp> system   # 显示远端主机系统类型. 
ftp> user user-name [password] [account] # 重新以别的用户名登录远端主机. 
ftp> ? [command] # 同HELP. [command]指定需要帮助的命令名称。如果没有指定 command，ftp 将显示全部命令的列表。
ftp> ! # 从 ftp 子系统退出到外壳。
```

关闭FTP连接

```shell
bye
exit
quit
```

下载文件

```shell
ftp> get readme.txt # 下载 readme.txt 文件
ftp> mget *.txt     # 下载 
```

上传文件

```shell
ftp> put /path/readme.txt # 上传 readme.txt 文件
ftp> mput *.txt           # 可以上传多个文件
```

# history

用于显示历史命令

## 补充说明

**history命令** 用于显示指定数目的指令命令，读取历史命令文件中的目录到历史命令缓冲区和将历史命令缓冲区中的目录写入命令文件。

该命令单独使用时，仅显示历史命令，在命令行中，可以使用符号`!`执行指定序号的历史命令。例如，要执行第2个历史命令，则输入`!2`。

历史命令是被保存在内存中的，当退出或者登录shell时，会自动保存或读取。在内存中，历史命令仅能够存储1000条历史命令，该数量是由环境变量`HISTSIZE`进行控制。

### 语法

```shell
history(选项)(参数)
```

### 选项

```shell
-c：清空当前历史命令；
-a：将历史命令缓冲区中命令写入历史命令文件中；
-r：将历史命令文件中的命令读入当前历史命令缓冲区；
-w：将当前历史命令缓冲区命令写入历史命令文件中。
```

### 参数

n：打印最近的n条历史命令。

### 实例

使用history命令显示最近使用的10条历史命令，输入如下命令：

```shell
[root@localhost ~]# history 10
   92  ls
   93  cd ..
   94  ls
   95  exit
   96  ls -a
   97  cd .ssh/
   98  ls
   99  cat known_hosts
  100  exit
  101  history 10
```

列出最近3条记录

```shell
[root@localhost ~]# history 3
   15  2017-08-26 11:44:35  root history 3
   16  2017-08-26 11:44:37  root history n
   17  2017-08-26 11:44:40  root history 3
```

清空历史记录

```shell
[root@localhost ~]# history -c
```

更多实例:

```shell
history -cw
`~/.bash_history`: 保存历史命令
`/etc/profile` -> HISSIZE: 历史命令保存数量
推荐添加 h -> history, hsi -> history|grep 别名
`!n`: 执行第 n 条历史命令
`!xxx`: 执行最后一条 xxx 开头的命令
```

# groupadd

用于创建一个新的工作组

## 补充说明

**groupadd命令** 用于创建一个新的工作组，新工作组的信息将被添加到系统文件中。

### 语法

```shell
groupadd(选项)(参数)
```

### 选项

```shell
-g：指定新建工作组的id；
-r：创建系统工作组，系统工作组的组ID小于500；
-K：覆盖配置文件“/ect/login.defs”；
-o：允许添加组ID号不唯一的工作组。
```

### 参数

组名：指定新建工作组的组名。

### 实例

建立一个新组，并设置组ID加入系统：

```shell
groupadd -g 344 jsdigname
```

此时在`/etc/passwd`文件中产生一个组ID（GID）是344的项目。

# groupmod

更改群组识别码或名称

## 补充说明

**groupmod命令** 更改群组识别码或名称。需要更改群组的识别码或名称时，可用groupmod指令来完成这项工作。

### 语法

```shell
groupmod(选项)(参数)
```

### 选项

```shell
-g<群组识别码>：设置欲使用的群组识别码；
-o：重复使用群组识别码；
-n<新群组名称>：设置欲使用的群组名称。
```

### 参数

组名：指定要修改的工作的组名。

# groupdel

用于删除指定的工作组

## 补充说明

**groupdel命令** 用于删除指定的工作组，本命令要修改的系统文件包括/ect/group和/ect/gshadow。若该群组中仍包括某些用户，则必须先删除这些用户后，方能删除群组。

### 语法

```shell
groupdel(参数)
```

### 参数

组：要删除的工作组名。

### 实例

```shell
groupadd damon  //创建damon工作组
groupdel damon  //删除这个工作组
```

# userdel

用于删除给定的用户以及与用户相关的文件

## 补充说明

**userdel命令** 用于删除给定的用户，以及与用户相关的文件。若不加选项，则仅删除用户帐号，而不删除相关文件。

### 语法

```shell
userdel(选项)(参数)
```

### 选项

```shell
-f：强制删除用户，即使用户当前已登录；
-r：删除用户的同时，删除与用户相关的所有文件。
```

### 参数

用户名：要删除的用户名。

### 实例

userdel命令很简单，比如我们现在有个用户linuxde，其家目录位于`/var`目录中，现在我们来删除这个用户：

```shell
userdel linuxde       # 删除用户linuxde，但不删除其家目录及文件；
userdel -r linuxde    # 删除用户linuxde，其家目录及文件一并删除；
```

请不要轻易用`-r`选项；他会删除用户的同时删除用户所有的文件和目录，切记如果用户目录下有重要的文件，在删除前请备份。

其实也有最简单的办法，但这种办法有点不安全，也就是直接在`/etc/passwd`中删除您想要删除用户的记录；但最好不要这样做，`/etc/passwd`是极为重要的文件，可能您一不小心会操作失误。

# usermod

用于修改用户的基本信息

## 补充说明

**usermod命令** 用于修改用户的基本信息。usermod 命令不允许你改变正在线上的使用者帐号名称。当 usermod 命令用来改变user id，必须确认这名user没在电脑上执行任何程序。你需手动更改使用者的 crontab 档。也需手动更改使用者的 at 工作档。采用 NIS server 须在server上更动相关的NIS设定。

### 语法

```shell
usermod(选项)(参数)
```

### 选项

```shell
-c<备注>：修改用户帐号的备注文字；
-d<登入目录>：修改用户登入时的目录，只是修改/etc/passwd中用户的家目录配置信息，不会自动创建新的家目录，通常和-m一起使用；
-m<移动用户家目录>:移动用户家目录到新的位置，不能单独使用，一般与-d一起使用。
-e<有效期限>：修改帐号的有效期限；
-f<缓冲天数>：修改在密码过期后多少天即关闭该帐号；
-g<群组>：修改用户所属的群组；
-G<群组>；修改用户所属的附加群组；
-l<帐号名称>：修改用户帐号名称；
-L：锁定用户密码，使密码无效；
-s<shell>：修改用户登入后所使用的shell；
-u<uid>：修改用户ID；
-U:解除密码锁定。
```

### 参数

登录名：指定要修改信息的用户登录名。

### 实例

将 newuser2 添加到组 staff 中：

```shell
usermod -G staff newuser2
```

修改newuser的用户名为newuser1：

```shell
usermod -l newuser1 newuser
```

锁定账号newuser1：

```shell
usermod -L newuser1
```

解除对newuser1的锁定：

```shell
usermod -U newuser1
```

增加用户到用户组中:

```shell
apk add shadow # 安装 shadow 包, usermod 命令包含在 usermod 中
usermod -aG group user # 添加用户到用户组中
```

`-a` 参数表示附加，只和 `-G` 参数一同使用，表示将用户增加到组中。

修改用户家目录：

```
[root@node-1 ~]# useradd lutixiaya
[root@node-1 ~]# ls /home
lutixiaya
[root@node-1 ~]# usermod -md /data/new_home lutixiaya
[root@node-1 ~]# ls /home/
[root@node-1 ~]# ls /data/
new_home
```

# clear

清除当前屏幕终端上的任何信息

## 补充说明

**clear命令** 用于清除当前屏幕终端上的任何信息。

### 语法

```shell
clear
```

### 实例

直接输入clear命令当前终端上的任何信息就可被清除。

# alias

用来设置指令的别名

## 补充说明

**alias命令** 用来设置指令的别名。我们可以使用该命令可以将一些较长的命令进行简化。使用alias时，用户必须使用单引号`''`将原来的命令引起来，防止特殊字符导致错误。

alias命令的作用只局限于该次登入的操作。若要每次登入都能够使用这些命令别名，则可将相应的alias命令存放到bash的初始化文件`/etc/bashrc`中。

### 语法

```shell
alias(选项)(参数)
```

### 选项

```shell
-c string 如果有 -c 选项，那么命令将从 string 中读取。如果 string 后面有参数 (argument)，它们将用于给位置参数 (positional
         parameter，以 $0 起始) 赋值。
-i        如果有 -i 选项，shell 将交互地执行 ( interactive )。
-l        选项使得 bash 以类似登录 shell (login shell) 的方式启动 (参见下面的 启动(INVOCATION) 章节)。
-r        如果有 -r 选项，shell 成为受限的 ( restricted ) (参见下面的 受限的shell(RESTRICTED SHELL) 章节)。
-s        如果有  -s  选项，或者如果选项处理完以后，没有参数剩余，那么命令将从标准输入读取。   这个选项允许在启动一个交互
         shell 时可以设置位置参数。
-D        向标准输出打印一个以    $    为前导的，以双引号引用的字符串列表。    这是在当前语言环境不是    C    或    POSIX
         时，脚本中需要翻译的字符串。 这个选项隐含了 -n 选项；不会执行命令。
[-+]O [shopt_option]
         shopt_option 是一个 shopt 内建命令可接受的选项  (参见下面的  shell  内建命令(SHELL  BUILTIN  COMMANDS)  章节)。
         如果有     shopt_option，-O     将设置那个选项的取值；    +O    取消它。    如果没有给出    shopt_option，shopt
         将在标准输出上打印设为允许的选项的名称和值。 如果启动选项是 +O，输出将以一种可以重用为输入的格式显示。
--        -- 标志选项的结束，禁止其余的选项处理。任何 -- 之后的参数将作为文件名和参数对待。参数 - 与此等价。

Bash 也解释一些多字节的选项。在命令行中，这些选项必须置于需要被识别的单字符参数之前。

--dump-po-strings
      等价于 -D，但是输出是 GNU gettext po (可移植对象) 文件格式
--dump-strings
      等价于 -D
--help 在标准输出显示用法信息并成功退出
--init-file file
--rcfile file
      如果 shell 是交互的，执行 file 中的命令，而不是标准的个人初始化文件 ~/.bashrc (参见下面的 启动(INVOCATION) 章节)

--login
      等价于 -l

--noediting
      如果 shell 是交互的，不使用 GNU readline 库来读命令行

--noprofile
      不读取系统范围的启动文件  /etc/profile  或者任何个人初始化文件  ~/.bash_profile,  ~/.bash_login,   或   ~/.profile
      。默认情况下， bash 在作为登录 shell 启动时读取这些文件 (参见下面的 启动(INVOCATION) 章节)

--norc 如果 shell 是交互的，不读取/执行个人初始化文件 ~/.bashrc 这个选项在 shell 以 sh 命令启动时是默认启用的

--posix
      如果默认操作与 POSIX 1003.2 标准不同的话，改变 bash 的行为来符合标准 (posix mode)

--restricted
      shell 成为受限的 (参见下面的 受限的shell(RESTRICTED SHELL) 章节)

--rpm-requires
      产生一个为使脚本运行，需要的文件的列表。 这个选项包含了 -n 选项。 它是为了避免进行编译期错误检测时的限制－－ Back‐
      ticks, [] tests,  还有 evals 不会被解释，一些依赖关系可能丢失

--verbose
      等价于 -v

--version
      在标准输出显示此 bash 的版本信息并成功退出。
```

### 参数

命令别名设置：定义命令别名，格式为“命令别名=‘实际命令’”。

### 实例

**alias 的基本使用方法为：**

```
alias 新的命令='原命令 -选项/参数'
```

例如：`alias l=‘ls -lsh'`将重新定义ls命令，现在只需输入l就可以列目录了。直接输入 alias 命令会列出当前系统中所有已经定义的命令别名。

要删除一个别名，可以使用 unalias 命令，如 unalias l。

**查看系统已经设置的别名：**

```shell
[root@localhost ~]# 
[root@localhost ~]# alias
alias cp='cp -i'
alias egrep='egrep --color=auto'
alias fgrep='fgrep --color=auto'
alias grep='grep --color=auto'
alias l.='ls -d .* --color=auto'
alias ll='ls -l --color=auto'
alias ls='ls --color=auto'
alias mv='mv -i'
alias rm='rm -i'
alias which='alias | /usr/bin/which --tty-only --read-alias --show-dot --show-tilde'
[root@localhost ~]# 
```

### 命令别名永久生效

直接在shell里设定的命令别名，在终端关闭或者系统重新启动后都会失效，如何才能永久有效呢？办法就是将别名的设置加入 `~/.bashrc` 文件，然后重新载入下文件就可以了。

```shell
$ vim ~/.bashrc
```

在文件最后面加入别名设置，如：alias rm=’rm -i’，保存后重新载入：

```shell
$ source ~/.bashrc
```

这样就可以永久保存命令的别名了。因为修改的是当前用户目录下的.bashrc文件，所以这样的方式只对当前用户有用。如果要对所有用户都有效，修改/etc目录下的bashrc文件就可以了。在CentOS7下，这个文件是/etc/bash.bashrc。此外在CentOS7下，细看~/.bashrc文件，会发有这样一段代码：

```shell
if [ -f ~/.bash_aliases ]; then
    . ~/.bash_aliases
fi
```

这个代码的意思就是加载.bash_aliases文件，CentOS7已经帮我们考虑好了，所以也可以在用户根目录下新建一个文件 `.bash_aliases` 存放命令别名设置。

### 小结

alias这个命令是shell的内建命令，可以根据自己的使用习惯设置一些别名，需要注意的就是和其他命令冲突的情况。

#### 一、 范例演示

在使用和维护Linux系统过程中，我们常常希望根据自己的需要来定义一些命令，那么这样的操作是不是很难呢？其实不是，系统已经为我们设置好了相关的命令，下面小编就以CentOS6.4系统为例，为大家演示如何创建自己的命令。

#### 二、 自定义命令简介

CentOS系统下的创建自定义命令其实比较简单，使用的命令就是alias，具体使用的方法就是 alias 自定义命令名=‘命令’。但是需要注意的是，在自定义之前需要查看自定义的命令名是否是系统已经使用的命令名，否则会冲突，另外一个就是定期清理不需要的自定义命令名。

#### 三、 演示举例

假定系统操作员希望进行如下操作：

1.进入目录 `/root`
2.查看目录下文件
3.切换回根目录

通常这需要三条命令 `cd /root`, `ls`, `cd /` ，如果需要经常使用那么我们可以通过自定义命令的方式用一条命令完成全部操作。

#### 四、 操作过程

首先使用命令 `type` 自定义命令名 ，查看自定义命令名是否被系统占用。从图中可以看出test命令名已被系统占用，所以不能使用，而 `loo` 这个命令名经查询可以使用。

使用命令alias创建自定义命令：`alias loo='cd /root;ls;cd /'` 。需要注意的是命令的使用格式，分号与分号之间是没有空格的。

测试一下自定义命令，当输入命令 `loo` 时，发现系统依次完成了 `cd /root`、`ls`、`cd /` 三条命令，说明设置成功。同时也可直接使用命令 `alias` 查询系统中是否添加了loo这个自定义命令。

如果希望删除这个自定义命令，可以使用命令 `unalias` 自定义命令名 来完成。执行之后发现，`loo` 命令已不存在，同时自定义命令库中也没有 `loo` 命令。

# blkid

查看块设备的文件系统类型、LABEL、UUID等信息

## 补充说明

在Linux下可以使用 **blkid命令** 对查询设备上所采用文件系统类型进行查询。blkid主要用来对系统的块设备（包括交换分区）所使用的文件系统类型、LABEL、UUID等信息进行查询。要使用这个命令必须安装e2fsprogs软件包。

### 语法

```shell
blkid -L | -U
blkid [-c ] [-ghlLv] [-o] [-s ][-t ] -[w ] [ ...]
blkid -p [-s ] [-O ] [-S ][-o] ...
blkid -i [-s ] [-o] ...
```

### 选项

```shell
-c <file>   # 指定cache文件(default: /etc/blkid.tab, /dev/null = none)
-d          # don't encode non-printing characters
-h          # 显示帮助信息
-g          # garbage collect the blkid cache
-o <format> # 指定输出格式
-k          # list all known filesystems/RAIDs and exit
-s <tag>    # 显示指定信息，默认显示所有信息
-t <token>  # find device with a specific token (NAME=value pair)
-l          # look up only first device with token specified by -t
-L <label>  # convert LABEL to device name
-U <uuid>   # convert UUID to device name
-v          # 显示版本信息
-w <file>   # write cache to different file (/dev/null = no write)
<dev>       # specify device(s) to probe (default: all devices)
Low-level probing options:
-p          # low-level superblocks probing (bypass cache)
-i          # gather information about I/O limits
-S <size>   # overwrite device size
-O <offset> # probe at the given offset
-u <list>   # filter by "usage" (e.g. -u filesystem,raid)
-n <list>   # filter by filesystem type (e.g. -n vfat,ext3)
```

### 实例

1、列出当前系统中所有已挂载文件系统的类型：

```shell
sudo blkid
```

2、显示指定设备 UUID：

```shell
sudo blkid -s UUID /dev/sda5
```

3、显示所有设备 UUID：

```shell
sudo blkid -s UUID
```

4、显示指定设备 LABEL：

```shell
sudo blkid -s LABEL /dev/sda5
```

5、显示所有设备 LABEL：

```shell
sudo blkid -s LABEL
```

6、显示所有设备文件系统：

```shell
sudo blkid -s TYPE
```

7、显示所有设备：

```shell
sudo blkid -o device
```

8、以列表方式查看详细信息：

```shell
sudo blkid -o list
```

# clock

用于调整 RTC 时间。

## 补充说明

**clock命令**用于调整 RTC 时间。 RTC 是电脑内建的硬件时间，执行这项指令可以显示现在时刻，调整硬件时钟的时间，将系统时间设成与硬件时钟之时间一致，或是把系统时间回存到硬件时钟。

### 语法

```shell
clock [--adjust][--debug][--directisa][--getepoch][--hctosys][--set --date="<日期时间>"]
[--setepoch --epoch=< >][--show][--systohc][--test][--utc][--version]
```

### 选项

```shell
--adjust 　第一次使用"--set"或"--systohc"参数设置硬件时钟，会在/etc目录下产生一个名称为adjtime的文件。当再次使用这两个参数调整硬件时钟，此文件便会记录两次调整间之差异，日后执行clock指令加上"--adjust"参数时，程序会自动根 据记录文件的数值差异，计算出平均值，自动调整硬件时钟的时间。
--debug 　详细显示指令执行过程，便于排错或了解程序执行的情形。
--directisa 　告诉clock指令不要通过/dev/rtc设备文件，直接对硬件时钟进行存取。这个参数适用于仅有ISA总线结构的老式电脑。
--getepoch 　把系统核心内的硬件时钟新时代数值，呈现到标准输出设备。
--hctosys 　Hardware Clock to System Time，把系统时间设成和硬件时钟一致。由于这个动作将会造成系统全面更新文件的存取时间，所以最好在系统启动时就执行它。
--set--date 　设置硬件时钟的日期和时间。
--setepoch--epoch=<年份> 　设置系统核心之硬件时钟的新时代数值，年份以四位树字表示。
--show 　读取硬件时钟的时间，并将其呈现至标准输出设备。
--systohc 　System Time to Hardware Clock，将系统时间存回硬件时钟内。
--test 　仅作测试，并不真的将时间写入硬件时钟或系统时间。
--utc 　把硬件时钟上的时间时为CUT，有时也称为UTC或UCT。
--version 　显示版本信息。
```

### 实例

获取当前的时间

```shell
clock # 获取当前的时间
```

显示UTC时间

```shell
clock -utc #显示UTC时间
```

# echo

输出指定的字符串或者变量

## 补充说明

**echo命令** 用于在shell中打印shell变量的值，或者直接输出指定的字符串。linux的echo命令，在shell编程中极为常用, 在终端下打印变量value的时候也是常常用到的，因此有必要了解下echo的用法echo命令的功能是在显示器上显示一段文字，一般起到一个提示的作用。

### 语法

```shell
echo(选项)(参数)
```

### 选项

```shell
-e：激活转义字符。
```

使用`-e`选项时，若字符串中出现以下字符，则特别加以处理，而不会将它当成一般文字输出：

- `\a` 发出警告声；
- `\b` 删除前一个字符；
- `\c` 不产生进一步输出 (\c 后面的字符不会输出)；
- `\f` 换行但光标仍旧停留在原来的位置；
- `\n` 换行且光标移至行首；
- `\r` 光标移至行首，但不换行；
- `\t` 插入tab；
- `\v` 与\f相同；
- `\\` 插入\字符；
- `\nnn` 插入 `nnn`（八进制）所代表的ASCII字符；

### 参数

变量：指定要打印的变量。

### 实例

用echo命令打印带有色彩的文字：

**文字色：**

```shell
echo -e "\e[1;31mThis is red text\e[0m"
This is red text
```

- `\e[1;31m` 将颜色设置为红色
- `\e[0m` 将颜色重新置回

颜色码：重置=0，黑色=30，红色=31，绿色=32，黄色=33，蓝色=34，洋红=35，青色=36，白色=37

**背景色** ：

```shell
echo -e "\e[1;42mGreed Background\e[0m"
Greed Background
```

颜色码：重置=0，黑色=40，红色=41，绿色=42，黄色=43，蓝色=44，洋红=45，青色=46，白色=47

**文字闪动：**

```shell
echo -e "\033[37;31;5mMySQL Server Stop...\033[39;49;0m"
```

红色数字处还有其他数字参数：0 关闭所有属性、1 设置高亮度（加粗）、4 下划线、5 闪烁、7 反显、8 消隐

# exit

退出当前的shell

## 补充说明

**exit命令** 同于退出shell，并返回给定值。在shell脚本中可以终止当前脚本执行。执行exit可使shell以指定的状态值退出。若不设置状态值参数，则shell以预设值退出。状态值0代表执行成功，其他值代表执行失败。

### 语法

```shell
exit(参数)
```

### 参数

返回值：指定shell返回值。

### 实例

退出当前shell：

```shell
[root@localhost ~]# exit
logout
```

在脚本中，进入脚本所在目录，否则退出：

```shell
cd $(dirname $0) || exit 1
```

在脚本中，判断参数数量，不匹配就打印使用方式，退出：

```shell
if [ "$#" -ne "2" ]; then
    echo "usage: $0 <area> <hours>"
    exit 2
fi
```

在脚本中，退出时删除临时文件：

```shell
trap "rm -f tmpfile; echo Bye." EXIT
```

检查上一命令的退出码：

```shell
./mycommand.sh
EXCODE=$?
if [ "$EXCODE" == "0" ]; then
    echo "O.K"
fi
```

# logout

退出当前登录的Shell

## 补充说明

**logout命令** 用于退出当前登录的Shell，logout指令让用户退出系统，其功能和login指令相互对应。

### 语法

```shell
logout
```

# findfs

标签或UUID查找文件系统

## 补充说明

**findfs命令** 依据卷标（Label）和UUID查找文件系统所对应的设备文件。findfs命令会搜索整个磁盘，看是否有匹配的标签或者UUID没有，如果有则打印到标注输出上。findfs命令也是e2fsprogs项目的一部分。

### 语法

```shell
findfs(参数)
```

### 参数

`LABEL=<卷标>`或者`UUID=<UUID>`：按照卷标或者UUID查询文件系统。

### 实例

通过卷标名查找对应的文件系统：

```shell
findfs LABEL=/boot
/dev/hda1
```

# iostat

监视系统输入输出设备和CPU的使用情况

## 补充说明

**iostat命令** 被用于监视系统输入输出设备和CPU的使用情况。它的特点是汇报磁盘活动统计情况，同时也会汇报出CPU使用情况。同vmstat一样，iostat也有一个弱点，就是它不能对某个进程进行深入分析，仅对系统的整体情况进行分析。

### 语法

```shell
iostat(选项)(参数)
```

### 选项

```shell
-c：仅显示CPU使用情况；
-d：仅显示设备利用率；
-k：显示状态以千字节每秒为单位，而不使用块每秒；
-m：显示状态以兆字节每秒为单位；
-p：仅显示块设备和所有被使用的其他分区的状态；
-t：显示每个报告产生时的时间；
-V：显示版号并退出；
-x：显示扩展状态。
```

### 参数

- 间隔时间：每次报告的间隔时间（秒）；
- 次数：显示报告的次数。

### 实例

用`iostat -x /dev/sda1`来观看磁盘I/O的详细情况：

```shell
iostat -x /dev/sda1 
Linux 2.6.18-164.el5xen (localhost.localdomain)
2010年03月26日  

avg-cpu:  %user   %nice %system %iowait 
%steal   %idle  
            0.11    0.02    0.18    0.35   
0.03    99.31  

Device:         tps   Blk_read/s    Blk_wrtn/s  
Blk_read   Blk_wrtn  
sda1                0.02          0.08       
0.00          2014               4 
```

详细说明：第二行是系统信息和监测时间，第三行和第四行显示CPU使用情况（具体内容和mpstat命令相同）。这里主要关注后面I/O输出的信息，如下所示：

| 标示     | 说明                              |
| -------- | --------------------------------- |
| Device   | 监测设备名称                      |
| rrqm/s   | 每秒需要读取需求的数量            |
| wrqm/s   | 每秒需要写入需求的数量            |
| r/s      | 每秒实际读取需求的数量            |
| w/s      | 每秒实际写入需求的数量            |
| rsec/s   | 每秒读取区段的数量                |
| wsec/s   | 每秒写入区段的数量                |
| rkB/s    | 每秒实际读取的大小，单位为KB      |
| wkB/s    | 每秒实际写入的大小，单位为KB      |
| avgrq-sz | 需求的平均大小区段                |
| avgqu-sz | 需求的平均队列长度                |
| await    | 等待I/O平均的时间（milliseconds） |
| svctm    | I/O需求完成的平均时间             |
| %util    | 被I/O需求消耗的CPU百分比          |

# hostname

显示和设置系统的主机名

## 补充说明

**hostname命令** 用于显示和设置系统的主机名称。环境变量HOSTNAME也保存了当前的主机名。在使用hostname命令设置主机名后，系统并不会永久保存新的主机名，重新启动机器之后还是原来的主机名。如果需要永久修改主机名，需要同时修改`/etc/hosts`和`/etc/sysconfig/network`的相关内容。

### 语法

```shell
hostname(选项)(参数)
```

### 选项

```shell
-v：详细信息模式；
-a：显示主机别名；
-d：显示DNS域名；
-f：显示FQDN名称；
-i：显示主机的ip地址；
-s：显示短主机名称，在第一个点处截断；
-y：显示NIS域名。
```

### 参数

主机名：指定要设置的主机名。

### 实例

```shell
[root@AY1307311912260196fcZ ~]# hostname
AY1307311912260196fcZ

# change hostname
hostname newname # for now
vi /etc/hosts # forever
reboot
```

# lscpu

显示有关CPU架构的信息

## 补充说明

**lscpu命令** 是显示有关CPU架构的信息。

### 语法

```shell
lscpu [选项]
```

### 选项

```shell
 -a, --all               # 打印在线和离线CPU（默认为-e）
 -b, --online            # 仅打印在线CPU（-p的默认值）
 -c, --offline           # 打印离线CPU
 -e, --extended[=<list>] # 打印出一个扩展的可读格式
 -p, --parse[=<list>]    # 打印出可解析的格式
 -s, --sysroot <dir>     # 将指定的目录用作系统根目录
 -x, --hex               # 打印十六进制掩码，而不是CPU列表

 -h, --help     # 显示此帮助并退出
 -V, --version  # 输出版本信息并退出
```

### 参数

```shell
可用列：
           CPU  逻辑CPU编号
          CORE  逻辑核心号码
        SOCKET  逻辑套接字号
          NODE  逻辑NUMA节点号
          BOOK  逻辑书号
         CACHE  显示了如何在CPU之间共享高速缓存
  POLARIZATION  虚拟硬件上的CPU调度模式
       ADDRESS  CPU的物理地址
    CONFIGURED  显示管理程序是否分配了CPU
        ONLINE  显示Linux是否正在使用CPU
```

### 例子

```shell
[root@localhost ~]# lscpu
Architecture:          x86_64
CPU op-mode(s):        32-bit, 64-bit
Byte Order:            Little Endian
CPU(s):                4
On-line CPU(s) list:   0-3
Thread(s) per core:    1
Core(s) per socket:    4
Socket(s):             1
NUMA node(s):          1
Vendor ID:             GenuineIntel
CPU family:            6
Model:                 30
Model name:            Intel(R) Xeon(R) CPU           X3430  @ 2.40GHz
Stepping:              5
CPU MHz:               2394.055
BogoMIPS:              4788.11
Virtualization:        VT-x
L1d cache:             32K
L1i cache:             32K
L2 cache:              256K
L3 cache:              8192K
NUMA node0 CPU(s):     0-3
```

# lsblk

列出块设备信息

## 补充说明

**lsblk命令** 用于列出所有可用块设备的信息，而且还能显示他们之间的依赖关系，但是它不会列出RAM盘的信息。块设备有硬盘，闪存盘，cd-ROM等等。lsblk命令包含在util-linux-ng包中，现在该包改名为util-linux。这个包带了几个其它工具，如dmesg。要安装lsblk，请在此处下载util-linux包。Fedora用户可以通过命令`sudo yum install util-linux-ng`来安装该包。

### 选项

```shell
-a, --all            # 显示所有设备。
-b, --bytes          # 以bytes方式显示设备大小。
-d, --nodeps         # 不显示 slaves 或 holders。
-D, --discard        # print discard capabilities。
-e, --exclude <list> # 排除设备 (default: RAM disks)。
-f, --fs             # 显示文件系统信息。
-h, --help           # 显示帮助信息。
-i, --ascii          # use ascii characters only。
-m, --perms          # 显示权限信息。
-l, --list           # 使用列表格式显示。
-n, --noheadings     # 不显示标题。
-o, --output <list>  # 输出列。
-P, --pairs          # 使用key="value"格式显示。
-r, --raw            # 使用原始格式显示。
-t, --topology       # 显示拓扑结构信息。
```

### 实例

lsblk命令默认情况下将以树状列出所有块设备。打开终端，并输入以下命令：

```shell
lsblk

NAME   MAJ:MIN rm   SIZE RO type mountpoint
sda      8:0    0 232.9G  0 disk 
├─sda1   8:1    0  46.6G  0 part /
├─sda2   8:2    0     1K  0 part 
├─sda5   8:5    0   190M  0 part /boot
├─sda6   8:6    0   3.7G  0 part [SWAP]
├─sda7   8:7    0  93.1G  0 part /data
└─sda8   8:8    0  89.2G  0 part /personal
sr0     11:0    1  1024M  0 rom
```

7个栏目名称如下：

1. **NAME** ：这是块设备名。
2. **MAJ:MIN** ：本栏显示主要和次要设备号。
3. **RM** ：本栏显示设备是否可移动设备。注意，在本例中设备sdb和sr0的RM值等于1，这说明他们是可移动设备。
4. **SIZE** ：本栏列出设备的容量大小信息。例如298.1G表明该设备大小为298.1GB，而1K表明该设备大小为1KB。
5. **RO** ：该项表明设备是否为只读。在本案例中，所有设备的RO值为0，表明他们不是只读的。
6. **TYPE** ：本栏显示块设备是否是磁盘或磁盘上的一个分区。在本例中，sda和sdb是磁盘，而sr0是只读存储（rom）。
7. **MOUNTPOINT** ：本栏指出设备挂载的挂载点。

默认选项不会列出所有空设备。要查看这些空设备，请使用以下命令：

```shell
lsblk -a
```

lsblk命令也可以用于列出一个特定设备的拥有关系，同时也可以列出组和模式。可以通过以下命令来获取这些信息：

```shell
lsblk -m
```

该命令也可以只获取指定设备的信息。这可以通过在提供给lsblk命令的选项后指定设备名来实现。例如，你可能对了解以字节显示你的磁盘驱动器大小比较感兴趣，那么你可以通过运行以下命令来实现：

```shell
lsblk -b /dev/sda

等价于

lsblk --bytes /dev/sda
```

你也可以组合几个选项来获取指定的输出。例如，你也许想要以列表格式列出设备，而不是默认的树状格式。你可能也对移除不同栏目名称的标题感兴趣。可以将两个不同的选项组合，以获得期望的输出，命令如下：

```shell
lsblk -nl
```

要获取SCSI设备的列表，你只能使用-S选项。该选项是大写字母S，不能和-s选项混淆，该选项是用来以颠倒的顺序打印依赖的。

```shell
lsblk -S
```

lsblk列出SCSI设备，而-s是逆序选项（将设备和分区的组织关系逆转过来显示），其将给出如下输出。输入命令：

```shell
lsblk -s
```

# lsof

显示Linux系统当前已打开的所有文件列表 `lsof -p pid`

## 补充说明

**lsof命令** 用于查看你进程打开的文件，打开文件的进程，进程打开的端口(TCP、UDP)。找回/恢复删除的文件。是十分方便的系统监视工具，因为lsof命令需要访问核心内存和各种文件，所以需要root用户执行。

在linux环境下，任何事物都以文件的形式存在，通过文件不仅仅可以访问常规数据，还可以访问网络连接和硬件。所以如传输控制协议 (TCP) 和用户数据报协议 (UDP) 套接字等，系统在后台都为该应用程序分配了一个文件描述符，无论这个文件的本质如何，该文件描述符为应用程序与基础操作系统之间的交互提供了通用接口。因为应用程序打开文件的描述符列表提供了大量关于这个应用程序本身的信息，因此通过lsof工具能够查看这个列表对系统监测以及排错将是很有帮助的。

### 语法

```shell
lsof(选项)
```

### 选项

```shell
-a：列出打开文件存在的进程；
-c<进程名>：列出指定进程所打开的文件；
-g：列出GID号进程详情；
-d<文件号>：列出占用该文件号的进程；
+d<目录>：列出目录下被打开的文件；
+D<目录>：递归列出目录下被打开的文件；
-n<目录>：列出使用NFS的文件；
-i<条件>：列出符合条件的进程。（4、6、协议、:端口、 @ip ）
-p<进程号>：列出指定进程号所打开的文件；
-u：列出UID号进程详情；
-h：显示帮助信息；
-v：显示版本信息。
```

### 实例

```shell
lsof
command     PID USER   FD      type             DEVICE     SIZE       NODE NAME
init          1 root  cwd       DIR                8,2     4096          2 /
init          1 root  rtd       DIR                8,2     4096          2 /
init          1 root  txt       REG                8,2    43496    6121706 /sbin/init
init          1 root  mem       REG                8,2   143600    7823908 /lib64/ld-2.5.so
init          1 root  mem       REG                8,2  1722304    7823915 /lib64/libc-2.5.so
init          1 root  mem       REG                8,2    23360    7823919 /lib64/libdl-2.5.so
init          1 root  mem       REG                8,2    95464    7824116 /lib64/libselinux.so.1
init          1 root  mem       REG                8,2   247496    7823947 /lib64/libsepol.so.1
init          1 root   10u     FIFO               0,17                1233 /dev/initctl
migration     2 root  cwd       DIR                8,2     4096          2 /
migration     2 root  rtd       DIR                8,2     4096          2 /
migration     2 root  txt   unknown                                        /proc/2/exe
ksoftirqd     3 root  cwd       DIR                8,2     4096          2 /
ksoftirqd     3 root  rtd       DIR                8,2     4096          2 /
ksoftirqd     3 root  txt   unknown                                        /proc/3/exe
migration     4 root  cwd       DIR                8,2     4096          2 /
migration     4 root  rtd       DIR                8,2     4096          2 /
migration     4 root  txt   unknown                                        /proc/4/exe
ksoftirqd     5 root  cwd       DIR                8,2     4096          2 /
ksoftirqd     5 root  rtd       DIR                8,2     4096          2 /
ksoftirqd     5 root  txt   unknown                                        /proc/5/exe
events/0      6 root  cwd       DIR                8,2     4096          2 /
events/0      6 root  rtd       DIR                8,2     4096          2 /
events/0      6 root  txt   unknown                                        /proc/6/exe
events/1      7 root  cwd       DIR                8,2     4096          2 /
```

**lsof输出各列信息的意义如下：**

- COMMAND：进程的名称
- PID：进程标识符
- PPID：父进程标识符（需要指定-R参数）
- USER：进程所有者
- PGID：进程所属组
- FD：文件描述符，应用程序通过文件描述符识别该文件。

文件描述符列表：

1. cwd：表示current work dirctory，即：应用程序的当前工作目录，这是该应用程序启动的目录，除非它本身对这个目录进行更改
2. txt：该类型的文件是程序代码，如应用程序二进制文件本身或共享库，如上列表中显示的 /sbin/init 程序
3. lnn：library references (AIX);
4. er：FD information error (see NAME column);
5. jld：jail directory (FreeBSD);
6. ltx：shared library text (code and data);
7. mxx ：hex memory-mapped type number xx.
8. m86：DOS Merge mapped file;
9. mem：memory-mapped file;
10. mmap：memory-mapped device;
11. pd：parent directory;
12. rtd：root directory;
13. tr：kernel trace file (OpenBSD);
14. v86 VP/ix mapped file;
15. 0：表示标准输出
16. 1：表示标准输入
17. 2：表示标准错误

一般在标准输出、标准错误、标准输入后还跟着文件状态模式：

1. u：表示该文件被打开并处于读取/写入模式。
2. r：表示该文件被打开并处于只读模式。
3. w：表示该文件被打开并处于。
4. 空格：表示该文件的状态模式为unknow，且没有锁定。
5. -：表示该文件的状态模式为unknow，且被锁定。

同时在文件状态模式后面，还跟着相关的锁：

1. N：for a Solaris NFS lock of unknown type;
2. r：for read lock on part of the file;
3. R：for a read lock on the entire file;
4. w：for a write lock on part of the file;（文件的部分写锁）
5. W：for a write lock on the entire file;（整个文件的写锁）
6. u：for a read and write lock of any length;
7. U：for a lock of unknown type;
8. x：for an SCO OpenServer Xenix lock on part of the file;
9. X：for an SCO OpenServer Xenix lock on the entire file;
10. space：if there is no lock.

文件类型：

1. DIR：表示目录。
2. CHR：表示字符类型。
3. BLK：块设备类型。
4. UNIX： UNIX 域套接字。
5. FIFO：先进先出 (FIFO) 队列。
6. IPv4：网际协议 (IP) 套接字。
7. DEVICE：指定磁盘的名称
8. SIZE：文件的大小
9. NODE：索引节点（文件在磁盘上的标识）
10. NAME：打开文件的确切名称

列出指定进程号所打开的文件:

```shell
lsof -p $pid
```

获取端口对应的进程ID=>pid

```shell
lsof -i:9981 -P -t -sTCP:LISTEN
```

# jobs

显示Linux中的任务列表及任务状态

## 补充说明

**jobs命令** 用于显示Linux中的任务列表及任务状态，包括后台运行的任务。该命令可以显示任务号及其对应的进程号。其中，任务号是以普通用户的角度进行的，而进程号则是从系统管理员的角度来看的。一个任务可以对应于一个或者多个进程号。

在Linux系统中执行某些操作时候，有时需要将当前任务暂停调至后台，或有时须将后台暂停的任务重启开启并调至前台，这一序列的操作将会使用到 jobs、bg、和 fg 三个命令以及两个快捷键来完成。

### 语法

```shell
jobs(选项)(参数)
```

### 选项

```shell
-l：显示进程号；
-p：仅任务对应的显示进程号；
-n：显示任务状态的变化；
-r：仅输出运行状态（running）的任务；
-s：仅输出停止状态（stoped）的任务。
```

### 参数

任务标识号：指定要显示的任务识别号。

### 实例

使用jobs命令显示当前系统的任务列表，输入如下命令：

```shell
jobs -l               #显示当前系统的任务列表
```

上面的命令执行后，将显示出当前系统下的任务列表信息，具体如下所示：

```shell
[1] + 1903 运行中          find / -name password &
```

注意：要得到以上输出信息，必须在执行jobs命令之前执行命令`find / -name password &`。否则，执行jobs命令不会显示任何信息。

其中，输出信息的第一列表示任务编号，第二列表示任务所对应的进程号，第三列表示任务的运行状态，第四列表示启动任务的命令。

# fuser

使用文件或文件结构识别进程

## 补充说明

**fuser命令** 用于报告进程使用的文件和网络套接字。fuser命令列出了本地进程的进程号，那些本地进程使用file，参数指定的本地或远程文件。对于阻塞特别设备，此命令列出了使用该设备上任何文件的进程。

每个进程号后面都跟随一个字母，该字母指示进程如何使用文件。

- `c` ：指示进程的工作目录。
- `e` ：指示该文件为进程的可执行文件(即进程由该文件拉起)。
- `f` ：指示该文件被进程打开，默认情况下f字符不显示。
- `F` ：指示该文件被进程打开进行写入，默认情况下F字符不显示。
- `r` ：指示该目录为进程的根目录。
- `m` ：指示进程使用该文件进行内存映射，抑或该文件为共享库文件，被进程映射进内存。

### 语法

```shell
fuser(选项)(参数)
```

### 选项

```shell
-a：显示命令行中指定的所有文件；
-k：杀死访问指定文件的所有进程；
-i：杀死进程前需要用户进行确认；
-l：列出所有已知信号名；
-m：指定一个被加载的文件系统或一个被加载的块设备；
-n：选择不同的名称空间；
-u：在每个进程后显示所属的用户名。
```

### 参数

文件：可以是文件名或者TCP、UDP端口号。

### 实例

要列出使用`/etc/passwd`文件的本地进程的进程号，请输入：

```shell
fuser /etc/passwd
```

要列出使用`/etc/filesystems`文件的进程的进程号和用户登录名，请输入：

```shell
fuser -u /etc/filesystems
```

要终止使用给定文件系统的所有进程，请输入：

```shell
fuser -k -x -u -c /dev/hd1  或者  fuser -kxuc /home
```

任一命令都列出了进程号和用户名，然后终止每个正在使用`/dev/hd1 (/home)`文件系统的进程。仅有root用户能终止属于另一用户的进程。如果您正在试图卸下`/dev/hd1`文件系统，而一个正在访问`/dev/hd1`文件系统的进程不允许这样，您可能希望使用此命令。

要列出正在使用已从给定文件系统删除的文件的全部进程，请输入：

```shell
fuser -d /usr文件
```

`/dev/kmem` 用于系统映像。
`/dev/mem` 也用于系统映像。