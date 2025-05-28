package com.co.kc.shortening.infrastructure.repository;

import com.co.kc.shortening.infrastructure.mybatis.entity.Administrator;
import com.co.kc.shortening.infrastructure.mybatis.service.AdministratorService;
import com.co.kc.shortening.user.domain.model.*;

import java.util.Collections;

/**
 * @author kc
 */
public class UserMySqlRepository implements UserRepository {
    private final AdministratorService administratorService;

    public UserMySqlRepository(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @Override
    public User find(UserId userId) {
        Administrator administrator = administratorService.findByAdministratorId(userId.getId()).orElse(null);
        if (administrator == null) {
            return null;
        }

        User user = new User(
                new UserId(administrator.getAdministratorId()),
                new UserEmail(administrator.getEmail()),
                new UserName(administrator.getUsername()),
                new UserPassword(administrator.getPassword()),
                Collections.emptyList());
        user.setId(administrator.getId());
        return user;
    }

    @Override
    public User find(UserEmail email) {
        Administrator administrator = administratorService.findByEmail(email.getEmail()).orElse(null);
        if (administrator == null) {
            return null;
        }

        User user = new User(
                new UserId(administrator.getAdministratorId()),
                new UserEmail(administrator.getEmail()),
                new UserName(administrator.getUsername()),
                new UserPassword(administrator.getPassword()),
                Collections.emptyList());
        user.setId(administrator.getId());
        return user;
    }

    @Override
    public void save(User user) {
        Administrator administrator = new Administrator();
        administrator.setAdministratorId(user.getUserId().getId());
        administrator.setEmail(user.getEmail().getEmail());
        administrator.setUsername(user.getName().getName());
        administrator.setPassword(user.getPassword().getPassword());
        administratorService.save(administrator);
    }

    @Override
    public void remove(User user) {
        administratorService.remove(administratorService.getQueryWrapper()
                .eq(Administrator::getAdministratorId, user.getUserId()));
    }
}
