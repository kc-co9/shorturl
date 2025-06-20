package com.co.kc.shortening.infrastructure.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.co.kc.shortening.application.model.cqrs.dto.BlocklistQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.BlocklistQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.service.query.BlocklistQueryService;
import com.co.kc.shortening.infrastructure.mybatis.entity.UrlBlocklist;
import com.co.kc.shortening.infrastructure.mybatis.enums.UrlBlocklistStatus;
import com.co.kc.shortening.infrastructure.mybatis.service.UrlBlocklistService;
import com.co.kc.shortening.common.utils.FunctionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author kc
 */
public class BlocklistMySqlQueryService implements BlocklistQueryService {
    private final UrlBlocklistService urlBlocklistService;

    public BlocklistMySqlQueryService(UrlBlocklistService urlBlocklistService) {
        this.urlBlocklistService = urlBlocklistService;
    }

    @Override
    public PagingResult<BlocklistQueryDTO> queryBlocklist(BlocklistQuery query) {
        IPage<UrlBlocklist> blocklistPage = urlBlocklistService.page(new Page<>(query.getPageNo(), query.getPageSize()), urlBlocklistService.getQueryWrapper()
                .eq(Objects.nonNull(query.getBlockId()), UrlBlocklist::getBlockId, query.getBlockId())
                .eq(Objects.nonNull(query.getStatus()), UrlBlocklist::getStatus, UrlBlocklistStatus.convert(query.getStatus()).orElse(null))
                .orderByDesc(UrlBlocklist::getId));
        List<BlocklistQueryDTO> blocklistList = FunctionUtils.mappingList(blocklistPage.getRecords(), blocklist -> {
            BlocklistQueryDTO blocklistQueryDTO = new BlocklistQueryDTO();
            blocklistQueryDTO.setBlockId(blocklist.getBlockId());
            blocklistQueryDTO.setBlockLink(blocklist.getUrl());
            blocklistQueryDTO.setRemark(blocklist.getRemark());
            blocklistQueryDTO.setStatus(UrlBlocklistStatus.convert(blocklist.getStatus()).get());
            blocklistQueryDTO.setCreateTime(blocklist.getCreateTime());
            return blocklistQueryDTO;
        });
        return PagingResult.<BlocklistQueryDTO>newBuilder()
                .paging(query.getPaging())
                .records(blocklistList)
                .totalCount(blocklistPage.getTotal())
                .build();
    }
}
