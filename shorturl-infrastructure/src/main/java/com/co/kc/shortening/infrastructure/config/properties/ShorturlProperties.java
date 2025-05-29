package com.co.kc.shortening.infrastructure.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kc
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "shorturl")
public class ShorturlProperties {
    private String shortDomain;
}
