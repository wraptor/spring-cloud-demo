# spring-cloud-demo-01-eureka

> 本Demo演示了Eureka服务端的搭建，以及消费者如何通过Eureka服务注册中心调用其他供应者提供的接口。

## 模块介绍

spring-cloud-demo-01-eureka模块分为三个子模块，分别为Eureka-Server(Eureka服务端)、Eureka-Provider(供应者)和Eureka-Consumer(消费者)。

## Eureka服务注册与发现
### 1.Eureka-Server
1.1 添加依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
</dependencies>
```
1.2 在启动类上添加注解@EnableEurekaServer
```java
@EnableEurekaServer
@SpringBootApplication
public class SpringCloudDemo01EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo01EurekaServerApplication.class, args);
    }
}
```
1.3 添加配置文件
```yaml
server:
  port: 8761
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false #该实例是否向EurekaServer注册自己，默认true
    fetch-registry: false  #该实例是否向Eureka服务器获取所有的注册信息表，默认true
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/  #配置 Eureka-Server 地址
```
### 2.Eureka-Provider
1.1 添加依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```
1.2 在启动类上添加注解@EnableEurekaServer
```java
@EnableEurekaServer
@SpringBootApplication
public class SpringCloudDemo01EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo01EurekaServerApplication.class, args);
    }
}
```
1.3 添加配置文件
```yaml
server:
  port: 8761
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false #该实例是否向EurekaServer注册自己，默认true
    fetch-registry: false  #该实例是否向Eureka服务器获取所有的注册信息表，默认true
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/  #配置 Eureka-Server 地址
```
## 启动Demo
- 1.启动Eureka-Server
- 2.启动Eureka-Provider
- 3.启动Eureka-Consumer

## 结果检验

打开http://localhost:8761/，将会看到Eureka的界面，在其中