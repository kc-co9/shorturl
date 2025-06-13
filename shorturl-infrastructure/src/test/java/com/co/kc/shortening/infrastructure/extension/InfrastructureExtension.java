package com.co.kc.shortening.infrastructure.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class InfrastructureExtension implements BeforeAllCallback, AfterAllCallback {
    private final RedisExtension redisExtension = new RedisExtension();
    private final DatabaseExtension databaseExtension = new DatabaseExtension();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        // RedisExtension需要在DatabaseExtension前执行，因为如果ApplicationContext已经初始化完成了，对Redis的设置将无效。
        redisExtension.beforeAll(context);
        databaseExtension.beforeAll(context);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        databaseExtension.afterAll(context);
        redisExtension.afterAll(context);
    }
}
