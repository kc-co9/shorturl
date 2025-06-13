package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

/**
 * @author kc
 */
class PermissionMemoryRepositoryTests {

    private PermissionRepository permissionRepository;

    @BeforeEach
    public void initRepository() {
        permissionRepository = new PermissionMemoryRepository();
    }

    @Test
    void testFindSavedPermission() {
        Permission newPermission = new Permission(new PermissionId(10L), new PermissionValue("perm:test"), new PermissionDesc("testDesc"));
        permissionRepository.save(newPermission);
        List<Permission> permissionList = permissionRepository.find(Collections.singletonList(new PermissionId(10L)));
        Assertions.assertNotNull(permissionList);
        Assertions.assertFalse(permissionList.isEmpty());
        Assertions.assertEquals(1, permissionList.size());

        Permission savedPermission = permissionList.get(0);
        Assertions.assertEquals(newPermission, savedPermission);
    }
}
