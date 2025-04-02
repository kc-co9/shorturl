package com.co.kc.shorturl.admin.controller;

import com.co.kc.shorturl.admin.model.dto.request.*;
import com.co.kc.shorturl.admin.model.dto.response.ShorturlCreateDTO;
import com.co.kc.shorturl.admin.model.dto.response.ShorturlDTO;
import com.co.kc.shorturl.admin.security.annotation.Auth;
import com.co.kc.shorturl.admin.service.ShorturlBizService;
import com.co.kc.shorturl.common.model.io.PagingResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kc
 */
@RestController
@RequestMapping("/shorturl")
public class ShorturlController {

    @Autowired
    private ShorturlBizService shorturlBizService;

    @Auth
    @GetMapping("/v1/shorturlList")
    @ApiOperation(value = "短链列表")
    public PagingResult<ShorturlDTO> shorturlList(@ModelAttribute @Validated ShorturlListRequest request) {
        return shorturlBizService.getShorturlList(request.getKey(), request.getStatus(), request.getPaging());
    }

    @Auth
    @PostMapping("/v1/createShorturl")
    @ApiOperation(value = "短链创建")
    public ShorturlCreateDTO createShorturl(@RequestBody @Validated ShorturlCreateRequest request) {
        String shorturl = shorturlBizService.createShorturl(request.getUrl(), request.getValidStart(), request.getValidEnd());
        return new ShorturlCreateDTO(shorturl);
    }

    @Auth
    @PostMapping("/v1/updateShorturl")
    @ApiOperation(value = "短链更新")
    public void updateShorturl(@RequestBody @Validated ShorturlUpdateRequest request) {
        shorturlBizService.updateShorturl(request.getId(), request.getStatus(), request.getValidStart(), request.getValidEnd());
    }

}
