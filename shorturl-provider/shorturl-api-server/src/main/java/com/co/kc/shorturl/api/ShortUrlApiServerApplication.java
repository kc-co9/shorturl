package com.co.kc.shorturl.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author K.C
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.co.kc.shortening.*")
public class ShortUrlApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlApiServerApplication.class, args);
    }

}
