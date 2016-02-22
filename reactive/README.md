# Asynchronous/Reactive responses with Spring Cloud
This is an example application to show how to return reactive responses from Spring Cloud.
The application expose a memory list of two users through the /users endpoint, but instead of returning a List<User>, our Service is reactive and returns an Observable<User>.

## Running the application
To start playing around you need to build it and run it

```bash
./gradlew clean build bootRun
```

## Configuration
Parameters ,like the port that this application is listening too, are placed in the application.yml file.

## Usage
Once the application is up and running, let's say is running on port 8002, let's see if everything worked as expected

```bash
$ curl -X GET -H "Accept: application/json" "http://localhost:8002/users"
```

We'll see a list of users, but the response was a non-blocking response. For this to work, we need a few tweaks.
First, we need the `@EnableAsync` annotation to tell Spring that we want asynchronous responses.
Then, we want to transform our Observable response into a Spring `DeferredResult`. We could do it manually in all our endpoints, or use [the RxJava Spring Cloud Started library](https://github.com/jmnarloch/rxjava-spring-boot-starter/) that does exactly the same.

[I think that until next Spring release](https://github.com/jmnarloch/rxjava-spring-boot-starter/issues/1#issuecomment-186657775), we need a last tweet adding the following annotation `@SpringBootApplication(exclude = RxJavaAutoConfiguration.class)` in our Application.
