package com.scmspain.howtospring.reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import javax.inject.Inject;

@RequestMapping(path = "/")
@ResponseBody
@CrossOrigin
public class Endpoint {

    private static final Logger logger = LoggerFactory.getLogger(Endpoint.class);
    private MyService myService;

    @Inject
    public Endpoint(MyService myService) {
        this.myService = myService;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public Observable<User> listUsers() {
        return myService.listUsers();
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
    public Observable<User> showUser(@PathVariable String userId) {
        return myService.showUser(userId);
    }
}
