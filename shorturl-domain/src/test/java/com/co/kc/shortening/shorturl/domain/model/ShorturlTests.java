package com.co.kc.shortening.shorturl.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;
import com.co.kc.shortening.common.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShorturlTests {
    private Shorturl shorturl;

    @BeforeEach
    public void initShorturl() {
        shorturl = ShorturlFactory.createTestShorturl();
    }

    @Test
    void testShorturlPropertiesSucceedToSet() {
        Assertions.assertEquals(ShorturlFactory.getTestShortId(), shorturl.getShortId());
        Assertions.assertEquals(ShorturlFactory.getTestShortCode(), shorturl.getShortCode());
        Assertions.assertEquals(ShorturlFactory.testValidTime, shorturl.getValidTime());
        Assertions.assertEquals(ShorturlFactory.getTestRawLink(), shorturl.getRawLink());
        Assertions.assertEquals(ShorturlFactory.testStatus, shorturl.getStatus());
    }


    @Test
    void testActivate() {
        shorturl.activate();
        Assertions.assertEquals(ShorturlStatus.ONLINE, shorturl.getStatus());

        shorturl.inactivate();
        Assertions.assertEquals(ShorturlStatus.OFFLINE, shorturl.getStatus());
    }

    @Test
    void testIsActive() {
        shorturl.activate();
        Assertions.assertTrue(shorturl.isActive());

        shorturl.inactivate();
        Assertions.assertFalse(shorturl.isActive());
    }

    @Test
    void testUpdateValidTime() {
        shorturl.changeValidTime(ShorturlFactory.testChangedValidTime);
        Assertions.assertEquals(ShorturlFactory.testChangedValidTime, shorturl.getValidTime());
    }

    @Test
    void testIsInValidTime() {
        Assertions.assertTrue(shorturl.isInValidTime());
        shorturl.changeValidTime(ShorturlFactory.testExpiredValidTime);
        Assertions.assertFalse(shorturl.isInValidTime());
    }

    @Test
    void testAutoInactivateWhenUpdateExpiredValidTime() {
        shorturl.activate();
        Assertions.assertTrue(shorturl.isActive());
        shorturl.changeValidTime(ShorturlFactory.testExpiredValidTime);
        Assertions.assertFalse(shorturl.isInValidTime());
        Assertions.assertFalse(shorturl.isActive());
    }

    @Test
    void testFailActivateBecauseOfNotInValidTime() {
        shorturl.changeValidTime(ShorturlFactory.testExpiredValidTime);
        try {
            shorturl.activate();
        } catch (BusinessException e) {
            Assertions.assertEquals("短链已过期，激活失败", e.getMsg());
            return;
        }
        Assertions.fail();
    }

    @Test
    void testGetShortLink() {
        Link shortLink = shorturl.getLink(ShorturlFactory.getTestShortDomain());
        Assertions.assertEquals(ShorturlFactory.testShortLink, shortLink.getUrl());
    }
}
