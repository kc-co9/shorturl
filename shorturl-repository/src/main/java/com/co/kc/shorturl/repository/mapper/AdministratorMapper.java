package com.co.kc.shorturl.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.co.kc.shorturl.repository.po.entity.Administrator;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理者(Administrator)表数据库访问层
 *
 * @author makejava
 * @since 2025-04-02 10:45:44
 */
@Mapper
public interface AdministratorMapper extends BaseMapper<Administrator> {
}

