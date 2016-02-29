package com.scmspain.howtospring.jdbc;

import rx.Observable;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private Map<String, User> users;

    public InMemoryUserRepository() {
        this.users = new HashMap<>();
        this.users.put("abcd", new User("abcd", "Jonh", "Doe"));
        this.users.put("xyz", new User("xyz", "Alice", "Wonderland"));
    }

    @Override
    public void add(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public Observable<User> findAll() {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                users.values().forEach(user -> subscriber.onNext(user));
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<User> findById(String userId) {
        return Observable.just(users.get(userId));
    }

    @Override
    public Observable<User> findByName(String name) {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                users.values().stream().filter(user1 -> user1.getName().equals(name)).forEach(user -> subscriber.onNext(user));
                subscriber.onCompleted();
            }
        });
    }
}
