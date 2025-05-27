package com.co.kc.shortening.user.domain.model;

/**
 * 用户-资源库
 *
 * @author kc
 */
public interface UserRepository {
    /**
     * 根据userId查找用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    User find(UserId userId);

    /**
     * 根据邮箱查找用户
     *
     * @param email 邮箱
     * @return 用户
     */
    User find(UserEmail email);

    /**
     * 保存用户
     *
     * @param user 用户
     */
    void save(User user);

    /**
     * 移除用户
     *
     * @param user 用户
     */
    void remove(User user);
}
