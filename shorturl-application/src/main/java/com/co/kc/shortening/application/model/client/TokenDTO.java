package com.co.kc.shortening.application.model.client;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class TokenDTO {
    private Long userId;
    private LocalDateTime createTime;
}
