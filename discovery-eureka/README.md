# Spring Cloud Discovery using Eureka

This is an example application to show an application can register itself on a running Eureka instance.
The application expose a memory list of two users through the /users endpoint.

## Eureka
For this application to work we need a running Eureka instance. We recommend you to use [Netflix' Docker image for Eureka](https://hub.docker.com/r/netflixoss/eureka/).
If Eureka is running on http://192.168.99.100:8000/eureka/, there you can see that there are no applications registered.


## Running the application
To start playing around you need to build it and run it

```bash
./gradlew clean build bootRun
```

## Configuration
Parameters ,like the port that this application is listening too, are placed in the application.yml file.
Important parameters for eureka are

```yaml
eureka:
  instance:
    preferIpAddress: true
  client:
      serviceUrl:
        defaultZone: http://192.168.99.100:8080/eureka/v2/
```

## Usage
Once the application is up and running, let's say is running on port 8001, let's see if everything worked as expected

```bash
$ curl -X GET -H "Accept: application/json" "http://localhost:8001/users"
```

The application should've been registered on Eureka already, so let's check http://192.168.99.100:8000/eureka/

## Discovering Eureka nodes using DNS TXT records
Instead of explicitly specify the route to the eureka servers, you can use [a DNS based lookup for determining other eureka servers](https://github.com/Netflix/eureka/wiki/Deploying-Eureka-Servers-in-EC2#configuring-eips-using-dns).

```yaml
eureka:
  client:
    registerWithEureka: true
    fetchRegistry: true
    useDnsForFetchingServiceUrls: true
    eurekaServerDNSName: txt.dns.records.com
    eurekaServerPort: 8080
    eurekaServerURLContext: eureka
    region: eu-west-1
```

You can find all the available configuration options [on the Bean class](https://github.com/spring-cloud/spring-cloud-netflix/blob/master/spring-cloud-netflix-eureka-client/src/main/java/org/springframework/cloud/netflix/eureka/EurekaClientConfigBean.java) that configures the Eureka client.