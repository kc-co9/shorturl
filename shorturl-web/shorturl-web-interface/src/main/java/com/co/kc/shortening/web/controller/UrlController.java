package com.co.kc.shortening.web.controller;

import com.co.kc.shortening.application.service.appservice.ShorturlAppService;
import com.co.kc.shortening.web.model.vo.UrlPreviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author kc
 */
@RestController
@RequiredArgsConstructor
public class UrlController {
    private final ShorturlAppService shorturlAppService;

    @GetMapping("/preview/{code}")
    public UrlPreviewVO preview(@PathVariable(value = "code") String code) {
        String rawLink = shorturlAppService.redirect(code);
        return new UrlPreviewVO(rawLink);
    }

    @GetMapping("/{code}")
    public void redirect(@PathVariable(value = "code") String code, HttpServletResponse response) {
        String rawLink = shorturlAppService.redirect(code);
        response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
        response.setHeader("Location", rawLink);
    }

}
