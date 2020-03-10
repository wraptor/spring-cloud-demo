# spring-cloud-demo-02-config

> 本Demo演示了Config服务端的搭建，以及客户端如何通过Config服务端获取配置文件。

## 模块介绍

spring-cloud-demo-02-config模块分为两个子模块，分别为Config-Server(Config服务端)和Config-Client(Config客户端)。

## 一、搭建Config服务端

### 1.Config-Server

1.1 添加依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

1.2 在启动类上添加注解@EnableConfigServer

```java
@EnableConfigServer
@SpringBootApplication
public class SpringCloudDemo02ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo02ConfigServerApplication.class, args);
    }

}
```

1.3 添加配置文件

```yaml
spring:
  application:
    name: config-server
  cloud:
    config:
      server:
        git:
          uri: https://github.com/seepine/spring-cloud-demo-config
server:
  port: 10000
```

1.4 创建git配置文件仓库（可参考https://github.com/seepine/spring-cloud-demo-config）

创建文件名为config-client-dev.yml
```yaml
user:
  username: seepine
  password: 123456
```

1.5 启动ConfigServer并验证

输入地址及文件名：[http://127.0.0.1:10000/config-client-dev.yml](http://127.0.0.1:10000/config-client-dev.yml)

![](https://pic.downk.cc/item/5e6705f798271cb2b87b4ce1.png)

### 2.Config-Client

2.1 添加依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

2.2 添加配置文件
```yaml
spring:
  application:
    name: config-client
  cloud:
    config:
      uri: http://localhost:10000
  profiles:
    active: dev
server:
  port: 11000
```

2.3 创建两个获取配置文件值的实体类
```java
@Data
@Component
public class User {
    @Value("${user.username}")
    private String username;
    @Value("${user.password}")
    private String password;
}
```
```java
@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserConfig {
    private String username;
    private String password;
}
```

2.4 编写测试接口
```java
@RestController
@AllArgsConstructor
public class ApiController {
    private User user;
    private UserConfig userConfig;

    @GetMapping("/user")
    public Object getUser() {
        return user;
    }
    @GetMapping("/user/config")
    public Object getUserConfig() {
        return userConfig;
    }
}
```

2.5 分别测试两个接口
![](https://pic.downk.cc/item/5e670cb798271cb2b87e351c.png)
![](https://pic.downk.cc/item/5e670cc298271cb2b87e3f38.png)

### 3.整合Eureka

3.1 Config-Server与Config-Client添加依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

3.2 Config-Server与Config-Client添加配置
```yaml
eureka:
  instance:
    hostname: localhost #eureka服务端的实例名称
  client:
    service-url:
      defaultZone: http://seepine:123456@${eureka.instance.hostname}:8761/eureka/  # 与注册中心交互的url
```

3.3 Config-Server配置文件修改
```yaml
#      uri: http://localhost:10000
      discovery:
        enabled: true  #默认false，设为true表示使用注册中心中的configserver配置，而不是配置的uri
        service-id: CONFIG-SERVER  #指定config server在服务发现中的serviceId，默认为：configserver
```

3.4 重启客户端分别测试两个接口

![](https://pic.downk.cc/item/5e670cb798271cb2b87e351c.png)
![](https://pic.downk.cc/item/5e670cc298271cb2b87e3f38.png)