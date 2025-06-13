package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class UserNameTests {
    @Test
    void testCreateNullUserName() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserName(null));
        Assertions.assertEquals("name is null or empty", ex.getMessage());
    }

    @Test
    void testCreateEmptyUserName() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserName(""));
        Assertions.assertEquals("name is null or empty", ex.getMessage());
    }

    @Test
    void testCreateUserNameSuccessfully() {
        UserName userName = new UserName("testUserName");
        Assertions.assertEquals("testUserName", userName.getName());
    }

    @Test
    void testSameUserNameIsEqual() {
        UserName userName1 = new UserName("testUserName");
        UserName userName2 = new UserName("testUserName");
        Assertions.assertEquals(userName1, userName2);
    }

    @Test
    void testDifferentUserNameIsNotEqual() {
        UserName userName1 = new UserName("testUserName1");
        UserName userName2 = new UserName("testUserName2");
        Assertions.assertNotEquals(userName1, userName2);
    }
}
