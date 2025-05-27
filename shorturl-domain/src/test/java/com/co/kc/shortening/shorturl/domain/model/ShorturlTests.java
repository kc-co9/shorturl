package com.co.kc.shortening.shorturl.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;
import com.co.kc.shorturl.common.exception.BusinessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class ShorturlTests {
    private Shorturl shorturl;

    @Before
    public void initShorturl() {
        ShortId shortId = new ShortId(10L);
        ShortCode code = new ShortCode("testCode");
        Link rawLink = new Link("http://www.test.com");
        ShorturlStatus status = ShorturlStatus.ONLINE;
        ValidTimeInterval validTime = new ValidTimeInterval(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        shorturl = new Shorturl(shortId, code, rawLink, status, validTime);
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
        ValidTimeInterval validTime = new ValidTimeInterval(LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1));
        shorturl.changeValidTime(validTime);

        Assert.assertEquals(validTime, shorturl.getValidTime());
    }

    @Test
    public void testIsInValidTime() {
        Assert.assertTrue(shorturl.isInValidTime());

        ValidTimeInterval validTime = new ValidTimeInterval(LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1));
        shorturl.changeValidTime(validTime);
        Assert.assertFalse(shorturl.isInValidTime());
    }

    @Test
    public void testAutoInactivateWhenUpdateExpiredValidTime() {
        shorturl.activate();
        Assert.assertTrue(shorturl.isActive());

        ValidTimeInterval expiredValidTime =
                new ValidTimeInterval(LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1));
        shorturl.changeValidTime(expiredValidTime);
        Assert.assertFalse(shorturl.isInValidTime());
        Assert.assertFalse(shorturl.isActive());
    }

    @Test
    public void testFailActivateBecauseOfNotInValidTime() {
        ValidTimeInterval expiredValidTime =
                new ValidTimeInterval(LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1));
        shorturl.changeValidTime(expiredValidTime);

        try {
            shorturl.activate();
        } catch (BusinessException e) {
            Assert.assertEquals("短链已过期，激活失败", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetLink() {
        Link shortHost = new Link("http://www.short.com");
        Link shortLink = shorturl.getLink(shortHost);
        Assert.assertEquals("http://www.short.com/testCode", shortLink.getUrl());
    }
}
