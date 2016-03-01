package com.scmspain.howtospring.hystrixfeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import rx.Observable;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@FeignClient(name = "discovery-eureka", configuration = FeignConfiguration.class)
public interface UserServiceRestClient {

    @GET
    @Path("/users")
    Observable<List<User>> getUsers();

    @GET
    @Path("/users/{userId}")
    Observable<User> getUser(@PathParam("userId") String userId);

    @POST
    @Path("/users")
    @Consumes("application/json")
    Observable<User> saveUser(User user);

    @POST
    @Path("/users")
    @Consumes("application/json")
    Observable<User> saveUser(User user, @HeaderParam("Auth-Token") String token);

    @POST
    @Path("/users")
    @Consumes("application/json")
    Observable<User> saveUserWithExplicitBody(String userJson);

    @POST
    @Path("/users")
    @Consumes("application/json")
    Observable<User> saveUserWithExplicitBody(String userJson, @HeaderParam("Auth-Token") String token);
}
