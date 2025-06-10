package com.co.kc.shortening.shorturl.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;
import com.co.kc.shortening.common.exception.BusinessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShorturlTests {
    private Shorturl shorturl;

    @Before
    public void initShorturl() {
        shorturl = ShorturlFactory.createShorturl();
    }

    @Test
    public void testShorturlPropertiesSucceedToSet() {
        Assert.assertEquals(ShorturlFactory.getTestShortId(), shorturl.getShortId());
        Assert.assertEquals(ShorturlFactory.getTestShortCode(), shorturl.getShortCode());
        Assert.assertEquals(ShorturlFactory.testValidTime, shorturl.getValidTime());
        Assert.assertEquals(ShorturlFactory.getTestRawLink(), shorturl.getRawLink());
        Assert.assertEquals(ShorturlFactory.testStatus, shorturl.getStatus());
    }


    @Test
    public void testActivate() {
        shorturl.activate();
        Assert.assertEquals(ShorturlStatus.ONLINE, shorturl.getStatus());

        shorturl.inactivate();
        Assert.assertEquals(ShorturlStatus.OFFLINE, shorturl.getStatus());
    }

    @Test
    public void testIsActive() {
        shorturl.activate();
        Assert.assertTrue(shorturl.isActive());

        shorturl.inactivate();
        Assert.assertFalse(shorturl.isActive());
    }

    @Test
    public void testUpdateValidTime() {
        shorturl.changeValidTime(ShorturlFactory.testChangedValidTime);
        Assert.assertEquals(ShorturlFactory.testChangedValidTime, shorturl.getValidTime());
    }

    @Test
    public void testIsInValidTime() {
        Assert.assertTrue(shorturl.isInValidTime());
        shorturl.changeValidTime(ShorturlFactory.testExpiredValidTime);
        Assert.assertFalse(shorturl.isInValidTime());
    }

    @Test
    public void testAutoInactivateWhenUpdateExpiredValidTime() {
        shorturl.activate();
        Assert.assertTrue(shorturl.isActive());
        shorturl.changeValidTime(ShorturlFactory.testExpiredValidTime);
        Assert.assertFalse(shorturl.isInValidTime());
        Assert.assertFalse(shorturl.isActive());
    }

    @Test
    public void testFailActivateBecauseOfNotInValidTime() {
        shorturl.changeValidTime(ShorturlFactory.testExpiredValidTime);
        try {
            shorturl.activate();
        } catch (BusinessException e) {
            Assert.assertEquals("短链已过期，激活失败", e.getMsg());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetShortLink() {
        Link shortLink = shorturl.getLink(ShorturlFactory.getTestShortDomain());
        Assert.assertEquals(ShorturlFactory.testShortLink, shortLink.getUrl());
    }
}
