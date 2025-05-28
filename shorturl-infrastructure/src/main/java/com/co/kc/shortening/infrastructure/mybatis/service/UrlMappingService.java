package com.co.kc.shortening.infrastructure.mybatis.service;

import com.co.kc.shortening.infrastructure.mybatis.entity.UrlMapping;
import com.co.kc.shortening.infrastructure.mybatis.mapper.UrlMappingMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * url标识符(UrlKey)表服务接口
 *
 * @author makejava
 * @since 2025-03-27 11:21:29
 */
@Service
public class UrlMappingService extends BaseService<UrlMappingMapper, UrlMapping> {
    public Optional<UrlMapping> findByShortId(Long shortId) {
        return this.getFirst(this.getQueryWrapper()
                .eq(UrlMapping::getShortId, shortId));
    }

    public Optional<UrlMapping> findByCode(String code) {
        return this.getFirst(this.getQueryWrapper()
                .eq(UrlMapping::getCode, code));
    }

    public Optional<UrlMapping> findByHash(String hash, String url) {
        return this.getFirst(this.getQueryWrapper()
                .eq(UrlMapping::getHash, hash)
                .eq(UrlMapping::getUrl, url));
    }
}
