# Using JDBC Template in Spring Cloud
In this example we show how to use JDBC Template inside a Spring Cloud application. The API exposes endpoints to work with users that are saved in a repository

# Usage
Start up the application

```bash
$ docker-compose up -d
```

First we need to check which users are already in the repository:

```bash
$ curl -i -XGET "http://192.168.99.100:8000/users"
```

It may be empty, so let's create a new user

```bash
$ curl -i -XPOST -H "Content-Type: application/json" -d '{"id": "123", "name": "Naruto", "lastname": "Uzumaki"}' "http://192.168.99.100:8000/users"
```

And then see if it was created correctly

```bash
$ curl -i -XGET "http://192.168.99.100:8000/users"
```

Or get the details of the user

```bash
$ curl -i -XGET "http://192.168.99.100:8000/users/123"
```

We can even filter by name

```bash
$ curl -i -XGET "http://192.168.99.100:8000/users?name=Naruto"
```

## Configuration
There are two different `UserRepository` implementations: one of them is an in-memory repository; and a JDBC repository using PostgreSQL. We select one of the repositories based on the profile.
There are two `docker-compose` files so you can select the profile to use (and so the repository implementation). To use the in-memory repository

```bash
$ docker-compose -f docker-compose-test.yml up -d
```

To use the PostgreSQL repository

```bash
$ docker-compose -f docker-compose.yml up -d
```

The configuration to connect to the database from our application is defined in the `application.yml` file.

## Dependencies
Please, notice that we've added HikariCP dependency in our `build.gradle`.

## PostgreSQL Container
We use the official PostgreSQL Docker container to start a local database. The table that we use is defined in the `docker-entrypoint-initdb.d/database.sql` file, that is loaded when the container is created.

The database username, password and database name are defined passing environment variables when we run the container. Take a look at the `docker-compose.yml` file see an example.

## Integration Tests
We have a simple End-to-End test that send HTTP requests to our application to check whether or not is working as expected. These tests need both the application listening requests and the database that saves users. We use `docker-compose` to start the full stack.

```bash
./gradlew clean build # Compile the project
docker-compose -f docker-compose-integration.yml up -d # Start the stack
docker-compose -f docker-compose-integration.yml run app gradle integrationTests # Execute gradle tasks for tests
docker-compose -f docker-compose-integration.yml down # Remove the containers
```

There is a `integration-tests.sh` script that you can run to execute these tests easily.

You can use this strategy to execute all kinds of integration tests: DAO tests, HTTP tests, consumers tests, etc