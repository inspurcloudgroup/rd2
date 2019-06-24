# Spring Boot Web 支持#

## 实验步骤 ##
这一节将学习使用 Spring Boot 开发 web 应用。本节需要同学们已经掌握了 Spring MVC 的知识。

由于 maven 下载包需要联网，这里先将 maven 包下载下来，放到本地仓库便可以解决联网的问题
```
wget http://labfile.oss.aliyuncs.com/courses/1152/m2.zip
unzip m2.zip
mv .m2 /home/shiyanlou/
```

## Spring Boot MVC 基本配置 ##

上一章节已经知道了如何修改 Spring Boot 的访问端口，那么可能有同学会问，之前的 Spring MVC 项目都是放到 tomcat 容器中运行，为什么在这里没有 tomcat 也可以访问呢？因为 SpringBoot 已经内置了 Servlet 容器，包括 tomcat，jetty，Undertow 等，同学们可以自由选择。接着看看 Spring Boot 提供了哪些与 Servlet 容器相关的属性配置：

![](https://doc.shiyanlou.com/document-uid441493labid7987timestamp1535531648183.png/wm)

可以在图中看到许多 servlet 容器相关的配置，有 jsp，session,cookie。这里说明一下server.servlet.path这个属性，这个属性用于设置 dispatcher servlet 的监听路径，默认是： / ，但是这个属性在 SpringBoot2.x 中有改动，在 SpringBoot1.x 中该属性为server.servlet-path。

再看看 SpirngBoot 为 Spring mvc 提供了哪些配置：

![](https://doc.shiyanlou.com/document-uid441493labid7987timestamp1535533328468.png/wm)

可以在图中看到许多在学习 Spring MVC 时熟悉的属性。比如spring.mvc.view.prefix 和 spring.mvc.view.suffix,这两个属性用于配置视图文件的前缀和后缀。接下来看看如何在 Spring Boot 中使用它们，首先建立一个 Spring Boot 项目springboot (前面已经说过如何建立，这里不再赘述),然后修改 pom.xml，添加 maven 相关依赖包。
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
创建如下目录结构（和上个实验一样的目录结构，可以将上个实现的代码直接拿过来修改）

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542851989479.png/wm)

接下来在application.properties编写如下内容
```
#端口
server.port=8080
#视图文件前缀
spring.mvc.view.prefix=/view/
#视图文件后缀
spring.mvc.view.suffix=.html
```
接着在src/main/resources/static/中创建view目录，在 view 目录下创建shiyanlou.html文件。(注意：这里只是为了演示 SpringBoot 映射到视图文件，实际上开发中并不会放到这里，SpringBoot 推荐使用模板引擎，也就是放到 template 中)

内容如下
```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<p style="color: red;">welcome shiyanlou</p>
</body>
</html>
```
接着来编写ShiyanlouController.java
```
package com.shiyanlou.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//注意这里已经不是@RestController了 和上个试验不同，因为需要返回视图，所以不能使用@ResponseBody
@Controller
public class ShiyanlouController {

    @RequestMapping("shiyanlou")
    public String shiyanlou(){
        return "shiyanlou";
    }
}
```
看看SpringbootApplication.java：
```
package com.shiyanlou.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
```
运行使用mvn spring-boot:run运行程序，选择工具中的 web 服务，访问https://**********.simplelab.cn/shiyanlou

可以看到成功的映射到了的视图文件。

## SpringBoot 静态资源 ##

SpringBoot 的静态资源默认目录为/static、/public、/resources、和/META-INF/resources，默认映射路径都是/。SpringBoot 默认会按照META/resources > resources > static > public 的优先级寻找对应的资源文件并返回第一个找到的文件。下面来试一试 SpringBoot 的静态资源访问，在 static 中放入一张图片1.jpg,接下来重启项目，访问`https://**.simplelab.cn/1.jpg`

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542852779293.png/wm)

成功的访问到了放在其中的静态资源。如果不想使用默认的静态资源路径，可以配置自己的静态资源路径，通过实现WebMvcConfigurer接口的addResourceHandlers方法来自定义静态资源，打开SpringbootApplication.java，添加以下内容：
```
package com.shiyanlou.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringbootApplication {


    //设置配置类
    @Configuration
    static class WebConfig implements WebMvcConfigurer {

        //重写addResourceHandlers方法
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            //设置静态资源映射路径为/**，资源位置为类路径下的upload
            registry.addResourceHandler("/**").addResourceLocations("classpath:/upload/");
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
```
接着在src/main/resources目录下建立upload目录，下载图片文件
```
wget http://labfile.oss.aliyuncs.com/courses/1152/1.jpg
```
将1.jpg移动到upload目录下，重启项目，访问https://********.simplelab.cn/1.jpg

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542852779293.png/wm)

成功访问到了自定义目录的静态资源。

除了使用 Java 配置外，也可以使用 application.properties 配置，
```
#端口
server.port=8080
spring.mvc.view.prefix=/view/
spring.mvc.view.suffix=.html

#多个配置之间使用,分割  注意这个属性会使默认的配置失效
spring.resources.static-locations=classpath:/upload/,classpath:/static
```
然后注释掉SpringbootApplication.java中的配置内容
```
package com.shiyanlou.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringbootApplication {


    //设置配置类
//    @Configuration
//    static class WebConfig implements WebMvcConfigurer {
//
//        //重写addResourceHandlers方法
//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//            //设置静态资源映射路径为/**，资源位置为类路径下的upload
//            registry.addResourceHandler("/**").addResourceLocations("classpath:/upload/");
//        }
//    }
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
```
重启项目，打开浏览器访问https://********.simplelab.cn/1.jpg

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542852779293.png/wm)

说明成功的使用 application.properties 文件改变了 SpringBoot 的静态文件配置。

另外尽量不在 SpringBoot 中使用 src/main/wabapp 目录。

>如果你的应用使用 jar 打包，不要使用/src/main/webapp 目录，尽管它是一个标准目录,该目录仅仅在使用 war 打包部署的时候生效，在大部分构建工具都它都会被忽略

以上为 SpringBoot 官方原话。





















































