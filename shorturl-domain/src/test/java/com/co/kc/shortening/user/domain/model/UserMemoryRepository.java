package com.co.kc.shortening.user.domain.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kc
 */
public class UserMemoryRepository implements UserRepository {
    private final Map<UserId, User> idUserMemory = new ConcurrentHashMap<>();
    private final Map<UserEmail, User> emailUserMemory = new ConcurrentHashMap<>();

    @Override
    public User find(UserId userId) {
        return idUserMemory.get(userId);
    }

    @Override
    public User find(UserEmail email) {
        return emailUserMemory.get(email);
    }

    @Override
    public void save(User user) {
        idUserMemory.put(user.getUserId(), user);
        emailUserMemory.put(user.getEmail(), user);
    }

    @Override
    public void remove(User user) {
        idUserMemory.remove(user.getUserId());
        emailUserMemory.remove(user.getEmail());
    }
}
