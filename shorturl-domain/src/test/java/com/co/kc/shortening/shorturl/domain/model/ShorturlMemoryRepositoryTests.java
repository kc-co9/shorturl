package com.co.kc.shortening.shorturl.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class ShorturlMemoryRepositoryTests {
    private ShorturlRepository shorturlRepository;

    @Before
    public void initShorturlRepository() {
        shorturlRepository = new ShorturlMemoryRepository();
    }

    @Test
    public void testFindNullByShortId() {
        Shorturl shorturl = shorturlRepository.find(new ShortId(10L));
        Assert.assertNull(shorturl);
    }

    @Test
    public void testFindNullByCode() {
        Shorturl shorturl = shorturlRepository.find(new ShortCode("testNullCode"));
        Assert.assertNull(shorturl);
    }

    @Test
    public void testFindSavedShorturl() {
        ShortId shortId = new ShortId(10L);
        ShortCode code = new ShortCode("testCode");
        Link rawLink = new Link("http://www.test.com");
        ShorturlStatus status = ShorturlStatus.ONLINE;
        ValidTimeInterval validTime = new ValidTimeInterval(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));

        Shorturl newShorturl = new Shorturl(shortId, code, rawLink, status, validTime);
        shorturlRepository.save(newShorturl);

        Shorturl savedShorturl1 = shorturlRepository.find(shortId);
        Shorturl savedShorturl2 = shorturlRepository.find(code);

        Assert.assertEquals(savedShorturl1, savedShorturl2);

        Assert.assertEquals(newShorturl, savedShorturl1);
        Assert.assertEquals(code, savedShorturl1.getShortCode());

        Assert.assertEquals(newShorturl, savedShorturl2);
        Assert.assertEquals(code, savedShorturl2.getShortCode());
    }
}
