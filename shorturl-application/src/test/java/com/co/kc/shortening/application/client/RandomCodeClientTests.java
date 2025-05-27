package com.co.kc.shortening.application.client;

import org.junit.Assert;
import org.junit.Test;

public class RandomCodeClientTests {
    @Test
    public void testGenRandomCode() {
        IdClient<String> codeClient = new RandomCodeClient();
        Assert.assertNotNull(codeClient.next());
        Assert.assertEquals(20, codeClient.next().length());
    }
}
