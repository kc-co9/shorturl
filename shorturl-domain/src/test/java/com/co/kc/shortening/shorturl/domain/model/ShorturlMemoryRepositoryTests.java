package com.co.kc.shortening.shorturl.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Shorturl newShorturl = ShorturlFactory.createTestShorturl();
        shorturlRepository.save(newShorturl);

        Shorturl savedShorturl1 = shorturlRepository.find(ShorturlFactory.getTestShortId());
        Shorturl savedShorturl2 = shorturlRepository.find(ShorturlFactory.getTestShortCode());

        Assertions.assertEquals(savedShorturl1, savedShorturl2);

        Assertions.assertEquals(newShorturl, savedShorturl1);
        Assertions.assertEquals(ShorturlFactory.getTestShortCode(), savedShorturl1.getShortCode());

        Assertions.assertEquals(newShorturl, savedShorturl2);
        Assertions.assertEquals(ShorturlFactory.getTestShortCode(), savedShorturl2.getShortCode());
    }
}
