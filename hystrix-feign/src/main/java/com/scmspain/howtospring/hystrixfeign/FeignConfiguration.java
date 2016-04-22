package com.scmspain.howtospring.hystrixfeign;

import feign.Contract;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jaxrs.JAXRSContract;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfiguration {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeignConfiguration.class);

    @Bean
    public Contract feignContract() {
        return new JAXRSContract();
    }

    @Bean
    @ConditionalOnClass(RequestInterceptor.class)
    RequestInterceptor userAgentInterceptor() {
        return new RequestInterceptor() {
            @Value("${spring.application.name}")
            private String serviceName;

            @Override
            public void apply(RequestTemplate template) {
                template.header("User-Agent", serviceName);
            }
        };
    }

    @Bean
    @ConditionalOnClass(RequestInterceptor.class)
    RequestInterceptor xForwardedForInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                template.header("X-Forwarded-For", attr.getRequest().getRemoteAddr());
            }
        };
    }

    @Bean
    public feign.Logger.Level feignLoggingLevel() {
        return feign.Logger.Level.BASIC;
    }

}
