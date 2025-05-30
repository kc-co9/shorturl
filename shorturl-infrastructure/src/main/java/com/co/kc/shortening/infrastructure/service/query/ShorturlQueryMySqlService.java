package com.co.kc.shortening.infrastructure.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.ShorturlQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.service.queryservice.ShorturlQueryService;
import com.co.kc.shortening.infrastructure.mybatis.entity.UrlMapping;
import com.co.kc.shortening.infrastructure.mybatis.service.UrlMappingService;
import com.co.kc.shortening.common.utils.FunctionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author kc
 */
public class ShorturlQueryMySqlService implements ShorturlQueryService {
    private final UrlMappingService urlMappingService;

    public ShorturlQueryMySqlService(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @Override
    public PagingResult<ShorturlQueryDTO> queryShorturl(ShorturlQuery query) {
        IPage<UrlMapping> urlMappingPage =
                urlMappingService.page(new Page<>(query.getPageNo(), query.getPageSize()), urlMappingService.getQueryWrapper()
                        .eq(StringUtils.isNotBlank(query.getCode()), UrlMapping::getCode, query.getCode())
                        .eq(Objects.nonNull(query.getStatus()), UrlMapping::getStatus, query.getStatus())
                        .orderByDesc(UrlMapping::getId));
        List<ShorturlQueryDTO> shorturlList = FunctionUtils.mappingList(urlMappingPage.getRecords(), urlMapping -> {
            ShorturlQueryDTO shorturlQueryDTO = new ShorturlQueryDTO();
            shorturlQueryDTO.setShortId(urlMapping.getShortId());
            shorturlQueryDTO.setCode(urlMapping.getCode());
            shorturlQueryDTO.setRawLink(urlMapping.getUrl());
            shorturlQueryDTO.setStatus(urlMapping.getStatus().getCode());
            shorturlQueryDTO.setValidStart(urlMapping.getValidStart());
            shorturlQueryDTO.setValidEnd(urlMapping.getValidEnd());
            return shorturlQueryDTO;
        });
        return PagingResult.<ShorturlQueryDTO>newBuilder()
                .paging(query.getPaging())
                .records(shorturlList)
                .totalCount(urlMappingPage.getTotal())
                .totalPages(urlMappingPage.getPages())
                .build();
    }
}
