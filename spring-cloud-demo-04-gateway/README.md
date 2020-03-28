# spring-cloud-demo-03-nacos

> 本Demo演示了网关的搭建，以及如何使用网关配合Nacos（使用eureka和config同理）访问其他服务，并进行分流、限流和熔断等配置。

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
其中"nacos-provider"为被访问服务的spring application name，也是向nacos注册的服务名
[http://localhost:13000/nacos-provider/user/config](http://localhost:13000/nacos-provider/user/config)
![](https://pic.downk.cc/item/5e7f0fe1504f4bcb0452e6fe.png)

## 二、过滤器

### 1.建立response全局过滤器来进行统一返回
实现GlobalFilter, Ordered
```java
@Component
public class ResponseGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        DataBufferUtils.release(dataBuffer);
                        String rs = new String(content, StandardCharsets.UTF_8);
                        Response<String> response = new Response<>();
                        response.setCode(Objects.requireNonNull(originalResponse.getStatusCode()).value());
                        response.setMsg(originalResponse.getStatusCode().name());
                        if (originalResponse.getStatusCode() == HttpStatus.OK) {
                            response.setData(rs);
                        }
                        byte[] newRs = JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8);
                        originalResponse.getHeaders().setContentLength(newRs.length);
                        return bufferFactory.wrap(newRs);
                    }));
                }
                return super.writeWith(body);
            }
        };
        // replace response with decorator
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }
    @Override
    public int getOrder() {
        return -2;
    }
}
```

### 2.分别测试正确及错误的接口

[http://localhost:13000/nacos-provider/user/config](http://localhost:13000/nacos-provider/user/config)

![](https://pic.downk.cc/item/5e7f2274504f4bcb0462a6d4.png)

[http://localhost:13000/nacos-provider/user/config/123](http://localhost:13000/nacos-provider/user/config/123)

![](https://pic.downk.cc/item/5e7f228f504f4bcb0462bc98.png)

