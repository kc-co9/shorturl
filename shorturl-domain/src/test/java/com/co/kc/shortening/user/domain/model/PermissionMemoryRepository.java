package com.co.kc.shortening.user.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kc
 */
public class PermissionMemoryRepository implements PermissionRepository {
    private final Map<PermissionId, Permission> memory = new ConcurrentHashMap<>();

    @Override
    public List<Permission> find(List<PermissionId> permissionIds) {
        List<Permission> result = new ArrayList<>();
        for (PermissionId permissionId : permissionIds) {
            Permission permission = memory.get(permissionId);
            if (permission != null) {
                result.add(permission);
            }
        }
        return result;
    }

    @Override
    public void save(Permission permission) {
        memory.put(permission.getPermissionId(), permission);
    }
}
