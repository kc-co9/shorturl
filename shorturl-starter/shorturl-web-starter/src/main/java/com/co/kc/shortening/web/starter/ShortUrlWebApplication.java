package com.co.kc.shortening.web.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author K.C
 */
@SpringBootApplication(scanBasePackages = "com.co.kc.shortening")
public class ShortUrlWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlWebApplication.class, args);
    }

}
