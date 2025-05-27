package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class UserEmailTests {

    @Test
    public void testNullUserEmail() {
        try {
            new UserEmail(null);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("email is null or empty", ex.getMessage());
        }
    }

    @Test
    public void testEmptyUserEmail() {
        try {
            new UserEmail("");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("email is null or empty", ex.getMessage());
        }
    }

    @Test
    public void testIllegalUserEmail() {
        try {
            new UserEmail("wrong email");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("email is illegal", ex.getMessage());
        }
    }

    @Test
    public void testLegalUserEmail() {
        UserEmail userEmail = new UserEmail("username@test.com");
        Assert.assertEquals("username@test.com", userEmail.getEmail());
    }
}
