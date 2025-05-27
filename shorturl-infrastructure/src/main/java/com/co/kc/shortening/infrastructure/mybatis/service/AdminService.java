package com.co.kc.shortening.infrastructure.mybatis.service;

import com.co.kc.shortening.infrastructure.mybatis.entity.Administrator;
import com.co.kc.shortening.infrastructure.mybatis.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 管理者(Administrator)表服务接口
 *
 * @author makejava
 * @since 2025-04-02 10:45:47
 */
@Service
public class AdminService extends BaseService<AdminMapper, Administrator> {

    public Optional<Administrator> findById(long id) {
        return getFirst(this.getQueryWrapper().eq(Administrator::getId, id));
    }

    public Optional<Administrator> findByAccount(String account) {
        return getFirst(this.getQueryWrapper().eq(Administrator::getAccount, account));
    }
}
