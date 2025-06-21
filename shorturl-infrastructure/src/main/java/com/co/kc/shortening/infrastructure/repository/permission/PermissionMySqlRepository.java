package com.co.kc.shortening.infrastructure.repository.permission;

import com.co.kc.shortening.user.domain.model.Permission;
import com.co.kc.shortening.user.domain.model.PermissionId;
import com.co.kc.shortening.user.domain.model.PermissionRepository;

import java.util.Collections;
import java.util.List;

/**
 * @author kc
 */
public class PermissionMySqlRepository implements PermissionRepository {
    @Override
    public List<Permission> find(List<PermissionId> permissionIds) {
        return Collections.emptyList();
    }

    @Override
    public void save(Permission permission) {
    }
}
