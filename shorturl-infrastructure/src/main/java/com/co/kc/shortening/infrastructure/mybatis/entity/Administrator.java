package com.co.kc.shortening.infrastructure.mybatis.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 管理者(Administrator)实体类
 *
 * @author makejava
 * @since 2025-04-02 10:45:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Administrator extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -23720920342386229L;
    /**
     * 管理者ID
     */
    private Long administratorId;
    /**
     * 管理者邮箱
     */
    private String email;
    /**
     * 管理者用户名
     */
    private String username;
    /**
     * 管理者密码
     */
    private String password;
}

