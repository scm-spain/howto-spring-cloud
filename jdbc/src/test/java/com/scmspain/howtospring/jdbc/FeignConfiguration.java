package com.scmspain.howtospring.jdbc;

import feign.Contract;
import feign.jaxrs.JAXRSContract;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeignConfiguration.class);

    @Bean
    public Contract feignContract() {
        return new JAXRSContract();
    }

}
