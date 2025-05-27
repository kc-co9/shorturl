package com.co.kc.shortening.application.client;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class RandomIdClientTests {
    @Test
    public void testGenRandomId() {
        IdClient<Long> idClient = new RandomIdClient();
        Assert.assertNotNull(idClient.next());
        Assert.assertTrue(idClient.next() >= 1);
    }
}
