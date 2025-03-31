package com.co.kc.shorturl.repository.dao;

import com.co.kc.shorturl.repository.po.entity.UrlBlacklist;
import com.co.kc.shorturl.repository.mapper.UrlBlacklistMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * url黑名单(UrlBlacklist)表服务接口
 *
 * @author makejava
 * @since 2025-03-27 12:03:33
 */
@Repository
public class UrlBlacklistRepository extends BaseRepository<UrlBlacklistMapper, UrlBlacklist> {
    public Optional<UrlBlacklist> findBlacklistByHash(String hash, String url) {
        return this.getFirst(this.getQueryWrapper()
                .eq(UrlBlacklist::getHash, hash)
                .eq(UrlBlacklist::getUrl, url));
    }
}
