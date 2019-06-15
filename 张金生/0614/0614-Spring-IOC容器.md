# Spring IoC 容器 #

用户在使用 Spring 所提供的各种功能之前， 必须在 Spring IoC 容器中装配好 Bean ，并建立 Bean 和 Bean 之间的关联关系。

## 项目文件结构 ##

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1541750258605.png/wm)

## 实验步骤 ##
接下来我们看学习spring最关键的部分IOC容器

## 理论基础 ##

**IoC 是什么**

Ioc—Inversion of Control，即“控制反转”，它不是什么技术，而是一种设计思想。在 Java 开发中， Ioc 意味着将你设计好的对象交给容器控制，而不是传统的在你的对象内部直接控制。如何理解好 Ioc 呢？理解好 Ioc 的关键是要明确“谁控制谁，控制什么，为何是反转（有反转就应该有正转了），哪些方面反转了”，那我们来深入分析一下：

谁控制谁，控制什么：传统 Java SE 程序设计，我们直接在对象内部通过 new 进行创建对象，是程序主动去创建依赖对象；而 IoC 是有专门一个容器来创建这些对象，即由 IoC 容器来控制对象的创建；谁控制谁？当然是 IoC 容器控制了对象；控制什么？那就是主要控制了外部资源获取（不只是对象包括比如文件等）。

为何是反转，哪些方面反转了：有反转就有正转，传统应用程序是由我们自己在对象中主动控制去直接获取依赖对象，也就是正转；而反转则是由容器来帮忙创建及注入依赖对象；为何是反转？因为由容器帮我们查找及注入依赖对象，对象只是被动的接受依赖对象，所以是反转；哪些方面反转了？依赖对象的获取被反转了。

用图例说明一下，传统程序设计都是主动去创建相关对象然后再组合起来：

