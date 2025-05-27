package com.co.kc.shortening.infrastructure.mybatis.service;

import com.co.kc.shortening.infrastructure.mybatis.entity.UrlBlocklist;
import com.co.kc.shortening.infrastructure.mybatis.mapper.UrlBlocklistMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * url黑名单(UrlBlocklist)表服务接口
 *
 * @author makejava
 * @since 2025-03-27 12:03:33
 */
@Repository
public class UrlBlocklistService extends BaseService<UrlBlocklistMapper, UrlBlocklist> {
    public Optional<UrlBlocklist> findByHash(String hash, String url) {
        return this.getFirst(this.getQueryWrapper()
                .eq(UrlBlocklist::getHash, hash)
                .eq(UrlBlocklist::getUrl, url));
    }
}
