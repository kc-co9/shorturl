package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author kc
 */
public class RoleTests {
    private Role role;

    @Before
    public void initRole() {
        role = new Role(new RoleId(10L), new RoleName("testRole"), Collections.emptyList());
    }

    @Test
    public void testCreateRoleSuccessfully() {
        Assert.assertEquals(new RoleId(10L), role.getRoleId());
        Assert.assertEquals(new RoleName("testRole"), role.getName());
        Assert.assertEquals(Collections.emptyList(), role.getPermissionIds());
    }

    @Test
    public void testRoleChangeName() {
        Assert.assertEquals(new RoleName("testRole"), role.getName());
        role.changeName(new RoleName("testChangeRole"));
        Assert.assertEquals(new RoleName("testChangeRole"), role.getName());
    }

    @Test
    public void testRolePermission() {
        Assert.assertEquals(Collections.emptyList(), role.getPermissionIds());
        role.changePermission(Arrays.asList(new PermissionId(1L), new PermissionId(2L)));
        Assert.assertEquals(Arrays.asList(new PermissionId(1L), new PermissionId(2L)), role.getPermissionIds());
    }
}
