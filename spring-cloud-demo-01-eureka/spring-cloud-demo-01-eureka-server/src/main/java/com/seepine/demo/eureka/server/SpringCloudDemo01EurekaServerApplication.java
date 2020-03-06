package com.seepine.demo.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author seepine
 * @date 2020/1/25 23:52
 * @since 1.0.0
 */
@EnableEurekaServer
@SpringBootApplication
public class SpringCloudDemo01EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo01EurekaServerApplication.class, args);
    }
}
