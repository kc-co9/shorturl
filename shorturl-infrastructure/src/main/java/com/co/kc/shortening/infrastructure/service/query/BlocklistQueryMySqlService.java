package com.co.kc.shortening.infrastructure.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.co.kc.shortening.application.model.cqrs.dto.BlocklistQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.BlocklistQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.service.queryservice.BlocklistQueryService;
import com.co.kc.shortening.infrastructure.mybatis.entity.UrlBlocklist;
import com.co.kc.shortening.infrastructure.mybatis.service.UrlBlocklistService;
import com.co.kc.shorturl.common.utils.FunctionUtils;

import java.util.List;

/**
 * @author kc
 */
public class BlocklistQueryMySqlService implements BlocklistQueryService {
    private final UrlBlocklistService urlBlocklistService;

    public BlocklistQueryMySqlService(UrlBlocklistService urlBlocklistService) {
        this.urlBlocklistService = urlBlocklistService;
    }

    @Override
    public PagingResult<BlocklistQueryDTO> queryBlocklist(BlocklistQuery query) {
        IPage<UrlBlocklist> blocklistPage =
                urlBlocklistService.page(new Page<>(query.getPageNo(), query.getPageSize()),
                        urlBlocklistService.getQueryWrapper().orderByDesc(UrlBlocklist::getId));
        List<BlocklistQueryDTO> blocklistList = FunctionUtils.mappingList(blocklistPage.getRecords(), blocklist -> {
            BlocklistQueryDTO blocklistQueryDTO = new BlocklistQueryDTO();
            blocklistQueryDTO.setBlockId(blocklist.getBlockId());
            blocklistQueryDTO.setBlockLink(blocklist.getUrl());
            blocklistQueryDTO.setRemark(blocklist.getRemark());
            blocklistQueryDTO.setStatus(blocklist.getStatus().getCode());
            blocklistQueryDTO.setCreateTime(blocklist.getCreateTime());
            return blocklistQueryDTO;
        });
        return PagingResult.<BlocklistQueryDTO>newBuilder()
                .paging(query.getPaging())
                .records(blocklistList)
                .totalCount(blocklistPage.getTotal())
                .totalPages(blocklistPage.getPages())
                .build();
    }
}
