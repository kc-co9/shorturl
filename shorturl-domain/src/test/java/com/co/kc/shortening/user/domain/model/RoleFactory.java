package com.co.kc.shortening.user.domain.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RoleFactory {

    public static final Long testRoleId = 10L;
    public static final String testRoleName = "testRole";
    public static final String testRoleChangedName = "testChangedRole";
    public static final List<Long> testPermissionIds = Collections.emptyList();
    public static final List<Long> testChangedPermissionIds = Arrays.asList(1L, 2L);
    public static final List<String> testPermissionValueList = Collections.emptyList();

    public static Role createRole() {
        return new Role(
                getTestRoleId(),
                getTestRoleName(),
                getTestPermissionIds());
    }

    public static RoleId getTestRoleId() {
        return new RoleId(testRoleId);
    }

    public static RoleName getTestRoleName() {
        return new RoleName(testRoleName);
    }

    public static RoleName getTestRoleChangedName() {
        return new RoleName(testRoleChangedName);
    }

    public static List<PermissionId> getTestPermissionIds() {
        return testPermissionIds.stream().map(PermissionId::new).collect(Collectors.toList());
    }

    public static List<PermissionId> getTestChangedPermissionIds() {
        return testChangedPermissionIds.stream().map(PermissionId::new).collect(Collectors.toList());
    }
}
