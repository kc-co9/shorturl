package com.co.kc.shortening.application.client;

import com.co.kc.shortening.application.model.client.TokenDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MemoryTokenClientTests {
    private TokenClient tokenClient;

    @Before
    public void initTokenClient() {
        tokenClient = new MemoryTokenClient();
    }

    @Test
    public void testCreateToken() {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUserId(10L);
        String token = tokenClient.create(tokenDTO);
        Assert.assertTrue(StringUtils.isNotBlank(token));
    }

    @Test
    public void testParseTokenAfterCreatingToken() {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUserId(10L);
        String token = tokenClient.create(tokenDTO);
        Assert.assertTrue(StringUtils.isNotBlank(token));

        TokenDTO parseTokenDTO = tokenClient.parse(token);
        Assert.assertEquals(10L, parseTokenDTO.getUserId().longValue());
    }
}
