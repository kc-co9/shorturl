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

    public static SignInDTO userToDTO(User user) {
        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setUserId(user.getUserId().getId());
        signInDTO.setEmail(user.getEmail().getEmail());
        signInDTO.setUsername(user.getName().getName());
        return signInDTO;
    }
}
