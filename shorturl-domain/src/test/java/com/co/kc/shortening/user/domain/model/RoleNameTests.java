package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class RoleNameTests {
    @Test
    public void testCreateNullRoleName() {
        try {
            new RoleName(null);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("name is null or empty", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEmptyRoleName() {
        try {
            new RoleName("");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("name is null or empty", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateRoleNameSuccessfully() {
        RoleName roleName = new RoleName("testRoleName");
        Assert.assertEquals("testRoleName", roleName.getName());
    }

    @Test
    public void testSameRoleNameIsEqual() {
        RoleName roleName1 = new RoleName("testRoleName");
        RoleName roleName2 = new RoleName("testRoleName");
        Assert.assertEquals(roleName1, roleName2);
    }

    @Test
    public void testDifferentRoleNameIsNotEqual() {
        RoleName roleName1 = new RoleName("testRoleName1");
        RoleName roleName2 = new RoleName("testRoleName2");
        Assert.assertNotEquals(roleName1, roleName2);
    }
}
