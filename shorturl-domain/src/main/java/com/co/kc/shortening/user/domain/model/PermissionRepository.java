package com.co.kc.shortening.user.domain.model;

import java.util.List;

/**
 * 权限-资源库
 *
 * @author kc
 */
public interface PermissionRepository {
    /**
     * 根据权限ID查询权限
     *
     * @param permissionIds 权限ID列表
     * @return 权限列表
     */
    List<Permission> find(List<PermissionId> permissionIds);

    /**
     * 保存权限
     *
     * @param permission 权限
     */
    void save(Permission permission);
}
