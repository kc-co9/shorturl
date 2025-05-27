package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class PermissionDescTests {
    @Test
    public void testCreateNullPermissionDesc() {
        PermissionDesc permissionDesc = new PermissionDesc(null);
        Assert.assertEquals("", permissionDesc.getDesc());
    }

    @Test
    public void testCreateEmptyPermissionDesc() {
        PermissionDesc permissionDesc = new PermissionDesc("");
        Assert.assertEquals("", permissionDesc.getDesc());
    }

    @Test
    public void testSamePermissionDescIsEqual() {
        PermissionDesc permissionDesc1 = new PermissionDesc("");
        PermissionDesc permissionDesc2 = new PermissionDesc("");
        Assert.assertEquals(permissionDesc1, permissionDesc2);

        PermissionDesc permissionDesc3 = new PermissionDesc(null);
        PermissionDesc permissionDesc4 = new PermissionDesc("");
        Assert.assertEquals(permissionDesc3, permissionDesc4);

        PermissionDesc permissionDesc5 = new PermissionDesc("permission1");
        PermissionDesc permissionDesc6 = new PermissionDesc("permission1");
        Assert.assertEquals(permissionDesc5, permissionDesc6);
    }

    @Test
    public void testDifferentPermissionDescIsNotEqual() {
        PermissionDesc permissionDesc1 = new PermissionDesc("permission1");
        PermissionDesc permissionDesc2 = new PermissionDesc("permission2");
        Assert.assertNotEquals(permissionDesc1, permissionDesc2);
    }
}
