package com.co.kc.shorturl.admin.assembler;

import com.co.kc.shortening.application.model.cqrs.dto.BlocklistQueryDTO;
import com.co.kc.shorturl.admin.model.dto.response.BlocklistListVO;

/**
 * @author kc
 */
public class BlocklistListVoAssembler {
    private BlocklistListVoAssembler() {
    }

    public static BlocklistListVO blocklistQueryDTOToVO(BlocklistQueryDTO blocklistQueryDTO) {
        BlocklistListVO blocklistListVO = new BlocklistListVO();
        blocklistListVO.setBlockId(blocklistQueryDTO.getBlockId());
        blocklistListVO.setBlockLink(blocklistQueryDTO.getBlockLink());
        blocklistListVO.setRemark(blocklistQueryDTO.getRemark());
        blocklistListVO.setCreateTime(blocklistQueryDTO.getCreateTime());
        return blocklistListVO;
    }
}
