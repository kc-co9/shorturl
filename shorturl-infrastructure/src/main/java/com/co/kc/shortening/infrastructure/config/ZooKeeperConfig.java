package com.co.kc.shortening.infrastructure.config;

import com.co.kc.shortening.infrastructure.config.properties.ZooKeeperProperties;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ZooKeeper客户端
 *
 * @author kc
 */
@Configuration
public class ZooKeeperConfig {
    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework zkCurator(ZooKeeperProperties zooKeeperProperties) {
        return CuratorFrameworkFactory.builder()
                .connectString(zooKeeperProperties.getHost())
                .namespace(zooKeeperProperties.getNamespace())
                .sessionTimeoutMs(zooKeeperProperties.getSessionTimeoutMs())
                .connectionTimeoutMs(zooKeeperProperties.getConnectionTimeoutMs())
                .retryPolicy(new RetryNTimes(zooKeeperProperties.getFailRetryCount(), zooKeeperProperties.getFailRetryIntervalMs()))
                .build();
    }
}
