package com.co.kc.shorturl.repository.dao;

import com.co.kc.shorturl.repository.entity.UrlBlacklist;
import com.co.kc.shorturl.repository.mapper.UrlBlacklistMapper;
import org.springframework.stereotype.Repository;

/**
 * url黑名单(UrlBlacklist)表服务接口
 *
 * @author makejava
 * @since 2025-03-27 12:03:33
 */
@Repository
public class UrlBlacklistRepository extends BaseRepository<UrlBlacklistMapper, UrlBlacklist> {
}
