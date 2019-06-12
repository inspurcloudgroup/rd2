# Linux

## 知识点

**搜索文件**

- whereis 简单快速

```
whereis who
whereis find
```

看到 whereis find 找到了三个路径，两个可执行文件路径和一个 man 在线帮助文件所在路径，这个搜索很快，因为它并没有从硬盘中依次查找，而是直接从数据库中查询。  
> whereis 只能搜索二进制文件(-b)，man 帮助文件(-m)和源代码文件(-s)。

- locate 快而全

通过" /var/lib/mlocate/mlocate.db "数据库查找.

可能会找不到，需要手动执行一次 updatedb 命令.

用来查找指定目录下的不同文件类型

```
# 查找 /etc 下所有以 sh 开头的文件
sudo apt-get update
sudo apt-get install locate
locate /etc/sh

# 查找 /usr/share/ 下所有 jpg 文件
#  * 号前面是反斜杠转义
locate /usr/share/\*.jpg
```

> 只统计数目可以加上 -c 参数，-i 参数可以忽略大小写进行查找，whereis 的 -b、-m、-s 同样可以使用

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190612-1560347883679)

- which 小而精

which 本身是 Shell 内建的一个命令，我们通常使用 which 来确定是否安装了某个指定的软件，因为它只从 PATH 环境变量指定的路径中去搜索命令

```
which man
```

- find 精而细

不但可以通过文件类型、文件名进行查找而且可以根据文件的属性（如文件的时间戳，文件的权限等）进行搜索。

```
#  /etc/ 目录下面 ，搜索名字叫做 interfaces 的文件或者目录
sudo find /etc/ -name interfaces
```

> 基本命令格式为 find [path] [option] [action]

|参数|说明|
|--|--|
|-atime|最后访问时间|
|-ctime	|最后修改文件内容的时间|
|-mtime	|最后修改文件属性的时间| 

下面以 -mtime 参数举例：

-mtime n：n 为数字，表示为在 n 天之前的“一天之内”修改过的文件  
-mtime +n：列出在 n 天之前（不包含 n 天本身）被修改过的文件  
-mtime -n：列出在 n 天之内（包含 n 天本身）被修改过的文件  
-newer file：file 为一个已存在的文件，列出比 file 还要新的文件名  

![](https://doc.shiyanlou.com/linux_base/5-8.png/wm)

```
# 列出 home 目录中，当天（24 小时之内）有改动的文件：
find ~ -mtime 0
# 列出 home 目录中，比 Code 文件夹新的文件：
find ~ -newer /home/shiyanlou/Code
```

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190612-1560350408775)

- 作业：找出 /etc/ 目录下的所有以 .list 结尾的文件  

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190612-1560350539157)

> find命令或者locate，问题是权限不够代表什么意思？那么我这里尝试用root账户进行find

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190612-1560350721099)

> root账户进行find查找

### 挑战：寻找文件

```
# 寻找文件位置
sudo find * -name sources.list
# 改变文件用户shiyanlou
sudo chown shiyanlou /etc/apt/sources.list
# 改变文件权限，仅用户可访问
sudo chmod 700 /etc/apt/sources.list
```

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190612-1560351978867)

> 这里测试了find命令中，将路径作为未知量进行全局搜索
![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190612-1560351787559)

### 文件打包与解压缩

**常见压缩包文件格式**  
 > .zip，.7z .rar .gz .xz .bz2 .tar .tar.gz .tar.xz .tar.bz2 

**zip 压缩打包程序**

- 使用 zip 打包文件夹：

```
cd /home/shiyanlou
zip -r -q -o shiyanlou.zip /home/shiyanlou/Desktop
du -h shiyanlou.zip
file shiyanlou.zip
```

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190612-1560352568284)

> -r 参数表示递归打包包含子目录的全部内容，-q 参数表示为安静模式，即不向屏幕输出信息，-o，表示输出文件  
> du命令查看打包后文件的大小

- 设置压缩级别为 9 和 1（9 最大，1 最小），重新打包：

```
zip -r -9 -q -o shiyanlou_9.zip /home/shiyanlou/Desktop -x ~/*.zip
zip -r -1 -q -o shiyanlou_1.zip /home/shiyanlou/Desktop -x ~/*.zip
```

