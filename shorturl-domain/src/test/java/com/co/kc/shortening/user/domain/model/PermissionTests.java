package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kc
 */
public class PermissionTests {
    private Permission permission;

    @Before
    public void initPermission() {
        permission = new Permission(
                new PermissionId(10L), new PermissionValue("perm:test"), new PermissionDesc("permission desc"));
    }

    @Test
    public void testCreatePermissionSuccessfully() {
        Assert.assertEquals(new PermissionId(10L), permission.getPermissionId());
        Assert.assertEquals(new PermissionValue("perm:test"), permission.getPermissionValue());
        Assert.assertEquals(new PermissionDesc("permission desc"), permission.getPermissionDesc());
    }

    @Test
    public void testPermissionChangePermissionValue() {
        Assert.assertEquals(new PermissionValue("perm:test"), permission.getPermissionValue());
        permission.changePermission(new PermissionValue("perm:other:test"));
        Assert.assertEquals(new PermissionValue("perm:other:test"), permission.getPermissionValue());
    }

    @Test
    public void testPermissionChangePermissionDesc() {
        Assert.assertEquals(new PermissionDesc("permission desc"), permission.getPermissionDesc());
        permission.changeDescription(new PermissionDesc("permission other desc"));
        Assert.assertEquals(new PermissionDesc("permission other desc"), permission.getPermissionDesc());
    }
}
