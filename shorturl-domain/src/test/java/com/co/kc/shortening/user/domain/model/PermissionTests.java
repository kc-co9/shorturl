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
        permission = PermissionFactory.createPermission();
    }

    @Test
    public void testPermissionPropertiesSucceedToSet() {
        Assert.assertEquals(PermissionFactory.getTestPermissionId(), permission.getPermissionId());
        Assert.assertEquals(PermissionFactory.getTestPermissionValue(), permission.getPermissionValue());
        Assert.assertEquals(PermissionFactory.getTestPermissionDesc(), permission.getPermissionDesc());
    }

    @Test
    public void testPermissionChangePermissionValue() {
        Assert.assertEquals(PermissionFactory.getTestPermissionValue(), permission.getPermissionValue());
        permission.changePermission(PermissionFactory.getTestChangedPermissionValue());
        Assert.assertEquals(PermissionFactory.getTestChangedPermissionValue(), permission.getPermissionValue());
    }

    @Test
    public void testPermissionChangePermissionDesc() {
        Assert.assertEquals(PermissionFactory.getTestPermissionDesc(), permission.getPermissionDesc());
        permission.changeDescription(PermissionFactory.getTestChangedPermissionDesc());
        Assert.assertEquals(PermissionFactory.getTestChangedPermissionDesc(), permission.getPermissionDesc());
    }
}
