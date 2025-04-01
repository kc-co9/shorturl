package com.co.kc.shorturl.admin.controller;

import com.co.kc.shorturl.admin.model.dto.request.*;
import com.co.kc.shorturl.admin.model.dto.response.BlocklistDTO;
import com.co.kc.shorturl.admin.model.dto.response.ShorturlCreateDTO;
import com.co.kc.shorturl.admin.model.dto.response.ShorturlDTO;
import com.co.kc.shorturl.admin.service.UrlBizService;
import com.co.kc.shorturl.common.model.io.PagingResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kc
 */
@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private UrlBizService urlBizService;

    @GetMapping("/shorturlList")
    @ApiOperation(value = "短链列表")
    public PagingResult<ShorturlDTO> shorturlList(@ModelAttribute @Validated ShorturlListRequest request) {
        return urlBizService.getShorturlList(request.getKey(), request.getStatus(), request.getPaging());
    }

    @PostMapping("/createShorturl")
    @ApiOperation(value = "短链创建")
    public ShorturlCreateDTO createShorturl(@RequestBody @Validated ShorturlCreateRequest request) {
        String shorturl = urlBizService.createShorturl(request.getUrl(), request.getValidStart(), request.getValidEnd());
        return new ShorturlCreateDTO(shorturl);
    }

    @PostMapping("/updateShorturl")
    @ApiOperation(value = "短链更新")
    public void updateShorturl(@RequestBody @Validated ShorturlUpdateRequest request) {
        urlBizService.updateShorturl(request.getId(), request.getStatus(), request.getValidStart(), request.getValidEnd());
    }

    @GetMapping("/blocklistList")
    @ApiOperation(value = "链接黑名单列表")
    public PagingResult<BlocklistDTO> blocklistList(@ModelAttribute @Validated BlocklistListRequest request) {
        return urlBizService.getBlocklistList(request.getPaging());
    }

    @PostMapping("/addBlocklist")
    @ApiOperation(value = "链接黑名单新增")
    public void addBlocklist(@RequestBody @Validated BlocklistAddRequest request) {
        urlBizService.addBlocklist(request.getUrl(), request.getRemark());
    }

    @PostMapping("/removeBlocklist")
    @ApiOperation(value = "链接黑名单移除")
    public void removeBlocklist(@RequestBody @Validated BlocklistRemoveRequest request) {
        urlBizService.removeBlocklist(request.getId());
    }


}
