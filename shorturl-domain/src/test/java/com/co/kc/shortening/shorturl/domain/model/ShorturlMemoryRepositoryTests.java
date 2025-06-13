package com.co.kc.shortening.shorturl.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class ShorturlMemoryRepositoryTests {
    private ShorturlRepository shorturlRepository;

    @BeforeEach
    public void initShorturlRepository() {
        shorturlRepository = new ShorturlMemoryRepository();
    }

    @Test
    void testFindNullByShortId() {
        Shorturl shorturl = shorturlRepository.find(new ShortId(10L));
        Assertions.assertNull(shorturl);
    }

    @Test
    void testFindNullByCode() {
        Shorturl shorturl = shorturlRepository.find(new ShortCode("testNullCode"));
        Assertions.assertNull(shorturl);
    }

    @Test
    void testFindSavedShorturl() {
        ShortId shortId = new ShortId(10L);
        ShortCode code = new ShortCode("testCode");
        Link rawLink = new Link("http://www.test.com");
        ShorturlStatus status = ShorturlStatus.ONLINE;
        ValidTimeInterval validTime = new ValidTimeInterval(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));

        Shorturl newShorturl = new Shorturl(shortId, code, rawLink, status, validTime);
        shorturlRepository.save(newShorturl);

        Shorturl savedShorturl1 = shorturlRepository.find(shortId);
        Shorturl savedShorturl2 = shorturlRepository.find(code);

        Assertions.assertEquals(savedShorturl1, savedShorturl2);

        Assertions.assertEquals(newShorturl, savedShorturl1);
        Assertions.assertEquals(code, savedShorturl1.getShortCode());

        Assertions.assertEquals(newShorturl, savedShorturl2);
        Assertions.assertEquals(code, savedShorturl2.getShortCode());
    }
}
