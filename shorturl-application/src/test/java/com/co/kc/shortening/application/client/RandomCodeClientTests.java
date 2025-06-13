package com.co.kc.shortening.application.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomCodeClientTests {
    @Test
    void testGenRandomCode() {
        IdClient<String> codeClient = new RandomCodeClient();
        Assertions.assertNotNull(codeClient.next());
        Assertions.assertEquals(20, codeClient.next().length());
    }
}
