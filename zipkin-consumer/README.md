# Zipkin Consumer
This Spring Cloud application has two responsibilities:
- It's a consumer that fetches Spring Sleuth messages from Kafka to save them into a database in a Zipkin compatible format.
- It also exposes the Zipkin Query API through HTTP, so that a Zipkin UI can query it to print traces.

This application is useful when other Spring Cloud applications use Spring Sleuth to send tracing messages to Kafka, because this consumer can transform those messages into a Zipkin compatible format so Zipkin UI can use them to show tracing information.

## Running the application
To start playing around you need to build it and run it

```bash
./gradlew clean build bootRun
```

## Configuration
You can see some parameters in the application.yml file.
Since this application will consume Kafka messages, it's important to correctly configure Kafka.

To enable the consumer that will read Kafka messages, our application must add the annotation `@EnableZipkinStreamServer`. This annotation enables both the consumer and the Zipkin Query REST API.

You could also split both components, if you wan to deploy the consumer and the API separetly.
Check [the documentation to create your own consumer](http://cloud.spring.io/spring-cloud-sleuth/spring-cloud-sleuth.html#_custom_consumer). To create a Spring Cloud application exposing only the Zipkin Query API, just use the `@EnableZipkinServer` annotation.

## Zipkin
[Zipkin](https://twitter.github.io/zipkin/) is a component for distributed tracing composed by different components:
- Zipkin UI: Frontend application that calls to the Query API
- Zipkin Query API: REST API exposing endpoints with information about traces. Store traces information in its own database
- Zipkin Collector: Collects information about traces saving it into the Query API database (directly or via the HTTP API).

The collector can be as simple as the own application making HTTP calls to the Query API to save the traces in the database. Or even a consumer consuming messages from Kafka/RabbitMQ saving that information in the database.

## Spring Sleuth
[Spring Sleuth](http://cloud.spring.io/spring-cloud-sleuth/spring-cloud-sleuth.html) is a Spring component for distributed tracing. Sleuth can save tracing information through HTTP calls, or sending messages to queues like Kafka/RabbitMQ.
What we'll do is save Sleuth information into the Zipkin Query API database using a Kafka consumer so we can use the Zipkin UI to analyze the traces.

