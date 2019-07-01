# Profile配置说明及线上部署 #

## Profile 配置 ##

在实际的开发过程，所使用的配置文件一般是本地的配置，而服务器上面的配置会和本地的配置不同，比如日志输出级别、数据库地址、数据库密码等，在开发环境下的密码可能很简单甚至没有密码，而生产环境不同，生产环境肯定是需要设置密码的，而在开发迭代的过程中，需要不断将更新后的程序部署到服务器，不能每次都将配置文件改过去改过来，当配置文件非常多的时候，麻烦不说，还非常的容易出错，所以需要生产环境一套配置，开发环境一套配置，并且可以很方便的切换。Spring 的配置文件提供了这样的功能。

在前面的实验中，可以看到src/main/resouces/目录下有一个application.properties文件，SpringBoot 提供了一种命名规则application-{profile}.properties，profile 为自定义内容，比如application-dev.properties，可以在这个文件里面定义在开发环境中使用的配置，如端口，数据库连接地址，用户名，密码等信息，而application.properties则提供公共配置，在application.properties中使用spring.profiles.active属性激活，可以激活多个配置，不同配置使用逗号分隔。如spring.profiles.active=dev,prod。

下面建立一个简单的项目来学习 Profile 的配置。先将 maven 包放到本地仓库，注意这里使用shiyanlou账户，不要使用root账户。
```
wget http://labfile.oss.aliyuncs.com/courses/1152/m2.zip
unzip m2.zip -d /home/shiyanlou/
```
建立一个 maven 项目springboot
```
mvn archetype:generate -DgroupId=com.shiyanlou -DartifactId=springboot -DarchetypeArtifactId=maven-archetype-quickstart
```
接着修改根目录的pom.xml文件。

pom.xml

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

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
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
在src/main/resources中建立application.properties、application-dev.properties、application-prod.properties。

application.properties内容在：
```
#激活的配置文件
spring.profiles.active=dev
```
application-dev.properties内容：
```
hello=shiyanlou_dev
```
application-prod.properties内容：
```
hello=shiyanlou_prod
```
在src/main/java目录下的com.shiyanlou.springboot包中建立两个文件ShiyanlouController.java，SpringbootApplication.java。

ShiyanlouController.java内容：
```
package com.shiyanlou.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShiyanlouController {

    @Value("${hello}")
    private String hello;


    @RequestMapping("shiyanlou")
    public String shiyanlou() {
        return hello;
    }
}
```
SpringbootApplication.java内容：
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
启动项目mvm spring-boot:run，点击右上角的工具中的 web 服务按钮，访问https://*********.simplelab.cn/shiyanlou,结果如下：

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542866360630.png/wm)

修改application.properties配置：
```
#激活的配置文件
spring.profiles.active=prod
```
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542868514104.png/wm)

成功切换配置文件。如果发现切换后得到的结果没有改变可以尝试清空浏览器缓存。

## SpringBoot 部署 ##

部署 SpringBoot 之前，需要先将 SpringBoot 打包成可执行的 Jar 包,进入目录/home/project/springboot,然后使用 maven 将其打包成 jar 文件
```
cd /home/project/springboot
mvn package
```
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542868679864.png/wm)

出现 build success 就说明打包成功，jar 文件会生成在 target 目录中。

打包完成之后，可以在 target 目录中找到 jar 包，进入 target 目录，使用命令
```
java -jar springboot-0.0.1-SNAPSHOT.jar
```
就可以运行 jar 包了。接着点击右上角工具--web服务访问https://*********.simplelab.cn/shiyanlou

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542868514104.png/wm)

可以访问，部署成功。

如果需要使用 war 的方式部署到外部 tomcat 容器中，修改 pom.xml 文件的配置，将打包方式修改为 war
```
    <packaging>war</packaging>
```
接着修改SpringbootApplication.java
```
package com.shiyanlou.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringbootApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringbootApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
```
因为没有 web.xml 文件，所以需要继承SpringBootServletInitializer并且覆盖configure(SpringApplicationBuilder builder)方法

接下来的步骤和打包 jar 文件是一样的。打包好了之后将 war 文件放到 tomcat 的 webapps 目录中就可以了。


