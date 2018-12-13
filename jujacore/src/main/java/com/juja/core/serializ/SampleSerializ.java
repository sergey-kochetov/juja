package com.juja.core.serializ;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SampleSerializ {
    private static final String FILE_NAME = "file.data";
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(FILE_NAME));){
            output.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInput input = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            map = (Map<Integer, String>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        map.forEach((key, value) -> System.out.println(key + " -> " + value));


        try {
            Files.deleteIfExists(Paths.get(FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class MyClass implements Serializable {
    String field = "field1";
}
