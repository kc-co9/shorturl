package com.co.kc.shortening.user.domain.model;

import com.co.kc.shortening.shared.domain.model.Identification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 权限-实体
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Permission extends Identification {
    private final PermissionId permissionId;
    private PermissionValue permissionValue;
    private PermissionDesc permissionDesc;

    public Permission(PermissionId permissionId, PermissionValue permissionValue, PermissionDesc permissionDesc) {
        this.permissionId = permissionId;
        this.permissionDesc = permissionDesc;
        this.permissionValue = permissionValue;
    }

    public void changePermission(PermissionValue permissionValue) {
        this.permissionValue = permissionValue;
    }

    public void changeDescription(PermissionDesc permissionDesc) {
        this.permissionDesc = permissionDesc;
    }
}
