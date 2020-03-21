package com.seepine.demo.nacos.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * SpringCloudDemo03NacosConsumerApplication
 *
 * @author seepine(seepine @ 163.com)
 * @date 2020/3/10 14:19
 * @since 1.1.0
 */
@EnableFeignClients
@SpringBootApplication
public class SpringCloudDemo03NacosConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo03NacosConsumerApplication.class, args);
    }
}
