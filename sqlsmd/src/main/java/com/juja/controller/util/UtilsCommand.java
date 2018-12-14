package com.juja.controller.util;

import java.util.*;

public class UtilsCommand {

    public static Map<String, Object> getDataMap() {
        return new LinkedHashMap<>();
    }

    public static List<String> getDataList() {
        return new LinkedList<>();
    }

    public static List<String> getDataList(Set<String> set) {
        return new LinkedList<>(set);
    }
    public static Set<Map<String, Object>> getDataSetMap() {
        return new LinkedHashSet<>();
    }
    public static Set<String> getDataSet() {
        return new LinkedHashSet<>();
    }
}
