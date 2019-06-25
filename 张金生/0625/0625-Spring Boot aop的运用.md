# Spring Boot aop的运用 #

## 实验步骤 ##
首先了解一下什么是 aop

由于 maven 下载包需要联网，这里先将 maven 包下载下来，放到本地仓库便可以解决联网的问题

wget http://labfile.oss.aliyuncs.com/courses/1152/m2.zip
unzip m2.zip
mv .m2 /home/shiyanlou/

## 什么是 AOP ##
AOP 为 Aspect Oriented Programming 的缩写，意为：面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP 是 OOP 的延续，是软件开发中的一个热点，也是 Spring 框架中的一个重要内容，是函数式编程的一种衍生范型。利用 AOP 可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。 ----来自百度百科


## 如何使用 AOP ##

要使用 aop,需要先了解几个常用的 aop 注解

注解	描述
@Before	在方法执行前执行
@After	在方法执行后执行
@AfterReturning	在结果返回后执行
@AfterThrowing	在抛出异常后执行
@Around	环绕通知，相当于同时使用@Before 和@After

接下来继续在上节实验的基础上进行修改。上节实验代码获取
```
wget http://labfile.oss.aliyuncs.com/courses/1152/springbootshiyan5.zip
```
在 pom.xml 加入如下 maven 依赖包
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
            <version>2.0.4.RELEASE</version>
        </dependency>
```
在com.shiyanlou.springboot包下面建立SpringbootAop.java
```
package com.shiyanlou.springboot;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

//声明为切面类
@Aspect
@Component
public class SpringbootAop {


//设置切点
    @Pointcut(value = "execution(* com.shiyanlou.springboot..*.run(..))")
    public void aop(){
    }

    @Before("aop()")
    public void before(){
        System.out.println("before：执行方法前");
    }


    @After("aop()")
    public void after(){
        System.out.println("after：执行方法后");
    }


    @AfterThrowing("aop()")
    public void afterThrowing(){
        System.out.println("afterThrowing：异常抛出后");
    }

    @AfterReturning("aop()")
    public void afterReturning(){
        System.out.println("afterReturning：方法返回后");
    }

    @Around("aop()")
    public void around(ProceedingJoinPoint joinPoint){

        System.out.println("around：环绕通知前");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("around：环绕通知后");

    }

}
```
运行项目mvn spring-boot:run

接下来修改UserService.java,去掉异常

```
package com.shiyanlou.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 添加事务管理
     */
    @Transactional(rollbackFor = Exception.class)
    public User save(User user) {
//        保存实体类
        userRepository.save(user);
//       去除异常
//        int shiyanlou = 1 / 0;

        //修改密码
        user.setPassword("123456");
        //重新保存，更新记录
        return userRepository.save(user);
    }


}

```

重新运行后控制台输出.

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542854876984.png/wm)


## 使用 aop 进行事务控制 ##

aop 的应用场景之一就是事务控制，下面学习使用 aop 进行事务控制。

修改SpringbootAop.java
```
package com.shiyanlou.springboot;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Aspect
@Component
public class SpringbootAop {


    //设置切点
    @Pointcut(value = "execution(* com.shiyanlou.springboot..*.run(..))")
    public void aop() {
    }

    @Before("aop()")
    public void before() {
        System.out.println("before：执行方法前");
    }


    @After("aop()")
    public void after() {
        System.out.println("after：执行方法后");
    }


    @AfterThrowing("aop()")
    public void afterThrowing() {
        System.out.println("afterThrowing：异常抛出后");
    }

    @AfterReturning("aop()")
    public void afterReturning() {
        System.out.println("afterReturning：方法返回后");
    }

    @Around("aop()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("around：环绕通知前");
        //执行方法
        joinPoint.proceed();
        System.out.println("around：环绕通知后");

    }


    /**
     * 注入事务管理器
     */
    @Autowired
    public PlatformTransactionManager platformTransactionManager;

    /**
     * 设置事务拦截器
     */
    @Bean
    public TransactionInterceptor transactionInterceptor() {
        //设置事务属性 可以通过它设置事务的基本属性，如事务是读写事务或者只读事务，事务的超时时间等
        DefaultTransactionAttribute defaultTransactionAttribute = new DefaultTransactionAttribute();
        //设置为读写事务
        defaultTransactionAttribute.setReadOnly(false);
        //通过方法名匹配事务
        NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource = new NameMatchTransactionAttributeSource();
        //为save方法添加事务，事务属性为defaultTransactionAttribute设置的属性
        nameMatchTransactionAttributeSource.addTransactionalMethod("save", defaultTransactionAttribute);
        //新建一个事务拦截器，使用platformTransactionManager作为事务管理器，拦截的方法为nameMatchTransactionAttributeSource中匹配到的方法
        return new TransactionInterceptor(platformTransactionManager, nameMatchTransactionAttributeSource);
    }


    @Bean
    public Advisor advisor() {
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        //execution 表达式 匹配save方法
        aspectJExpressionPointcut.setExpression("execution(* com.shiyanlou.springboot..*.save(..))");
        //返回aop切面，切面=切点+通知
        return new DefaultPointcutAdvisor(aspectJExpressionPointcut, transactionInterceptor());
    }
}
```
删除UserService.java中的@Transactional(rollbackFor = Exception.class),同时注释掉int shiyanlou = 1 / 0;。数据库同样适用上节课程的数据库，如果同学们的环境是从上节课程保存下来了，可以直接继续使用，首先使用TRUNCATE TABLE user;清空数据库数据方便观察效果。
如果数据库已经丢失，可以按照上一章节的内容重建数据库。这里只贴出相关的代码，如果同学们需要更加详细的过程可以回到上一节查看。
```
sudo service mysql start
mysql -u root
create database test;
show databases;
use test;
CREATE TABLE user
(
    id int PRIMARY KEY AUTO_INCREMENT,
    username varchar(50),
    password varchar(50)
);
```
运行程序，查看数据库

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542855213137.png/wm)

可以看到成功的插入了数据，并且改变了密码为 123456；接下来恢复异常，恢复被注释掉的int shiyanlou = 1 / 0;，再重新运行项目。

查询数据

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542855213137.png/wm)

可以看到并没有脏数据的产生，说明使用 aop 控制事务成功了，事务被成功回滚。

