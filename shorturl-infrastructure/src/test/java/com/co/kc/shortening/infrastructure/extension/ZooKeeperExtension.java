package com.co.kc.shortening.infrastructure.extension;

import org.apache.curator.test.TestingServer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZooKeeperExtension implements BeforeAllCallback, AfterAllCallback {

    private static final Logger LOG = LoggerFactory.getLogger(ZooKeeperExtension.class);

    static {
        try {
            TestingServer server = new TestingServer(2181);
            // 设置Spring Boot读取的配置项
            System.setProperty("zookeeper.host", server.getConnectString());
            // 启动ZooKeeper
            server.start();
        } catch (Exception e) {
            LOG.error("ZooKeeper启动失败", e);
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) {
    }

    @Override
    public void afterAll(ExtensionContext context) {
    }
}
