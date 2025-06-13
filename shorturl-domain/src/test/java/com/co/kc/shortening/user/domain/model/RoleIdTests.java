package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class RoleIdTests {
    @Test
    void testCreateNullRoleId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new RoleId(null));
        Assertions.assertEquals("id is null", e.getMessage());
    }

    @Test
    void testCreateLessThanZeroRoleId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new RoleId(-1L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }

    @Test
    void testCreateEqualToZeroRoleId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new RoleId(0L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }

    @Test
    void testCreateRoleIdSuccessfully() {
        RoleId roleId = new RoleId(10L);
        Assertions.assertEquals(10L, roleId.getId().longValue());
    }

    @Test
    void testSameRoleIdIsEqual() {
        RoleId roleId1 = new RoleId(10L);
        RoleId roleId2 = new RoleId(10L);
        Assertions.assertEquals(roleId1, roleId2);
    }

    @Test
    void testDifferentRoleIdIsNotEqual() {
        RoleId roleId1 = new RoleId(10L);
        RoleId roleId2 = new RoleId(20L);
        Assertions.assertNotEquals(roleId1, roleId2);
    }
}
