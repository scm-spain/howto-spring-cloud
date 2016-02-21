package com.scmspain.howtospring.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/")
public class Endpoint {

    private final InMemoryUserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(Endpoint.class);

    @Inject
    public Endpoint(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        return "Welcome to your Spring Cloud project!";
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List listUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userRepository.add(user);
//        return new ResponseEntity<>(user, null, HttpStatus.CREATED);
        return ResponseEntity
                .created(URI.create("/users/1"))
                .body(user);
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
    public User showUser(@PathVariable String userId) {
        return userRepository.findById(userId);
    }
}
