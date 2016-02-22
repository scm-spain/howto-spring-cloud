package com.scmspain.howtospring.reactive.configuration;

import com.scmspain.howtospring.reactive.InMemoryUserRepository;
import com.scmspain.howtospring.reactive.MyService;
import com.scmspain.howtospring.reactive.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationFacadeDependencyInjection {
    @Bean
    public UserRepository forUserRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    public MyService forMyService(UserRepository userRepository) {
        return new MyService(userRepository);
    }
}