![](https://doc.shiyanlou.com/document-uid122889labid1933timestamp1469084848042.png/wm)

当有了 IoC/DI 的容器后，在客户端类中不再主动去创建这些对象了，如图：

![](https://doc.shiyanlou.com/document-uid370051labid1933timestamp1489561115014.png/wm)

## IoC 能做什么 ##

IoC 不是一种技术，只是一种思想，一个重要的面向对象编程的法则，它能指导我们如何设计出松耦合、更优良的程序。传统应用程序都是由我们在类内部主动创建依赖对象，从而导致类与类之间高耦合，难于测试；有了IoC容器后，把创建和查找依赖对象的控制权交给了容器，由容器进行注入组合对象，所以对象与对象之间是松散耦合，这样也方便测试，利于功能复用，更重要的是使得程序的整个体系结构变得非常灵活。

其实 IoC 对编程带来的最大改变不是从代码上，而是从思想上，发生了“主从换位”的变化。应用程序原本是老大，要获取什么资源都是主动出击，但是在 IoC/DI 思想中，应用程序就变成被动的了，被动的等待 IoC 容器来创建并注入它所需要的资源了。

IoC 很好的体现了面向对象设计法则之一的好莱坞法则：“别找我们，我们找你”；即由 IoC 容器帮对象找相应的依赖对象并注入，而不是由对象主动去找。

## IoC 和 DI ##

DI—Dependency Injection，即“依赖注入”：是组件之间依赖关系由容器在运行期决定，形象的说，即由容器动态的将某个依赖关系注入到组件之中。依赖注入的目的并非为软件系统带来更多功能，而是为了提升组件重用的频率，并为系统搭建一个灵活、可扩展的平台。通过依赖注入机制，我们只需要通过简单的配置，而无需任何代码就可指定目标需要的资源，完成自身的业务逻辑，而不需要关心具体的资源来自何处，由谁实现。

理解 DI 的关键是：“谁依赖谁，为什么需要依赖，谁注入谁，注入了什么”，那我们来深入分析一下：

- 谁依赖于谁：当然是某个容器管理对象依赖于 IoC 容器；“被注入对象的对象”依赖于“依赖对象”；
- 为什么需要依赖：容器管理对象需要 IoC 容器来提供对象需要的外部资源；
- 谁注入谁：很明显是 IoC 容器注入某个对象，也就是注入“依赖对象”；
- 注入了什么：就是注入某个对象所需要的外部资源（包括对象、资源、常量数据）。

IoC 和 DI 有什么关系呢？其实它们是同一个概念的不同角度描述，由于控制反转概念比较含糊（可能只是理解为容器控制对象这一个层面，很难让人想到谁来维护对象关系），所以 2004 年大师级人物 Martin Fowler 又给出了一个新的名字：“依赖注入”，相对 IoC 而言，“依赖注入”明确描述了“被注入对象依赖 IoC 容器配置依赖对象”。

## IoC 容器 ##

IoC 容器就是具有依赖注入功能的容器，IoC 容器负责实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。应用程序无需直接在代码中 new 相关的对象，应用程序由 IoC 容器进行组装。在 Spring 中 BeanFactory 是 IoC 容器的实际代表者。

Spring IoC 容器如何知道哪些是它管理的对象呢？

这就需要配置文件，Spring IoC 容器通过读取配置文件中的配置元数据，通过元数据对应用中的各个对象进行实例化及装配。一般使用基于 xml 配置文件进行配置元数据，而且 Spring 与配置文件完全解耦的，可以使用其他任何可能的方式进行配置元数据，比如注解、基于 java 文件的、基于属性文件的配置都可以。

在 Spring Ioc 容器的代表就是 org.springframework.beans 包中的 BeanFactory 接口， BeanFactory 接口提供了 IoC 容器最基本功能；而 org.springframework.context 包下的 ApplicationContext 接口扩展了 BeanFactory ，还提供了与Spring AOP 集成、国际化处理、事件传播及提供不同层次的 context 实现 (如针对 web 应用的 WebApplicationContext )。简单说， BeanFactory 提供了 IoC 容器最基本功能，而 ApplicationContext 则增加了更多支持企业级功能支持。 ApplicationContext 完全继承 BeanFactory ，因而 BeanFactory 所具有的语义也适用于 ApplicationContext。

XmlBeanFactory ： BeanFactory 实现，提供基本的 IoC 容器功能，可以从 classpat h或文件系统等获取资源；
```
 File file = new File("fileSystemConfig.xml");
 Resource resource = new FileSystemResource(file);
 BeanFactory beanFactory = new XmlBeanFactory(resource);
```
```
 Resource resource = new ClassPathResource("classpath.xml");                 
 BeanFactory beanFactory = new XmlBeanFactory(resource);
```
ClassPathXmlApplicationContext ： ApplicationContext 实现，从 classpath 获取配置文件；
```
  BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath.xml");
```
FileSystemXmlApplicationContext ： ApplicationContext 实现 ，从文件系统获取配置文件。
```
  BeanFactory beanFactory = new FileSystemXmlApplicationContext("fileSystemConfig.xml");
```

## Spring 中 Bean 的定义及注入 Value ##

Spring 中，bean 的定义有三种方式：

- 基于 XML 的配置
- 基于注解的配置
- 基于 Java 类的配置

bean 的注入有两种方式：基于构造函数的依赖注入和基于设值函数的依赖注入

这里我们先给大家介绍第一种，基于 XML 的配置方法，这种方法在 Spring 中是最常见的

3.2.1 基于 XML 的配置方法

基于 XML 的配置方法又分为三种写法：一般方法，缩写方法，p schema 方法。先看下面的 Bean ： FileNameGenerator.java ，其中包含两个 properties ，name 和 type ，我们向两个 properties 注入 value 。5

新建一个 Maven 项目，步骤如下：

打开WEB IDE，选择File->Open New Terminal，在终端中输入：
```
mvn archetype:generate -DgroupId=com.shiyanlou.spring -DartifactId=bean -DarchetypeArtifactId=maven-archetype-quickstart
```
具体过程可以参照第二章，这里不再赘述，接着选择File->Open Workspace切换工作空间，选择bean目录，必须切换到该目录下，否则识别不了项目

和第一个 HelloShiyanlou 实验一样，修改 pom.xml 文件：
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.shiyanlou.spring</groupId>
    <artifactId>bean</artifactId>
    <version>1.0-SNAPSHOT</version>

    <name>bean</name>

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

在 src/main/java 路径下，新建类： FileNameGenerator.java ，所属包为： com.shiyanlou.spring.bean ，内容为：
```
package com.shiyanlou.spring.bean;

public class FileNameGenerator {
     private String name;
     private String type;

     public String getName() {
         return name;
     }
     public void setName(String name) {
         this.name = name;
     }
      public String getType() {
         return type;
     }
    public void setType(String type) {
       this.type = type;
   }
    /*
    *打印文件名和文件类型的方法
    */
    public void printFileName() {
       System.out.println("FileName & FileType  is  :  "+name+" & "+type);
    }
}
```
我们先在 src/main/ 下新建一个 Folder，命名为 resources，接着在 src/main/resources 路径下新建，SpringBeans.xml 文件，
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--一般方法-->
    <bean id="FileNameGenerator" class="com.shiyanlou.spring.bean.FileNameGenerator">
        <property name="name">
            <value>shiyanlou</value>
        </property>
        <property name="type">
            <value>txt</value>
        </property>
    </bean>
    <!-- 另一重配置方法 缩写方法-->
    <!-- 
        <bean id="FileNameGenerator" class="com.shiyanlou.spring.bean.FileNameGenerator">
               <property name="name" value="shiyanlou" />
               <property name="type" value="txt" />
           </bean>
     -->
</beans>
```
第三种方法： "p" schema 方法
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="FileNameGenerator" class="com.shiyanlou.spring.bean.FileNameGenerator">
        <property name="name">
            <value>shiyanlou</value>
        </property>
        <property name="type">
            <value>txt</value>
        </property>
    </bean>
    <bean id="FileNameGenerator" class="com.shiyanlou.spring.bean.FileNameGenerator"
             p:name="shiyanlou" p:type="txt" />
</beans>
```
注意，这种方法需要在 bean 的配置文件 xml 中，加入声明。
```
xmlns:p="http://www.springframework.org/schema/p"
```
最后需要在 com.shiyanlou.spring.bean 路径下写一个 App.java。
```
package com.shiyanlou.spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shiyanlou.spring.bean.FileNameGenerator;

public class App 
{
     private static ApplicationContext context;

        public static void main( String[] args )
        {
            context = new ClassPathXmlApplicationContext("SpringBeans.xml");

            FileNameGenerator obj = (FileNameGenerator) context.getBean("FileNameGenerator");
            obj.printFileName();
        }
}
```
打开terminal，输入
```
mvn compile
mvn exec:java -Dexec.mainClass="com.shiyanlou.spring.bean.App"
```
此处输入图片的描述

## 基于注解的配置 ##

注解是为 Spring 容器提供 Bean 定义的信息，把 XML 定义的信息通过类注解描述出来。众所周知，Spring容器三大要素：Bean 定义、 Bean 实现类以及 Spring 框架。如果采用 XML 配置，Bean 定义和 Bean 实现类本身分离，而采用注解配置，Bean 定义在 Bean 实现类上注解就可以实现。在后面我我们会有一节实验专门讲解基于注解的配置，以下先简单列举几个注解方式：

** @Component**

被此注解标注的类将被 Spring 容器自动识别，自动生成 Bean 定义。即：
```
packeage com.shiyanlou.spring;

@Component("shiyanlou")
public class shiyanlou{

}
```
与在XML中配置以下效果相同
```
<bean id="shiyanlou" class="com.shiyanlou.spring.shiyanlou">
```
除此之外，Spring 有三个与 @Component 等效的注解：

- @Controller:对应表现层的 Bean，也就是 Action 。
- @Service:对应的是业务层 Bean 。
- @Repository:对应数据访问层 Bean 。
**@Autowired**

@Autowired 可以用来装配 bean，都可以写在字段上，或者方法上。使用 @Autowired，首先要在在 applicationContext.xml 中加入 <bean class="org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor"/> @Autowired 默认按类型装配，默认情况下要求依赖对象必须存在，如果要允许 null 值，可以设置它的 required 属性为 false 。例如：

- @Autowired()
- @Qualifier("shiyanlouDao")
- private ShiyanlouDao shiyanlouDao;
**Configuration**

通过使用注释 @Configuration 告诉 Spring ，这个 Class 是 Spring 的核心配置文件，并且通过使用注解 @Bean 定义 bean ，举例说明：
```
package com.shiyanlou.spring.java_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name="animal")
    public IAnimal getAnimal(){
        return new Dog();
    }
}
```
App.java 内容：
```
package com.shiyanlou.spring.java_config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        IAnimal obj = (IAnimal) context.getBean("animal");
        obj.makeSound();

    }

}
```
在 ApplicationContext.xml 文件中只需要添加：
```
<bean id="animal" class="com.shiyanlou.spring.java_config.Dog">
```

## Spring Inner Bean - 内部嵌套的 Bean ##

以下 Demo 演示了一个 Bean 中嵌套了另一个 Bean ，即所谓的内部嵌套 Bean 的配置方法，内部嵌套的 Bean 支持属性（ property ）注入和构造函数（ constructor - arg ）注入。

由于只是简单地知识点讲解，所以我们继续使用上面的项目。

## 编写 Person.java ##

在 Src/main/java 路径下创建包 com.shiyanlou.spring.innerbean ，并在包内创建类 Person.java 。

内容如下：
```
package com.shiyanlou.spring.innerbean;

public class Person {
    private String name;
    private String address;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [address=" + address + ", age=" + age + ", name=" + name + "]";
    }  
}
```
编写 Customer.java

在包 com.shiyanlou.spring.innerbean 下新建类 Customer.java 内容如下：
```
package com.shiyanlou.spring.innerbean;

public class Customer {
         private Person person;
        //带参构造方法
        public Customer(Person person) {
            this.person = person;
        }
        //有带参构造方法一定要有默认构造方法
        public Customer(){}

        public void setPerson(Person person) {
            this.person = person;
        }

        @Override
        public String toString() {
            return "Customer [person=" + person + "]";
        }
}
```
编写 SpringBeans.xml

接下来配置 Bean ，配置Bean时，要在 Customer 的 Bean 中注入内部 Bean ，即 Person 。三种方法：

1) 在 Customer 中，可以用 ref 属性引用 Person 的 Bean ，如下：
```
    <bean id="CustomerBean" class="com.shiyanlou.spring.innerbean.Customer">
        <property name="person" ref="PersonBean" />
    </bean>

    <bean id="PersonBean" class="com.shiyanlou.spring.innerbean.Person">
        <property name="name" value="shiyanlou" />
        <property name="address" value="chengdu" />
        <property name="age" value="25" />
    </bean>
```
以上方法利用 ref 很好的引用了 Person ，但是，一旦 Person 被用在 Customer 下，也就不会被别的 Bean 引用。

2) 最好的方法就是在 Customer 的 Bean 中声明一个内部 Bean ，如下:
```
 <bean id="CustomerBean" class="com.shiyanlou.spring.innerbean.Customer">
        <property name="person">
            <bean class="com.shiyanlou.spring.innerbean.Person">
                <property name="name" value="shiyanlou" />
                <property name="address" value="chengdu" />
                <property name="age" value="25" />
            </bean>
        </property>
  </bean>
```
3) 内部Bean也可以通过构造函数注入
```
 <bean id="CustomerBean" class="com.shiyanlou.spring.innerbean.Customer">
     <constructor-arg>
         <bean class="com.shiyanlou.spring.innerbean.Person">
             <property name="name" value="shiyanlou" />
             <property name="address" value="chengdu" />
             <property name="age" value="25" />
         </bean>
     </constructor-arg>
 </bean>
```
这里我们采用第二种方法。

 App.java

