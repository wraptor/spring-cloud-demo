package com.seepine.demo.nacos.provider.controller;

import com.seepine.demo.nacos.provider.entity.UserConfig;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApiController
 *
 * @author seepine(seepine @ 163.com)
 * @date 2020/3/21 23:08
 * @since 1.0.0
 */
@RestController
@AllArgsConstructor
public class ApiController {
    private UserConfig userConfig;

    @GetMapping("/user/config")
    public Object getUserConfig() {
        return userConfig;
    }
}
