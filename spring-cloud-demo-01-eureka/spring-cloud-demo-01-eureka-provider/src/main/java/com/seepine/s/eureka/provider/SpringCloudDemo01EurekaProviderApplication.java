package com.seepine.s.eureka.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * Eureka Provider 启动累
 *
 * @author seepine(seepine @ 163.com)
 * @date 2020/1/28 18:18
 * @since 1.1.0
 */
@SpringBootApplication
@EnableEurekaClient
public class SpringCloudDemo01EurekaProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo01EurekaProviderApplication.class, args);
    }

}
