package com.scmspain.howtospring.sleuth.service1;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "sleuth-service2")
public interface Service2Client {

        @RequestMapping(value = "/users", method = RequestMethod.GET)
        List<String> getUsers();

        @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
        String getUser(@PathVariable("userId") String userId);

}
