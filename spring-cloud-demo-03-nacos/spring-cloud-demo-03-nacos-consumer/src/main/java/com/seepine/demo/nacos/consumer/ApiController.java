package com.seepine.demo.nacos.consumer;

import com.seepine.demo.nacos.consumer.feign.RemoteApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author seepine
 * @date 2020/3/21 23:22
 */
@RestController
@AllArgsConstructor
public class ApiController {
    RemoteApiService remoteApiService;
    @GetMapping("/get")
    public Object get() {
        return remoteApiService.userConfig();
    }
}
