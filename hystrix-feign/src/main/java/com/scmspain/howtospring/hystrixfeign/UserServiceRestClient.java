package com.scmspain.howtospring.hystrixfeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rx.Observable;

import java.util.List;

@FeignClient(name = "discovery-eureka", configuration = FeignConfiguration.class)
public interface UserServiceRestClient {

        @RequestMapping(value = "/users", method = RequestMethod.GET)
        Observable<List<User>> getUsers();

        @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
        Observable<User> getUser(@PathVariable("userId") String userId);

}
