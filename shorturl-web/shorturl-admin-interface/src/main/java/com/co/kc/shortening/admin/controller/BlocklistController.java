package com.co.kc.shortening.admin.controller;

import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistAddCommand;
import com.co.kc.shortening.application.model.cqrs.dto.BlocklistQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.BlocklistQuery;
import com.co.kc.shortening.application.service.appservice.BlocklistAppService;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistRemoveCommand;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistUpdateCommand;
import com.co.kc.shortening.application.service.queryservice.BlocklistQueryService;
import com.co.kc.shortening.admin.assembler.BlocklistListVoAssembler;
import com.co.kc.shortening.admin.model.request.BlocklistAddRequest;
import com.co.kc.shortening.admin.model.request.BlocklistListRequest;
import com.co.kc.shortening.admin.model.request.BlocklistRemoveRequest;
import com.co.kc.shortening.admin.model.request.BlocklistUpdateRequest;
import com.co.kc.shortening.admin.model.response.BlocklistListVO;
import com.co.kc.shortening.application.annotation.Auth;
import com.co.kc.shortening.application.model.io.PagingResult;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kc
 */
@RestController
@AllArgsConstructor
@RequestMapping("/blocklist")
public class BlocklistController {

    private final BlocklistAppService blocklistAppService;
    private final BlocklistQueryService blocklistQueryService;

    @Auth
    @GetMapping("/v1/blocklistList")
    @ApiOperation(value = "链接黑名单列表")
    public PagingResult<BlocklistListVO> blocklistList(@ModelAttribute @Validated BlocklistListRequest request) {
        BlocklistQuery query = new BlocklistQuery();
        query.setPageNo(request.getPageNo());
        query.setPageSize(request.getPageSize());
        PagingResult<BlocklistQueryDTO> pagingResult = blocklistQueryService.queryBlocklist(query);
        return pagingResult.mapping(BlocklistListVoAssembler::blocklistQueryDTOToVO);
    }

    @Auth
    @PostMapping("/v1/addBlocklist")
    @ApiOperation(value = "链接黑名单新增")
    public void addBlocklist(@RequestBody @Validated BlocklistAddRequest request) {
        BlocklistAddCommand blocklistAddCommand = new BlocklistAddCommand();
        blocklistAddCommand.setBlockLink(request.getUrl());
        blocklistAddCommand.setRemark(request.getRemark());
        blocklistAddCommand.setStatus(request.getStatus());
        blocklistAppService.add(blocklistAddCommand);
    }

    @Auth
    @PostMapping("/v1/updateBlocklist")
    @ApiOperation(value = "链接黑名单更新")
    public void updateBlocklist(@RequestBody @Validated BlocklistUpdateRequest request) {
        BlocklistUpdateCommand command = new BlocklistUpdateCommand();
        command.setBlockId(request.getId());
        command.setRemark(request.getRemark());
        command.setStatus(request.getStatus());
        blocklistAppService.update(command);
    }

    @Auth
    @PostMapping("/v1/removeBlocklist")
    @ApiOperation(value = "链接黑名单移除")
    public void removeBlocklist(@RequestBody @Validated BlocklistRemoveRequest request) {
        BlocklistRemoveCommand command = new BlocklistRemoveCommand();
        command.setBlockId(request.getId());
        blocklistAppService.remove(command);
    }


}
