# Spring Cloud Netflix Hello World

This is an example application to show the basics of Spring Cloud using Netflix tools. In this example we are using Swagger to generate documentation, and Logback to get a nice access log.
The application expose 4 different url's, defined on the Endpoint class, which let us create/get users. Users are stored in memory using the InMemoryUserRepository class.

## Running the application
To start playing around you need to build it and run it

```bash
./gradlew clean build bootRun
```

## Configuration
Parameters ,like the port that this application is listening too, are placed in the application.yml file.

## Usage
Once the application is up and running, let's say is running on port 8000, you can start creating users

```bash
$ curl -X POST -H "Content-Type: application/json" -d '{
    "id": "abc",
    "name": "Alice",
    "lastname": "Wonderland"
}' "http://localhost:8000/users"
```

This will create Alice in our memory database. Let's see if everything worked as expected visiting

```bash
$ curl -X GET -H "Accept: application/json" "http://localhost:8000/users"
```

Notice that you get a JSON with a list of all the existing users, but we could get an XML with only Alice

```bash
$ curl -X GET -H "Accept: application/xml" "http://localhost:8000/users/abc"
```

Let's create another user


```bash
$ curl -X POST -H "Content-Type: application/json" -d '{
    "id": "xyz",
    "name": "John",
    "lastname": "Doe"
}' "http://localhost:8000/users"
```

And list them again

```bash
$ curl -X GET "http://localhost:8000/users"
```

## Documentation with Swagger
In this example we are also showing how to use Swagger to document our Spring Cloud application. To auto generate our documentation we need [the Swagger dependency](http://mvnrepository.com/artifact/io.springfox/springfox-swagger2).
We just need to include a new Bean configuring Swagger. Then, Swagger will detect our Spring Cloud annotations for endpoints and automatically generate our documentation in the following url http://localhost:8000/v2/api-docs.

[Learn how to use it](https://springfox.github.io/springfox/docs/snapshot/).

We can even include [the Swagger UI dependency](http://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui), and we'll expose the Swagger UI in this application on http://localhost:8000/swagger-ui.html

## Logging with Logback
This application is currently logging everything to stdout using [Logback](http://logback.qos.ch/) through [SLF4J](http://www.slf4j.org/). There is also [a library that we use](https://github.com/akihyro/spring-boot-ext-logback-access/) to be able to get an access log from Tomcat.
