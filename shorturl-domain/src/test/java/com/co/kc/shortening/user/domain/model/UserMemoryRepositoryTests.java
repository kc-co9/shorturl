package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * @author kc
 */
class UserMemoryRepositoryTests {
    private UserRepository userRepository;

    @BeforeEach
    public void initUserRepository() {
        userRepository = new UserMemoryRepository();
    }

    @Test
    void testFindSavedUser() {
        User newUser =
                new User(
                        new UserId(10L),
                        new UserEmail("username@test.com"),
                        new UserName("testUserName"),
                        new UserPassword("testUserPassword"),
                        Collections.emptyList());
        userRepository.save(newUser);

        User userById = userRepository.find(new UserId(10L));
        Assertions.assertEquals(userById, newUser);

        User userByEmail = userRepository.find(new UserId(10L));
        Assertions.assertEquals(userByEmail, newUser);
    }

    @Test
    void testRemoveSavedUser() {
        User newUser =
                new User(
                        new UserId(10L),
                        new UserEmail("username@test.com"),
                        new UserName("testUserName"),
                        new UserPassword("testUserPassword"),
                        Collections.emptyList());
        userRepository.save(newUser);

        User user = userRepository.find(new UserId(10L));
        Assertions.assertEquals(user, newUser);

        userRepository.remove(user);
        Assertions.assertNull(userRepository.find(new UserId(10L)));
    }
}
