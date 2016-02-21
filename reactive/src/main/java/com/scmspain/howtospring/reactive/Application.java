package com.scmspain.howtospring.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.rx.RxJavaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableEurekaClient()
@SpringBootApplication(exclude = RxJavaAutoConfiguration.class)
@SpringCloudApplication()
@EnableFeignClients
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
