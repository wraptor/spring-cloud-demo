package com.seepine.demo.config.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * SpringCloudDemo02ConfigClientApplication
 *
 * @author seepine(seepine @ 163.com)
 * @date 2020/3/9 18:12
 * @since 1.1.0
 */
@SpringBootApplication
public class SpringCloudDemo02ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo02ConfigClientApplication.class, args);
    }

}
