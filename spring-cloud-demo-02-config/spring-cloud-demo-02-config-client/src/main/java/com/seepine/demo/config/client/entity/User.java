package com.seepine.demo.config.client.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * User
 *
 * @author seepine(seepine @ 163.com)
 * @date 2020/3/9 18:56
 * @since 1.0.0
 */
@Data
@Component
public class User {
    @Value("${user.username}")
    private String username;
    @Value("${user.password}")
    private String password;
}
