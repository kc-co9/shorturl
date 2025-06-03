package com.co.kc.shortening.user.domain.model;

public class PermissionFactory {

    public static final Long testPermissionId = 5L;
    public static final String testPermissionValue = "test:perm";
    public static final String testChangedPermissionValue = "test:changed:perm";
    public static final String testPermissionDesc = "perm desc";
    public static final String testChangedPermissionDesc = "perm changed desc";

    public static Permission createPermission() {
        return new Permission(
                getTestPermissionId(),
                getTestPermissionValue(),
                getTestPermissionDesc());
    }

    public static PermissionId getTestPermissionId() {
        return new PermissionId(testPermissionId);
    }

    public static PermissionValue getTestPermissionValue() {
        return new PermissionValue(testPermissionValue);
    }

    public static PermissionValue getTestChangedPermissionValue() {
        return new PermissionValue(testChangedPermissionValue);
    }

    public static PermissionDesc getTestPermissionDesc() {
        return new PermissionDesc(testPermissionDesc);
    }

    public static PermissionDesc getTestChangedPermissionDesc() {
        return new PermissionDesc(testChangedPermissionDesc);
    }
}
