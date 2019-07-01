# Spring Boot 多数据源的配置 #

## 实验步骤 ##
在开发的过程中，可能会遇到使用多数据的场景，接下来讲解如何使用 SpringBoot 多数据源，这里采用 SpringDataJpa 来讲解。

由于 maven 下载包需要联网，这里先将 maven 包下载下来，放到本地仓库便可以解决联网的问题
```
wget http://labfile.oss.aliyuncs.com/courses/1152/m2.zip
unzip m2.zip
mv .m2 /home/shiyanlou/
```
## 什么是多数据源 ##

>数据源（Data Source）顾名思义，数据的来源，是提供某种所需要数据的器件或原始媒体。在数据源中存储了所有建立数据库连接的信息。就像通过指定文件名称可以在文件系统中找到文件一样，通过提供正确的数据源名称，你可以找到相应的数据库连接。----来自百度百科

一般情况下，开发项目的时候只使用了单个数据库，而多数据源可以帮助使用多个数据库或者不同类型的数据库。

## 建立 mysql 数据库 ##

这里使用 2 个不同的数据库(test1 和 test2)来实现多数据源。

需要先登录 mysql（确保 mysql 已经启动，使用sudo service mysql start命令启动）

登入数据库
```
mysql -u root
```

然后建立相对应的数据库和数据表。
```
create database test1;
use test1;
CREATE TABLE user_one
(
    id int PRIMARY KEY AUTO_INCREMENT,
    username varchar(50),
    password varchar(50)
);

create database test2;

use test2;
CREATE TABLE user_two
(
    id int PRIMARY KEY AUTO_INCREMENT,
    username varchar(50),
    password varchar(50)
);
```
## 建立项目结构 ##

创建 maven 项目springboot，修改 pom.xml
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
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
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
接着在src/main/java中建立包com.shiyanlou.springboot,在包com.shiyanlou.springboot中再建立first和second两个包。


DataSourceConfig.java
```
在com.shiyanlou.springboot下建立DataSourceConfig.java

package com.shiyanlou.springboot;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源配置
 */
@Configuration
public class DataSourceConfig {

    @Bean
    @Primary //该注解表示为主数据源
    @ConfigurationProperties("app.datasource.first") //读取前缀为app.datasource.first的属性
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.first")
    public DataSource firstDataSource() {
        return firstDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean  //这是第二个数据源，不能加@Primary注解了
    @ConfigurationProperties("app.datasource.second")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.second")
    public DataSource secondDataSource() {
        return secondDataSourceProperties().initializeDataSourceBuilder().build();
    }
}

```
## UserOne.java ##

在com.shiyanlou.springboot.first下建立UserOne.java
```
package com.shiyanlou.springboot.first;

import javax.persistence.*;

/**
 * 设置表名为two_one，并且标记该类为实体类
 */
@Table(name = "user_one")
@Entity
public class UserOne {

    /**
     * 设置主键生成策略
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public int id;

    @Column
    private String username;

    @Column
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
```

## FirstDataSourceConfiguration.java ##

在包com.shiyanlou.springboot.first中建立FirstDataSourceConfiguration.java
```
package com.shiyanlou.springboot.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        //设置Repository所在位置
        basePackages = {"com.shiyanlou.springboot.first"},
        //设置entityManager工厂
        entityManagerFactoryRef = "entityManagerFactoryFirst",
        //设置事务管理器
        transactionManagerRef = "transactionManagerFirst"
        )
public class FirstDataSourceConfiguration {
    /**
     * 数据源一
     */
    private final DataSource firstDataSource;


    /**
     * 构造方式注入依赖
     */
    @Autowired
    public FirstDataSourceConfiguration(@Qualifier("firstDataSource") DataSource firstDataSource) {
        this.firstDataSource = firstDataSource;
    }


    /**
     * 配置entityManager工厂
     */
    @Primary
    @Bean(name = "entityManagerFactoryFirst")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryFirst(EntityManagerFactoryBuilder builder) {
        return builder
                //设置数据源
                .dataSource(firstDataSource)
                //设置entity的包路径
                .packages("com.shiyanlou.springboot.first")
                .build();
    }

    /**
     * 配置事务管理器
     */
    @Primary
    @Bean(name = "transactionManagerFirst")
    public PlatformTransactionManager transactionManagerFirst(EntityManagerFactoryBuilder builder) {
        //返回jpa事务管理器
        return new JpaTransactionManager(entityManagerFactoryFirst(builder).getObject());
    }
}

```
## UserOneRepository.java ##
在包com.shiyanlou.springboot.first中建立UserOneRepository.java,该类作为 Dao 层
```
package com.shiyanlou.springboot.first;

import org.springframework.data.repository.CrudRepository;

/**
 * 继承CrudRepository
 */
public interface UserOneRepository extends CrudRepository<UserOne, Integer> {

}
```
## UserOneService.java ##

