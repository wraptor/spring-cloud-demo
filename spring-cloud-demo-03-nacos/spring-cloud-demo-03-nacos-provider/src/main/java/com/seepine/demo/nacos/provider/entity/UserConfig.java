package com.seepine.demo.nacos.provider.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * UserConfig
 *
 * @author seepine(seepine @ 163.com)
 * @date 2020/3/21 23:05
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserConfig {
    private String username;
    private String password;
}
