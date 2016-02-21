package com.scmspain.howtospring.hello;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InMemoryUserRepository {
    private Map<String, User> users = new HashMap<>();

    public void add(User user) {
        users.put(user.getId(), user);
    }

    public List findAll() {
        return new ArrayList<>(users.values());
    }

    public User findById(String userId) {
        return users.get(userId);
    }
}
