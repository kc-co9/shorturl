package com.co.kc.shortening.user.service;

import com.co.kc.shortening.user.domain.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kc
 */
public class UserServiceTests {

    private UserService userService;

    @Before
    public void initUserService() {
        Permission permission1 = new Permission(new PermissionId(1L), new PermissionValue("perm:1"), new PermissionDesc("权限1"));
        Permission permission2 = new Permission(new PermissionId(2L), new PermissionValue("perm:2"), new PermissionDesc("权限2"));
        Permission permission3 = new Permission(new PermissionId(3L), new PermissionValue("perm:3"), new PermissionDesc("权限3"));
        Permission permission4 = new Permission(new PermissionId(4L), new PermissionValue("perm:4"), new PermissionDesc("权限4"));
        Permission permission5 = new Permission(new PermissionId(5L), new PermissionValue("perm:5"), new PermissionDesc("权限5"));
        Permission permission6 = new Permission(new PermissionId(6L), new PermissionValue("perm:6"), new PermissionDesc("权限6"));

        Role role1 = new Role(new RoleId(1L), new RoleName("role1"), Arrays.asList(new PermissionId(1L), new PermissionId(2L)));
        Role role2 = new Role(new RoleId(2L), new RoleName("role2"), Arrays.asList(new PermissionId(3L), new PermissionId(4L), new PermissionId(5L), new PermissionId(6L)));

        PermissionRepository permissionRepository = new PermissionMemoryRepository();
        permissionRepository.save(permission1);
        permissionRepository.save(permission2);
        permissionRepository.save(permission3);
        permissionRepository.save(permission4);
        permissionRepository.save(permission5);
        permissionRepository.save(permission6);

        RoleRepository roleRepository = new RoleMemoryRepository();
        roleRepository.save(role1);
        roleRepository.save(role2);

        userService = new UserService(roleRepository, permissionRepository);
    }

    @Test
    public void testGetRoleList() {
        User user1 = new User(new UserId(1L), new UserEmail("testName1@test.com"), new UserName("testName1"), new UserPassword("testPassword1"), Collections.singletonList(new RoleId(1L)));
        User user2 = new User(new UserId(2L), new UserEmail("testName2@test.com"), new UserName("testName2"), new UserPassword("testPassword2"), Collections.singletonList(new RoleId(2L)));
        User user3 = new User(new UserId(3L), new UserEmail("testName3@test.com"), new UserName("testName3"), new UserPassword("testPassword3"), Arrays.asList(new RoleId(1L), new RoleId(2L)));
        User user4 = new User(new UserId(4L), new UserEmail("testName4@test.com"), new UserName("testName4"), new UserPassword("testPassword4"), Collections.emptyList());

        List<Role> roleList1 = userService.getRoleList(user1);
        Assert.assertFalse(roleList1.isEmpty());
        Assert.assertEquals(1, roleList1.size());
        Assert.assertEquals(new RoleId(1L), roleList1.get(0).getRoleId());

        List<Role> roleList2 = userService.getRoleList(user2);
        Assert.assertFalse(roleList2.isEmpty());
        Assert.assertEquals(1, roleList2.size());
        Assert.assertEquals(new RoleId(2L), roleList2.get(0).getRoleId());

        List<Role> roleList3 = userService.getRoleList(user3);
        Assert.assertFalse(roleList3.isEmpty());
        Assert.assertEquals(2, roleList3.size());
        List<RoleId> roleIdList = roleList3.stream().map(Role::getRoleId).collect(Collectors.toList());
        Assert.assertTrue(roleIdList.contains(new RoleId(1L)));
        Assert.assertTrue(roleIdList.contains(new RoleId(2L)));

        List<Role> roleList4 = userService.getRoleList(user4);
        Assert.assertTrue(roleList4.isEmpty());
    }

    @Test
    public void testGetPermissionList() {
        User user1 = new User(new UserId(1L), new UserEmail("testName1@test.com"), new UserName("testName1"), new UserPassword("testPassword1"), Collections.singletonList(new RoleId(1L)));
        User user2 = new User(new UserId(2L), new UserEmail("testName2@test.com"), new UserName("testName2"), new UserPassword("testPassword2"), Collections.singletonList(new RoleId(2L)));
        User user3 = new User(new UserId(3L), new UserEmail("testName3@test.com"), new UserName("testName3"), new UserPassword("testPassword3"), Arrays.asList(new RoleId(1L), new RoleId(2L)));
        User user4 = new User(new UserId(4L), new UserEmail("testName4@test.com"), new UserName("testName4"), new UserPassword("testPassword4"), Collections.emptyList());

        List<Permission> permissionList1 = userService.getPermissionList(user1);
        Assert.assertFalse(permissionList1.isEmpty());
        List<PermissionId> permissionIdList1 = permissionList1.stream().map(Permission::getPermissionId).collect(Collectors.toList());
        Assert.assertTrue(permissionIdList1.contains(new PermissionId(1L)));
        Assert.assertTrue(permissionIdList1.contains(new PermissionId(2L)));

        List<Permission> permissionList2 = userService.getPermissionList(user2);
        Assert.assertFalse(permissionList2.isEmpty());
        List<PermissionId> permissionIdList2 = permissionList2.stream().map(Permission::getPermissionId).collect(Collectors.toList());
        Assert.assertTrue(permissionIdList2.contains(new PermissionId(3L)));
        Assert.assertTrue(permissionIdList2.contains(new PermissionId(4L)));
        Assert.assertTrue(permissionIdList2.contains(new PermissionId(5L)));
        Assert.assertTrue(permissionIdList2.contains(new PermissionId(6L)));

        List<Permission> permissionList3 = userService.getPermissionList(user3);
        Assert.assertFalse(permissionList3.isEmpty());
        List<PermissionId> permissionIdList3 = permissionList3.stream().map(Permission::getPermissionId).collect(Collectors.toList());
        Assert.assertTrue(permissionIdList3.contains(new PermissionId(1L)));
        Assert.assertTrue(permissionIdList3.contains(new PermissionId(2L)));
        Assert.assertTrue(permissionIdList3.contains(new PermissionId(3L)));
        Assert.assertTrue(permissionIdList3.contains(new PermissionId(4L)));
        Assert.assertTrue(permissionIdList3.contains(new PermissionId(5L)));
        Assert.assertTrue(permissionIdList3.contains(new PermissionId(6L)));

        List<Permission> permissionList4 = userService.getPermissionList(user4);
        Assert.assertTrue(permissionList4.isEmpty());
    }
}
