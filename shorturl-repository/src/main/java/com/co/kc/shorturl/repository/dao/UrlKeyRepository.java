package com.co.kc.shorturl.repository.dao;

import com.co.kc.shorturl.repository.po.entity.UrlKey;
import com.co.kc.shorturl.repository.mapper.UrlKeyMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * url标识符(UrlKey)表服务接口
 *
 * @author makejava
 * @since 2025-03-27 11:21:29
 */
@Repository
public class UrlKeyRepository extends BaseRepository<UrlKeyMapper, UrlKey> {
    public Optional<UrlKey> findUrlKeyByHash(String hash, String url) {
        return this.getFirst(this.getQueryWrapper()
                .eq(UrlKey::getHash, hash)
                .eq(UrlKey::getUrl, url));
    }
}
