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
import com.co.kc.shortening.common.exception.NotFoundException;
import com.co.kc.shortening.web.common.constants.enums.BlockFacadeStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kc
 */
@Api(tags = "黑名单路由")
@RestController
@RequiredArgsConstructor
@RequestMapping("/blocklist")
public class BlocklistController {

    private final BlocklistAppService blocklistAppService;
    private final BlocklistQueryService blocklistQueryService;

    @Auth
    @GetMapping("/v1/blocklistList")
    @ApiOperation(value = "链接黑名单列表")
    public PagingResult<BlocklistListVO> blocklistList(@ModelAttribute @Validated BlocklistListRequest request) {
        BlocklistQuery query = new BlocklistQuery();
        query.setBlockId(request.getBlockId());
        query.setStatus(BlockFacadeStatus.convert(request.getStatus()).orElse(null));
        query.setPaging(request.getPaging());
        PagingResult<BlocklistQueryDTO> pagingResult = blocklistQueryService.queryBlocklist(query);
        return pagingResult.mapping(BlocklistListVoAssembler::blocklistQueryDTOToVO);
    }

    @Auth
    @PostMapping("/v1/addBlocklist")
    @ApiOperation(value = "链接黑名单新增")
    public void addBlocklist(@RequestBody @Validated BlocklistAddRequest request) {
        BlocklistAddCommand blocklistAddCommand = new BlocklistAddCommand();
        blocklistAddCommand.setBlockLink(request.getBlockLink());
        blocklistAddCommand.setRemark(request.getRemark());
        blocklistAddCommand.setStatus(
                BlockFacadeStatus.convert(request.getStatus()).orElseThrow(() -> new NotFoundException("状态为空")));
        blocklistAppService.add(blocklistAddCommand);
    }

    @Auth
    @PostMapping("/v1/updateBlocklist")
    @ApiOperation(value = "链接黑名单更新")
    public void updateBlocklist(@RequestBody @Validated BlocklistUpdateRequest request) {
        BlocklistUpdateCommand command = new BlocklistUpdateCommand();
        command.setBlockId(request.getBlockId());
        command.setRemark(request.getRemark());
        command.setStatus(
                BlockFacadeStatus.convert(request.getStatus()).orElseThrow(() -> new NotFoundException("状态为空")));
        blocklistAppService.update(command);
    }

    @Auth
    @PostMapping("/v1/removeBlocklist")
    @ApiOperation(value = "链接黑名单移除")
    public void removeBlocklist(@RequestBody @Validated BlocklistRemoveRequest request) {
        BlocklistRemoveCommand command = new BlocklistRemoveCommand();
        command.setBlockId(request.getBlockId());
        blocklistAppService.remove(command);
    }

}
