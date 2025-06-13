package com.co.kc.shortening.infrastructure.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.ShorturlQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.provider.ShortDomainProvider;
import com.co.kc.shortening.application.service.query.ShorturlQueryService;
import com.co.kc.shortening.infrastructure.mybatis.entity.UrlMapping;
import com.co.kc.shortening.infrastructure.mybatis.enums.UrlMappingStatus;
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
    private final ShortDomainProvider shortDomainProvider;

    public ShorturlQueryMySqlService(UrlMappingService urlMappingService, ShortDomainProvider shortDomainProvider) {
        this.urlMappingService = urlMappingService;
        this.shortDomainProvider = shortDomainProvider;
    }

    @Override
    public PagingResult<ShorturlQueryDTO> queryShorturl(ShorturlQuery query) {
        IPage<UrlMapping> urlMappingPage =
                urlMappingService.page(new Page<>(query.getPageNo(), query.getPageSize()), urlMappingService.getQueryWrapper()
                        .eq(Objects.nonNull(query.getShortId()), UrlMapping::getShortId, query.getShortId())
                        .eq(StringUtils.isNotBlank(query.getCode()), UrlMapping::getCode, query.getCode())
                        .eq(Objects.nonNull(query.getStatus()), UrlMapping::getStatus, UrlMappingStatus.convert(query.getStatus()).orElse(null))
                        .orderByDesc(UrlMapping::getId));
        List<ShorturlQueryDTO> shorturlList = FunctionUtils.mappingList(urlMappingPage.getRecords(), urlMapping -> {
            ShorturlQueryDTO shorturlQueryDTO = new ShorturlQueryDTO();
            shorturlQueryDTO.setShortId(urlMapping.getShortId());
            shorturlQueryDTO.setCode(urlMapping.getCode());
            shorturlQueryDTO.setRawLink(urlMapping.getUrl());
            shorturlQueryDTO.setShortLink(shortDomainProvider.getDomain() + "/" + urlMapping.getCode());
            shorturlQueryDTO.setStatus(UrlMappingStatus.convert(urlMapping.getStatus()).get());
            shorturlQueryDTO.setValidStart(urlMapping.getValidStart());
            shorturlQueryDTO.setValidEnd(urlMapping.getValidEnd());
            return shorturlQueryDTO;
        });
        return PagingResult.<ShorturlQueryDTO>newBuilder()
                .paging(query.getPaging())
                .records(shorturlList)
                .totalCount(urlMappingPage.getTotal())
                .build();
    }
}
