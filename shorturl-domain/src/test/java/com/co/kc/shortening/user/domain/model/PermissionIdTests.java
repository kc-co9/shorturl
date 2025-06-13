package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class PermissionIdTests {
    @Test
    void testCreateNullPermissionId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new PermissionId(null));
        Assertions.assertEquals("id is null", e.getMessage());
    }

    @Test
    void testCreateLessThanZeroPermissionId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new PermissionId(-1L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }

    @Test
    void testCreateEqualToZeroPermissionId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new PermissionId(0L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }

    @Test
    void testCreatePermissionIdSuccessfully() {
        PermissionId permissionId = new PermissionId(10L);
        Assertions.assertEquals(10L, permissionId.getId().longValue());
    }

    @Test
    void testSamePermissionIdIsEqual() {
        PermissionId permissionId1 = new PermissionId(10L);
        PermissionId permissionId2 = new PermissionId(10L);
        Assertions.assertEquals(permissionId1, permissionId2);
    }

    @Test
    void testDifferentPermissionIdIsNotEqual() {
        PermissionId permissionId1 = new PermissionId(10L);
        PermissionId permissionId2 = new PermissionId(20L);
        Assertions.assertNotEquals(permissionId1, permissionId2);
    }
}
