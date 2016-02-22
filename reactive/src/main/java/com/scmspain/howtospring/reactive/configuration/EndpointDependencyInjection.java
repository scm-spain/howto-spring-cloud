package com.scmspain.howtospring.reactive.configuration;

import com.scmspain.howtospring.reactive.Endpoint;
import com.scmspain.howtospring.reactive.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointDependencyInjection {
    @Bean
    public Endpoint forEndpoint(MyService service) {
        return new Endpoint(service);
    }
}
