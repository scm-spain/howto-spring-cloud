# Spring Cloud Exception Handling Example

This is an example on how to use ControllerAdvice to handle exceptions and personalize response body.
More info: https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#using-controlleradvice-classes

## Running the application
To start playing around you need to build it and run it

```bash
./gradlew clean build bootRun
```

## Usage

Once app is running, try to do a GET` request:
```bash
$ curl -X GET "http://localhost:8000/users/1"
```

You should get a valid response:
```
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 15
Date: Thu, 10 Nov 2016 16:40:00 GMT

{"name": "wow"}
```

Now try to do a `POST` request to the same url:
```bash
$ curl -X POST -H "Content-Type: application/json" -d '{}' "http://localhost:8000/users/1"
```

You should get something like this:
```
curl -i -XPOST "http://localhost:8000/users/1"
HTTP/1.1 405 Method Not Allowed
Server: Apache-Coyote/1.1
Content-Type: text/plain;charset=UTF-8
Content-Length: 35
Date: Thu, 10 Nov 2016 16:38:27 GMT

{"message": "personalized message"}
```
