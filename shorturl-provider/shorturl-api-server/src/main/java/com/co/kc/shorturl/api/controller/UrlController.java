package com.co.kc.shorturl.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kc
 */
@RestController
public class UrlController {

    @GetMapping("/preview/{code}")
    public void preview(@PathVariable(value = "code") String code) {
    }

    @GetMapping("/{code}")
    public void redirect(@PathVariable(value = "code") String code) {
    }

}
