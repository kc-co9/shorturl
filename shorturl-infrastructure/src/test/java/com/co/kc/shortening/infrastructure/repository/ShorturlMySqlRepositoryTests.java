package com.co.kc.shortening.infrastructure.repository;

import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.shorturl.domain.model.Shorturl;
import com.co.kc.shortening.shorturl.domain.model.ShorturlFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class ShorturlMySqlRepositoryTests {
    @Autowired
    private ShorturlMySqlRepository shorturlMySqlRepository;

    @Test
    void testFindSavedShorturlByShortId() {
        Shorturl newShorturl = ShorturlFactory.createTestShorturl();
        shorturlMySqlRepository.save(newShorturl);

        Shorturl shorturl = shorturlMySqlRepository.find(newShorturl.getShortId());
        Assertions.assertEquals(ShorturlFactory.getTestShortId(), shorturl.getShortId());
        Assertions.assertEquals(ShorturlFactory.getTestShortCode(), shorturl.getShortCode());
        Assertions.assertEquals(ShorturlFactory.getTestRawLink(), shorturl.getRawLink());
        Assertions.assertEquals(ShorturlFactory.getTestShortLink(), shorturl.getLink(ShorturlFactory.getTestShortDomain()));
        Assertions.assertEquals(ShorturlFactory.testStatus, shorturl.getStatus());
        Assertions.assertEquals(ShorturlFactory.testValidTime, shorturl.getValidTime());
    }

    @Test
    void testFindSavedShorturlByShortCode() {
        Shorturl newShorturl = ShorturlFactory.createTestShorturl();
        shorturlMySqlRepository.save(newShorturl);

        Shorturl shorturl = shorturlMySqlRepository.find(newShorturl.getShortCode());
        Assertions.assertEquals(ShorturlFactory.getTestShortId(), shorturl.getShortId());
        Assertions.assertEquals(ShorturlFactory.getTestShortCode(), shorturl.getShortCode());
        Assertions.assertEquals(ShorturlFactory.getTestRawLink(), shorturl.getRawLink());
        Assertions.assertEquals(ShorturlFactory.getTestShortLink(), shorturl.getLink(ShorturlFactory.getTestShortDomain()));
        Assertions.assertEquals(ShorturlFactory.testStatus, shorturl.getStatus());
        Assertions.assertEquals(ShorturlFactory.testValidTime, shorturl.getValidTime());
    }

    @Test
    void testUpdateSavedShorturl() {
        Shorturl newShorturl = ShorturlFactory.createTestShorturl();
        shorturlMySqlRepository.save(newShorturl);

        Shorturl updatesShorturl = shorturlMySqlRepository.find(newShorturl.getShortId());
        updatesShorturl.changeStatus(ShorturlFactory.testChangedStatus);
        updatesShorturl.changeValidTime(ShorturlFactory.testChangedValidTime);
        shorturlMySqlRepository.save(updatesShorturl);

        Shorturl shorturl = shorturlMySqlRepository.find(newShorturl.getShortId());
        Assertions.assertEquals(ShorturlFactory.testChangedStatus, shorturl.getStatus());
        Assertions.assertEquals(ShorturlFactory.testChangedValidTime, shorturl.getValidTime());
    }
}
