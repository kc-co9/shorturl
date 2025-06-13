package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class RoleTests {
    private Role role;

    @BeforeEach
    void initRole() {
        role = RoleFactory.createRole();
    }

    @Test
    void testRolePropertiesSucceedToSet() {
        Assertions.assertEquals(RoleFactory.getTestRoleId(), role.getRoleId());
        Assertions.assertEquals(RoleFactory.getTestRoleName(), role.getName());
        Assertions.assertEquals(RoleFactory.getTestPermissionIds(), role.getPermissionIds());
    }

    @Test
    void testRoleChangeName() {
        Assertions.assertEquals(RoleFactory.getTestRoleName(), role.getName());
        role.changeName(RoleFactory.getTestRoleChangedName());
        Assertions.assertEquals(RoleFactory.getTestRoleChangedName(), role.getName());
    }

    @Test
    void testRolePermission() {
        Assertions.assertEquals(RoleFactory.getTestPermissionIds(), role.getPermissionIds());
        role.changePermission(RoleFactory.getTestChangedPermissionIds());
        Assertions.assertEquals(RoleFactory.getTestChangedPermissionIds(), role.getPermissionIds());
    }
}
