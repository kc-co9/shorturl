package com.co.kc.shortening.application.assembler;

import com.co.kc.shortening.application.model.cqrs.dto.UserDetailDTO;
import com.co.kc.shortening.user.domain.model.User;

/**
 * UserDetailDTO组装器
 *
 * @author kc
 */
public class UserDetailDtoAssembler {
    private UserDetailDtoAssembler() {
    }

    public static UserDetailDTO userToDTO(User user) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setUserId(user.getUserId().getId());
        userDetailDTO.setUserName(user.getName().getName());
        return userDetailDTO;
    }
}
