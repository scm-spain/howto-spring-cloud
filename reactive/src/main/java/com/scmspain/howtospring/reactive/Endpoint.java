package com.scmspain.howtospring.reactive;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.netflix.rx.RxResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rx.Single;

import java.util.List;

@RequestMapping(path = "/")
@ResponseBody
public class Endpoint {
    private MyService myService;

    public Endpoint(MyService myService) {
        this.myService = myService;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public Single<List<User>> listUsers() {
        return myService.listUsers().toList().toSingle();
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
    public Single<User> showUser(@PathVariable String userId) {
        return myService.showUser(userId).toSingle();
    }

    @RequestMapping(path = "/users.stream", method = RequestMethod.GET)
    public SseEmitter listUsersStream() {
        return RxResponse.sse(myService.listUsers());
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseEntity<Single<User>> addUser(@RequestBody AddUserRequestBody requestBody) {
        Single<User> user = myService.addUser(
            requestBody.getId(),
            requestBody.getName(),
            requestBody.getLastname()
        ).toSingle();

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(user);
    }

    public static class AddUserRequestBody {
        private String id;
        private String name;
        private String lastname;

        public AddUserRequestBody(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("lastname") String lastname
        ) {
            this.id = id;
            this.name = name;
            this.lastname = lastname;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLastname() {
            return lastname;
        }
    }
}
