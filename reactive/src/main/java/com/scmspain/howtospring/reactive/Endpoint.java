package com.scmspain.howtospring.reactive;

import org.springframework.web.bind.annotation.*;
import rx.Observable;

import javax.inject.Inject;

@RequestMapping(path = "/")
@ResponseBody
public class Endpoint {

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
