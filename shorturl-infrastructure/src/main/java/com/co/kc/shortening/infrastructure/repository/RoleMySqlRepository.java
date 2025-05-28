package com.co.kc.shortening.infrastructure.repository;

import com.co.kc.shortening.user.domain.model.Role;
import com.co.kc.shortening.user.domain.model.RoleId;
import com.co.kc.shortening.user.domain.model.RoleRepository;

import java.util.Collections;
import java.util.List;

/**
 * @author kc
 */
public class RoleMySqlRepository implements RoleRepository {
    @Override
    public List<Role> find(List<RoleId> roleIds) {
        return Collections.emptyList();
    }

    @Override
    public void save(Role role) {

    }
}
