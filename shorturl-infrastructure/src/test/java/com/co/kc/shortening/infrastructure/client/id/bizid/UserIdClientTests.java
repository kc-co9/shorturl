package com.co.kc.shortening.infrastructure.client.id.bizid;

import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class UserIdClientTests {
    @Autowired
    private UserIdClient userIdClient;

    @Test
    void testGenUserIdByNext() {
        Set<Long> set = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Assertions.assertTrue(set.add(userIdClient.next()));
        }
    }
}
