# spring-cloud-demo-03-nacos

> 本Demo演示了网关的搭建，以及如何使用网关配合Nacos访问其他服务，并进行分流、限流和熔断等配置。

## 模块介绍

spring-cloud-demo-04-gateway模块只有一个子模块，但其为配合spring-cloud-demo-03-nacos模块中的Provider(供应者)和Consumer(消费者)进行演示。

## 一、搭建网关

### 1.添加依赖

```xml
<dependencies>
    <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
</dependencies>
```

### 2.添加配置文件

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      config:
        server-addr: ${spring.cloud.nacos.discovery.server-addr}
        file-extension: yml
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
server:
  port: 13000
```

### 3.启动网关（启动nacos服务端的基础上）

![](https://pic.downk.cc/item/5e7f0db7504f4bcb0450d429.png)

### 4.启动spring-cloud-demo-03-nacos中的provider并测试

[http://localhost:12000/user/config](http://localhost:12000/user/config)
![](https://pic.downk.cc/item/5e7f0fb4504f4bcb0452b581.png)
![](https://pic.downk.cc/item/5e7f0fbe504f4bcb0452c173.png)

### 5.通过网关访问provider

[http://localhost:13000/nacos-provider/user/config](http://localhost:13000/nacos-provider/user/config)
![](https://pic.downk.cc/item/5e7f0fe1504f4bcb0452e6fe.png)


