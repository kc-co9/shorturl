package com.co.kc.shortening.user.service;

import com.co.kc.shortening.user.domain.model.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户领域服务
 *
 * @author kc
 */
public class UserService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public UserService(RoleRepository roleRepository,
                       PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<Role> getRoleList(User user) {
        return roleRepository.find(user.getRoleIds());
    }

    public List<Permission> getPermissionList(User user) {
        List<Role> roleList = roleRepository.find(user.getRoleIds());
        if (CollectionUtils.isEmpty(roleList)) {
            return Collections.emptyList();
        }

        List<PermissionId> permissionIds =
                roleList.stream().map(Role::getPermissionIds).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        return permissionRepository.find(permissionIds);
    }
}
