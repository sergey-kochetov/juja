package com.juja.parsing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SampleJSON {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Test
    public void testJSON() {
        // given
        Person person = new Person("Vasya", 30, Arrays.asList("Moscow", "Odessa", "Dubai"));

        // when then
        String json = GSON.toJson(person);
        assertEquals("{\n" +
                "  \"name\": \"Vasya\",\n" +
                "  \"age\": 30,\n" +
                "  \"geo\": [\n" +
                "    \"Moscow\",\n" +
                "    \"Odessa\",\n" +
                "    \"Dubai\"\n" +
                "  ]\n" +
                "}", json);
        // when then
        Person person1 = GSON.fromJson(json, Person.class);
        assertEquals("Person{name='Vasya', age=30, " +
                "getHistory=[Moscow, Odessa, Dubai]}", person1.toString());


    }
}

class Person {
    private String name;
    private Integer age;
    @SerializedName("geo")
    private List<String> getHistory = new ArrayList<>();

    public Person(String name, Integer age, List<String> getHistory) {
        this.name = name;
        this.age = age;
        this.getHistory = getHistory;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", getHistory=" + getHistory +
                '}';
    }
}
