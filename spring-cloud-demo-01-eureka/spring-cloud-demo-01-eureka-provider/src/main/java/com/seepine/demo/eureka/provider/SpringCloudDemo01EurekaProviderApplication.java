package com.seepine.demo.eureka.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * Eureka Provider 启动类
 *
 * @author seepine
 * @date 2020/1/28 18:18
 * @since 1.0.0
 */
@SpringBootApplication
public class SpringCloudDemo01EurekaProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo01EurekaProviderApplication.class, args);
    }
}
