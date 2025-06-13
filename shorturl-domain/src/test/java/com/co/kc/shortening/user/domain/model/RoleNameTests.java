package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class RoleNameTests {
    @Test
    void testCreateNullRoleName() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new RoleName(null));
        Assertions.assertEquals("name is null or empty", ex.getMessage());
    }

    @Test
    void testCreateEmptyRoleName() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new RoleName(""));
        Assertions.assertEquals("name is null or empty", ex.getMessage());
    }

    @Test
    void testCreateRoleNameSuccessfully() {
        RoleName roleName = new RoleName("testRoleName");
        Assertions.assertEquals("testRoleName", roleName.getName());
    }

    @Test
    void testSameRoleNameIsEqual() {
        RoleName roleName1 = new RoleName("testRoleName");
        RoleName roleName2 = new RoleName("testRoleName");
        Assertions.assertEquals(roleName1, roleName2);
    }

    @Test
    void testDifferentRoleNameIsNotEqual() {
        RoleName roleName1 = new RoleName("testRoleName1");
        RoleName roleName2 = new RoleName("testRoleName2");
        Assertions.assertNotEquals(roleName1, roleName2);
    }
}
