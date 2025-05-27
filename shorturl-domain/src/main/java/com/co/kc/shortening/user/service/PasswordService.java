package com.co.kc.shortening.user.service;

import com.co.kc.shortening.user.domain.model.UserPassword;
import com.co.kc.shortening.user.domain.model.UserRawPassword;

/**
 * 密码领域服务
 *
 * @author kc
 */
public interface PasswordService {

    /**
     * 加密密码
     *
     * @param rawPassword 原生密码
     * @return 加密密码
     */
    UserPassword encrypt(UserRawPassword rawPassword);

    /**
     * 校验密码
     *
     * @param rawPassword 原生密码
     * @param password    加密后的密码
     * @return 是否匹配
     */
    boolean verify(UserRawPassword rawPassword, UserPassword password);
}
