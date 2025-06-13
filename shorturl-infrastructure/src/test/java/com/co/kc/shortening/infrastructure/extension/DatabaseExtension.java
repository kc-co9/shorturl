package com.co.kc.shortening.infrastructure.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;

public class DatabaseExtension implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        // 通过ExtensionContext拿ApplicationContext
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/h2/ddl.sql"));
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {

    }
}
