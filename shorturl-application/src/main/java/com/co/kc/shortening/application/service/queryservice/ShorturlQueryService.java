package com.co.kc.shortening.application.service.queryservice;

import com.co.kc.shortening.application.model.cqrs.dto.ShorturlQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.ShorturlQuery;
import com.co.kc.shortening.application.model.io.PagingResult;

/**
 * 短链-查询服务
 *
 * @author kc
 */
public interface ShorturlQueryService {
    /**
     * 查询短链
     *
     * @param query 查询参数
     * @return ShorturlDTO
     */
    PagingResult<ShorturlQueryDTO> queryShorturl(ShorturlQuery query);
}
