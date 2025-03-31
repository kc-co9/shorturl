package com.co.kc.shorturl.admin.controller;

import com.co.kc.shorturl.admin.model.dto.*;
import com.co.kc.shorturl.admin.service.UrlBizService;
import com.co.kc.shorturl.common.model.PagingResult;
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
    public PagingResult<ShorturlDTO> shorturlList(@ModelAttribute @Validated ShorturlListRequest request) {
        return urlBizService.getShorturlList(request.getKey(), request.getStatus(), request.getPaging());
    }

    @PostMapping("/createShorturl")
    public ShorturlCreateResponse createShorturl(@RequestBody @Validated ShorturlCreateRequest request) {
        String shorturl = urlBizService.createShorturl(request.getUrl(), request.getValidStart(), request.getValidEnd());
        return new ShorturlCreateResponse(shorturl);
    }

    @GetMapping("/blacklistList")
    public PagingResult<BlacklistDTO> blacklistList(@ModelAttribute @Validated BlacklistListRequest request) {
        return urlBizService.getBlacklistList(request.getPaging());
    }

    @PostMapping("/addBlacklist")
    public void addBlacklist(@RequestBody @Validated BlacklistAddRequest request) {
        urlBizService.addBlacklist(request.getUrl(), request.getRemark());
    }

    @PostMapping("/removeBlacklist")
    public void removeBlacklist(@RequestBody @Validated BlacklistRemoveRequest request) {
        urlBizService.removeBlacklist(request.getId());
    }


}
