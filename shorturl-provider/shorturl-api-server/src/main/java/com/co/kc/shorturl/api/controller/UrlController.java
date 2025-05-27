package com.co.kc.shorturl.api.controller;

import com.co.kc.shortening.application.service.appservice.ShorturlAppService;
import com.co.kc.shorturl.api.model.UrlPreviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author kc
 */
@RestController
public class UrlController {

    @Autowired
    private ShorturlAppService shorturlAppService;

    @GetMapping("/preview/{code}")
    public UrlPreviewDTO preview(@PathVariable(value = "code") String code) {
        String rawLink = shorturlAppService.redirect(code);
        return new UrlPreviewDTO(rawLink);
    }

    @GetMapping("/{code}")
    public void redirect(@PathVariable(value = "code") String code, HttpServletResponse response) {
        String rawLink = shorturlAppService.redirect(code);
        response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
        response.setHeader("Location", rawLink);
    }

}
