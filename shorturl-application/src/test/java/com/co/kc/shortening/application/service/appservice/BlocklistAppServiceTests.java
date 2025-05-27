package com.co.kc.shortening.application.service.appservice;

import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.application.client.RandomIdClient;
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
        IdClient<Long> blockIdClient = new RandomIdClient();
        blocklistRepository = new BlocklistMemoryRepository();
        blocklistAppService = new BlocklistAppService(blockIdClient, blocklistRepository);
    }

    @Test
    public void testAddBlocklist() {
        BlocklistAddCommand command = new BlocklistAddCommand();
        command.setBlockLink("https://blocked.com");
        command.setRemark("测试黑名单链接");
        command.setStatus(1);
        BlocklistDTO blocklistDTO = blocklistAppService.add(command);

        Blocklist blocklist = blocklistRepository.find(new BlockId(blocklistDTO.getBlockId()));
        Assert.assertNotNull(blocklist);
        Assert.assertEquals("测试黑名单链接", blocklist.getRemark().getRemark());
        Assert.assertEquals(BlockStatus.ONLINE, blocklist.getStatus());
    }

    @Test
    public void testUpdateBlocklist() {
        BlocklistAddCommand addCommand = new BlocklistAddCommand();
        addCommand.setBlockLink("https://blocked.com");
        addCommand.setRemark("测试黑名单链接");
        addCommand.setStatus(1);
        BlocklistDTO blocklistDTO = blocklistAppService.add(addCommand);

        Blocklist addBlocklist = blocklistRepository.find(new BlockId(blocklistDTO.getBlockId()));
        Assert.assertNotNull(addBlocklist);
        Assert.assertEquals("测试黑名单链接", addBlocklist.getRemark().getRemark());
        Assert.assertEquals(BlockStatus.ONLINE, addBlocklist.getStatus());

        BlocklistUpdateCommand updateCommand = new BlocklistUpdateCommand();
        updateCommand.setBlockId(addBlocklist.getBlockId().getId());
        updateCommand.setRemark("修改测试黑名单链接");
        updateCommand.setStatus(0);
        blocklistAppService.update(updateCommand);

        Blocklist updateBlocklist = blocklistRepository.find(addBlocklist.getBlockId());
        Assert.assertNotNull(updateBlocklist);
        Assert.assertEquals("修改测试黑名单链接", updateBlocklist.getRemark().getRemark());
        Assert.assertEquals(BlockStatus.OFFLINE, updateBlocklist.getStatus());
    }

    @Test
    public void testRemoveBlocklist() {
        BlocklistAddCommand addCommand = new BlocklistAddCommand();
        addCommand.setBlockLink("https://blocked.com");
        addCommand.setRemark("测试黑名单链接");
        addCommand.setStatus(1);
        BlocklistDTO blocklistDTO = blocklistAppService.add(addCommand);

        Blocklist addBlocklist = blocklistRepository.find(new BlockId(blocklistDTO.getBlockId()));
        Assert.assertNotNull(addBlocklist);
        Assert.assertEquals("测试黑名单链接", addBlocklist.getRemark().getRemark());
        Assert.assertEquals(BlockStatus.ONLINE, addBlocklist.getStatus());

        BlocklistRemoveCommand removeCommand = new BlocklistRemoveCommand();
        removeCommand.setBlockId(addBlocklist.getBlockId().getId());
        blocklistAppService.remove(removeCommand);

        Blocklist removeBlocklist = blocklistRepository.find(addBlocklist.getBlockId());
        Assert.assertNull(removeBlocklist);
    }
}
