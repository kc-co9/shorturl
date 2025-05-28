package com.co.kc.shortening.infrastructure.mybatis.service;

import com.co.kc.shortening.infrastructure.mybatis.entity.Administrator;
import com.co.kc.shortening.infrastructure.mybatis.mapper.AdministratorMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 管理者(Administrator)表服务接口
 *
 * @author makejava
 * @since 2025-04-02 10:45:47
 */
@Service
public class AdministratorService extends BaseService<AdministratorMapper, Administrator> {

    public Optional<Administrator> findByAdministratorId(Long administratorId) {
        return getFirst(this.getQueryWrapper().eq(Administrator::getAdministratorId, administratorId));
    }

    public Optional<Administrator> findByEmail(String email) {
        return getFirst(this.getQueryWrapper().eq(Administrator::getEmail, email));
    }
}
