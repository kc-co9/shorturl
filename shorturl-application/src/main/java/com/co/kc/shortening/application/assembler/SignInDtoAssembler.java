package com.co.kc.shortening.application.assembler;

import com.co.kc.shortening.application.model.cqrs.dto.SignInDTO;
import com.co.kc.shortening.user.domain.model.User;

/**
 * AuthenticateDTO组装器
 *
 * @author kc
 */
public class SignInDtoAssembler {
    private SignInDtoAssembler() {
    }

    public static SignInDTO userTokenToDTO(User user, String token) {
        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setUserId(user.getUserId().getId());
        signInDTO.setToken(token);
        return signInDTO;
    }
}
