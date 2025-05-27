package com.co.kc.shortening.application.model.cqrs.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户DTO
 *
 * @author kc
 */
@Data
public class UserQueryDTO {
    /**
     * 管理者ID
     */
    private Long userId;

    /**
     * 管理者账号
     */
    private String email;

    /**
     * 管理者用户名
     */
    private String username;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