接下来，我们在 com.shiyanlou.spring.innerbean 包下面创建 App.java 进行测试。内容如下：
```
package com.shiyanlou.spring.innerbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shiyanlou.spring.innerbean.Customer;

public class App 
{
     private static ApplicationContext context;

        public static void main( String[] args )
        {
            context = new ClassPathXmlApplicationContext("SpringBeans.xml");

            Customer obj = (Customer) context.getBean("CustomerBean");

            System.out.println(obj.toString());
        }
}
```
运行
```
mvn compile
mvn exec:java -Dexec.mainClass="com.shiyanlou.spring.innerbean.App"
```
成功显示：
![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190614-1560523004392)


## Spring Bean Scopes - Bean 的作用域 ##

在 Spring 中，Bean 的作用域决定了从 Spring 容器中返回的 Bean 实例的类型。在 Spring 中，支持以下 5 种类型的作用域：

- singleton — 单例模式，由 IOC 容器返回一个唯一的 bean 实例。
- prototype — 原型模式，被请求时，每次返回一个新的 bean 实例。
- request — 每个 HTTP Request 请求返回一个唯一的 Bean 实例。
- session — 每个 HTTP Session 返回一个唯一的 Bean 实例。
- globalSession — Http Session 全局 Bean 实例。
注：大多数情况下，你可能只需要处理 Spring 的核心作用域 — 单例模式（ singleton ）和原型模式（ prototype ），默认情况下，作用域是单例模式。

