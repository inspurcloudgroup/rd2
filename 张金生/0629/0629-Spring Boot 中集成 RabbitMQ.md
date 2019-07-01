# Spring Boot 中集成 RabbitMQ #
RabbitMQ 是一种应用非常广泛的开源消息中间件，接下来将学习如何在 SpringBoot 中使用 RabbitMQ。

## 安装 RabbitMQ ##
自 Debian since 6.0 (squeeze) 和 Ubuntu 9.04 之后，rabbitmq-server 就在 Ubuntu 的官方源里面了，但是如果你想安装最新版本，可以在官网下载，或者使用官方提供的源来安装。本次实验我们就用 Ubuntu 源里的版本安装，所有的依赖都可以被自动安装。
```
sudo apt-get update
sudo apt-get install rabbitmq-server
```
## 启动 RabbitMQ ##
当 RabbitMQ 安装完毕的时候服务器就会像后台程序一般运行起来了。

服务器相关命令：
```
启动：sudo service rabbitmq-server start
关闭： sudo service rabbitmq-server stop
查看状态：sudo service rabbitmq-server status
```

## 新建项目 ##
由于 maven 下载包需要联网，这里先将 maven 包下载下来，放到本地仓库便可以解决联网的问题
```
wget http://labfile.oss.aliyuncs.com/courses/1152/m2.zip
unzip m2.zip
mv .m2 /home/shiyanlou/
```
首先需要新建一个 SpringBoot 项目，具体步骤可以查看前面的章节，这里不再赘述。
```
mvn archetype:generate -DgroupId=com.shiyanlou -DartifactId=rabbit -DarchetypeArtifactId=maven-archetype-quickstart
```
最后形成下面的目录结构：

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1548914014086.png/wm)

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
    <artifactId>rabbit</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>rabbit</name>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
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

## 配置 RabbitMQ ##
在src/main/resources中的 application.properties 配置文件中添加以下配置
```
# rabbitmq地址
spring.rabbitmq.host=localhost
# 用户名
spring.rabbitmq.username=guest
# 密码
spring.rabbitmq.password=guest
# 端口
spring.rabbitmq.port=5672
```
## RabbitMQConfig ##

在src/main/java/com/shiyanlou/中新建RabbitConfig用来配置 RabbitMQ，比如创建队列，创建 Exchange 等。这里我们采用发布/订阅模式，所以我们建立了一个 Exchange 和两个队列。
```
package com.shiyanlou;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author shiyanlou
 */
@Component
public class RabbitConfig {

    @Bean
    public Queue queueA() {
        return new Queue("queueA");
    }

    @Bean
    public Queue queueB() {
        return new Queue("queueB");
    }

    /**
     * 建立一个fanout模式的Exchange
     *
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 绑定队列A到交换机
     *
     * @param queueA
     * @param fanoutExchange
     * @return
     */
    @Bean
    public Binding bindingQueueA(Queue queueA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }

    /**
     * 绑定队列A到交换机
     *
     * @param queueB
     * @param fanoutExchange
     * @return
     */
    @Bean
    public Binding bindingQueueB(Queue queueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }

}

```

## 创建提供者 ##
在src/main/java/com/shiyanlou/中新建Provider用于生产消息
```
package com.shiyanlou;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shiyanlou
 */
@Component
public class Provider {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(String msg){
        System.out.println("提供者 发送消息：" + msg);
        amqpTemplate.convertAndSend("fanoutExchange","", msg);
    }
}
```
## 创建消费者 ##
在src/main/java/com/shiyanlou/中新建Consumer用于消费消息
```
package com.shiyanlou;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author shiyanlou
 */
@Component
public class Consumer {

    @RabbitListener(queues = "queueA")
    public void receiveQueueA(String msg){
        System.out.println("消费者 queueA 收到消息：" + msg);
    }

    @RabbitListener(queues = "queueB")
    public void receiveQueueB(String msg){
        System.out.println("消费者 queueB 收到消息：" + msg);
    }
}
```
## 添加 Spring Boot 启动类 ##
在src/main/java/com/shiyanlou新建 SpringBoot 启动类RabbitApplication.java
```
package com.shiyanlou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class, args);
    }

}
```
## 单元测试 ##

一切准备就绪后，我们需要编写单元测试，来检查是否程序是否可以正常运行。在·src/test/java下新建RabbitApplicationTests
```
import com.shiyanlou.Provider;
import com.shiyanlou.RabbitApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitApplication.class)
public class RabbitApplicationTests {

    @Autowired
    private Provider provider;

    @Test
    public void providerTest() {
        provider.sendMessage("Hello Shiyanlou");
    }

}
```

运行单元测试
```
cd /home/project/rabbit
mvn test
```
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1548916339724.png/wm)

