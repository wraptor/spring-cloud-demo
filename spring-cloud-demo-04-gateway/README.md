# spring-cloud-demo-03-nacos

> 本Demo演示了网关的搭建，以及如何使用网关配合Nacos（使用eureka和config同理）访问其他服务，并进行过滤、熔断和限流等配置。

## 模块介绍

spring-cloud-demo-04-gateway包含两个模块，分别是Server(网关服务)和Provider(供应者)进行演示。

## 一、搭建Provider
### 1.添加依赖

```xml
<dependencies>
    <dependency>
            <groupId>org.springframework.cloud</groupId>
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
</dependencies>
```

### 2.添加配置文件
```yaml
spring:
  application:
    name: gateway-provider
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
### 3.添加接口
```java
@RestController
public class ApiController {
    @GetMapping("/sleep/{second}")
    public String sleep(@PathVariable long second) throws InterruptedException {
        Thread.sleep(second);
        return "completed";
    }
}
```

### 4.启动Nacos
已启动的无需再次启动
```
#未启动过
docker run --name nacos -e MODE=standalone -p 8848:8848 -d nacos/nacos-server:latest
#启动过但停止的
docker start nacos
```
确保Nacos控制台能够访问
![](https://pic.downk.cc/item/5e7f6124504f4bcb04996682.png)

### 5.启动Spring-cloud-demo-04-gateway-provider并测试接口

![](https://pic.downk.cc/item/5e7f6124504f4bcb04996682.png)

![](https://pic.downk.cc/item/5e7f695e504f4bcb04a1378f.png)

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
配置文件中主要包含两部分，第一部分时配置nacos的服务发现及配置中心，第二部分是配置了网关的路由功能，通过网关的服务发现和单独配置路由断言这两种不同的方式来访问其他服务接口。
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
      discovery:   #方式一
        locator:
          enabled: true
          lower-case-service-id: true
      routes:   #方式二
        - id: gateway-provider  #可随意指定但是要保证唯一
          uri: lb://gateway-provider  #lib://指定服务名,也可直接指定地址(http://127.0.0.1:12000)
          predicates:
            - Path=/provider/**   #当网关匹配到改路径，将会转发到uri地址
          filters:
            - StripPrefix=1
server:
  port: 14000
```
关于"StripPrefix=1"配置，是网关自带的过滤器，去掉N个前缀，具体实现参考StripPrefixGatewayFilterFactory,
因为provider的接口路径为
```
http://localhost:12000/user/config
```
而从网关访问接口的路径是
```
http://localhost:13000/provider/user/config
```
由于我们配置了路由，网关会转发到对应的ip:port，也就是
```
http://localhost:12000/provider/user/config
```
此时会发现，与我们provider访问的接口路径多了一层/provider,此时若未配置StripPrefix=1将会出现404操作，
所以有如下三种方式可解决此问题
1.配置上StripPrefix=1；
2.在provider加上访问项目名称server.servlet.context-path=/provider；
3.编写request过滤器，过滤每个链接的第一个路径
### 3.启动网关

