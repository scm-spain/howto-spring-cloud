package com.scmspain.howtospring.reactive;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import rx.Observable;

import javax.inject.Inject;
import java.util.ArrayList;

public class MyService {

    private UserRepository userRepository;

    @Inject
    public MyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @HystrixCommand(fallbackMethod = "defaultUsers")
    public Observable<User> listUsers(){
        return this.userRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "defaultUser")
    public Observable<User> showUser(String userId){
        return this.userRepository.findById(userId);
    }

    public Observable<User> addUser(String userId, String name, String lastName) {
        return Observable.just(new User(userId, name, lastName))
            .doOnNext(userRepository::persist);
    }

    public Observable<User> defaultUsers(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("abc", "Default", "User"));

        return Observable.from(users);
    }

    public Observable<User> defaultUser(String userId){
        return Observable.just(new User("abc", "Default", "User"));
    }
}
