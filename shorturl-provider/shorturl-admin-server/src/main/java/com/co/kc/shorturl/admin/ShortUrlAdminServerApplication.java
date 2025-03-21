package com.co.kc.shorturl.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author K.C
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.co.kc.shorturl.*")
public class ShortUrlAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlAdminServerApplication.class, args);
    }

}
