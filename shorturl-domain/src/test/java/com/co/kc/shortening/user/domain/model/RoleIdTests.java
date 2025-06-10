package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class RoleIdTests {
    @Test
    public void testCreateNullRoleId() {
        try {
            new RoleId(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("id is null", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateLessThanZeroRoleId() {
        try {
            new RoleId(-1L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("id is less than or equal to 0", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEqualToZeroRoleId() {
        try {
            new RoleId(0L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("id is less than or equal to 0", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateRoleIdSuccessfully() {
        RoleId roleId = new RoleId(10L);
        Assert.assertEquals(10L, roleId.getId().longValue());
    }

    @Test
    public void testSameRoleIdIsEqual() {
        RoleId roleId1 = new RoleId(10L);
        RoleId roleId2 = new RoleId(10L);
        Assert.assertEquals(roleId1, roleId2);
    }

    @Test
    public void testDifferentRoleIdIsNotEqual() {
        RoleId roleId1 = new RoleId(10L);
        RoleId roleId2 = new RoleId(20L);
        Assert.assertNotEquals(roleId1, roleId2);
    }
}
