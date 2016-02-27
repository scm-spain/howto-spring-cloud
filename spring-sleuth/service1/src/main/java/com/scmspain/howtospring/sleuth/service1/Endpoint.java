package com.scmspain.howtospring.sleuth.service1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class Endpoint {

    private static final Logger logger = LoggerFactory.getLogger(Endpoint.class);

    private Service2Client service2Client;

    @Inject
    public Endpoint(Service2Client service2Client) {
        this.service2Client = service2Client;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        logger.info("Service1: Entrando en /");
        return "Hello!";
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<String> listUsers() {
        logger.info("Service1: Entrando en /users");

        return service2Client.getUsers();
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
    public String getUser(@PathVariable String userId) {
        logger.info("Service1: Entrando en /users");

        return service2Client.getUser(userId);
    }
}
