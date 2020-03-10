package com.seepine.demo.config.client.controller;

import com.seepine.demo.config.client.entity.User;
import com.seepine.demo.config.client.entity.UserConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApiController
 *
 * @author huanghs(xm_hhs @ 163.com)
 * @date 2020/3/9 18:57
 * @since 1.0.0
 */
@RestController
@AllArgsConstructor
public class ApiController {
    private User user;
    private UserConfig userConfig;

    @GetMapping("/user")
    public Object getUser() {
        return user;
    }
    @GetMapping("/user/config")
    public Object getUserConfig() {
        return userConfig;
    }
}
