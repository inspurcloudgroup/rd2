# 搭建第一个Spring Boot应用 #

## 实验步骤 ##
接下来将开始学习如何搭建自己的 Spring Boot 程序。

由于 maven 下载包需要联网，这里先将 maven 包下载下来，放到本地仓库便可以解决联网的问题(非会员用户一定要运行这一步，否则无法进行实验)

wget http://labfile.oss.aliyuncs.com/courses/1152/m2.zip
unzip m2.zip
cp .m2 /home/shiyanlou/

## 采用 Spring Initializr 搭建 ##

Spring Initializr 是官方提供的一种快捷搭建 Spring Boot 应用的方式。 只需要打开网址: https://start.spring.io/ 就可以看到:

![](https://doc.shiyanlou.com/document-uid441493labid7987timestamp1535422355067.png/wm)

我们可以看到上面可以选择构建工具，语言，Spring Boot 版本，group 和 artifact 以及依赖。 这里我们选择使用Maven构建，语言java，Spring Boot 版本2.0.4，group 为com.shiyanlou, artifact 为springboot，依赖我们选择web。

![](https://doc.shiyanlou.com/document-uid441493labid7987timestamp1535425658177.png/wm)

点击 Generate Project，我们会得到一个springboot.zip的压缩包。 这里直接提供该压缩包，将其下载解压
```
wget http://labfile.oss.aliyuncs.com/courses/1152/springboot.zip
unzip springboot.zip
```
接着切换工作空间到springboot目录。

然后在src/main/java目录下新建包路径com.shiyanlou.springboot.controller

在包中建立新类ShiyanlouController.java,代码如下：
```
package com.shiyanlou.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//RestController相当于同时使用@Controller和@ResponseBody注解
public class ShiyanlouController {

    @RequestMapping("shiyanlou")
    public String shiyanlou() {
        return "Hello Shiyanlou";
    }
}
```
项目文件结构如下：

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542779592020.png/wm)

然后打开 terminal，运行mvn spring-boot:run(这里使用 spirngboot 的 maven 插件启动)。点击工具中的 web 服务，接着修改地址栏为https://**************.simplelab.cn/shiyanlou 

结果

![](https://doc.shiyanlou.com/document-uid441493labid8432timestamp1542779866869.png/wm)























































































