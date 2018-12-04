package com.juja.controller;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UtilsCommand {

    public static Map<String, Object> getDataMap() {
        return new LinkedHashMap<>();
    }

    public static List<String> getDataList() {
        return new LinkedList<>();
    }

    public static List<Map<String, Object>> getDataListMap() {
        return new LinkedList<Map<String, Object>>();
    }
}
