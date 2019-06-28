# Spring Boot 中集成 Mybatis #

## 实验步骤 ##
接下来学习使用 SpringBoot 整合 Mybatis，并且开发一个简单的 restful 风格的 web 项目。

>REST（英文：Representational State Transfer，简称 REST）描述了一个架构样式的网络系统，比如 web 应用程序。它首次出现在 2000 年 Roy Fielding 的博士论文中，Roy Fielding 是 HTTP 规范的主要编写者之一。在目前主流的三种 Web 服务交互方案中，REST 相比于 SOAP（Simple Object Access protocol，简单对象访问协议）以及 XML-RPC 更加简单明了，无论是对 URL 的处理还是对 Payload 的编码，REST 都倾向于用更加简单轻量的方法设计和实现。值得注意的是 REST 并没有一个明确的标准，而更像是一种设计的风格。-------来自百度百科
由于 maven 下载包需要联网，这里先将 maven 包下载下来，放到本地仓库便可以解决联网的问题
```
wget http://labfile.oss.aliyuncs.com/courses/1152/m2.zip
unzip m2.zip
mv .m2 /home/shiyanlou/
```
## 新建项目 ##

新建一个 maven 项目springboot，建立如下目录结构，建立项目的过程可以参照实验 2，这里不再赘述。

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542864520817.png/wm)

建立好了目录结构，将依赖包加入到项目中。在 maven 的配置文件pom.xml中加入下面的依赖
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
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
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
## 实体类 ##

在com.shiyanlou.springboot.entity中存放实体类，这里只建立了一个实体类 User.java

**User.java**
```
package com.shiyanlou.springboot.entity;

import java.io.Serializable;


public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
## dao层 ##
建立一个 BaseMapper.java 用于提取常用的公共方法，减少重复代码。

**BaseMapper.java**

在com.shiyanlou.springboot.dao中建立BaseMapper.java
```
package com.shiyanlou.springboot.dao;

import java.util.List;


public interface BaseMapper<T> {
    /**
     * 保存
     */
    Integer save(T t);

    /**
     * 删除
     */
    void delete(Integer id);

    /**
     * 通过id查询
     */
    T findById(Integer id);

    /**
     * 更新
     */
    void update(T t);

    /**
     * 返回所有信息
     */
    List<T> list();
}
```
**UserMapper.java**

在com.shiyanlou.springboot.dao中建立UserMapper.java，UserMapper.java继承BaseMapper.java
```
package com.shiyanlou.springboot.dao;

import com.shiyanlou.springboot.entity.User;

public interface UserMapper extends BaseMapper<User> {

}
```
如果 BaseMapper.java 中的方法不足以满足的需求，直接在UserMapper.java中添加对应的方法就可以了。

**UserMapper.xml**

在src/main/resources中建立mapper文件夹，在mapper中建立UserMapper.xml
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.shiyanlou.springboot.dao.UserMapper">
    <select id="list" resultType="com.shiyanlou.springboot.entity.User">
        select *
        from user
    </select>
    <select id="findById" parameterType="int" resultType="com.shiyanlou.springboot.entity.User">
        select *
        from user
        where id = #{value}
    </select>
    <update id="update" parameterType="com.shiyanlou.springboot.entity.User">
        update user
        <set>
            <if test="username!=null and username!=''">
                username=#{username},
            </if>
            <if test="password!=null and password!=''">
                `password`=#{password}
            </if>
        </set>
        where id=#{id}
    </update>
    <delete id="delete" parameterType="int">
        delete
        from user
        where id = #{value}
    </delete>
    <insert id="save" parameterType="com.shiyanlou.springboot.entity.User" keyColumn="id" keyProperty="id"
            useGeneratedKeys="true">
        insert into user (username, `password`)
        values (#{username}, #{password})
    </insert>
</mapper>
```
## service 层 ##


将 service 常用的公共方法抽象出来，如简单的增删查改等功能，这样可以减少很多的重复代码。

IBaseService.java
```
在com.shiyanlou.springboot.service中建立IBaseService.java

package com.shiyanlou.springboot.service;

import java.util.List;


public interface IBaseService<T> {

    /**
     * 保存
     */
    Integer save(T t);

    /**
     * 删除
     */
    void delete(Integer id);

    /**
     * 通过id查询
     */
    T findById(Integer id);

    /**
     * 更新
     */
    void update(T t);

    /**
     * 返回所有信息
     */
    List<T> list();
}
```
**BaseServiceImpl.java**

在com.shiyanlou.springboot.service.impl包中建立BaseServiceImpl.java， BaseServiceImpl.java作为IBaseService.java的实现类，用于实现公共的增删查改等功能。
```
package com.shiyanlou.springboot.service.impl;

import com.shiyanlou.springboot.dao.BaseMapper;
import com.shiyanlou.springboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
public abstract class BaseServiceImpl<T> implements IBaseService<T> {

    @Autowired
    protected BaseMapper<T> baseMapper;

    @Override
    public void delete(Integer id) {
        baseMapper.delete(id);
    }

    @Override
    public Integer save(T t) {
        return baseMapper.save(t);
    }

    @Override
    public void update(T t) {
        baseMapper.update(t);
    }

    @Override
    public T findById(Integer id) {
        return baseMapper.findById(id);
    }


    @Override
    public List<T> list() {
        return baseMapper.list();
    }
}
```

**IUserService.java**

