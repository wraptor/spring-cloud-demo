package com.seepine.s.eureka.provider.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApiController
 *
 * @author seepine(seepine @ 163.com)
 * @date 2020/1/28 18:20
 * @since 1.1.0
 */
@RestController
public class ApiController {

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        System.out.println("I am provider , name :" + name);
        return "hello " + name;
    }

}
