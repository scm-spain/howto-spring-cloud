# Distributed Tracing with Spring Sleuth and Zipkin
In this module we use Spring Sleuth to instrumentalize two different Spring Cloud application so they send their tracing information to Kafka, so we can later analyze them using the Zipkin UI.

## Zipkin
[Zipkin](https://twitter.github.io/zipkin/) is a component for distributed tracing composed by different components:
- Zipkin UI: Frontend application that calls to the Query API
- Zipkin Query API: REST API exposing endpoints with information about traces. Store traces information in its own database
- Zipkin Collector: Collects information about traces saving it into the Query API database (directly or via the HTTP API).

The collector can be as simple as the own application making HTTP calls to the Query API to save the traces in the database. Or even a consumer consuming messages from Kafka/RabbitMQ saving that information in the database.

## Spring Sleuth
[Spring Sleuth](http://cloud.spring.io/spring-cloud-sleuth/spring-cloud-sleuth.html) is a Spring component for distributed tracing. Sleuth can save tracing information through HTTP calls, or sending messages to queues like Kafka/RabbitMQ.
What we'll do is save Sleuth information into the Zipkin Query API database using a Kafka consumer so we can use the Zipkin UI to analyze the traces.

You can start sending traces using Sleuth adding the `spring-cloud-sleuth-stream` dependency to your classpath. Since we want Sleuth to communicate with Kafka, we need to bind the Kafka implementation for Spring Stream, adding `spring-cloud-starter-stream-kafka` to our classpath.

## Running the application
Since we need several different components to showcase this, we'll use docker-compose to bring the environment up:

```bash
docker-compose up
```

This may take a while since this will start:
- Service1: Spring Cloud application sending traces to Kafka with Sleuth, exposed on port 8000
- Service2: Spring Cloud application sending traces to Kafka with Sleuth, exposed on port 8001
- Eureka: so both Spring applications can talk to each other, exposed on port 8080
- Kafka: That will receive Sleuth messages
- Zipkin Consumer: It consumes Kafka messages sent by Sleuth to save them in Zipkin Query Database
- Zipkin UI: Web interface to see traces, exposed on port 8081

## Application Flow
When you request `/users` to the Service1 application, it'll send an HTTP request to the `/users` endpoint in the Service2 application. Both application are logging stuff to the console, so in the console you should see these log lines including the trace id and span id.

If you go to the Zipkin UI and search for a given trace id, you should see all the spans across that trace id.


# Zipkin Consumer
The Zipking Consumer application has two responsibilities:
- It's a consumer that fetches Spring Sleuth messages from Kafka to save them into a database in a Zipkin compatible format.
- It also exposes the Zipkin Query API through HTTP, so that a Zipkin UI can query it to print traces.
