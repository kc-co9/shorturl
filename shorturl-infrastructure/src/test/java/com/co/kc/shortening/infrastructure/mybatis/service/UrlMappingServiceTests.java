package com.co.kc.shortening.infrastructure.mybatis.service;

import com.co.kc.shortening.common.utils.HashUtils;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.mybatis.entity.UrlMapping;
import com.co.kc.shortening.infrastructure.mybatis.enums.UrlMappingStatus;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.shorturl.domain.model.ShorturlFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class UrlMappingServiceTests {
    @Autowired
    private UrlMappingService urlMappingService;

    @Test
    void testFindAddedUrlMappingByShortId() {
        addTestUrlMapping();

        Optional<UrlMapping> urlMapping = urlMappingService.findByShortId(ShorturlFactory.testShortId);
        Assertions.assertTrue(urlMapping.isPresent());
        Assertions.assertEquals(ShorturlFactory.testShortId, urlMapping.get().getShortId());
        Assertions.assertEquals(ShorturlFactory.testShortCode, urlMapping.get().getCode());
        Assertions.assertEquals(ShorturlFactory.testRawLink, urlMapping.get().getUrl());
        Assertions.assertEquals(HashUtils.murmurHash32(ShorturlFactory.testRawLink), urlMapping.get().getHash());
        Assertions.assertEquals(UrlMappingStatus.convert(ShorturlFactory.testStatus).get(), urlMapping.get().getStatus());
        Assertions.assertEquals(ShorturlFactory.testValidTime.getStartTime(), urlMapping.get().getValidStart());
        Assertions.assertEquals(ShorturlFactory.testValidTime.getEndTime(), urlMapping.get().getValidEnd());
    }

    @Test
    void testFindAddedUrlMappingByShortCode() {
        addTestUrlMapping();

        Optional<UrlMapping> urlMapping = urlMappingService.findByCode(ShorturlFactory.testShortCode);
        Assertions.assertTrue(urlMapping.isPresent());
        Assertions.assertEquals(ShorturlFactory.testShortId, urlMapping.get().getShortId());
        Assertions.assertEquals(ShorturlFactory.testShortCode, urlMapping.get().getCode());
        Assertions.assertEquals(ShorturlFactory.testRawLink, urlMapping.get().getUrl());
        Assertions.assertEquals(HashUtils.murmurHash32(ShorturlFactory.testRawLink), urlMapping.get().getHash());
        Assertions.assertEquals(UrlMappingStatus.convert(ShorturlFactory.testStatus).get(), urlMapping.get().getStatus());
        Assertions.assertEquals(ShorturlFactory.testValidTime.getStartTime(), urlMapping.get().getValidStart());
        Assertions.assertEquals(ShorturlFactory.testValidTime.getEndTime(), urlMapping.get().getValidEnd());
    }

    @Test
    void testFindAddedUrlMappingByHash() {
        addTestUrlMapping();

        Optional<UrlMapping> urlMapping =
                urlMappingService.findByHash(HashUtils.murmurHash32(ShorturlFactory.testRawLink), ShorturlFactory.testRawLink);
        Assertions.assertTrue(urlMapping.isPresent());
        Assertions.assertEquals(ShorturlFactory.testShortId, urlMapping.get().getShortId());
        Assertions.assertEquals(ShorturlFactory.testShortCode, urlMapping.get().getCode());
        Assertions.assertEquals(ShorturlFactory.testRawLink, urlMapping.get().getUrl());
        Assertions.assertEquals(HashUtils.murmurHash32(ShorturlFactory.testRawLink), urlMapping.get().getHash());
        Assertions.assertEquals(UrlMappingStatus.convert(ShorturlFactory.testStatus).get(), urlMapping.get().getStatus());
        Assertions.assertEquals(ShorturlFactory.testValidTime.getStartTime(), urlMapping.get().getValidStart());
        Assertions.assertEquals(ShorturlFactory.testValidTime.getEndTime(), urlMapping.get().getValidEnd());
    }

    @Test
    void testUpdateAddedUrlMapping() {
        addTestUrlMapping();

        urlMappingService.update(urlMappingService.getUpdateWrapper()
                .set(UrlMapping::getStatus, UrlMappingStatus.convert(ShorturlFactory.testChangedStatus).get())
                .set(UrlMapping::getValidStart, ShorturlFactory.testChangedValidTime.getStartTime())
                .set(UrlMapping::getValidEnd, ShorturlFactory.testChangedValidTime.getEndTime())
                .eq(UrlMapping::getShortId, ShorturlFactory.testShortId));

        Optional<UrlMapping> urlMapping = urlMappingService.findByShortId(ShorturlFactory.testShortId);
        Assertions.assertEquals(UrlMappingStatus.convert(ShorturlFactory.testChangedStatus).get(), urlMapping.get().getStatus());
        Assertions.assertEquals(ShorturlFactory.testChangedValidTime.getStartTime(), urlMapping.get().getValidStart());
        Assertions.assertEquals(ShorturlFactory.testChangedValidTime.getEndTime(), urlMapping.get().getValidEnd());
    }


    @Test
    void testRemoveAddedUrlMapping() {
        UrlMapping newUrlMapping = addTestUrlMapping();

        urlMappingService.removeById(newUrlMapping.getId());

        Optional<UrlMapping> urlMapping = urlMappingService.findByShortId(ShorturlFactory.testShortId);
        Assertions.assertFalse(urlMapping.isPresent());
    }


    private UrlMapping addTestUrlMapping() {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setShortId(ShorturlFactory.testShortId);
        urlMapping.setUrl(ShorturlFactory.testRawLink);
        urlMapping.setCode(ShorturlFactory.testShortCode);
        urlMapping.setHash(HashUtils.murmurHash32(ShorturlFactory.testRawLink));
        urlMapping.setStatus(UrlMappingStatus.convert(ShorturlFactory.testStatus).get());
        urlMapping.setValidStart(ShorturlFactory.testValidTime.getStartTime());
        urlMapping.setValidEnd(ShorturlFactory.testValidTime.getEndTime());
        urlMappingService.save(urlMapping);
        return urlMapping;
    }
}
