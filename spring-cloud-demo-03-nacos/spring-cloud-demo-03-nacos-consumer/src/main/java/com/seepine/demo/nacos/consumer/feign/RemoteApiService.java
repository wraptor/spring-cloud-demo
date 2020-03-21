package com.seepine.demo.nacos.consumer.feign;

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
@FeignClient("nacos-provider")
public interface RemoteApiService {
    /**
     * userConfig
     *
     * @return java.lang.Object
     * @author seepine(seepine @ 163.com)
     * @date 2020/3/21 23:24
     * @since 1.0.0
     */
    @GetMapping("/user/config")
    Object userConfig();
}
