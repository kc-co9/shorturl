package com.co.kc.shorturl.repository.dao;

import com.co.kc.shorturl.repository.entity.KeyGen;
import com.co.kc.shorturl.repository.mapper.KeyGenMapper;
import org.springframework.stereotype.Repository;

/**
 * url标识符(UrlKey)表服务接口
 *
 * @author makejava
 * @since 2025-03-27 11:21:29
 */
@Repository
public class UrlKeyRepository extends BaseRepository<KeyGenMapper, KeyGen> {
}