![](https://pic.downk.cc/item/5e7f6a3e504f4bcb04a20b63.png)

### 4.通过网关访问provider

方式一：其中"nacos-provider"为被访问服务的spring application name，也是向nacos注册的服务名
[http://127.0.0.1:14000/gateway-provider/sleep/1](http://127.0.0.1:14000/gateway-provider/sleep/1)
![](https://pic.downk.cc/item/5e7f6a84504f4bcb04a24e42.png)

方式二：也可以使用routes配置的路径来访问
[http://127.0.0.1:14000/provider/sleep/1](http://127.0.0.1:14000/provider/sleep/1)
![](https://pic.downk.cc/item/5e7f5935504f4bcb049172de.png)


## 二、过滤器
过滤器可以实现通过网关来过滤token、请求头、统一返回格式等功能，通过简单例子来介绍过滤器以及其实现方法。

### 1.建立response全局过滤器来进行统一返回
实现GlobalFilter, Ordered
```
@Component
public class ResponseGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            @SuppressWarnings("unchecked")
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        DataBufferUtils.release(dataBuffer);
                        String rs = new String(content, StandardCharsets.UTF_8);
                        Response<String> response;
                        if (originalResponse.getStatusCode() == HttpStatus.OK) {
                            response = Response.build(originalResponse.getStatusCode(), rs);
                        } else {
                            response = Response.build(Objects.requireNonNull(originalResponse.getStatusCode()));
                        }
                        byte[] newRs = JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8);
                        originalResponse.getHeaders().setContentLength(newRs.length);
                        return bufferFactory.wrap(newRs);
                    }));
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }
    @Override
    public int getOrder() {
        return -2;
    }
}
```

### 2.分别测试正确及错误的接口

[http://127.0.0.1:14000/provider/sleep/1/](http://127.0.0.1:14000/provider/sleep/1/)

![](https://pic.downk.cc/item/5e8099f6504f4bcb0460aeea.png)

[http://127.0.0.1:14000/provider/sleep/1/test](http://127.0.0.1:14000/provider/sleep/1/test)

![](https://pic.downk.cc/item/5e7f228f504f4bcb0462bc98.png)

## 三、服务熔断

服务熔断由hystrix提供，通过在过滤器下配置熔断降级
### 1.Server添加依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```
### 2.熔断配置

在配置文件中的gateway节点下增加default-filters过滤器，并增加nacos-provider的routes配置（当然也可以使用Bean的方式配置），使用routes
```yaml
spring:
  cloud:
    gateway:
      #配置全局过滤器
      default-filters:
        - name: Hystrix
          args:
            name: fallbackcmd
            fallbackUri: forward:/fallback    #指定熔断跳转地址
```


### 3.编写熔断接口
```java
@RestController
public class FallbackController {
    @GetMapping("/fallback")
    public Response<String> fallback() {
        return Response.build(HttpStatus.SERVICE_UNAVAILABLE);
    }
}
```

ps:
也可以使用handle的方法，开启configs.
### 4.测试熔断效果

默认超时时间为一秒钟，分别输入不同参数可得结果与结论一致

[http://127.0.0.1:14000/provider/sleep/500](http://127.0.0.1:14000/provider/sleep/500)

![](https://pic.downk.cc/item/5e809d12504f4bcb04639fcd.png)
[http://127.0.0.1:14000/provider/sleep/1500](http://127.0.0.1:14000/provider/sleep/1500)

![](https://pic.downk.cc/item/5e809d1e504f4bcb0463aa51.png)

### 5.修改熔断时间
```yaml
hystrix:
  command:
    fallbackcmd:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 5000
```

### 6.再次测试

[http://127.0.0.1:14000/provider/sleep/4500](http://127.0.0.1:14000/provider/sleep/4500)

![](https://pic.downk.cc/item/5e809dea504f4bcb04646b15.png)

[http://127.0.0.1:14000/provider/sleep/5500](http://127.0.0.1:14000/provider/sleep/5500)

![](https://pic.downk.cc/item/5e809e0d504f4bcb04648ddf.png)

### 7.hystrix-dashboard
添加依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```
访问/hystrix

## 四、限流
Spring Cloud Gateway集成了Redis限流，可以根据不同服务做不同的限流规则，如iP限流、用户限流 和接口限流，本例演示的是全局Ip限流

### 1.添加Redis依赖
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-redis-reactive</artifactId>
</dependency>
```

### 2.添加相关配置
```yaml
spring:
  redis:
    host: localhost
  cloud:
    gateway:
      #配置全局过滤器
      default-filters:
        - name: RequestRateLimiter
          args:
            key-resolver: '#{@remoteAddrKeyResolver}'   # 使用SpEL名称引用Bean，与建立的RateLimiterConfig类中的bean的name相同
            redis-rate-limiter.replenishRate: 20    # 每秒最大访问次数
            redis-rate-limiter.burstCapacity: 20    # 令牌桶最大容量
```

### 3.新建限流配置类，指定限流key
```java
@Configuration
public class RateLimiterConfig {
    @Bean
    public KeyResolver remoteAddrKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
    }
}
```

### 4.通过jmeter测试效果

未配置限流

![](https://pic.downk.cc/item/5e80c48a504f4bcb0482cb65.png)

配置限流

![](https://pic.downk.cc/item/5e80c417504f4bcb04826dfb.png)
