package com.scmspain.howtospring.cache;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;

@Repository
public class SlowInMemoryUserRepository implements UserRepository {
  private Map<String, User> data = Maps.newConcurrentMap();

  @Override
  @CacheResult(cacheName = "user")
  public User get(String key) {
    slowOperation();
    return data.get(key);
  }

  @Override
  @CacheRemove(cacheName = "user")
  public void set(@CacheKey String key, User user) {
    data.put(key, user);
  }

  @SuppressWarnings("EmptyCatchBlock")
  private void slowOperation() {
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {}
  }

}
