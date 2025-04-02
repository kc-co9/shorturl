package com.co.kc.shorturl.admin.controller;

import com.co.kc.shorturl.admin.model.dto.request.BlocklistAddRequest;
import com.co.kc.shorturl.admin.model.dto.request.BlocklistListRequest;
import com.co.kc.shorturl.admin.model.dto.request.BlocklistRemoveRequest;
import com.co.kc.shorturl.admin.model.dto.request.BlocklistUpdateRequest;
import com.co.kc.shorturl.admin.model.dto.response.BlocklistDTO;
import com.co.kc.shorturl.admin.security.annotation.Auth;
import com.co.kc.shorturl.admin.service.BlocklistBizService;
import com.co.kc.shorturl.common.model.io.PagingResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kc
 */
@RestController
@RequestMapping("/blocklist")
public class BlocklistController {

    @Autowired
    private BlocklistBizService blocklistBizService;

    @Auth
    @GetMapping("/v1/blocklistList")
    @ApiOperation(value = "链接黑名单列表")
    public PagingResult<BlocklistDTO> blocklistList(@ModelAttribute @Validated BlocklistListRequest request) {
        return blocklistBizService.getBlocklistList(request.getPaging());
    }

    @Auth
    @PostMapping("/v1/addBlocklist")
    @ApiOperation(value = "链接黑名单新增")
    public void addBlocklist(@RequestBody @Validated BlocklistAddRequest request) {
        blocklistBizService.addBlocklist(request.getUrl(), request.getRemark());
    }

    @Auth
    @PostMapping("/v1/updateBlocklist")
    @ApiOperation(value = "链接黑名单更新")
    public void updateBlocklist(@RequestBody @Validated BlocklistUpdateRequest request) {
        blocklistBizService.updateBlocklist(request.getId(), request.getRemark());
    }

    @Auth
    @PostMapping("/v1/removeBlocklist")
    @ApiOperation(value = "链接黑名单移除")
    public void removeBlocklist(@RequestBody @Validated BlocklistRemoveRequest request) {
        blocklistBizService.removeBlocklist(request.getId());
    }


}
