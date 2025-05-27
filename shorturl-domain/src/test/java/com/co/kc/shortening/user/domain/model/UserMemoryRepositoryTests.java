package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * @author kc
 */
public class UserMemoryRepositoryTests {
    private UserRepository userRepository;

    @Before
    public void initUserRepository() {
        userRepository = new UserMemoryRepository();
    }

    @Test
    public void testFindSavedUser() {
        User newUser =
                new User(
                        new UserId(10L),
                        new UserEmail("username@test.com"),
                        new UserName("testUserName"),
                        new UserPassword("testUserPassword"),
                        Collections.emptyList());
        userRepository.save(newUser);

        User userById = userRepository.find(new UserId(10L));
        Assert.assertEquals(userById, newUser);

        User userByEmail = userRepository.find(new UserId(10L));
        Assert.assertEquals(userByEmail, newUser);
    }

    @Test
    public void testRemoveSavedUser() {
        User newUser =
                new User(
                        new UserId(10L),
                        new UserEmail("username@test.com"),
                        new UserName("testUserName"),
                        new UserPassword("testUserPassword"),
                        Collections.emptyList());
        userRepository.save(newUser);

        User user = userRepository.find(new UserId(10L));
        Assert.assertEquals(user, newUser);

        userRepository.remove(user);
        Assert.assertNull(userRepository.find(new UserId(10L)));
    }
}
