package com.scmspain.howtospring.jdbc.configuration;

import com.scmspain.howtospring.jdbc.InMemoryUserRepository;
import com.scmspain.howtospring.jdbc.JdbcUserRepository;
import com.scmspain.howtospring.jdbc.MyService;
import com.scmspain.howtospring.jdbc.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ApplicationFacadeDependencyInjection {
    @Bean
    @Profile("test")
    public UserRepository forUserRepositoryInTest() {
        return new InMemoryUserRepository();
    }

    @Bean
    @Profile({"dev", "pre", "pro"})
    public UserRepository forUserRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcUserRepository(jdbcTemplate);
    }

    @Bean
    public MyService forMyService(UserRepository userRepository) {
        return new MyService(userRepository);
    }
}
