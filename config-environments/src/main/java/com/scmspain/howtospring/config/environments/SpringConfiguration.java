package com.scmspain.howtospring.config.environments;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class SpringConfiguration implements Configuration {
    @Inject
    private Environment env;

    @Override
    public boolean containsProperty(String key) {
        return env.containsProperty(key);
    }

    @Override
    public String getProperty(String key) {
        return getProperty(key, String.class);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return env.getProperty(key, defaultValue);
    }

    @Override
    public <T> T getProperty(String key, Class<T> targetValueType) {
        return env.getProperty(key, targetValueType);
    }

    @Override
    public <T> Class<T> getPropertyAsClass(String key, Class<T> targetValueType) {
        return env.getPropertyAsClass(key, targetValueType);
    }
}
