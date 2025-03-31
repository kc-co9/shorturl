package com.co.kc.shorturl.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kc
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "shorturl")
public class ShorturlConfig {
    private String host;
}
