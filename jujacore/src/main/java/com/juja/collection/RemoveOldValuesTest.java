package com.juja.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RemoveOldValuesTest {
    @Test
    public void testMyMap() {
        // given
        MyMap map = new MyMap();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);

        // when then
        assertEquals("{3=3, 4=4, 5=5}", map.toString());
    }

}
// мапа хранит только 3 последних значений
class MyMap extends LinkedHashMap<String, Integer> {
    private static final int MAX_SIZE = 3;

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        return size() > MAX_SIZE;
    }
}
