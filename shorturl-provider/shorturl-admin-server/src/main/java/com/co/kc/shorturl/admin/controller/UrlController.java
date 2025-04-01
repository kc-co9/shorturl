package com.co.kc.shorturl.admin.controller;

import com.co.kc.shorturl.admin.model.dto.request.*;
import com.co.kc.shorturl.admin.model.dto.response.BlacklistDTO;
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

    @GetMapping("/blacklistList")
    @ApiOperation(value = "链接黑名单列表")
    public PagingResult<BlacklistDTO> blacklistList(@ModelAttribute @Validated BlacklistListRequest request) {
        return urlBizService.getBlacklistList(request.getPaging());
    }

    @PostMapping("/addBlacklist")
    @ApiOperation(value = "链接黑名单新增")
    public void addBlacklist(@RequestBody @Validated BlacklistAddRequest request) {
        urlBizService.addBlacklist(request.getUrl(), request.getRemark());
    }

    @PostMapping("/removeBlacklist")
    @ApiOperation(value = "链接黑名单移除")
    public void removeBlacklist(@RequestBody @Validated BlacklistRemoveRequest request) {
        urlBizService.removeBlacklist(request.getId());
    }


}
