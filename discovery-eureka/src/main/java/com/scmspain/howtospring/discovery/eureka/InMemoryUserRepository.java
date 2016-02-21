package com.scmspain.howtospring.discovery.eureka;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryUserRepository {
    private Map<String, User> users;

    public InMemoryUserRepository() {
        this.users = new HashMap<>();
        this.users.put("abcd", new User("abcd", "Jonh", "Doe"));
        this.users.put("vxyz", new User("vxyz", "Alice", "Wonderland"));
    }

    public List findAll() {
        return new ArrayList<>(users.values());
    }

    public User findById(String userId) {
        return users.get(userId);
    }
}
