package com.co.kc.shortening.application.model.client;

import lombok.Data;

import java.util.List;

/**
 * Session传输对象
 *
 * @author kc
 */
@Data
public class SessionDTO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户角色ID
     */
    private List<Long> roleIdList;
    /**
     * 用户权限ID
     */
    private List<Long> permissionIdList;
    /**
     * 用户权限值
     */
    private List<String> permissionValueList;
}
