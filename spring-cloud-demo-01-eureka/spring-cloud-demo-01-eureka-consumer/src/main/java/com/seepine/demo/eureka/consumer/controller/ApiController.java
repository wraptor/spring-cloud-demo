package com.seepine.demo.eureka.consumer.controller;

import com.seepine.demo.eureka.consumer.feign.RemoteApiService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * ApiController
 *
 * @author seepine(seepine @ 163.com)
 * @date 2020/1/28 18:24
 * @since 1.1.0
 */
@RestController
@AllArgsConstructor
public class ApiController {
    private DiscoveryClient discoveryClient;
    private RestTemplate restTemplate;
    private RemoteApiService remoteApiService;

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        System.out.println("I am consumer , name :" + name);
        List<ServiceInstance> instances = discoveryClient.getInstances("provider");
        if (!instances.isEmpty()) {
            ServiceInstance serviceInstance = instances.get(0);
            return restTemplate.getForObject(serviceInstance.getUri().toString() + "/hello/" + name, String.class);
        }
        return "not find provider";
    }

    @RequestMapping("/helloByFeign/{name}")
    public String helloByFeign(@PathVariable String name) {
        return remoteApiService.sayHello(name);
    }
}
