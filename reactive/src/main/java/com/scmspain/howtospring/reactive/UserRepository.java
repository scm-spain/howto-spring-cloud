package com.scmspain.howtospring.reactive;

import rx.Observable;

public interface UserRepository {
    Observable<User> findAll();

    Observable<User> findById(String userId);
}
