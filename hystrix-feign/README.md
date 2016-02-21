# HTTP calls from Spring Cloud using Feign
This is an example application to show how to use Feign (an http client with load balancing) to execute http calls from one service to another one, wrapping our calls with Hystrix so we can get the benefits of the circuit breaker pattern.

## Running the application
To start playing around you need to build it and run it

```bash
./gradlew clean build bootRun
```

## Configuration
Parameters ,like the port that this application is listening too, are placed in the application.yml file.
You can change the configuration for a single Hystrix command using the command name (by default ClassName.methodName), or change the configuration for all of them using the `default` command name.
Open application.yml to see an example.

## Usage
This application has no business logic.
It only calls another API, fetch the response and show it to us. So for this example to work we need to start the `discovery-eureka` application too. That's the API that will respond with user data to this application.
Once the application is up and running, let's say is running on port 8000, we can list users

```bash
$ curl -X GET -H "Accept: application/json" "http://localhost:8000/users"
```

The response will vary depending on the status of the other API thanks to Hystrix. If the API with the users data is working fine, we'll see the list of users. Otherwise we'll see the fallback response configured to be used when the other API is down.

## Feign
We use Feign to model the API that we want to call. Check the code for the UserServiceRestClient to see an example.
The `@FeignClient` annotation receives the name that the API used to be registered in Eureka, so the API is automatically discovered for us.
If that service has several instances running, Ribbon will do client side load balancing between them.

Notice that since we don't want to block the thread while making the HTTP call, we tell Feign that our client will return an Observable object.
So we can subscribe to the response of the HTTP client and do something whenever the List<User> is emitted.

## Hystrix
The fallback methods need to have the same signature as the normal methods.
Fallback methods can be Hystrix commands too, with their own fallback methods, chaining the fallback process.

## Hystrix Dashboard
By default, Hystrix exposes a few endpoints with useful information about the HTTP calls being made, like http://localhost:8000/metrics, or http://localhost:8000/hystrix.stream.

We can even enable the Hystrix dashboard to print charts with this information. To enable the dashboard in this app include the `@EnableHystrixDashboard` in your Spring application. Then you can go to http://localhost:8000/hystrix