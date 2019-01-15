package com.juja.patterns.objectpool;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {
    private static Map<Object, String> ids = new HashMap<>();
    private static Integer ID = 0;

    public static String put(Object obj) {
        String id = String.valueOf(++ID);
        ids.put(obj, id);
        return id;
    }

    public static String get(Object object) {
        return ids.get(object);
    }

    public static void reset() {
        ID = 0;
    }
}
