package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class PermissionIdTests {
    @Test
    public void testCreateNullPermissionId() {
        try {
            new PermissionId(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("id is null", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateLessThanZeroPermissionId() {
        try {
            new PermissionId(-1L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("id is less than or equal to 0", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEqualToZeroPermissionId() {
        try {
            new PermissionId(0L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("id is less than or equal to 0", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreatePermissionIdSuccessfully() {
        PermissionId permissionId = new PermissionId(10L);
        Assert.assertEquals(10L, permissionId.getId().longValue());
    }

    @Test
    public void testSamePermissionIdIsEqual() {
        PermissionId permissionId1 = new PermissionId(10L);
        PermissionId permissionId2 = new PermissionId(10L);
        Assert.assertEquals(permissionId1, permissionId2);
    }

    @Test
    public void testDifferentPermissionIdIsNotEqual() {
        PermissionId permissionId1 = new PermissionId(10L);
        PermissionId permissionId2 = new PermissionId(20L);
        Assert.assertNotEquals(permissionId1, permissionId2);
    }
}
