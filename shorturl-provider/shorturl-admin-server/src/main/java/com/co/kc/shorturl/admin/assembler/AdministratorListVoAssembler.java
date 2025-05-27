package com.co.kc.shorturl.admin.assembler;

import com.co.kc.shortening.application.model.cqrs.dto.UserQueryDTO;
import com.co.kc.shorturl.admin.model.dto.response.AdministratorListVO;

/**
 * @author kc
 */
public class AdministratorListVoAssembler {
    private AdministratorListVoAssembler() {
    }

    public static AdministratorListVO userQueryDTOToVO(UserQueryDTO userQueryDTO) {
        AdministratorListVO administratorListVO = new AdministratorListVO();
        administratorListVO.setUserId(userQueryDTO.getUserId());
        administratorListVO.setEmail(userQueryDTO.getEmail());
        administratorListVO.setUsername(userQueryDTO.getUsername());
        administratorListVO.setCreateTime(userQueryDTO.getCreateTime());
        return administratorListVO;
    }
}
