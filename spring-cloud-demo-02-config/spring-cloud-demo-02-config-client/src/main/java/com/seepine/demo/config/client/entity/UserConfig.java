package com.seepine.demo.config.client.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * UserConfig
 *
 * @author seepine(seepine @ 163.com)
 * @date 2020/3/10 10:22
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserConfig {
    private String username;
    private String password;
}
