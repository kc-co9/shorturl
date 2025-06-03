package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kc
 */
public class RoleTests {
    private Role role;

    @Before
    public void initRole() {
        role = RoleFactory.createRole();
    }

    @Test
    public void testRolePropertiesSucceedToSet() {
        Assert.assertEquals(RoleFactory.getTestRoleId(), role.getRoleId());
        Assert.assertEquals(RoleFactory.getTestRoleName(), role.getName());
        Assert.assertEquals(RoleFactory.getTestPermissionIds(), role.getPermissionIds());
    }

    @Test
    public void testRoleChangeName() {
        Assert.assertEquals(RoleFactory.getTestRoleName(), role.getName());
        role.changeName(RoleFactory.getTestRoleChangedName());
        Assert.assertEquals(RoleFactory.getTestRoleChangedName(), role.getName());
    }

    @Test
    public void testRolePermission() {
        Assert.assertEquals(RoleFactory.getTestPermissionIds(), role.getPermissionIds());
        role.changePermission(RoleFactory.getTestChangedPermissionIds());
        Assert.assertEquals(RoleFactory.getTestChangedPermissionIds(), role.getPermissionIds());
    }
}
