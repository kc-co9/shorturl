package com.co.kc.shortening.application.client;

import com.co.kc.shortening.application.model.client.TokenDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemoryTokenClientTests {
    private TokenClient tokenClient;

    @BeforeEach
    public void initTokenClient() {
        tokenClient = new MemoryTokenClient();
    }

    @Test
    void testCreateToken() {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUserId(10L);
        String token = tokenClient.create(tokenDTO);
        Assertions.assertTrue(StringUtils.isNotBlank(token));
    }

    @Test
    void testParseTokenAfterCreatingToken() {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUserId(10L);
        String token = tokenClient.create(tokenDTO);
        Assertions.assertTrue(StringUtils.isNotBlank(token));

        TokenDTO parseTokenDTO = tokenClient.parse(token);
        Assertions.assertEquals(10L, parseTokenDTO.getUserId().longValue());
    }
}
