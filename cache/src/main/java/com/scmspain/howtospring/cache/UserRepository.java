package com.scmspain.howtospring.cache;

public interface UserRepository {
  User get(String key);
  void set(String key, User user);
}
