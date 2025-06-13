package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class PermissionTests {
    private Permission permission;

    @BeforeEach
    void initPermission() {
        permission = PermissionFactory.createPermission();
    }

    @Test
    void testPermissionPropertiesSucceedToSet() {
        Assertions.assertEquals(PermissionFactory.getTestPermissionId(), permission.getPermissionId());
        Assertions.assertEquals(PermissionFactory.getTestPermissionValue(), permission.getPermissionValue());
        Assertions.assertEquals(PermissionFactory.getTestPermissionDesc(), permission.getPermissionDesc());
    }

    @Test
    void testPermissionChangePermissionValue() {
        Assertions.assertEquals(PermissionFactory.getTestPermissionValue(), permission.getPermissionValue());
        permission.changePermission(PermissionFactory.getTestChangedPermissionValue());
        Assertions.assertEquals(PermissionFactory.getTestChangedPermissionValue(), permission.getPermissionValue());
    }

    @Test
    void testPermissionChangePermissionDesc() {
        Assertions.assertEquals(PermissionFactory.getTestPermissionDesc(), permission.getPermissionDesc());
        permission.changeDescription(PermissionFactory.getTestChangedPermissionDesc());
        Assertions.assertEquals(PermissionFactory.getTestChangedPermissionDesc(), permission.getPermissionDesc());
    }
}
