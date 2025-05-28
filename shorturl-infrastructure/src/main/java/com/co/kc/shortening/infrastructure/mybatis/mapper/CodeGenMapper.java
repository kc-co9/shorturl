package com.co.kc.shortening.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.co.kc.shortening.infrastructure.mybatis.entity.CodeGen;
import org.apache.ibatis.annotations.Mapper;

/**
 * key生成器(KeyGen)表数据库访问层
 *
 * @author makejava
 * @since 2025-03-27 11:21:19
 */
@Mapper
public interface CodeGenMapper extends BaseMapper<CodeGen> {
}

