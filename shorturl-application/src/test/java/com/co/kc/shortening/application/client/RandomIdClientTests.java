package com.co.kc.shortening.application.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class RandomIdClientTests {
    @Test
    void testGenRandomId() {
        IdClient<Long> idClient = new RandomIdClient();
        Assertions.assertNotNull(idClient.next());
        Assertions.assertTrue(idClient.next() >= 1);
    }
}
