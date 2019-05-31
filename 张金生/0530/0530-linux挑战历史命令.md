#挑战：历史命令#

##介绍##

在 Linux 中，对于文本的处理和分析是极为重要的，现在有一个文件叫做 data1，可以使用下面的命令下载：

$ cd /home/shiyanlou
$ wget http://labfile.oss.aliyuncs.com/courses/1/data1
data1 文件里记录是一些命令的操作记录，现在需要你从里面找出出现频率次数前3的命令并保存在 /home/shiyanlou/result。

##目标##

处理文本文件 /home/shiyanlou/data1
将结果写入 /home/shiyanlou/result
结果包含三行内容，每行内容都是出现的次数和命令名称，如“100 ls”
##提示##

cut 截取 (参数可以使用 -c 8-，使用 man cut 可以查看含义)
uniq -dc 去重
sort 的参数选择 -k1 -n -r
操作过程使用管道，例如：
$ cd /home/shiyanlou
$ cat data1 |....|....|....   >  /home/shiyanlou/result

##实现##
```
cat data1 |cut -c 8-|sort|uniq -dc|sort -rn -k1 |head -3 > /home/shiyanlou/result
```
