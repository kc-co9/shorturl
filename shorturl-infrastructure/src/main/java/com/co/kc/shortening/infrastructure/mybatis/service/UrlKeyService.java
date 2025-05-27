package com.co.kc.shortening.infrastructure.mybatis.service;

import com.co.kc.shortening.infrastructure.mybatis.entity.UrlKey;
import com.co.kc.shortening.infrastructure.mybatis.mapper.UrlKeyMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * url标识符(UrlKey)表服务接口
 *
 * @author makejava
 * @since 2025-03-27 11:21:29
 */
@Repository
public class UrlKeyService extends BaseService<UrlKeyMapper, UrlKey> {
    public Optional<UrlKey> findByKey(String key) {
        return this.getFirst(this.getQueryWrapper()
                .eq(UrlKey::getKey, key));
    }

    public Optional<UrlKey> findByHash(String hash, String url) {
        return this.getFirst(this.getQueryWrapper()
                .eq(UrlKey::getHash, hash)
                .eq(UrlKey::getUrl, url));
    }


}
