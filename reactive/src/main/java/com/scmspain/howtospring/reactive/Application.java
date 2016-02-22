package com.scmspain.howtospring.reactive;

import com.scmspain.howtospring.reactive.configuration.ApplicationFacadeDependencyInjection;
import com.scmspain.howtospring.reactive.configuration.EndpointDependencyInjection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.rx.RxJavaAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableEurekaClient()
@SpringBootApplication(exclude = RxJavaAutoConfiguration.class)
@SpringCloudApplication()
@EnableFeignClients
@EnableAsync
@Import({ApplicationFacadeDependencyInjection.class, EndpointDependencyInjection.class })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
