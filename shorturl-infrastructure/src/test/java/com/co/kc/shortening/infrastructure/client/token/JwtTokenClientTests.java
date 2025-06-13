package com.co.kc.shortening.infrastructure.client.token;

import com.co.kc.shortening.application.model.client.TokenDTO;
import com.co.kc.shortening.common.utils.DateUtils;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.user.domain.model.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class JwtTokenClientTests {

    @Autowired
    private JwtTokenClient jwtTokenClient;

    @Test
    void testParseJwtToken() {
        TokenDTO createdTokenDTO = new TokenDTO();
        createdTokenDTO.setUserId(UserFactory.testUserId);
        createdTokenDTO.setCreateTime(LocalDateTime.now());
        String token = jwtTokenClient.create(createdTokenDTO);

        TokenDTO parsedTokenDTO = jwtTokenClient.parse(token);
        Assertions.assertEquals(createdTokenDTO.getUserId(), parsedTokenDTO.getUserId());
        Assertions.assertTrue(DateUtils.equals(createdTokenDTO.getCreateTime(), parsedTokenDTO.getCreateTime()));
    }
}
