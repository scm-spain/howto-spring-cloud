package com.scmspain.howtospring.hystrixfeign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rx.Observable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyService {
    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    private UserServiceRestClient userServiceRestClient;

    @Inject
    public MyService(UserServiceRestClient userServiceRestClient) {
        this.userServiceRestClient = userServiceRestClient;
    }

    @HystrixCommand(fallbackMethod = "defaultUsers")
    public Observable<List<User>> getUsersFromAnotherService(){
        return this.userServiceRestClient.getUsers();
    }

    @HystrixCommand(fallbackMethod = "defaultUser")
    public Observable<User> getUser(String userId){
        return this.userServiceRestClient.getUser(userId);
    }

    public Observable<List<User>> defaultUsers(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("abc", "Default", "User"));

        return Observable.just(users);
    }

    public Observable<User> defaultUser(String userId){
        return Observable.just(new User("abc", "Default", "User"));
    }
}
