package com.juja.junit.user.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Address {
    private String country;
    private String city;
    private int zip;

    public Address(String country, String city, int zip) {
        this.country = country;
        this.city = city;
        this.zip = zip;
    }
}
