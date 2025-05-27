package com.co.kc.shortening.user.domain.model;

import java.util.List;

/**
 * 角色-资源库
 *
 * @author kc
 */
public interface RoleRepository {
    /**
     * 根据角色ID查询角色
     *
     * @param roleIds 角色ID列表
     * @return 角色列表
     */
    List<Role> find(List<RoleId> roleIds);

    /**
     * 保存角色
     *
     * @param role 角色
     */
    void save(Role role);
}
