package com.co.kc.shortening.web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlPreviewDTO {
    /**
     * 原始链接
     */
    private String url;
}
