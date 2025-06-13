package com.co.kc.shortening.infrastructure.repository;

import com.co.kc.shortening.blocklist.domain.model.Blocklist;
import com.co.kc.shortening.blocklist.domain.model.BlocklistFactory;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
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
class BlocklistMySqlRepositoryTests {

    @Autowired
    private BlocklistMySqlRepository blocklistMySqlRepository;

    @Test
    void testFindSavedBlocklistByBlockId() {
        Blocklist newBlocklist = BlocklistFactory.createTestBlocklist();
        blocklistMySqlRepository.save(newBlocklist);

        Blocklist blocklist = blocklistMySqlRepository.find(newBlocklist.getBlockId());
        Assertions.assertEquals(BlocklistFactory.getTestBlockId(), blocklist.getBlockId());
        Assertions.assertEquals(BlocklistFactory.testBlockStatus, blocklist.getStatus());
        Assertions.assertEquals(BlocklistFactory.getTestBlockRemark(), blocklist.getRemark());
        Assertions.assertEquals(BlocklistFactory.getTestBlockLink(), blocklist.getLink());
    }

    @Test
    void testFindSavedBlocklistByLink() {
        Blocklist newBlocklist = BlocklistFactory.createTestBlocklist();
        blocklistMySqlRepository.save(newBlocklist);

        Blocklist blocklist = blocklistMySqlRepository.find(newBlocklist.getLink());
        Assertions.assertEquals(BlocklistFactory.getTestBlockId(), blocklist.getBlockId());
        Assertions.assertEquals(BlocklistFactory.testBlockStatus, blocklist.getStatus());
        Assertions.assertEquals(BlocklistFactory.getTestBlockRemark(), blocklist.getRemark());
        Assertions.assertEquals(BlocklistFactory.getTestBlockLink(), blocklist.getLink());
    }

    @Test
    void testUpdateSavedBlocklist() {
        Blocklist newBlocklist = BlocklistFactory.createTestBlocklist();
        blocklistMySqlRepository.save(newBlocklist);

        Blocklist updateBlocklist = blocklistMySqlRepository.find(newBlocklist.getLink());
        updateBlocklist.changeStatus(BlocklistFactory.testBlockChangedStatus);
        updateBlocklist.changeRemark(BlocklistFactory.getTestBlockChangedRemark());
        blocklistMySqlRepository.save(updateBlocklist);

        Blocklist blocklist = blocklistMySqlRepository.find(newBlocklist.getLink());
        Assertions.assertEquals(BlocklistFactory.testBlockChangedStatus, blocklist.getStatus());
        Assertions.assertEquals(BlocklistFactory.getTestBlockChangedRemark(), blocklist.getRemark());
    }

    @Test
    void testRemoveSavedBlocklist() {
        Blocklist newBlocklist = BlocklistFactory.createTestBlocklist();
        blocklistMySqlRepository.save(newBlocklist);

        Blocklist blocklist = blocklistMySqlRepository.find(newBlocklist.getLink());
        Assertions.assertEquals(BlocklistFactory.getTestBlockId(), blocklist.getBlockId());
        Assertions.assertEquals(BlocklistFactory.testBlockStatus, blocklist.getStatus());
        Assertions.assertEquals(BlocklistFactory.getTestBlockRemark(), blocklist.getRemark());
        Assertions.assertEquals(BlocklistFactory.getTestBlockLink(), blocklist.getLink());
    }
}