## singleton 和 prototype 区别 ##

继续使用原来的项目，在 src/main/java 路径下，新建包： com.shiyanlou.spring.customer.services，新建类 CustomerService.java 如下:
```
package com.shiyanlou.spring.customer.services;

public class CustomerService
{
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```
1). 如果是 singleton 情况下的 SpringBeans.xml 配置如下:
```
<bean id="CustomerService"  class="com.shiyanlou.spring.customer.services.CustomerService" />
```
以上配置中，如果没有指定scope范围，默认情况下是singleton模式。

2). Prototype 情况下的配置如下：
```
<bean id="CustomerService" class="com.shiyanlou.spring.customer.services.CustomerService" scope="prototype"/>
```
然后，在路径 com.shiyanlou.spring.customer.services 下创建 App.java ，内容如下：
```
package com.shiyanlou.spring.customer.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
     private static ApplicationContext context;

        public static void main( String[] args )
        {
            context = new ClassPathXmlApplicationContext(new String[] {"SpringBeans.xml"});

            CustomerService CSA = (CustomerService) context.getBean("CustomerService");
            CSA.setMessage("Message by CSA");
            System.out.println("Message : "+ CSA.getMessage());

            //retrieve it again
            CustomerService CSB = (CustomerService) context.getBean("CustomerService");
            System.out.println("Message : "+ CSB.getMessage());
        }
}
```
运行
```
mvn compile
mvn exec:java -Dexec.mainClass="com.shiyanlou.spring.customer.services.App"
```
实验结果如下：

singleton 情况:

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190614-1560523498185)

由此可知，设置 scope 为 prototype 后，测试代码中，每调用一次 getBean() 方法后，都会得到一个新的实例。


























































