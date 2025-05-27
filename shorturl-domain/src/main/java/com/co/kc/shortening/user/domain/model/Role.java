package com.co.kc.shortening.user.domain.model;

import com.co.kc.shortening.shared.domain.model.Identification;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * 角色-聚合根
 *
 * @author kc
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class Role extends Identification {
    private final RoleId roleId;
    private RoleName name;
    private List<PermissionId> permissionIds;

    public Role(RoleId roleId, RoleName name, List<PermissionId> permissionIds) {
        this.roleId = roleId;
        this.name = name;
        this.permissionIds = permissionIds;
    }

    public void changeName(RoleName roleName) {
        this.name = roleName;
    }

    public void changePermission(List<PermissionId> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
