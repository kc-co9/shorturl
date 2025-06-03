package com.co.kc.shortening.infrastructure.repository;

import com.co.kc.shortening.infrastructure.mybatis.entity.UrlMapping;
import com.co.kc.shortening.infrastructure.mybatis.enums.UrlMappingStatus;
import com.co.kc.shortening.infrastructure.mybatis.service.UrlMappingService;
import com.co.kc.shortening.shared.domain.model.Link;
import com.co.kc.shortening.shorturl.domain.model.*;

/**
 * 短链资源库MySQL实现
 *
 * @author kc
 */
public class ShorturlMySqlRepository implements ShorturlRepository {
    private final UrlMappingService urlMappingService;

    public ShorturlMySqlRepository(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @Override
    public Shorturl find(ShortId shortId) {
        UrlMapping urlMapping = urlMappingService.findByShortId(shortId.getId()).orElse(null);
        if (urlMapping == null) {
            return null;
        }

        Shorturl shorturl = new Shorturl(
                new ShortId(urlMapping.getShortId()),
                new ShortCode(urlMapping.getCode()),
                new Link(urlMapping.getUrl()),
                UrlMappingStatus.convert(urlMapping.getStatus()),
                new ValidTimeInterval(urlMapping.getValidStart(), urlMapping.getValidEnd()));
        shorturl.setId(urlMapping.getId());
        return shorturl;
    }

    @Override
    public Shorturl find(ShortCode code) {
        UrlMapping urlMapping = urlMappingService.findByCode(code.getCode()).orElse(null);
        if (urlMapping == null) {
            return null;
        }

        Shorturl shorturl = new Shorturl(
                new ShortId(urlMapping.getShortId()),
                new ShortCode(urlMapping.getCode()),
                new Link(urlMapping.getUrl()),
                UrlMappingStatus.convert(urlMapping.getStatus()),
                new ValidTimeInterval(urlMapping.getValidStart(), urlMapping.getValidEnd()));
        shorturl.setId(urlMapping.getId());
        return shorturl;
    }

    @Override
    public void save(Shorturl shorturl) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setShortId(shorturl.getShortId().getId());
        urlMapping.setUrl(shorturl.getRawLink().getUrl());
        urlMapping.setCode(shorturl.getShortCode().getCode());
        urlMapping.setHash(shorturl.getRawLink().getHash());
        urlMapping.setStatus(UrlMappingStatus.convert(shorturl.getStatus()));
        urlMapping.setValidStart(shorturl.getValidTime().getStartTime());
        urlMapping.setValidEnd(shorturl.getValidTime().getEndTime());
        urlMappingService.save(urlMapping);
    }
}
