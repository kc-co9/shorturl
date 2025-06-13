package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class PermissionDescTests {
    @Test
    void testCreateNullPermissionDesc() {
        PermissionDesc permissionDesc = new PermissionDesc(null);
        Assertions.assertEquals("", permissionDesc.getDesc());
    }

    @Test
    void testCreateEmptyPermissionDesc() {
        PermissionDesc permissionDesc = new PermissionDesc("");
        Assertions.assertEquals("", permissionDesc.getDesc());
    }

    @Test
    void testSamePermissionDescIsEqual() {
        PermissionDesc permissionDesc1 = new PermissionDesc("");
        PermissionDesc permissionDesc2 = new PermissionDesc("");
        Assertions.assertEquals(permissionDesc1, permissionDesc2);

        PermissionDesc permissionDesc3 = new PermissionDesc(null);
        PermissionDesc permissionDesc4 = new PermissionDesc("");
        Assertions.assertEquals(permissionDesc3, permissionDesc4);

        PermissionDesc permissionDesc5 = new PermissionDesc("permission1");
        PermissionDesc permissionDesc6 = new PermissionDesc("permission1");
        Assertions.assertEquals(permissionDesc5, permissionDesc6);
    }

    @Test
    void testDifferentPermissionDescIsNotEqual() {
        PermissionDesc permissionDesc1 = new PermissionDesc("permission1");
        PermissionDesc permissionDesc2 = new PermissionDesc("permission2");
        Assertions.assertNotEquals(permissionDesc1, permissionDesc2);
    }
}
