package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * @author kc
 */
public class PermissionMemoryRepositoryTests {

    private PermissionRepository permissionRepository;

    @Before
    public void initRepository() {
        permissionRepository = new PermissionMemoryRepository();
    }

    @Test
    public void testFindSavedPermission() {
        Permission newPermission = new Permission(new PermissionId(10L), new PermissionValue("perm:test"), new PermissionDesc("testDesc"));
        permissionRepository.save(newPermission);
        List<Permission> permissionList = permissionRepository.find(Collections.singletonList(new PermissionId(10L)));
        Assert.assertNotNull(permissionList);
        Assert.assertFalse(permissionList.isEmpty());
        Assert.assertEquals(1, permissionList.size());

        Permission savedPermission = permissionList.get(0);
        Assert.assertEquals(newPermission, savedPermission);
    }
}
