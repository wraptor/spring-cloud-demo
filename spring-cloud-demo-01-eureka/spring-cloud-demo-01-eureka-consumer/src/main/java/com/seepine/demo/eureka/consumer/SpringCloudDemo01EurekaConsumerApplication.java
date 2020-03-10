package com.seepine.demo.eureka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Eureka Consumer 启动类
 *
 * @author seepine(seepine @ 163.com)
 * @date 2020/1/29 00:08
 * @since 1.1.0
 */
@SpringBootApplication
@EnableFeignClients
public class SpringCloudDemo01EurekaConsumerApplication {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo01EurekaConsumerApplication.class, args);
    }

}
