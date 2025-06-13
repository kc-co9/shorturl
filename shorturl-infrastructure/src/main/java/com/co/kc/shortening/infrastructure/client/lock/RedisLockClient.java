package com.co.kc.shortening.infrastructure.client.lock;

import com.co.kc.shortening.application.client.LockClient;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author kc
 */
public class RedisLockClient implements LockClient {

    private static final Logger LOG = LoggerFactory.getLogger(RedisLockClient.class);

    private final RedissonClient redissonClient;

    public RedisLockClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean tryLock(String key, Long liveTime) {
        try {
            return redissonClient.getLock(key).tryLock(0, liveTime, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            LOG.error("RedisLockClient invoke tryLock method failure, key:{}", key, ex);
        }
        return false;
    }

    @Override
    public boolean tryLock(String key) {
        return redissonClient.getLock(key).tryLock();
    }

    /**
     * 尝试加锁
     *
     * @param key      key
     * @param liveTime 锁的缓存时长
     * @param waitTime 等待锁的时间
     * @return 是否锁定成
     */
    @Override
    public boolean tryLock(String key, Long liveTime, Long waitTime) {

        try {
            return redissonClient.getLock(key).tryLock(waitTime, liveTime, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            LOG.error("RedisLockClient invoke tryLock method failure, key:{}", key, ex);
        }
        return false;
    }

    @Override
    public boolean releaseLock(String key) {
        try {
            RLock lock = redissonClient.getLock(key);
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            } else {
                LOG.warn("RedisLockClient invoke tryLock method failure because of not existing or not holding , key:{}", key);
            }
        } catch (Exception ex) {
            // 解锁失败
            LOG.error("RedisLockClient invoke releaseLock method failure, key:{}", key, ex);
        }
        return true;
    }

}
