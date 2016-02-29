package com.scmspain.howtospring.jdbc;

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
    public Observable<User> listUsers(String name){
        if (name != null) {
            return this.userRepository.findByName(name);
        }

        return this.userRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "defaultUser")
    public Observable<User> showUser(String userId){
        return this.userRepository.findById(userId);
    }

    public void createUser(User user){
        userRepository.add(user);
    }

    public Observable<User> defaultUsers(String name){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("abc", "Default", "User"));

        return Observable.from(users);
    }

    public Observable<User> defaultUser(String userId){
        return Observable.just(new User("abc", "Default", "User"));
    }
}
