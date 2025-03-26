package com.co.kc.shorturl.admin.controller;

import com.co.kc.shorturl.admin.model.ShorturlCreateRequest;
import com.co.kc.shorturl.admin.model.ShorturlCreateResponse;
import com.co.kc.shorturl.admin.model.ShorturlListResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kc
 */
@RestController
public class UrlController {
    @GetMapping("/shorturlList")
    public ShorturlListResponse shorturlList() {
        return null;
    }

    @PostMapping("/createShorturl")
    public ShorturlCreateResponse createShorturl(@RequestBody @Validated ShorturlCreateRequest request) {
        return null;
    }
}
