package com.seepine.demo.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 * @author seepine(seepine@163.com)
 * @date 2020/3/9 18:32
 * @since 1.0.0
 */
@EnableEurekaClient
@EnableConfigServer
@SpringBootApplication
public class SpringCloudDemo02ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo02ConfigServerApplication.class, args);
    }

}
