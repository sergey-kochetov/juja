package com.juja.junit.user.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private int id;
    private String name;
    private String email;
    private int age;
    private Address address;

    public User(int id, String name, String email, int age, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
    }
}
