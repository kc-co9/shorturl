package com.co.kc.shorturl.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.co.kc.shorturl.repository.po.entity.UrlBlocklist;
import org.apache.ibatis.annotations.Mapper;

/**
 * url黑名单(UrlBlocklist)表数据库访问层
 *
 * @author makejava
 * @since 2025-03-27 12:03:30
 */
@Mapper
public interface UrlBlocklistMapper extends BaseMapper<UrlBlocklist> {

}

