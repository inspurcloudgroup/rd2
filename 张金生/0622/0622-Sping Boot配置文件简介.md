# Sping Boot配置文件简介 #

## 实验步骤 ##
接下来开始学习 SpringBoot 的配置文件，继续使用上一个实验的项目代码进行学习。

由于 maven 下载包需要联网，这里先将 maven 包下载下来，放到本地仓库便可以解决联网的问题（如果在上个试验中已经保存了那么不用多次下载）
```
wget http://labfile.oss.aliyuncs.com/courses/1152/m2.zip
unzip m2.zip
mv .m2 /home/shiyanlou/
```
## application.properties 配置文件 ##

在上一个实验的小节中，可以看到官方提供的压缩包中的 src/main/resources 目录下有一个 application.properties 配置文件(如果没有就手动创建一个)，这就是 SpringBoot 默认的配置文件，可以对其进行更改来修改 SpringBoot 的配置。在修改之前，首先需要知道 properties 文件的格式。properties 文件的内容都是以键值对的形式出现键=值,比如要修改 SpringBoot 的端口，那么就在配置文件中填写
```
#端口
server.port=8080
```
注意！在实验楼提供的环境中，只有 8080 端口才对外开发，并且访问时会隐藏 8080 端口，所以在环境中我们只能通过控制台输出观察，地址栏是观察不到的
properties 文件中注释使用#开头;
```
#端口
server.port=8080
```
properties 文件中每个属性占有一行。
properties 中除了填写 SpringBoot 的配置信息外也可以自定义属性，比如在其中添加
```
#端口
server.port=8080
shiyanlou.springboot=Hello_shiyanlou
```
shiyanlou.springboot是自定义的属性，那么如何在 springBoot 中使用它们呢？打开ShiyanlouController.java,修改为下面的代码。
```
package com.shiyanlou.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShiyanlouController {
//使用@Value注解注入属性值
    @Value("${shiyanlou.springboot}")
    private String shiyanlou;

    @RequestMapping("shiyanlou")
    public String shiyanlou(){
        return shiyanlou;
    }
}
```
然后重新运行项目mvn spring-boot:run，依旧访问路径https://**************.simplelab.cn/shiyanlou，如果得到下图的画面，就说明成功的访问了自定义属性。

![](![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190622-1561208705346))

注意: 这里只能访问 application.properties 中的属性，如果是其他自定义的配置文件中的属性是访问不到的，还需要其他的处理。

首先在src/main/resources目录下建立shiyanlou.properties文件,内容为：

shiyanlou.test=test_shiyanlou
修改ShiyanlouController.java：
```
package com.shiyanlou.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//加载classpath目录下的shiyanlou.properties文件
@PropertySource(value = "classpath:shiyanlou.properties")
public class ShiyanlouController {

    @Value("${shiyanlou.test}")
    private String shiyanlou;

    @RequestMapping("shiyanlou")
    public String shiyanlou(){
        return shiyanlou;
    }
}
```
再次运行程序，可以看到

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542781939206.png/wm)

说明加载自定义配置文件成功了。

## Spirng Boot Java 配置 ##

Spring Boot 除了可以使用 application.properties 配置之外，也可以使用 Java 来自定义配置，就比如在 2.1 中说到的修改访问端口为 8080，也可以通过 Java 代码来实现。

建立包com.shiyanlou.springboot.config,在包目录下建立ServletConfig.java;
```
package com.shiyanlou.springboot.config;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

//@Configuration表示该类为配置类，改注解可以被@ComponentScan扫描到
@Configuration
public class ServletConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        //设置端口为8080
        factory.setPort(8080);
    }
}
```
然后将 application.properties 中的 server.port=8080 注释掉
```
#端口
#server.port=8080
shiyanlou.springboot=Hello_shiyanlou
```
然后启动程序,如果出现test_shiyanlou那么就说明成功的通过 java 代码修改了 SpringBoot 的配置。
![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190622-1561209052471)
![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190622-1561209103825)

>注意：这是 SpringBoot2.x 通过 Java 代码修改内嵌容器端口的方式，如果是使用 SpringBoot1.x 这个方法就行不通了，SpringBoot1.x 是通过实现EmbeddedServletContainerCustomizer接口来修改。

## SpringBoot xml 配置 ##

SpringBoot 已经不推荐使用 xml 作为配置方式，如果一定要使用，可以通过@ImportResource注解来完成。 首先注释掉ServletConfig.java中的@Configuration

```
package com.shiyanlou.springboot.config;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;


//@Configuration
public class ServletConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        //设置端口
        factory.setPort(8080);
    }
}
```
现在启动项目，在启动日志中可以看到端口已经回到了默认端口 8080

>2018-08-29 14:56:09.384 INFO 48712 --- [ main] o.s.b.w.embedded.tomcat.TomcatWebServer : Tomcat started on port(s): 8080 (http) with context path ''
接着在src/main/resources/中建立 xml 文件config.xml：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="servletConfig" class="com.shiyanlou.springboot.config.ServletConfig"/>

</beans>
```
然后在SpringbootApplication.java修改为如下形式：
```
package com.shiyanlou.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

//通过@ImportResource加载xml配置文件
@ImportResource(value = "classpath:config.xml")
@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
```
接着重启项目，如果可以成功访问，那么说明成功的导入了 xml 配置。






















































