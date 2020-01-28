package com.seepine.demo.eureka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringCloudDemo01EurekaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudDemo01EurekaConsumerApplication.class, args);
	}

}
