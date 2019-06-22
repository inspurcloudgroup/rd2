# 搭建第一个Spring Boot应用 #

## 实验步骤 ##
接下来将开始学习如何搭建自己的 Spring Boot 程序。

由于 maven 下载包需要联网，这里先将 maven 包下载下来，放到本地仓库便可以解决联网的问题(非会员用户一定要运行这一步，否则无法进行实验)

wget http://labfile.oss.aliyuncs.com/courses/1152/m2.zip
unzip m2.zip
cp .m2 /home/shiyanlou/

## 采用 Spring Initializr 搭建 ##

Spring Initializr 是官方提供的一种快捷搭建 Spring Boot 应用的方式。 只需要打开网址: https://start.spring.io/ 就可以看到:

![](https://doc.shiyanlou.com/document-uid441493labid7987timestamp1535422355067.png/wm)

我们可以看到上面可以选择构建工具，语言，Spring Boot 版本，group 和 artifact 以及依赖。 这里我们选择使用Maven构建，语言java，Spring Boot 版本2.0.4，group 为com.shiyanlou, artifact 为springboot，依赖我们选择web。

![](https://doc.shiyanlou.com/document-uid441493labid7987timestamp1535425658177.png/wm)

点击 Generate Project，我们会得到一个springboot.zip的压缩包。 这里直接提供该压缩包，将其下载解压
```
wget http://labfile.oss.aliyuncs.com/courses/1152/springboot.zip
unzip springboot.zip
```
接着切换工作空间到springboot目录。

然后在src/main/java目录下新建包路径com.shiyanlou.springboot.controller

在包中建立新类ShiyanlouController.java,代码如下：
```
package com.shiyanlou.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//RestController相当于同时使用@Controller和@ResponseBody注解
public class ShiyanlouController {

    @RequestMapping("shiyanlou")
    public String shiyanlou() {
        return "Hello Shiyanlou";
    }
}
```
项目文件结构如下：

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542779592020.png/wm)

然后打开 terminal，运行mvn spring-boot:run(这里使用 spirngboot 的 maven 插件启动)。点击工具中的 web 服务，接着修改地址栏为https://**************.simplelab.cn/shiyanlou 

结果
https://4b5bbd813836.simplelab.cn/shiyanlou

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190622-1561206280885)


## 手动构建 Spring Boot 项目 ##

首先我们需要创建一个普通的 maven 项目 springboot
```
mvn archetype:generate -DgroupId=com.shiyanlou -DartifactId=springboot -DarchetypeArtifactId=maven-archetype-quickstart
```
创建过程中会提示输入版本号等，直接使用默认即可。接着输入Y确定创建

创建完成后切换工作空间到springboot目录，接着建立目录src/main/resources，这里不要单元测试，所以将src/test目录删除，建立包路径com.shiyuanlou.springboot和com.shiyanlou.springboot.controller。

最终的目录结构如下

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542780836940.png/wm)

添加 maven 依赖，修改 pom.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.shiyanlou</groupId>
    <artifactId>springboot</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>springboot</name>
    <description>Demo project for Spring Boot</description>

    <!--设置父模块 这样就可以继承父模块中的配置信息-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.4.RELEASE</version>
        <relativePath/>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
    <!--添加web依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
        <!--spirng Boot maven插件-->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```
在包com.shiyanlou.springboot中建立SpringbootApplication.java，代码如下：
```
package com.shiyanlou.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication注解相当于同时使用@EnableAutoConfiguration、@ComponentScan、@Configurations三个注解  
//@EnableAutoConfiguration用于打开SpringBoot自动配置，而其余注解为Spring注解，这里不再赘述
@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
```
在包com.shiyanlou.springboot.controller 下建立ShiyanlouController.java，代码如下：
```
package com.shiyanlou.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShiyanlouController {

    @RequestMapping("shiyanlou")
    public String shiyanlou() {
        return "Hello Shiyanlou";
    }
}
```
打开 terminal，输入（一定要在项目目录中运行 maven 命令，比如这里是 springboot 目录）
```
mvn spring-boot:run
```
出现Hello Shiyanlou,那么就说明成功了！

## SpringBoot 中的 Starter ##

Maven 中我们只添加了一个依赖，那就是spring-boot-starter-web，我们知道之前使用 Spring 创建 Web 项目的时候，maven 依赖可是很长一条，这里居然这么少，没错就是这么少，其实里面的依赖都被封装好了。我们现在只添加了 web 项目的依赖，如果我们要添加其他的依赖怎么办呢，同样的，比如我们访问数据库，那么我们可以添加持久层框架，比如spring-data-jpa,我们只需要添加spring-boot-starter-data-jpa依赖就可以了。

同学们应该都发现了，他们有一个共同点，都是spring-boot-starter开头，后面接上特定的应用程序，这样可以帮助我们快速找到我们所需要的 starter。官方提供的 starter 一般为spring-boot-starter-*，第三方一般为*-spring-boot-starter。

接下来我们来看看 Springboot 官方提供了哪些 Starter：

Spring Boot 应用 Starter!
[](https://doc.shiyanlou.com/document-uid441493labid7987timestamp1535505356623.png/wm)

Spring Boot 生产相关 Starter
![](https://doc.shiyanlou.com/document-uid441493labid7987timestamp1535505857263.png/wm)

Spring Boot 容器和日志 Starter

![](https://doc.shiyanlou.com/document-uid441493labid7987timestamp1535505974994.png/wm)


