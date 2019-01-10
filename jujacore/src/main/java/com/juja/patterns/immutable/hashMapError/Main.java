package com.juja.patterns.immutable.hashMapError;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Key, Object> map = new HashMap<>();
        System.out.println("добавим в нее два объекта под разными ключами 'key1' и 'key2'");
        Key key1 = new Key("key1");
        Key key2 = new Key("key2");

        map.put(key1, "object1");
        map.put(key2, "object2");
        printMap(map, key1, key2); // object1, object2

        System.out.println("поменяем один из ключей key1 = key1_changed");
        key1.setData("key1_changed");
        printMap(map, key1, key2); // null, object2

        System.out.println("но мапап то все еще содержит 2 элемента");
        System.out.println(map.size());

        System.out.println("но где же он? берем по старому ключу 'key1'");
        printMap(map, new Key("key1")); // null

        System.out.println("берем по новому ключу 'key1_changed'");
        printMap(map, new Key("key1_changed")); // null

        System.out.println("инерирумся по всей мапе");
        map.forEach((key, o) -> System.out.printf("%s=%s%n", key.toString(), o.toString()));
        System.out.println("магия?");
    }

    private static void printMap(Map<Key, Object> map, Key... key) {
        System.out.println("посмотрим на месте ли объекты");
        for (int i = 0; i < key.length; i++) {
            System.out.println(map.get(key[i]));
        }
    }
}
