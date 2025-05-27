package com.co.kc.shortening.application.model.cqrs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author kc
 */
@Data
@AllArgsConstructor
public class ShorturlDTO {
    /**
     * 短链ID
     */
    private Long shortId;
    /**
     * 短链
     */
    private String shorturl;
}
