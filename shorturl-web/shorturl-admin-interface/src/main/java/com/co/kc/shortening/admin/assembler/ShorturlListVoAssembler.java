package com.co.kc.shortening.admin.assembler;

import com.co.kc.shortening.application.model.cqrs.dto.ShorturlQueryDTO;
import com.co.kc.shortening.admin.model.dto.response.ShorturlListVO;

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
        shorturlListVO.setShortLink(shorturlQueryDTO.getCode());
        shorturlListVO.setStatus(shorturlQueryDTO.getStatus());
        shorturlListVO.setValidTimeStart(shorturlQueryDTO.getValidStart());
        shorturlListVO.setValidTimeEnd(shorturlQueryDTO.getValidEnd());
        return shorturlListVO;
    }
}
