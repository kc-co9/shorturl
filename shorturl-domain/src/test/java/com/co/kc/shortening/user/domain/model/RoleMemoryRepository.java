package com.co.kc.shortening.user.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kc
 */
public class RoleMemoryRepository implements RoleRepository {
    private final Map<RoleId, Role> memory = new ConcurrentHashMap<>();

    @Override
    public List<Role> find(List<RoleId> roleIds) {
        List<Role> result = new ArrayList<>();
        for (RoleId roleId : roleIds) {
            Role role = memory.get(roleId);
            if (role != null) {
                result.add(role);
            }
        }
        return result;
    }

    @Override
    public void save(Role role) {
        memory.put(role.getRoleId(), role);
    }
}
