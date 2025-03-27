package com.co.kc.shorturl.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.co.kc.shorturl.repository.entity.KeyGen;
import org.apache.ibatis.annotations.Mapper;

/**
 * key生成器(KeyGen)表数据库访问层
 *
 * @author makejava
 * @since 2025-03-27 11:21:19
 */
@Mapper
public interface KeyGenMapper extends BaseMapper<KeyGen> {
}

