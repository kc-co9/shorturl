package com.co.kc.shortening.application.model.cqrs.dto;

import lombok.Data;

/**
 * UserDetailDTO
 *
 * @author kc
 */
@Data
public class UserDetailDTO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
}
