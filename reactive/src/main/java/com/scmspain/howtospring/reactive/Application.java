package com.scmspain.howtospring.reactive;

import com.scmspain.howtospring.reactive.configuration.ApplicationFacadeDependencyInjection;
import com.scmspain.howtospring.reactive.configuration.EndpointDependencyInjection;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Import;

@SpringCloudApplication
@Import({ApplicationFacadeDependencyInjection.class, EndpointDependencyInjection.class })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
