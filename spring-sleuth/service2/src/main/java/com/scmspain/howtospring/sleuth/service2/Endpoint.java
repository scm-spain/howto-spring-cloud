package com.scmspain.howtospring.sleuth.service2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class Endpoint {

    private static final Logger logger = LoggerFactory.getLogger(Endpoint.class);

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        logger.info("Entrando en /");
        return "Hello!";
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<String> listUsers() {
        Map<String, String> users = new HashMap<>();
        users.put("abc", "Alice");
        users.put("xyz", "Bob");
        logger.info("Entrando en /users");

        return new ArrayList<>(users.values());
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
    public String getUser(@PathVariable String userId) {
        logger.info("Entrando en /users");

        return userId;
    }
}
