package com.co.kc.shortening.application.model.cqrs.dto;

import lombok.Data;

/**
 * 用户认证响应DTO
 *
 * @author kc
 */
@Data
public class SignInDTO {
    private Long userId;
    private String email;
    private String username;
}
