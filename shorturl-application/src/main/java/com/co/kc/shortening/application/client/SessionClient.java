package com.co.kc.shortening.application.client;

import com.co.kc.shortening.application.model.client.SessionDTO;

import java.util.concurrent.TimeUnit;

/**
 * Session组件
 *
 * @author kc
 */
public interface SessionClient {
    /**
     * 查询session信息
     *
     * @param sessionId session ID
     * @return session 信息
     */
    SessionDTO get(String sessionId);

    /**
     * 存储session信息
     *
     * @param sessionId  session ID
     * @param sessionDTO session 信息
     * @param expired    过期时间
     * @param timeUnit   时间单位
     */
    void save(String sessionId, SessionDTO sessionDTO, Long expired, TimeUnit timeUnit);

    /**
     * 登出session
     *
     * @param sessionId session ID
     */
    void remove(String sessionId);
}
