package com.co.kc.shortening.application.service.app;

import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistRemoveCommand;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.BlocklistAddDTO;
import com.co.kc.shortening.blocklist.domain.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlocklistAppServiceTests {

    private BlocklistRepository blocklistRepository;
    private BlocklistAppService blocklistAppService;

    @BeforeEach
    public void initBlocklistAppService() {
        blocklistRepository = new BlocklistMemoryRepository();
        blocklistAppService = BlocklistAppServiceFactory.createBlocklistAppService(blocklistRepository);
    }

    @Test
    void testAddBlocklist() {
        BlocklistAddCommand command = new BlocklistAddCommand();
        command.setBlockLink(BlocklistFactory.testBlockLink);
        command.setRemark(BlocklistFactory.testBlockRemark);
        command.setStatus(BlocklistFactory.testBlockStatus);
        BlocklistAddDTO blocklistAddDTO = blocklistAppService.add(command);

        Blocklist blocklist = blocklistRepository.find(new BlockId(blocklistAddDTO.getBlockId()));
        Assertions.assertNotNull(blocklist);
        Assertions.assertEquals(BlocklistFactory.getTestBlockRemark(), blocklist.getRemark());
        Assertions.assertEquals(BlocklistFactory.testBlockStatus, blocklist.getStatus());
    }

    @Test
    void testUpdateBlocklist() {
        BlocklistAddCommand addCommand = new BlocklistAddCommand();
        addCommand.setBlockLink(BlocklistFactory.testBlockLink);
        addCommand.setRemark(BlocklistFactory.testBlockRemark);
        addCommand.setStatus(BlocklistFactory.testBlockStatus);
        BlocklistAddDTO blocklistAddDTO = blocklistAppService.add(addCommand);

        BlocklistUpdateCommand updateCommand = new BlocklistUpdateCommand();
        updateCommand.setBlockId(blocklistAddDTO.getBlockId());
        updateCommand.setRemark(BlocklistFactory.testBlockChangedRemark);
        updateCommand.setStatus(BlocklistFactory.testBlockChangedStatus);
        blocklistAppService.update(updateCommand);

        Blocklist updateBlocklist = blocklistRepository.find(new BlockId(blocklistAddDTO.getBlockId()));
        Assertions.assertNotNull(updateBlocklist);
        Assertions.assertEquals(BlocklistFactory.getTestBlockChangedRemark(), updateBlocklist.getRemark());
        Assertions.assertEquals(BlocklistFactory.testBlockChangedStatus, updateBlocklist.getStatus());
    }

    @Test
    void testRemoveBlocklist() {
        BlocklistAddCommand addCommand = new BlocklistAddCommand();
        addCommand.setBlockLink(BlocklistFactory.testBlockLink);
        addCommand.setRemark(BlocklistFactory.testBlockRemark);
        addCommand.setStatus(BlocklistFactory.testBlockStatus);
        BlocklistAddDTO blocklistAddDTO = blocklistAppService.add(addCommand);

        BlocklistRemoveCommand removeCommand = new BlocklistRemoveCommand();
        removeCommand.setBlockId(blocklistAddDTO.getBlockId());
        blocklistAppService.remove(removeCommand);

        Blocklist removeBlocklist = blocklistRepository.find(new BlockId(blocklistAddDTO.getBlockId()));
        Assertions.assertNull(removeBlocklist);
    }
}
