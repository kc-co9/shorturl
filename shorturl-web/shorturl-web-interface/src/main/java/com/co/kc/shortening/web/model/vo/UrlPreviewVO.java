package com.co.kc.shortening.web.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlPreviewVO {
    /**
     * 原始链接
     */
    private String url;
}