在包com.shiyanlou.springboot.first中建立UserOneService.java，该类作为 Service 层。
```
package com.shiyanlou.springboot.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserOneService {

    @Autowired
    private UserOneRepository userOneRepository;

    @Transactional(rollbackFor = Exception.class)
    public UserOne save(UserOne userOne) {
//        保存实体类
        return userOneRepository.save(userOne);
    }
}
```
## second 包 ##

second 包里面使用的是第二个数据源，内容基本和 first 包里面的内容相同，只是名字变成了 UserTwo，这里只贴出SecondDataSourceConfiguration.java，其余的部分同学们可以自己完成，或者下载源码对比完成。
```
package com.shiyanlou.springboot.second;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        //设置Repository所在位置
        basePackages = {"com.shiyanlou.springboot.second"},
        //设置entityManager工厂
        entityManagerFactoryRef = "entityManagerFactorySecond",
        //设置事务管理器
        transactionManagerRef = "transactionManagerSecond"
)
public class SecondDataSourceConfiguration {
    /**
     * 数据源二
     */
    private final DataSource secondDataSource;

    /**
     * 构造方式注入依赖
     */
    @Autowired
    public SecondDataSourceConfiguration(@Qualifier("secondDataSource") DataSource secondDataSource) {
        this.secondDataSource = secondDataSource;
    }

    /**
     * 配置entityManager工厂
     */
    @Bean(name = "entityManagerFactorySecond")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecond(EntityManagerFactoryBuilder builder) {
        return builder
                //设置数据源
                .dataSource(secondDataSource)
                //设置entity的包路径
                .packages("com.shiyanlou.springboot.second")
                .build();
    }

    /**
     * 配置事务管理器
     */
    @Bean(name = "transactionManagerSecond")
    public PlatformTransactionManager transactionManagerSecond(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactorySecond(builder).getObject());
    }
}
```
## application.properties 和 SpringbootApplication.java ##
```
application.properties

#数据源2
app.datasource.first.url=jdbc:mysql://localhost:3306/test1
app.datasource.first.username=root
app.datasource.first.password=
app.datasource.first.driver-class-name=com.mysql.jdbc.Driver

#数据源2
app.datasource.second.url=jdbc:mysql://localhost:3306/test2
app.datasource.second.username=root
app.datasource.second.password=
app.datasource.second.driver-class-name=com.mysql.jdbc.Driver

#打印sql语句
spring.jpa.show-sql=true
```
SpringbootApplication.java
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
## 测试效果 ##

在com.shiyanlou.springboot下建立DataSourceTest.java
```
package com.shiyanlou.springboot;

import com.shiyanlou.springboot.first.UserOne;
import com.shiyanlou.springboot.first.UserOneService;
import com.shiyanlou.springboot.second.UserTwo;
import com.shiyanlou.springboot.second.UserTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * ApplicationRunner接口可以让在SpringBoot启动后马上执行想要执行的方法
 */
@Component
public class DataSourceTest implements ApplicationRunner {

    @Autowired
    public UserOneService userOneService;

    @Autowired
    public UserTwoService userTwoService;

    /**
     * 该方法再SpringBoot启动完成后立即执行
     */
    @Override
    public void run(ApplicationArguments args) {
//        新建一个实体类
        UserOne userOne = new UserOne();
        userOne.setUsername("shiyanlou1");
        userOne.setPassword("springboot1");
//        调用Service
        userOneService.save(userOne);
        UserTwo userTwo = new UserTwo();
        userTwo.setPassword("shiyanlou2");
        userTwo.setUsername("springBoot2");
//        调用Service
        userTwoService.save(userTwo);
    }
}
```

运行程序mvn spring-boot:run，查看数据库 test1 中的 user_one 和数据库 test2 中 user_two 的改变

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542857229602.png/wm)

两个数据库中的表都发生了改变，说明多数据源配置成功。









