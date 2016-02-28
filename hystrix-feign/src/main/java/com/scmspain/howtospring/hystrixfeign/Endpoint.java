package com.scmspain.howtospring.hystrixfeign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/")
public class Endpoint {

    private static final Logger logger = LoggerFactory.getLogger(Endpoint.class);
    private MyService myService;

    @Inject
    public Endpoint(MyService myService) {
        this.myService = myService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        return "Welcome to your Spring Cloud project!";
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> listUsers() {
        return myService.getUsersFromAnotherService().toBlocking().first();
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = myService.createUser(user).toBlocking().first();
        return ResponseEntity
                .created(URI.create("/users/" + createdUser.getId()))
                .body(createdUser);
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
    public User showUser(@PathVariable String userId) {
        return myService.getUser(userId).toBlocking().first();
    }
}
