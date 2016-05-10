package com.scmspain.howtospring.hystrixfeign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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

    @HystrixCommand()
    public Observable<User> createUser(User user){
//      return userServiceRestClient.saveUser(user, "AuthorizationTokenAbcd1234");

        try {
            ObjectMapper mapper = new ObjectMapper();
//          return userServiceRestClient.saveUserWithExplicitBody("{\"id\": \"" + user.getId() + "\", \"name\": \"" + user.getName() + "\", \"lastname\": \"" + user.getLastname() + "\"}");
            return userServiceRestClient.saveUserWithExplicitBody(mapper.writeValueAsString(user), "Some Value for Header");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Observable.empty();
        }
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
