package com.co.kc.shortening.infrastructure.mybatis.service;

import com.co.kc.shortening.blocklist.domain.model.BlocklistFactory;
import com.co.kc.shortening.common.utils.HashUtils;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.mybatis.entity.UrlBlocklist;
import com.co.kc.shortening.infrastructure.mybatis.enums.UrlBlocklistStatus;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
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
class UrlBlocklistServiceTests {
    @Autowired
    private UrlBlocklistService urlBlocklistService;

    @Test
    void testFindAddedUrlBlocklistByBlockId() {
        addTestUrlBlocklist();

        Optional<UrlBlocklist> urlBlocklist = urlBlocklistService.findByBlockId(BlocklistFactory.testBlockId);
        Assertions.assertTrue(urlBlocklist.isPresent());
        Assertions.assertEquals(BlocklistFactory.testBlockId, urlBlocklist.get().getBlockId());
        Assertions.assertEquals(BlocklistFactory.testBlockLink, urlBlocklist.get().getUrl());
        Assertions.assertEquals(HashUtils.murmurHash32(BlocklistFactory.testBlockLink), urlBlocklist.get().getHash());
        Assertions.assertEquals(UrlBlocklistStatus.convert(BlocklistFactory.testBlockStatus).get(), urlBlocklist.get().getStatus());
        Assertions.assertEquals(BlocklistFactory.testBlockRemark, urlBlocklist.get().getRemark());
    }

    @Test
    void testFindAddedUrlBlocklistByHash() {
        addTestUrlBlocklist();

        Optional<UrlBlocklist> urlBlocklist =
                urlBlocklistService.findByHash(HashUtils.murmurHash32(BlocklistFactory.testBlockLink), BlocklistFactory.testBlockLink);
        Assertions.assertTrue(urlBlocklist.isPresent());
        Assertions.assertEquals(BlocklistFactory.testBlockId, urlBlocklist.get().getBlockId());
        Assertions.assertEquals(BlocklistFactory.testBlockLink, urlBlocklist.get().getUrl());
        Assertions.assertEquals(HashUtils.murmurHash32(BlocklistFactory.testBlockLink), urlBlocklist.get().getHash());
        Assertions.assertEquals(UrlBlocklistStatus.convert(BlocklistFactory.testBlockStatus).get(), urlBlocklist.get().getStatus());
        Assertions.assertEquals(BlocklistFactory.testBlockRemark, urlBlocklist.get().getRemark());
    }

    @Test
    void testUpdateAddedUrlBlocklist() {
        addTestUrlBlocklist();

        urlBlocklistService.update(urlBlocklistService.getUpdateWrapper()
                .set(UrlBlocklist::getStatus, UrlBlocklistStatus.convert(BlocklistFactory.testBlockChangedStatus).get())
                .set(UrlBlocklist::getRemark, BlocklistFactory.testBlockChangedRemark)
                .eq(UrlBlocklist::getBlockId, BlocklistFactory.testBlockId));

        Optional<UrlBlocklist> urlBlocklist = urlBlocklistService.findByBlockId(BlocklistFactory.testBlockId);
        Assertions.assertTrue(urlBlocklist.isPresent());
        Assertions.assertEquals(UrlBlocklistStatus.convert(BlocklistFactory.testBlockChangedStatus).get(), urlBlocklist.get().getStatus());
        Assertions.assertEquals(BlocklistFactory.testBlockChangedRemark, urlBlocklist.get().getRemark());
    }

    @Test
    void testRemoveAddedUrlBlocklist() {
        UrlBlocklist urlBlocklist = addTestUrlBlocklist();

        urlBlocklistService.removeById(urlBlocklist.getId());

        Optional<UrlBlocklist> urlBlocklistById = urlBlocklistService.findByBlockId(BlocklistFactory.testBlockId);
        Assertions.assertFalse(urlBlocklistById.isPresent());
    }

    private UrlBlocklist addTestUrlBlocklist() {
        UrlBlocklist urlBlocklist = new UrlBlocklist();
        urlBlocklist.setBlockId(BlocklistFactory.testBlockId);
        urlBlocklist.setUrl(BlocklistFactory.testBlockLink);
        urlBlocklist.setHash(HashUtils.murmurHash32(BlocklistFactory.testBlockLink));
        urlBlocklist.setRemark(BlocklistFactory.testBlockRemark);
        urlBlocklist.setStatus(UrlBlocklistStatus.convert(BlocklistFactory.testBlockStatus).get());
        urlBlocklistService.save(urlBlocklist);
        return urlBlocklist;
    }
}
