package com.co.kc.shortening.admin.assembler;

import com.co.kc.shortening.application.model.cqrs.dto.BlocklistQueryDTO;
import com.co.kc.shortening.admin.model.response.BlocklistListVO;
import com.co.kc.shortening.web.common.constants.enums.BlockFacadeStatus;

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
        blocklistListVO.setStatus(BlockFacadeStatus.convert(blocklistQueryDTO.getStatus()).orElse(null));
        blocklistListVO.setCreateTime(blocklistQueryDTO.getCreateTime());
        return blocklistListVO;
    }
}
