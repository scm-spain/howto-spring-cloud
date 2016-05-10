package com.scmspain.howtospring.jdbc;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.cloud.netflix.feign.FeignClient;

import javax.ws.rs.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@FeignClient(name = "my-app", configuration = FeignConfiguration.class)
public class ApplicationTest {

    private static UserApi userApi;

    interface UserApi{
        @GET
        @Path("/users")
        List<User> getUsers();

        @GET
        @Path("/users/{userId}")
        User getUser(@PathParam("userId") String userId);

        @POST
        @Path("/users")
        @Consumes("application/json")
        User saveUser(User user);
    }

    @BeforeClass
    public static void setUp() throws Exception {
        ApplicationTest.userApi = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .contract(new JAXRSContract())
                .target(UserApi.class, "http://app:8000/");
    }

    @Test
    public void testListOfUsers() throws Exception {
        List<User> users = userApi.getUsers();
        assertThat(users).hasSize(0);
    }

    @Test
    public void testCreateUser() throws Exception {
        User userToCreate = new User("abcd", "Alice", "Wonderland");
        User userCreated = userApi.saveUser(userToCreate);
        assertThat(userToCreate).isEqualToComparingFieldByField(userCreated);

        List<User> users = userApi.getUsers();
        assertThat(users).hasSize(1);
        assertThat(userToCreate).isEqualToComparingFieldByField(users.get(0));

        assertThat(userApi.getUser(userToCreate.getId())).isEqualToComparingFieldByField(userToCreate);
    }
}
