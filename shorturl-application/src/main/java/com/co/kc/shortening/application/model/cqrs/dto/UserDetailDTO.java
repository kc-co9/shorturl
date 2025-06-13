package com.co.kc.shortening.application.model.cqrs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDetailDTO
 *
 * @author kc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户名
     */
    private String userName;
}
