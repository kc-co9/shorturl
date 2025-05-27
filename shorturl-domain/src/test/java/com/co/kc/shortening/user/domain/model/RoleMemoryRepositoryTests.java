package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * @author kc
 */
public class RoleMemoryRepositoryTests {
    private RoleRepository roleRepository;

    @Before
    public void initRepository() {
        roleRepository = new RoleMemoryRepository();
    }

    @Test
    public void testFindSavedPermission() {
        Role newRole = new Role(new RoleId(10L), new RoleName("testRoleName"), Collections.emptyList());
        roleRepository.save(newRole);
        List<Role> roleList = roleRepository.find(Collections.singletonList(new RoleId(10L)));
        Assert.assertNotNull(roleList);
        Assert.assertFalse(roleList.isEmpty());
        Assert.assertEquals(1, roleList.size());

        Role savedRole = roleList.get(0);
        Assert.assertEquals(newRole, savedRole);
    }
}
