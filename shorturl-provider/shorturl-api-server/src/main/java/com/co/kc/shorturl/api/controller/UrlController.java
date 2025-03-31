package com.co.kc.shorturl.api.controller;

import com.co.kc.shorturl.api.model.UrlPreviewDTO;
import com.co.kc.shorturl.api.service.UrlBizService;
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
    private UrlBizService urlBizService;

    @GetMapping("/preview/{key}")
    public UrlPreviewDTO preview(@PathVariable(value = "key") String key) {
        String url = urlBizService.parseShorturl(key);
        return new UrlPreviewDTO(url);
    }

    @GetMapping("/{key}")
    public void redirect(@PathVariable(value = "key") String key, HttpServletResponse response) {
        String url = urlBizService.parseShorturl(key);
        response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
        response.setHeader("Location", url);
    }

}
