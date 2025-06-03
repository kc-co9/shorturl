package com.co.kc.shortening.application.service.appservice;

import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistRemoveCommand;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.BlocklistDTO;
import com.co.kc.shortening.blocklist.domain.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlocklistAppServiceTests {

    private BlocklistRepository blocklistRepository;
    private BlocklistAppService blocklistAppService;

    @Before
    public void initBlocklistAppService() {
        blocklistRepository = new BlocklistMemoryRepository();
        blocklistAppService = BlocklistAppServiceFactory.createBlocklistAppService(blocklistRepository);
    }

    @Test
    public void testAddBlocklist() {
        BlocklistAddCommand command = new BlocklistAddCommand();
        command.setBlockLink(BlocklistFactory.testBlockLink);
        command.setRemark(BlocklistFactory.testBlockRemark);
        command.setStatus(BlocklistFactory.testBlockStatus);
        BlocklistDTO blocklistDTO = blocklistAppService.add(command);

        Blocklist blocklist = blocklistRepository.find(new BlockId(blocklistDTO.getBlockId()));
        Assert.assertNotNull(blocklist);
        Assert.assertEquals(BlocklistFactory.getTestBlockRemark(), blocklist.getRemark());
        Assert.assertEquals(BlocklistFactory.testBlockStatus, blocklist.getStatus());
    }

    @Test
    public void testUpdateBlocklist() {
        BlocklistAddCommand addCommand = new BlocklistAddCommand();
        addCommand.setBlockLink(BlocklistFactory.testBlockLink);
        addCommand.setRemark(BlocklistFactory.testBlockRemark);
        addCommand.setStatus(BlocklistFactory.testBlockStatus);
        BlocklistDTO blocklistDTO = blocklistAppService.add(addCommand);

        BlocklistUpdateCommand updateCommand = new BlocklistUpdateCommand();
        updateCommand.setBlockId(blocklistDTO.getBlockId());
        updateCommand.setRemark(BlocklistFactory.testBlockChangedRemark);
        updateCommand.setStatus(BlocklistFactory.testBlockChangedStatus);
        blocklistAppService.update(updateCommand);

        Blocklist updateBlocklist = blocklistRepository.find(new BlockId(blocklistDTO.getBlockId()));
        Assert.assertNotNull(updateBlocklist);
        Assert.assertEquals(BlocklistFactory.getTestBlockChangedRemark(), updateBlocklist.getRemark());
        Assert.assertEquals(BlocklistFactory.testBlockChangedStatus, updateBlocklist.getStatus());
    }

    @Test
    public void testRemoveBlocklist() {
        BlocklistAddCommand addCommand = new BlocklistAddCommand();
        addCommand.setBlockLink(BlocklistFactory.testBlockLink);
        addCommand.setRemark(BlocklistFactory.testBlockRemark);
        addCommand.setStatus(BlocklistFactory.testBlockStatus);
        BlocklistDTO blocklistDTO = blocklistAppService.add(addCommand);

        BlocklistRemoveCommand removeCommand = new BlocklistRemoveCommand();
        removeCommand.setBlockId(blocklistDTO.getBlockId());
        blocklistAppService.remove(removeCommand);

        Blocklist removeBlocklist = blocklistRepository.find(new BlockId(blocklistDTO.getBlockId()));
        Assert.assertNull(removeBlocklist);
    }
}
