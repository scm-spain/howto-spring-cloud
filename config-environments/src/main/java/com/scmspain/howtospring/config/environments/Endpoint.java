package com.scmspain.howtospring.config.environments;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(path = "/")
public class Endpoint {

    private static final Logger logger = LoggerFactory.getLogger(Endpoint.class);
    private final Configuration config;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${mydata.user.name}")
    private String userName;

    @Inject
    public Endpoint(Configuration configuration) {
        this.config = configuration;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        return "Hello " + userName + " from the application called '" + applicationName + "'";
    }

    @RequestMapping(path = "/name", method = RequestMethod.GET)
    public String name() {
        logger.info(config.getProperty("mydata.user.name", "default name"));
        return config.getProperty("mydata.user.name", "default name");
    }

    @RequestMapping(path = "/age", method = RequestMethod.GET)
    public Integer age() {
        logger.info(config.getProperty("mydata.user.age", "default age"));
        return config.getProperty("mydata.user.age", Integer.class);
    }

    @RequestMapping(path = "/msg", method = RequestMethod.GET)
    public String msg() {
        logger.info(config.getProperty("mydata.user.message"));
        return config.getProperty("mydata.message");
    }
}
