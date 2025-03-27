package com.co.kc.shorturl.admin.controller;

import com.co.kc.shorturl.admin.model.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kc
 */
@RestController
@RequestMapping("/url")
public class UrlController {

    @GetMapping("/shorturlList")
    public ShorturlListResponse shorturlList() {
        return null;
    }

    @PostMapping("/createShorturl")
    public ShorturlCreateResponse createShorturl(@RequestBody @Validated ShorturlCreateRequest request) {
        return null;
    }

    @GetMapping("/blacklistList")
    public BlacklistListResponse blacklistList() {
        return null;
    }

    @PostMapping("/addBlacklist")
    public void addBlacklist(@RequestBody @Validated BlacklistAddRequest request) {
    }

}
