package com.co.kc.shortening.infrastructure.service.app;

import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistRemoveCommand;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.BlocklistAddDTO;
import com.co.kc.shortening.application.service.app.BlocklistAppService;
import com.co.kc.shortening.blocklist.domain.model.BlockId;
import com.co.kc.shortening.blocklist.domain.model.Blocklist;
import com.co.kc.shortening.blocklist.domain.model.BlocklistFactory;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.repository.blocklist.BlocklistMySqlRepository;
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
class BlocklistAppServiceTests {
    @Autowired
    private BlocklistMySqlRepository blocklistMySqlRepository;
    @Autowired
    private BlocklistAppService blocklistAppService;

    @Test
    void testAddBlocklist() {
        BlocklistAddDTO blocklistAddDTO = addTestBlocklist();

        Blocklist blocklist = blocklistMySqlRepository.find(new BlockId(blocklistAddDTO.getBlockId()));
        Assertions.assertNotNull(blocklist);
        Assertions.assertEquals(blocklistAddDTO.getBlockId(), blocklist.getBlockId().getId());
        Assertions.assertEquals(BlocklistFactory.getTestBlockRemark(), blocklist.getRemark());
        Assertions.assertEquals(BlocklistFactory.testBlockStatus, blocklist.getStatus());
        Assertions.assertEquals(BlocklistFactory.getTestBlockLink(), blocklist.getLink());
    }

    @Test
    void testUpdateBlocklist() {
        BlocklistAddDTO blocklistAddDTO = addTestBlocklist();

        BlocklistUpdateCommand updateCommand = new BlocklistUpdateCommand();
        updateCommand.setBlockId(blocklistAddDTO.getBlockId());
        updateCommand.setRemark(BlocklistFactory.testBlockChangedRemark);
        updateCommand.setStatus(BlocklistFactory.testBlockChangedStatus);
        blocklistAppService.update(updateCommand);

        Blocklist blocklist = blocklistMySqlRepository.find(new BlockId(blocklistAddDTO.getBlockId()));
        Assertions.assertNotNull(blocklist);
        Assertions.assertEquals(BlocklistFactory.getTestBlockChangedRemark(), blocklist.getRemark());
        Assertions.assertEquals(BlocklistFactory.testBlockChangedStatus, blocklist.getStatus());
    }

    @Test
    void testRemoveBlocklist() {
        BlocklistAddDTO blocklistAddDTO = addTestBlocklist();

        BlocklistRemoveCommand removeCommand = new BlocklistRemoveCommand();
        removeCommand.setBlockId(blocklistAddDTO.getBlockId());
        blocklistAppService.remove(removeCommand);

        Assertions.assertNull(blocklistMySqlRepository.find(new BlockId(blocklistAddDTO.getBlockId())));
    }

    private BlocklistAddDTO addTestBlocklist() {
        BlocklistAddCommand addCommand = new BlocklistAddCommand();
        addCommand.setBlockLink(BlocklistFactory.testBlockLink);
        addCommand.setRemark(BlocklistFactory.testBlockRemark);
        addCommand.setStatus(BlocklistFactory.testBlockStatus);
        return blocklistAppService.add(addCommand);
    }
}