在com.shiyanlou.springboot.service中建立IUserService.java。 IUserService.java用于定义与User相关的业务功能，如果要自定义业务功能，直接在IUserService.java添加新的方法，然后在UserServiceImpl.java中实现即可。
```
package com.shiyanlou.springboot.service;

import com.shiyanlou.springboot.entity.User;


public interface IUserService extends IBaseService<User> {
}
```
## UserServiceImpl.java ##

在com.shiyanlou.springboot.service中建立UserServiceImpl.java,UserServiceImpl.java用于实现和 User 有关的业务逻辑。如果对BaseServiceImpl.java提供的公用增删查改功能不满意，可以选择直接覆盖或者新填一个方法。
```
package com.shiyanlou.springboot.service.impl;

import com.shiyanlou.springboot.entity.User;
import com.shiyanlou.springboot.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

}
```
## controller 层 ##

在com.shiyanlou.controller中建立UserController.java
```
package com.shiyanlou.springboot.controller;

import com.shiyanlou.springboot.entity.User;
import com.shiyanlou.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }


    //使用post请求新建
    @PostMapping()
    public String save(User user) {
        userService.save(user);
        return "save success";
    }

    //使用put请求更新 会拦截类似/user/1这种形式的路径
    @PutMapping("{id}")
    public String update(User user, @PathVariable int id) {
        //当数据不存在时，不允许更新
        if (userService.findById(id) == null) {
            return "Not Exist";
        }
        //防止传入的id不一致，如user中id属性是2，而url路径中id为1；
        if (user.getId() != id) {
            return "Unmatched parameters";
        }
        userService.update(user);
        return "update success";
    }

    //使用delete请求删除
    @DeleteMapping("{id}")
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "delete success";
    }

    //使用get方法查询
    @GetMapping()
    public List<User> list() {
        return userService.list();
    }

    //使用get方法查询单个数据
    @GetMapping("{id}")
    public User getById(@PathVariable int id) {
        return userService.findById(id);
    }
}

```
**application.properties、SpringbootApplication.java 和 mysql 数据库**

完成上面的代码后需要将 mybatis 和 springboot 整合，需要配置 mybatis 的 mapper 接口位置和 xml 文件的位置，只需要两个代码文件就可以完成的整合。

**application.properties**
```
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```
#mapper  xml文件位置
```
mybatis.mapper-locations=classpath:mapper/*.xml
```
## SpringbootApplication.java ##
```
package com.shiyanlou.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//扫描mapper接口位置
@MapperScan(basePackages = {"com.shiyanlou.springboot.dao"})
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
```
## mysql 数据库配置 ##

打开终端，启动 mysql 服务
```
sudo service mysql start
mysql -u root
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542864045528.png/wm)
```
创建数据库test,并且查看数据库是否创建成功。
```
create database test;
show databases;
```
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542864242674.png/wm)

切换到数据库test,创建数据表user;
```
use test;
CREATE TABLE user
(
    id int PRIMARY KEY AUTO_INCREMENT,
    username varchar(50),
    password varchar(50)
);
```
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542864286816.png/wm)

## 单元测试 ##

为了测试我们之前写的功能是否能够正常运行，我们需要在src/test/java目录下新建单元测试文件MVCTest.java
```
import com.shiyanlou.springboot.SpringbootApplication;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
// 定义单元测试执行顺序，采取测试用例名称升序
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MVCTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 保存
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("id", "1")
                .param("username", "shiyanlou")
                .param("password", "password")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("save success"))
                .andDo(print());
    }

    /**
     * 查询
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //采取内容匹配，所以要保证查询出来的数据和我们之前保存的数据是一致的，否则该项单元测试会失败
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"username\":\"shiyanlou\",\"password\":\"password\"}]"))
                .andDo(print());
    }

    /**
     * 更新
     *
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("username", "shiyanlou")
                .param("password", "shiyanlou")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("update success"))
                .andDo(print());
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"username\":\"shiyanlou\",\"password\":\"shiyanlou\"}"))
                .andDo(print());
    }

    /**
     * 删除
     *
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("delete success"))
                .andDo(print());
    }
}
```
输入mvn test启动单元测试，等待测试完成，可以看到结果如下（如果测试通过的话）。

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1548751952887.png/wm)

## 项目测试 ##

打开terminal，输入mvn spring-boot:run启动项目，打开第二个terminal，通过 linux 的 curl 来模拟访问。

先通过 POST 请求保存一个 user，注意 curl 命令中的 POST 要大写

curl 中-v 显示请求头信息，-X 指定使用的协议，-d 指定传输的数据。
```
curl -v -X POST -d "username=shiyanlou&password=springboot" http://localhost:8080/user
```
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542864675181.png/wm)


出现这个就说明插入成功了，来看下数据库中是否有数据。使用 get 方式访问http://localhost:8080/user 就可以调用 controller 中的 list 方法。
```
curl  http://localhost:8080/user
```
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542864701388.png/wm)


可以看到数据，说明确实保存成功了。

接着更新数据，把密码也更新成 shiyanlou，使用 PUT 方法。
```
curl -v -X PUT -d "username=shiyanlou&password=shiyanlou" http://localhost:8080/user/1
```
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542864725114.png/wm)

接着使用 getById 方法来查看，使用 GET 方法访问http://localhost:8080/user/1就可以了
```
curl http://localhost:8080/user/1
```
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542864764665.png/wm)

成功更新密码。

接着将它删除，使用 DELETE 方法访问http://localhost:8080/user/1即可。
```
curl -v -X DELETE http://localhost:8080/user/1
```
![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542864795489.png/wm)

再次使用 GET 访问http://localhost:8080/user

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542864834353.png/wm)

数据已经没有了，说明删除成功。





















