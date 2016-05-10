package com.scmspain.howtospring.hystrixfeign;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationDependencyInjection {
    @Bean
    public MyService forMyService(UserServiceRestClient userClient) {
        return new MyService(userClient);
    }
}
