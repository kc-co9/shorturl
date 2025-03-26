package com.co.kc.shorturl.admin.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kc
 */
@Data
public class ShorturlCreateRequest {
    @NotBlank(message = "url不能为空")
    private String url;
}
