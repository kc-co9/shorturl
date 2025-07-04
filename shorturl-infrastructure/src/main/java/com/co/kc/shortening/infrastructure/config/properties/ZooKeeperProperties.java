package com.co.kc.shortening.infrastructure.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kc
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class ZooKeeperProperties {
    private String host;
    private String namespace;
    private Integer sessionTimeoutMs;
    private Integer connectionTimeoutMs;
    private Integer failRetryCount;
    private Integer failRetryIntervalMs;
}
