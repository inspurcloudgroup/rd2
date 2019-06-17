# 基于注解的配置 #
获取源码
```
wget https://labfile.oss.aliyuncs.com/courses/578/springExample.zip
unzip springExample.zip
```

## 项目文件结构 ##

![](https://doc.shiyanlou.com/courses/uid987099-20190527-1558920492301/wm)

## @Required 注解 ##

Spring 依赖检查 bean 配置文件用于确定的特定类型(基本，集合或对象)的所有属性被设置。在大多数情况下，你只需要确保特定属性已经设置但不是所有属性。对于这种情况，你需要 @Required 注解。

首先创建一个新的 maven 工程springExample，打开 Terminal，选择 File->Open New Terminal，在终端中输入：
```
mvn archetype:generate -DgroupId=com.shiyanlou.demo -DartifactId=springExample -DarchetypeArtifactId=maven-archetype-quickstart
```
选择 File->Open Workspace 切换工作空间，选择springExample目录，必须切换到该目录下，否则识别不了项目

修改 pom.xml 文件，添加 Spring 的依赖：
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
        <spring.version>4.3.20.RELEASE</spring.version>

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
创建包 com.shiyanlou.demo.entity,创建 Person.java，代码如下：
```
package com.shiyanlou.demo.entity;

public class Person {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```
再在 entity 下创建 Dog.java,代码如下:
```
package com.shiyanlou.demo.entity;

import org.springframework.beans.factory.annotation.Required;

public class Dog {

    private Person person;
    private String name;

    public Person getPerson() {
        return person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Required
    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "person=" + person +
                ", name='" + name + '\'' +
                '}';
    }
}
```
我们先在 src/main/ 下新建一个 Folder，命名为 resources，现在可以开始创建 Spring Bean 配置文件，创建文件 SpringBeans.xml ，配置 bean 如下。文件位于 src/main/resources 下。
编辑 SpringBeans.xml 文件如下：
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           http://www.springframework.org/schema/context/spring-context.xsd">


    <context:annotation-config/>

    <bean id="personBean" class="com.shiyanlou.demo.entity.Person">
        <property name="name" value="jack"/>
        <property name="age" value="18"/>
    </bean>

    <bean id="dogBean" class="com.shiyanlou.demo.entity.Dog">
        <property name="name" value="tom"/>
        <property name="person" ref="personBean"/>
    </bean>
</beans>
```
最后创建 App.java,在包路径 com.shiyanlou.demo 下，代码如下:
```
package com.shiyanlou.demo;

import com.shiyanlou.demo.entity.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("SpringBeans.xml");

        Dog dog = (Dog) context.getBean("dogBean");
        System.out.println(dog);
    }
}
```
运行
```
mvn compile
mvn exec:java -Dexec.mainClass="com.shiyanlou.demo.App"
```
实验结果如下：

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190617-1560781903609)


如果我们将 SpringBean.xml 中的代码修改成如下：
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           http://www.springframework.org/schema/context/spring-context.xsd">


    <context:annotation-config/>

    <bean id="personBean" class="com.shiyanlou.demo.entity.Person">
        <property name="name" value="jack"/>
        <property name="age" value="18"/>
    </bean>

    <bean id="dogBean" class="com.shiyanlou.demo.entity.Dog">
        <property name="name" value="tom"/>
        <!--<property name="person" ref="personBean"/>-->
    </bean>
</beans>
```
再次运行
```
mvn compile
mvn exec:java -Dexec.mainClass="com.shiyanlou.demo.App"
```
实验结果如下： 
![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190617-1560782662183)

可以看到程序报错了，那是因为我们在 Dog.java 的 setPerson 上加上了 @Required 注解，表明在 Dog 中 person 必须被注入，但是在 SpringBean.xml 中我们没有配置 person，所以程序就会报错。


## @Autowired 注解 ##

@Autowired 可以用来装配 bean，可以写在字段上，或者方法上。@Autowired 默认按类型装配，默认情况下要求依赖对象必须存在，如果要允许 null 值，可以设置它的 required 属性为 false。

首先修改 SpringBean.xml 的代码如下：
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           http://www.springframework.org/schema/context/spring-context.xsd">


    <context:annotation-config/>

    <bean id="personBean" class="com.shiyanlou.demo.entity.Person">
        <property name="name" value="jack"/>
        <property name="age" value="18"/>
    </bean>

    <bean id="dogBean" class="com.shiyanlou.demo.entity.Dog">
        <property name="name" value="tom"/>
<!--        <property name="person" ref="personBean"/>-->
    </bean>
</beans>
```
修改 Dog.java 的代码如下，在 person 上加上了 @Autowired 注解，同时删掉了 person 的 setter 方法
```
package com.shiyanlou.demo.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

public class Dog {

    @Autowired
    private Person person;
    private String name;

    public Person getPerson() {
        return person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

/*
    @Required
    public void setPerson(Person person) {
        this.person = person;
    }
    */

    @Override
    public String toString() {
        return "Dog{" +
                "person=" + person +
                ", name='" + name + '\'' +
                '}';
    }
}
```
运行

```
mvn compile
mvn exec:java -Dexec.mainClass="com.shiyanlou.demo.App"
```

可以看到当我们删除了 person 的相关配置后，结果还是打印出了 person 的值，这是因为@Autowired 自动为我们注入了。

## @Qualifier 注解 ##

这个注解通常和@Autowired 一起使用，当你想对注入的过程做更多的控制，@Qualifier 可以帮助你指定做更详细配置。一般在两个或者多个 bean 是相同的类型，spring 在注入的时候会出现混乱。接下来通过一个例子来说明。

首先在 pom.xml 加入如下依赖，方便后面进行单元测试:
```
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>5.0.8.RELEASE</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
```
创建包 com.shiyanlou.demo.service，然后依次创建 Hello.java、HelloImpl1.java和HelloImpl2.java,代码如下:

Hello.java
```
package com.shiyanlou.demo.service;

public interface Hello {

    void sayHello();
}
```
HelloImpl1.java
```
package com.shiyanlou.demo.service;

public class HelloImpl1 implements Hello {

    @Override
    public void sayHello() {
        System.out.println("Hello shiyanlou 1");
    }
}
```
HelloImpl2.java
```
package com.shiyanlou.demo.service;

public class HelloImpl2 implements Hello {
    @Override
    public void sayHello() {
        System.out.println("Hello shiyanlou 2");
    }
}
```
然后在 SpringBeans.xml 中添加如下代码：
```
    <bean id="hello1" class="com.shiyanlou.demo.service.HelloImpl1"/>

    <bean id="hello2" class="com.shiyanlou.demo.service.HelloImpl2"/>
```
最后删掉 test 中的 AppTest.java，然后创建一个 HelloTest.java，代码如下：
```
import com.shiyanlou.demo.service.Hello;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:SpringBeans.xml"})
public class HelloTest {

    @Autowired
    @Qualifier("hello1")
    private Hello hello;

    @Test
    public void sayHello(){

        hello.sayHello();
    }
}
```

运行

mvn test

如果将 @Qualifier 中的值换成 hello2，则会打印 hello 实验楼 2。














