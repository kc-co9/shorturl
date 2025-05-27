package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class PermissionValueTests {
    @Test
    public void testCreateNullPermissionValue() {
        try {
            new PermissionValue(null);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("value is null or empty", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEmptyPermissionValue() {
        try {
            new PermissionValue("");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("value is null or empty", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreatePermissionValueSuccessfully() {
        PermissionValue permissionValue = new PermissionValue("testValue");
        Assert.assertEquals("testValue", permissionValue.getValue());
    }

    @Test
    public void testSamePermissionValueIsEqual() {
        PermissionValue permissionValue1 = new PermissionValue("testValue");
        PermissionValue permissionValue2 = new PermissionValue("testValue");
        Assert.assertEquals(permissionValue1, permissionValue2);
    }

    @Test
    public void testDifferentPermissionValueIsNotEqual() {
        PermissionValue permissionValue1 = new PermissionValue("testValue1");
        PermissionValue permissionValue2 = new PermissionValue("testValue2");
        Assert.assertNotEquals(permissionValue1, permissionValue2);
    }
}
