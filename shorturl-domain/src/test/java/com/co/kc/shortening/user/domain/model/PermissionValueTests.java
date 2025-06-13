package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class PermissionValueTests {
    @Test
    void testCreateNullPermissionValue() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new PermissionValue(null));
        Assertions.assertEquals("value is null or empty", ex.getMessage());
    }

    @Test
    void testCreateEmptyPermissionValue() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new PermissionValue(""));
        Assertions.assertEquals("value is null or empty", ex.getMessage());
    }

    @Test
    void testCreatePermissionValueSuccessfully() {
        PermissionValue permissionValue = new PermissionValue("testValue");
        Assertions.assertEquals("testValue", permissionValue.getValue());
    }

    @Test
    void testSamePermissionValueIsEqual() {
        PermissionValue permissionValue1 = new PermissionValue("testValue");
        PermissionValue permissionValue2 = new PermissionValue("testValue");
        Assertions.assertEquals(permissionValue1, permissionValue2);
    }

    @Test
    void testDifferentPermissionValueIsNotEqual() {
        PermissionValue permissionValue1 = new PermissionValue("testValue1");
        PermissionValue permissionValue2 = new PermissionValue("testValue2");
        Assertions.assertNotEquals(permissionValue1, permissionValue2);
    }
}
