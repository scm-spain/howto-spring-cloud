# Spring Cloud with Cache

This is an example application to show how to integrate [JSR-107: Java Temporary Caching API](https://jcp.org/en/jsr/detail?id=107)

This integration uses ehcache as implementation but you can use the JSR-107 implementation of your choice.

## Running the application
To start playing around you need to build it and run it

```bash
./gradlew clean build bootRun
```

## Configuration
Parameters, like the port that this application is listening to, are placed in the application.yml file.

## Usage
Once the application is up and running, you can set some data 

```bash
$ curl -X POST -H "Content-Type: application/json" -d '{
    "id": "1",
    "name": "Ken",
    "lastName": "Masters"
}' "http://localhost:8000/users"
```

You can get the data inserted by id, it will take a while since a sleep to simulate a slow operation is used.
```bash
$ curl "http://localhost:8000/users/1"
```

Once the data is queried it will be cached so the next request for the same data will be cached.

If the data is modified, the cache for that id will be invalidated, so the next call will not hit the cache, so it will query for it and cached.
