package com.scmspain.howtospring.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
  private UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<User> get(@PathVariable("key") String key) {
    return Optional.ofNullable(userRepository.get(key))
        .map(ResponseEntity::ok)
        .orElseGet(this::notFound);

  }

  @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> post(@RequestBody User user) {
    userRepository.set(user.getId(), user);

    return ResponseEntity.created(
        UriComponentsBuilder.fromPath("/users/{key}")
            .buildAndExpand(user.getId()).toUri())
        .build();
  }

  private <T> ResponseEntity<T> notFound() {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
