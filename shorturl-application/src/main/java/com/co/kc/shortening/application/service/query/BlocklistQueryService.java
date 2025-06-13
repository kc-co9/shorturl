package com.co.kc.shortening.application.service.query;

import com.co.kc.shortening.application.model.cqrs.dto.BlocklistQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.BlocklistQuery;
import com.co.kc.shortening.application.model.io.PagingResult;

/**
 * 黑名单-查询服务
 *
 * @author kc
 */
public interface BlocklistQueryService {
    /**
     * 查询黑名单
     *
     * @param query 查询参数
     * @return BlocklistDTO
     */
    PagingResult<BlocklistQueryDTO> queryBlocklist(BlocklistQuery query);
}
