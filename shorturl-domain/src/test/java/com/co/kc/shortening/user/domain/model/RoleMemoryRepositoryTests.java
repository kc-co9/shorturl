package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

/**
 * @author kc
 */
class RoleMemoryRepositoryTests {
    private RoleRepository roleRepository;

    @BeforeEach
    void initRepository() {
        roleRepository = new RoleMemoryRepository();
    }

    @Test
    void testFindSavedPermission() {
        Role newRole = new Role(new RoleId(10L), new RoleName("testRoleName"), Collections.emptyList());
        roleRepository.save(newRole);
        List<Role> roleList = roleRepository.find(Collections.singletonList(new RoleId(10L)));
        Assertions.assertNotNull(roleList);
        Assertions.assertFalse(roleList.isEmpty());
        Assertions.assertEquals(1, roleList.size());

        Role savedRole = roleList.get(0);
        Assertions.assertEquals(newRole, savedRole);
    }
}
