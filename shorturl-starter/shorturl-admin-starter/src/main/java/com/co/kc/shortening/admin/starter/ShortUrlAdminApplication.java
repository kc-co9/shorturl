package com.co.kc.shortening.admin.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author K.C
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.co.kc.shortening")
public class ShortUrlAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlAdminApplication.class, args);
    }

}
