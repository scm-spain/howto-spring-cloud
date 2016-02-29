package com.scmspain.howtospring.jdbc;

import rx.Observable;

public interface UserRepository {
    void add(User user);

    Observable<User> findAll();

    Observable<User> findById(String userId);

    Observable<User> findByName(String name);
}
