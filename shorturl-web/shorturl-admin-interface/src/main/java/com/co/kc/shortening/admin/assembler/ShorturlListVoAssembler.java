package com.co.kc.shortening.admin.assembler;

import com.co.kc.shortening.application.model.cqrs.dto.ShorturlQueryDTO;
import com.co.kc.shortening.admin.model.response.ShorturlListVO;
import com.co.kc.shortening.web.common.constants.enums.ShorturlFacadeStatus;

/**
 * @author kc
 */
public class ShorturlListVoAssembler {
    private ShorturlListVoAssembler() {
    }

    public static ShorturlListVO shorturlQueryDTOToVO(ShorturlQueryDTO shorturlQueryDTO) {
        ShorturlListVO shorturlListVO = new ShorturlListVO();
        shorturlListVO.setShortId(shorturlQueryDTO.getShortId());
        shorturlListVO.setRawLink(shorturlQueryDTO.getRawLink());
        shorturlListVO.setShortLink(shorturlQueryDTO.getShortLink());
        shorturlListVO.setStatus(ShorturlFacadeStatus.convert(shorturlQueryDTO.getStatus()).orElse(null));
        shorturlListVO.setValidTimeStart(shorturlQueryDTO.getValidStart());
        shorturlListVO.setValidTimeEnd(shorturlQueryDTO.getValidEnd());
        return shorturlListVO;
    }
}
