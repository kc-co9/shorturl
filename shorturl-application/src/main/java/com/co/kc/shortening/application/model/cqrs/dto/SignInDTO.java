package com.co.kc.shortening.application.model.cqrs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户认证响应DTO
 *
 * @author kc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDTO {
    private Long userId;
    private String token;
}
