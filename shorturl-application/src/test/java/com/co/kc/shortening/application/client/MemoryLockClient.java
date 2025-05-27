package com.co.kc.shortening.application.client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 *
 * @author kc
 */
public class MemoryLockClient implements LockClient {
    private final Map<String, ReentrantLock> multiLock = new HashMap<>();
    private final Map<String, Long> multiExpired = new HashMap<>();

    @Override
    public synchronized boolean tryLock(String key) {
        ReentrantLock lock = multiLock.computeIfAbsent(key, k -> new ReentrantLock());
        if (lock.isLocked() && multiExpired.containsKey(key)) {
            Long expired = multiExpired.get(key);
            if (expired - System.currentTimeMillis() <= 0) {
                forceUnlock(lock);
                multiExpired.remove(key);
            }
        }
        return lock.tryLock();
    }

    @Override
    public synchronized boolean tryLock(String key, Long liveTime) {
        ReentrantLock lock = multiLock.computeIfAbsent(key, k -> new ReentrantLock());
        if (lock.isLocked() && multiExpired.containsKey(key)) {
            Long expired = multiExpired.get(key);
            if (expired - System.currentTimeMillis() <= 0) {
                forceUnlock(lock);
                multiExpired.remove(key);
            }
        }

        boolean hasLock = lock.tryLock();
        if (hasLock) {
            multiExpired.put(key, System.currentTimeMillis() + liveTime * 1000);
        }
        return hasLock;
    }

    @Override
    public synchronized boolean tryLock(String key, Long liveTime, Long waitTime) throws InterruptedException {
        ReentrantLock lock = multiLock.computeIfAbsent(key, k -> new ReentrantLock());
        if (lock.isLocked() && multiExpired.containsKey(key)) {
            Long expired = multiExpired.get(key);
            if (expired - System.currentTimeMillis() <= 0) {
                forceUnlock(lock);
                multiExpired.remove(key);
            }
        }

        // 当前实现采用了被动过期的策略，因此可能会存在上一个锁过期了，但当前锁等待超时也获取不到锁的情况。
        boolean hasLock = lock.tryLock(waitTime, TimeUnit.SECONDS);
        if (hasLock) {
            multiExpired.put(key, System.currentTimeMillis() + liveTime * 1000);
        }
        return hasLock;
    }

    @Override
    public synchronized boolean releaseLock(String key) {
        ReentrantLock lock = multiLock.computeIfAbsent(key, k -> new ReentrantLock());
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            forceUnlock(lock);
            multiLock.remove(key);
            multiExpired.remove(key);
            return true;
        }
        return false;
    }

    private void forceUnlock(ReentrantLock lock) {
        try {
            // 获取AQS同步器
            Field syncField = ReentrantLock.class.getDeclaredField("sync");
            syncField.setAccessible(true);
            AbstractOwnableSynchronizer sync = (AbstractOwnableSynchronizer) syncField.get(lock);
            Method setExclusiveOwnerThreadMethod =
                    AbstractOwnableSynchronizer.class.getDeclaredMethod("setExclusiveOwnerThread", Thread.class);
            setExclusiveOwnerThreadMethod.setAccessible(true);
            setExclusiveOwnerThreadMethod.invoke(sync, Thread.currentThread());
            lock.unlock();
        } catch (Exception e) {
            throw new RuntimeException("强制解锁失败", e);
        }
    }
}
