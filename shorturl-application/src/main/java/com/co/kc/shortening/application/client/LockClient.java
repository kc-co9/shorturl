package com.co.kc.shortening.application.client;

/**
 * 锁客户端
 *
 * @author kc
 */
public interface LockClient {
    /**
     * 尝试加锁
     *
     * @param key key
     * @return 是否锁定成功
     */
    boolean tryLock(String key);

    /**
     * 尝试加锁
     *
     * @param key      key
     * @param liveTime 锁的缓存时长
     * @return 是否锁定成功
     */
    boolean tryLock(String key, Long liveTime);

    /**
     * 尝试加锁
     *
     * @param key      key
     * @param liveTime 锁的缓存时长
     * @param waitTime 等待锁的时间
     * @return 是否锁定成功
     * @throws InterruptedException 中断异常
     */
    boolean tryLock(String key, Long liveTime, Long waitTime) throws InterruptedException;

    /**
     * 释放锁
     *
     * @param key key
     * @return 是否释放成功
     */
    boolean releaseLock(String key);
}