> 1 表示最快压缩但体积大，9 表示体积最小但耗时最久。最后那个 -x 是为了排除我们上一次创建的 zip 文件

再用 du 命令分别查看默认压缩级别、最低、最高压缩级别及未压缩的文件的大小：

```
du -h -d 0 *.zip ~ | sort
```

> h， --human-readable（人能读的哈哈）  
> d， --max-depth（所查看文件的深度）

- 创建加密 zip 包

```
# 使用 -e 参数可以创建加密压缩包：
zip -r -e -o shiyanlou_encryption.zip /home/shiyanlou/Desktop
```

- 保证在 Linux 创建的 zip 压缩文件在 Windows运行  
*因为 Windows 系统与 Linux/Unix 在文本文件格式上的一些兼容问题，比如换行符（为不可见字符），在 Windows 为 CR+LF（Carriage-Return+Line-Feed：回车加换行），而在 Linux/Unix 上为 LF（换行），所以如果在不加处理的情况下，在 Linux 上编辑的文本，在 Windows 系统上打开可能看起来是没有换行的* 
 

```
#  -l 参数将 LF 转换为 CR+LF 
zip -r -l -o shiyanlou.zip /home/shiyanlou/Desktop
```

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190612-1560353783446)

**使用 unzip 命令解压缩 zip 文件**

```
# 将 shiyanlou.zip 解压到当前目录：
unzip shiyanlou.zip
# 使用安静模式，将文件解压到指定目录：
unzip -q shiyanlou.zip -d ziptest
# 不想解压只想查看压缩包的内容你可以使用 -l 参数：
unzip -l shiyanlou.zip
```

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190612-1560354525636)

> *注意中文编码的问题*。通常 Windows 系统上面创建的压缩文件，如果有有包含中文的文档或以中文作为文件名的文件时默认会采用 GBK 或其它编码，而 Linux 上面默认使用的是 UTF-8 编码

```
# 使用 -O（英文字母，大写 o）参数指定编码类型：
unzip -O GBK 中文压缩文件.zip
```

**tar 打包工具**

- tar解压和压缩都是同一个命令，只需参数不同，使用比较方便。

```
# 创建tar包进行归档
tar -cf shiyanlou.tar home/shiyanlou/Desktop
```

> -c 表示创建一个 tar 包文件，-f 用于指定创建的文件名，注意文件名必须紧跟在 -f 参数之后，-v 表示以可视的的方式输出打包的文件。    
> 上面会自动去掉表示绝对路径的 /，你也可以使用 -P 保留绝对路径符

```
# 解包一个文件（-x 参数）到指定路径的已存在目录（-C 参数）：
mkdir tardir
tar -xf shiyanlou.tar -C tardir

# 只查看不解包文件 -t 参数：
tar -tf shiyanlou.tar

# 使用 tar 备份文件当你在其他主机还原时希望保留文件的属性（-p 参数）和备份链接指向的源文件而不是链接本身（-h 参数）：
tar -cphf etc.tar /etc
```

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190613-1560355253826)

- 使用 gzip 工具创建 *.tar.gz 文件

```
# 只需要在创建 tar 文件的基础上添加 -z 参数，使用 gzip 来压缩文件：
tar -czf shiyanlou.tar.gz /home/shiyanlou/Desktop
# 解压 *.tar.gz 文件：
tar -czf shiyanlou.tar.gz /home/shiyanlou/Desktop
# 查看
du -h shiyanlou.tar.gz
```

|压缩文件格式|参数|
|--|--|
|*.tar.gz|-z|
|*.tar.xz|-J|
|*tar.bz2|-j|

- 作业：

```
touch test
# zip打包与解压
zip test_zip.zip /home/shiyanlou/test
unzip test_zip.zip -d /home/shiyanlou
# tar打包与解压
tar -cf test_tar.tar /home/shiyanlou/test
tar -xf test_tar.tar -C /home/shiyanlou
```

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190613-1560356036712)

# 今日体会

压缩和解压缩的常用命令：  
zip：  
打包 ：zip something.zip something （目录请加 -r 参数）  
解包：unzip something.zip  
指定路径：-d 参数  
tar：  
打包：tar -cf something.tar something  
解包：tar -xf something.tar  
指定路径：-C 参数  

*locate的查询结果在哪里显示?*
![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1079862-20190612-1560347883679)

# 明日计划
完成Linux课程7-8