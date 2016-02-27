package com.scmspain.howtospring.config.environments;

public interface Configuration {
    boolean containsProperty(String key);

    String getProperty(String key);

    String getProperty(String key, String defaultValue);

    <T> T getProperty(String key, Class<T> targetValueType);

    <T> Class<T> getPropertyAsClass(String key, Class<T> targetValueType);
}
