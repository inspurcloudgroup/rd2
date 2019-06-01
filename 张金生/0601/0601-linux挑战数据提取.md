#挑战：数据提取#

##介绍##

小明在做数据分析的时候需要提取文件中关于数字的部分，同时还要提取用户的邮箱部分，但是有的行不是数组也不是邮箱，现在需要你在 data2 这个文件中帮助他用正则表达式匹配出数字部分和邮箱部分。

数据文件可以使用以下命令下载：
```
$ cd /home/shiyanlou
$ wget http://labfile.oss.aliyuncs.com/courses/1/data2
```
下载后的数据文件路径为 /home/shiyanlou/data2。

##目标##

在文件 /home/shiyanlou/data2 中匹配数字开头的行，将所有以数字开头的行都写入 /home/shiyanlou/num 文件。
在文件 /home/shiyanlou/data2 中匹配出正确格式的邮箱，将所有的邮箱写入 /home/shiyanlou/mail 文件，注意该文件中每行为一个邮箱。
##提示##

邮箱的格式匹配
注意符号 . 的处理

##代码##


cd ~
grep  '^[[:digit:]]' data2 > num
grep -E '^[0-9a-zA-Z_-]+@[0-9a-zA-Z_-].([0-9a-zA-Z_-])+$' > mail

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190601-1559397324280)



