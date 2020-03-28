package com.seepine.demo.gateway.server.gateway.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试网关熔断等相关接口
 *
 * @author Seepine
 * @date 2020/3/28 22:16
 */
@RestController
public class ApiController {

    /**
     * 通过传入的秒数使线程沉睡，用来触发配置的熔断时间
     *
     * @param second 睡眠秒数
     * @return String
     * @throws InterruptedException InterruptedException
     */
    @GetMapping("/sleep/{second}")
    public String sleep(@PathVariable long second) throws InterruptedException {
        Thread.sleep(second);
        return "completed";
    }
}
