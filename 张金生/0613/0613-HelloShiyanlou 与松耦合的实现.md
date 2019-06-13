# HelloShiyanlou 与松耦合的实现 #

本节实验入门简单的 Maven 项目创建和如何实现松耦合。


## 项目文件结构 ##

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1541745318530.png/wm)

>关于在线环境的使用遇到任何问题查阅使用指南：https://www.shiyanlou.com/questions/89126

创建工程：
```
mvn archetype:generate -DgroupId=com.shiyanlou -DartifactId=springExample -DarchetypeArtifactId=maven-archetype-webapp

```
切换到工作空间
点击File-> Open Workspace 

添加package
添加包就是添加文件夹，点击File-> New Folder

```
cd springExample/src/main
mkdir -p java/com/shiyanlou/demo/helloworld
```

## 实验环境介绍 ##

**Maven 简介**

在项目开始之前，我们先来了解一下 Maven 相关知识。

>Maven 是一个项目管理和综合工具。Maven 提供了开发人员构建一个完整的生命周期框架。开发团队可以自动完成项目的基础工具建设，Maven 使用标准的目录结构和默认构建生命周期。

>在多个开发团队环境时，Maven 可以设置按标准在非常短的时间里完成配置工作。由于大部分项目的设置都很简单，并且可重复使用，Maven 让开发人员的工作更轻松，同时能够创建报表，检查、构建和测试自动化设置。

Maven 提供了开发人员的方式来管理：

- Builds
- Documentation
- Reporting
- Dependencies
- SCMs
- Releases
- Distribution
- mailing list

概括地说，Maven 简化和标准化项目建设过程，处理编译，分配，文档，团队协作和其他任务的无缝连接。 Maven 增加可重用性并负责建立相关的任务。

>注:实验楼环境中已经安装了 maven，所以这里略过 maven 的安装步骤，需要在本地安装 maven 的同学可以自行查找 maven 安装教程。

## 导入 Maven 项目所需的包 ##

由于 Maven 新建项目需要很多包，首先会扫描本地仓库，如果没有则需要联网，在中央仓库下载到本地仓库。我们已经把本次项目需要的包已经打包传到服务器上，现在，我们只需要从实验楼下载所要的包，并安放到正确的位置即可。

仍旧在 Terminal 中，输入：
```
wget http://labfile.oss.aliyuncs.com/courses/578/m2.zip
```
然后，解压：
```
unzip m2.zip
```
解压完成后，将生成的 .M2 文件夹移动到 maven 本地仓库，默认的 maven 本地仓库在 /home/shiyanlou/.m2/ 下。（作业：由何处可以看出本地仓库的默认路径？）所以我们解压好的文件正好覆盖源 .m2 文件夹。（在没有使用过 maven 的情况下是没有这个文件夹的，所以只需要将.m2文件夹移动到 /home/shiyanlou/即可）
```
mv .m2 /home/shiyanlou/.m2
```

## 创建 Maven 工程 ##

首先打开 WEB IDE，选择 File->Open New Terminal，在终端中输入：
```
mvn archetype:generate -DgroupId=com.shiyanlou.demo -DartifactId=springExample -DarchetypeArtifactId=maven-archetype-quickstart
```
参数说明：

- Group Id：项目的组织机构，也是包的目录结构，一般都是域名的倒序，比如 com.shiyanlou.demo；
- Atifact Id ：项目实际的名字，比如 springExample；
- archetype Artifact Id ：使用的 maven 骨架名称

输入命令之后，maven 会提示我们输入版本号，这里可以直接定义版本号也可以直接回车，接着 maven 会提示当前要创建项目的基本信息，输入 y 然后回车确认。

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190613-1560435544192)

然后我们选择 File->Open Workspace 切换工作空间，选择springExample目录，必须切换到该目录下，否则识别不了项目


## 代码编写 ##

**创建 pom.xml**

打开项目的 pom.xml ，添加 Spring 依赖。
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.shiyanlou</groupId>
    <artifactId>springExample</artifactId>
    <version>1.0-SNAPSHOT</version>

    <name>springExample</name>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <spring.version>5.1.1.RELEASE</spring.version>

    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
    </dependencies>
</project>
```
实际上，Maven 使用以上配置来唯一指定一个项目。引用一个库文件也是通过以上配置来确定所引用的库及其版本号，比如本例引用 Spring 库版本为 5.1.1.RELEASE。

**创建 HelloWorld.java**

接下来，我们在src/main/java目录中创建一个 Spring bean，首先创建一个包，包名为 com.shiyanlou.demo.helloworld ，再在该包内创建一个类，类名为 HelloWorld， 并将 HelloWorld.java 编辑如下：
```
package com.shiyanlou.demo.helloworld;

public class HelloWorld {

    private String name;

    public void setName(String n){
        this.name=n;
    }

    public void printHello(){
        System.out.println("The first Spring :hello"+name);
    }
}
```
**创建 SpringBeans.xml**

我们先在 src/main/ 下新建一个 Folder，命名为 resources，现在可以开始创建 Spring Bean 配置文件，创建文件 SpringBeans.xml ，配置 bean 如下。文件位于 src/main/resources 下。
编辑 SpringBeans.xml 文件如下：
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="helloBean" class="com.shiyanlou.demo.helloworld.HelloWorld">
        <property name="name" value="shiyanlou"/>
    </bean>
</beans>
```
**创建测试 App**

