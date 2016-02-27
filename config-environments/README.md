# Spring Cloud configuration
This application demonstrate how to read configuration files from Spring Cloud.

Typically, you can directly read configuration values by annotating a class property with the `@Value` annotation. This will automatically assign that value from the config file to the class property.
This way you can read any value in the configuration file.
You can also use the `Environment` object to access the properties values programmatically.

The problem with both approaches is that you end up depending on Spring Cloud own classes. To overcome this problem, we've created a `Configuration` interface that will be part of our domain and that our domain services can depend on to read config values without depending on Spring classes. Then, we can easily create different implementations for that interface.

The class SpringConfiguration is an example implementation for the Configuration interface. So we can now inject this class wherever we need to access configuration files.

## Usage
There are several `docker-compose` files that you can use to start up the application using different configuration files depending on the environment. The application has 4 several endpoints demonstrating different ways of using configuration values
- GET /
- GET /msg
- GET /name
- GET /age

## Environments
It's pretty common to need different values for different environments (to use a different database host for example). You can suffix your property files with the name of the environment where you want to use those values.
To execute the application for an specific environment, pass a system property

```
java -jar -Dspring.profiles.active=dev config-environments-1.0.0-SNAPSHOT.jar
```

This way, the `application-dev.yml` file will be used.

There are different `docker-compose` files that you can use to easily load different environments.

## Precedence
You can pass [configuration values to Spring in several ways](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html). The precedence works as follows

1. Command line arguments.
2. Properties from SPRING_APPLICATION_JSON (inline JSON embedded in an environment variable or system property)
3. JNDI attributes from java:comp/env.
4. Java System properties (System.getProperties()).
5. OS environment variables.
6. A RandomValuePropertySource that only has properties in random.\*.
7. Profile-specific application properties outside of your packaged jar (application-{profile}.properties and YAML variants)
8. Profile-specific application properties packaged inside your jar (application-{profile}.properties and YAML variants)
9. Application properties outside of your packaged jar (application.properties and YAML variants).
10. Application properties packaged inside your jar (application.properties and YAML variants).
11. `@PropertySource` annotations on your `@Configuration` classes.
12. Default properties (specified using SpringApplication.setDefaultProperties).


## Spring ways of loading config values
[Learn more ways of loading config values](http://www.mkyong.com/spring/spring-propertysources-example/).
