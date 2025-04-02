package com.co.kc.shorturl.repository.dao;

import com.co.kc.shorturl.repository.mapper.AdministratorMapper;
import com.co.kc.shorturl.repository.po.entity.Administrator;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 管理者(Administrator)表服务接口
 *
 * @author makejava
 * @since 2025-04-02 10:45:47
 */
@Service
public class AdministratorRepository extends BaseRepository<AdministratorMapper, Administrator> {

    public Optional<Administrator> findById(long id) {
        return getFirst(this.getQueryWrapper().eq(Administrator::getId, id));
    }

    public Optional<Administrator> findByAccount(String account) {
        return getFirst(this.getQueryWrapper().eq(Administrator::getAccount, account));
    }
}