在 com.shiyanlou.demo 包下创建一个类，类名为 App:
```
package com.shiyanlou.demo;

import com.shiyanlou.demo.helloworld.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("SpringBeans.xml");

        HelloWorld obj = (HelloWorld) context.getBean("helloBean");
        obj.printHello();
    }
}
```
**作为 java application 运行**

首先打开终端，使用mvn compile编译 java 程序，然后使用
```
mvn compile
mvn exec:java -Dexec.mainClass="com.shiyanlou.demo.App"  
```
这里用 maven 执行 java 程序，-Dexec.mainClass参数指定 main 方法所在类。
运行结果如下：

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190613-1560436712699)

至此，helloworld 程序便成功完成！

## 松耦合的目的 ##

上一部分，我们已经创建了 Maven 项目，打印出了 HelloShiyanlou 。为了方便，我使用上面的工程， pom.xml 文件一致，不必修改。下面，我们实验证明 Spring 的松耦合。假设项目需要输出到 CSV 或者 JSON 。

## 松耦合代码编写 ##

**IOutputGenerator.java**

创建 com.shiyanlou.demo.loosely_coupled 包，新建一个 IOutputGenerator 接口，接口内容如下：
```
package com.shiyanlou.demo.loosely_coupled;

public interface IOutputGenerator {
    public void generateOutput();

}
```
**CsvOutputGenerator.java**

CSV 输出，实现了 IOutputGenerator 接口。同样的包路径，新建一个 CsvOutputGenerator.java

内容如下：
```
package com.shiyanlou.demo.loosely_coupled;

public class CsvOutputGenerator implements IOutputGenerator {

    public void generateOutput() {
        System.out.println("Creating CsvOutputGenerator  Output......");
    }

}
```
**JsonOutputGenerator.java**

JSON 输出，实现了 IOutputGenerator 接口。同样的包路径，新建一个 JsonOutputGenerator.java
内容如下：
```
package com.shiyanlou.demo.loosely_coupled;

public class JsonOutputGenerator implements IOutputGenerator {

    public void generateOutput() {
        System.out.println("Creating JsonOutputGenerator  Output......");
    }

}
```

**用 Spring 依赖注入调用输出**

用 Spring 的松耦合实现输出相应的格式。
首先在 com.shiyanlou.demo.loosely_coupled 包内创建一个需要用到输出的类 OutputHelper.java ，内容如下：
```
package com.shiyanlou.demo.loosely_coupled;

public class OutputHelper {
    IOutputGenerator outputGenerator;

    public void generateOutput(){
        this.outputGenerator.generateOutput();
    }

    public void setOutputGenerator(IOutputGenerator outputGenerator){
        this.outputGenerator = outputGenerator;
    }
}

```
## 创建一个 spring 配置文件 ##
此文件用于依赖管理 src/main/resources 下创建配置文件 Spring-Output.xml 。
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="OutputHelper" class="com.shiyanlou.demo.loosely_coupled.OutputHelper">
        <property name="outputGenerator" ref="CsvOutputGenerator" />
    </bean>
    <bean id="CsvOutputGenerator" class="com.shiyanlou.demo.loosely_coupled.CsvOutputGenerator" />
    <bean id="JsonOutputGenerator" class="com.shiyanlou.demo.loosely_coupled.JsonOutputGenerator" />
</beans>
```

## App.java ##

修改此文件用于通过 Spring 调用相应的 output ，内容如下：
```
package com.shiyanlou.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shiyanlou.demo.loosely_coupled.OutputHelper;

public class App {

    private static ApplicationContext context;

    public static void main( String[] args )
    {
        context = new ClassPathXmlApplicationContext(new String[] {"Spring-Output.xml"});

        OutputHelper output = (OutputHelper)context.getBean("OutputHelper");
        output.generateOutput();
    }

}
```
现在，已经实现了松耦合，当需要输出改变时，不必修改任何代码 .java 文件，只要修改 Spring-Output.xml 文件 <property name="outputGenerator" ref="CsvOutputGenerator" /> 中的 ref 值，就可以实现输出不同的内容，不修改代码就减少了出错的可能性。

实验目录：
![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190613-1560437366253)


## 运行结果 ##
```
当 Spring-Output 如下时：

 <bean id="OutputHelper" class="com.shiyanlou.demo.loosely_coupled.OutputHelper">
        <property name="outputGenerator" ref="CsvOutputGenerator" />
 </bean>
```
输入命令：
```
mvn compile
mvn exec:java -Dexec.mainClass="com.shiyanlou.demo.App"  
```
注意：修改文件后都要使用 mvn compile 重新编译，然后再运行

运行结果为：

![](![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190613-1560437611521))

## 课后习题 ##
由何处可以看出本地仓库的默认路径？
Maven缺省的本地仓库路径为${user.home}/.m2/repository
可改变默认的 .m2 目录下的默认本地存储库文件夹
通过修改${user.home}/.m2/settings.xml 配置本地仓库路径

