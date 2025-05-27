package com.co.kc.shortening.infrastructure.mybatis.service;

import com.co.kc.shortening.infrastructure.mybatis.entity.KeyGen;
import com.co.kc.shortening.infrastructure.mybatis.mapper.KeyGenMapper;
import org.springframework.stereotype.Repository;

/**
 * key生成器(KeyGen)表服务接口
 *
 * @author makejava
 * @since 2025-03-27 11:21:27
 */
@Repository
public class KeyGenService extends BaseService<KeyGenMapper, KeyGen> {
}
