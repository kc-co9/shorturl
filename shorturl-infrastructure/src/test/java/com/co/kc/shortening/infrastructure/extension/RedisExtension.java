package com.co.kc.shortening.infrastructure.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;

public class RedisExtension implements BeforeAllCallback, AfterAllCallback {
    private static final GenericContainer<?> redisContainer =
            new GenericContainer<>("redis:7.2.0")
                    .withExposedPorts(6379)
                    .withReuse(true);

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!redisContainer.isRunning()) {
            redisContainer.start();
        }
        // 设置Spring Boot读取的配置项
        System.setProperty("spring.redis.host", redisContainer.getHost());
        System.setProperty("spring.redis.port", redisContainer.getMappedPort(6379).toString());
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        // 不做手动关闭，如果在 afterAll() 中 手动 stop() 容器时，如果你还有其它测试类共用这个 Redis，就会因为连接断掉而报错
        // if (redisContainer.isRunning()) {
        //    redisContainer.stop();
        // }
    }
}
