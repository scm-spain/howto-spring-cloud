package com.scmspain.howtospring.discovery.eureka;

public class User {
    private String id;
    private String name;
    private String lastname;

    public User() {
    }

    public User(String id, String name, String lastname) {
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
