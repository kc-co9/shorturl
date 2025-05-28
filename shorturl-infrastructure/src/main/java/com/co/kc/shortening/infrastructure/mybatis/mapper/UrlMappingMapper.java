package com.co.kc.shortening.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.co.kc.shortening.infrastructure.mybatis.entity.UrlMapping;
import org.apache.ibatis.annotations.Mapper;

/**
 * url标识符(UrlKey)表数据库访问层
 *
 * @author makejava
 * @since 2025-03-27 11:21:29
 */
@Mapper
public interface UrlMappingMapper extends BaseMapper<UrlMapping> {
}

