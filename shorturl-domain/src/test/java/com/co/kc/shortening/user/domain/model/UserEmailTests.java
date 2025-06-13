package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class UserEmailTests {

    @Test
    void testNullUserEmail() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserEmail(null));
        Assertions.assertEquals("email is null or empty", ex.getMessage());
    }

    @Test
    void testEmptyUserEmail() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserEmail(""));
        Assertions.assertEquals("email is null or empty", ex.getMessage());
    }

    @Test
    void testIllegalUserEmail() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserEmail("wrong email"));
        Assertions.assertEquals("email is illegal", ex.getMessage());
    }

    @Test
    void testLegalUserEmail() {
        UserEmail userEmail = new UserEmail("username@test.com");
        Assertions.assertEquals("username@test.com", userEmail.getEmail());
    }
}
