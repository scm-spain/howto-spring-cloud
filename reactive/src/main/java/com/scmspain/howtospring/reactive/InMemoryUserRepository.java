package com.scmspain.howtospring.reactive;

import org.springframework.stereotype.Service;
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
}
