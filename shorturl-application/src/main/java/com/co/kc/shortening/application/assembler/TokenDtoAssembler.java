package com.co.kc.shortening.application.assembler;

import com.co.kc.shortening.application.model.client.TokenDTO;
import com.co.kc.shortening.user.domain.model.User;

import java.time.LocalDateTime;

/**
 * @author kc
 */
public class TokenDtoAssembler {
    private TokenDtoAssembler() {
    }

    public static TokenDTO userToDTO(User user) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUserId(user.getUserId().getId());
        tokenDTO.setCreateTime(LocalDateTime.now());
        return tokenDTO;
    }
}
