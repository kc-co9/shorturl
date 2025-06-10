package com.co.kc.shortening.application.service.appservice;

import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistRemoveCommand;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistUpdateCommand;
import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.application.model.cqrs.dto.BlocklistDTO;
import com.co.kc.shortening.blocklist.domain.model.*;
import com.co.kc.shortening.common.exception.AlreadyExistsException;
import com.co.kc.shortening.common.exception.NotFoundException;
import com.co.kc.shortening.shared.domain.model.Link;

/**
 * 黑名单-应用服务
 *
 * @author kc
 */
public class BlocklistAppService {

    private final IdClient<Long> blockIdClient;
    private final BlocklistRepository blocklistRepository;

    public BlocklistAppService(IdClient<Long> blockIdClient,
                               BlocklistRepository blocklistRepository) {
        this.blockIdClient = blockIdClient;
        this.blocklistRepository = blocklistRepository;
    }

    public BlocklistDTO add(BlocklistAddCommand command) {
        BlockId blockId = new BlockId(blockIdClient.next());
        Link blockedLink = new Link(command.getBlockLink());
        BlockRemark blockRemark = new BlockRemark(command.getRemark());
        BlockStatus status = command.getStatus();

        Blocklist blocklist = blocklistRepository.find(blockedLink);
        if (blocklist != null) {
            throw new AlreadyExistsException("黑名单已存在");
        }

        blocklist = new Blocklist(blockId, blockedLink, blockRemark, status);
        blocklistRepository.save(blocklist);

        return new BlocklistDTO(blockId.getId(), blockedLink.getUrl());
    }

    public void update(BlocklistUpdateCommand command) {
        BlockId blockId = new BlockId(command.getBlockId());
        BlockRemark blockRemark = new BlockRemark(command.getRemark());
        BlockStatus status = command.getStatus();

        Blocklist blocklist = blocklistRepository.find(blockId);
        if (blocklist == null) {
            throw new NotFoundException("黑名单不存在");
        }

        blocklist.changeStatus(status);
        blocklist.changeRemark(blockRemark);
        blocklistRepository.save(blocklist);
    }

    public void remove(BlocklistRemoveCommand command) {
        BlockId blockId = new BlockId(command.getBlockId());
        Blocklist blocklist = blocklistRepository.find(blockId);
        if (blocklist == null) {
            throw new NotFoundException("黑名单不存在");
        }
        blocklistRepository.remove(blocklist);
    }

}
