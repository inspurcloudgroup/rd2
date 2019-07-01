# Spring Boot 中集成 Redis #


## 实验步骤 ##
学习如何安装 Redis 和如何使用 Spring Boot 整合 Redis，并且最后通过单元测试的方式来验证我们的代码

##  Redis 安装 ##
Redis 安装一般是通过下载源码包然后编译完成的，这样理论上可以做到安装流程跨平台，本节实验将演示在 Linux 系统上编译安装 Redis。

## 下载源码包 ##
Redis 的官网是：redis.io, 可以在上面下载最新的稳定版，实验楼提供的是目前最新的稳定版 5.0.3，首先下载源码包。

启动终端，使用 wget 命令下载文件：
```
wget http://labfile.oss.aliyuncs.com/courses/1232/redis-5.0.3.tar
```
## 编译安装 ##

下载的文件是 redis 源码，它基于 ANSI 标准，支持 GCC 等编译器，在实验楼环境中，只需要解压后使用 make 命令编译即可。

**解压源码包**

一般 Linux 环境都自带了 tar 归档工具，如果你的系统提示没有 tar 命令，请使用 apt-get install tar(ubuntu) 或者 yum install tar(centos) 进行安装。
```
$ tar -xvf redis-5.0.3.tar
$ ls
Code  Desktop  redis-5.0.3  redis-5.0.3.tar
```
解压时注意文件不含.gz 后缀，因此不能加 -z 参数
可以看到 shiyanlou 目录下多了一个 redis-5.0.3 目录，这就是解压完成后的源码目录。

**编译**

首先进入到解压后的目录：
```
cd redis-5.0.3
```
执行 make 命令：
```
shiyanlou:redis-5.0.3/ $ make
```
这个命令需要较长的时间，请耐心等待，编译完成以后相关程序就准备就绪了，主要是用到该目录下的两个程序

- redis-5.0.3/src/redis-server
- redis-5.0.3/src/redis-cli

redis-server 是数据库的中央服务器，它接收来自客户端的请求、操作、查询等工作，数据库中的数据也存放在 redis-server 所处的机器上

当然，在本例中还不能完全体会到 redis 的 C/S 模式，因为我们的客户端和服务端都在同一个系统上，实际上，redis 的客户端是多种多样的，它只是一个概念，当你用 Java 程序从另一台计算机上连接服务器是它也算是一个 redis 客户端

到目前位置，编译好的 redis 程序就可以直接使用了。

## 启动 Redis ##
首先启动 redis 服务端，在 redis5.0.3 目录下执行：
```
src/redis-server
```
启动成功过后将在控制台输出 Redis 点阵图标

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190628-1561723804488)

服务端启动后不能退出，这时需要重新打开一个终端窗

##  新建项目 ##
由于 maven 下载包需要联网，这里先将 maven 包下载下来，放到本地仓库便可以解决联网的问题
```
wget http://labfile.oss.aliyuncs.com/courses/1152/m2.zip
unzip m2.zip
mv .m2 /home/shiyanlou/
```
首先需要新建一个 SpringBoot 项目，具体步骤可以查看前面的章节，这里不再赘述。
```
mvn archetype:generate -DgroupId=com.shiyanlou -DartifactId=redis -DarchetypeArtifactId=maven-archetype-quickstart
```
最后形成下面的目录结构：

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1548924133982.png/wm)

## 添加依赖 ##
修改 pom.xml，添加下面的依赖

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.shiyanlou</groupId>
    <artifactId>redis</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>redis</name>
    <description></description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```
## 配置 Redis ##
在src/main/resources中的 application.properties 配置文件中添加以下配置
```
#redis 地址
spring.redis.host=localhost
#redis 端口
spring.redis.port=6379
#密码
spring.redis.password=
```
**RedisUtil**

在src/main/java/com/shiyanlou/中新建RedisUtil工具类，用来封装一些 redis 的基本操作，这里只写了 String 的操作和哈希表的部分操作。
```
package com.shiyanlou;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@SuppressWarnings("all")
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 将字符串值 value 关联到 key 。
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 返回 key 所关联的字符串值。
     *
     * @param key 键
     * @return 值 string
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 获取键过期时间
     *
     * @param key      键
     * @param timeUnit 时间单位
     * @return the long
     */
    public long ttl(String key, TimeUnit timeUnit) {
        return stringRedisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 设置键过期时间
     *
     * @param key      键
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return boolean boolean
     */
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return stringRedisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 获取旧值并且更新值
     *
     * @param key   键
     * @param value 新值
     * @return 旧值
     */
    public String getSet(String key, String value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 删除
     *
     * @param key 键
     * @return the boolean
     */
    public boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * Hset  将哈希表 key 中的域 field 的值设为 value 。
     *
     * @param key   键
     * @param field 域
     * @param value 值
     */
    public void hset(String key, String field, Object value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * Hget 返回哈希表 key 中给定域 field 的值。
     *
     * @param key   键
     * @param field 域
     * @return 值
     */
    public String hget(String key, String field) {
        return (String) stringRedisTemplate.opsForHash().get(key, field);
    }
}
```

## 添加 SpringBoot 启动类 ##
在src/main/java/com/shiyanlou新建 SpringBoot 启动类RedisApplication.java
```
package com.shiyanlou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

}
```
## 添加 SpringBoot 启动类 ##
在src/main/java/com/shiyanlou新建 SpringBoot 启动类RedisApplication.java
```
package com.shiyanlou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

}

```

## 单元测试 ##
接着新建一个单元测试来，用于测试我们的工具类能否正常运行。在·src/test/java下新建RedisApplicationTes

----------
ts
```
import com.shiyanlou.RedisApplication;
import com.shiyanlou.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void setGetTest() {
        redisUtil.set("name", "shiyanlou");
        Assert.assertEquals("shiyanlou", redisUtil.get("name"));
        redisUtil.del("name");

    }

    @Test
    public void delKeyTest() {
        redisUtil.set("name", "shiyanlou");
        redisUtil.del("name");
        Assert.assertNull(redisUtil.get("name"));
    }

    @Test
    public void updateTest() {
        redisUtil.set("old", "old");
        Assert.assertEquals(redisUtil.getSet("old", "new"), "old");
        Assert.assertEquals(redisUtil.get("old"), "new");
        redisUtil.del("old");
    }

    @Test
    public void expireTest() {
        redisUtil.set("expireTest", "expireTest");
        redisUtil.expire("expireTest", 10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(redisUtil.ttl("expireTest", TimeUnit.MILLISECONDS) < 10000);
        redisUtil.del("expireTest");
    }

    @Test
    public void hgetAndHsetTest() {
        redisUtil.hset("shi", "yan", "lou");
        Assert.assertEquals(redisUtil.hget("shi", "yan"), "lou");
        redisUtil.del("shi");
    }
}

```

## 运行单元测试 ##
```
cd /home/project/redis
mvn test
```
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1548924777663.png/wm)

五个测试用例全部通过，说明我们的工具类没有问题。

