package com.seepine.demo.eureka.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RemoteApiService
 *
 * @author seepine
 * @date 2020/3/7 16:52
 * @since 1.0.0
 */
@FeignClient("provider")
public interface RemoteApiService {
    /**
     * sayHello
     *
     * @param name name
     * @return java.lang.String
     * @author seepine(seepine @ 163.com)
     * @date 2020/3/7 16:52
     * @since 1.1.0
     */
    @GetMapping("/hello/{name}")
    String sayHello(@PathVariable("name") String name);
}
