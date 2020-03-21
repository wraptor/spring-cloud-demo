# spring-cloud-demo-02-config

> 本Demo演示了Config服务端的搭建，以及客户端如何通过Config服务端获取配置文件。

## 模块介绍

spring-cloud-demo-03-nacos模块分为两个子模块，分别为Provider(供应者)和Consumer(消费者)。

## 一、搭建Nacos服务端
本教程是用Docker搭建Nacos，也可到[官网](https://nacos.io/zh-cn/docs/quick-start.html)使用任意方法搭建。

### 1.安装Docker
[点此下载对应系统的docker-ce](https://hub.docker.com/search?q=&type=edition&offering=community&sort=updated_at&order=desc)

### 2.运行Nacos服务端
```
docker run --name nacos -e MODE=standalone -p 8848:8848 -d nacos/nacos-server:latest
```

### 3.验证
输入[http://127.0.0.1:8848/nacos/](http://127.0.0.1:8848/nacos/)即可看到Nacos控制台
![](https://pic.downk.cc/item/5e7629719d7d586a541e154c.png)

## 二.创建供应者

### 1.添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

### 2.添加配置文件

```yaml
spring:
  application:
    name: nacos-provider
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      config:
        server-addr: ${spring.cloud.nacos.discovery.server-addr}
        file-extension: yml
server:
  port: 12000
```

### 3.在Nacos中创建对应配置文件

其中[Data ID]需要与spring.application.name对应
![](https://pic.downk.cc/item/5e762d6b9d7d586a542109e8.png)

### 4.创建获取配置文件的实体类
```java
@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserConfig {
    private String username;
    private String password;
}
```

### 5.编写测试接口

```java
@RestController
@AllArgsConstructor
public class ApiController {
    private UserConfig userConfig;

    @GetMapping("/user/config")
    public Object getUserConfig() {
        return userConfig;
    }
}
```

### 6.测试服务发现与获取配置文件功能

![](https://pic.downk.cc/item/5e762e6e9d7d586a5421f9dd.png)

![](https://pic.downk.cc/item/5e762e7a9d7d586a5422082a.png)

### 7.测试配置文件动态刷新
使用@RefreshScope或@ConfigurationProperties即可使用动态刷新功能

7.1 进入Nacos修改配置文件参数
![](https://pic.downk.cc/item/5e762f449d7d586a5422d67d.png)
7.2 此时再次刷新接口
![](https://pic.downk.cc/item/5e762f609d7d586a5422fbbe.png)

## 三、创建消费者

### 1.添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

### 2.添加配置文件

```yaml
spring:
  application:
    name: nacos-consumer
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      config:
        server-addr: ${spring.cloud.nacos.discovery.server-addr}
        file-extension: yml
server:
  port: 13000
```

### 3.添加Feign
```java
@EnableFeignClients
@SpringBootApplication
public class SpringCloudDemo03NacosConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo03NacosConsumerApplication.class, args);
    }
}
```
```java
@FeignClient("nacos-provider")
public interface RemoteApiService {
    @GetMapping("/user/config")
    Object userConfig();
}
```

### 4.添加消费者接口
```java
@RestController
@AllArgsConstructor
public class ApiController {
    RemoteApiService remoteApiService;
    @GetMapping("/get")
    public Object get() {
        return remoteApiService.userConfig();
    }
}
```

### 5.测试服务发现功能及通过feign获取供应者的配置文件内容

![](https://pic.downk.cc/item/5e7634249d7d586a542776aa.png)

![](https://pic.downk.cc/item/5e76320a9d7d586a54256e2a.png)