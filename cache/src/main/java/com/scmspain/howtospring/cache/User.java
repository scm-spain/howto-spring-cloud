package com.scmspain.howtospring.cache;

import java.io.Serializable;

public class User implements Serializable {
  private String id;
  private String name;
  private String lastName;

  private User() {}

  public User(String id, String name, String lastName) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return lastName;
  }

}
