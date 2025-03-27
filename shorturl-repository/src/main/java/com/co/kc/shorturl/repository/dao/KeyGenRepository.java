package com.co.kc.shorturl.repository.dao;

import com.co.kc.shorturl.repository.entity.KeyGen;
import com.co.kc.shorturl.repository.mapper.KeyGenMapper;
import org.springframework.stereotype.Repository;

/**
 * key生成器(KeyGen)表服务接口
 *
 * @author makejava
 * @since 2025-03-27 11:21:27
 */
@Repository
public class KeyGenRepository extends BaseRepository<KeyGenMapper, KeyGen> {
}
