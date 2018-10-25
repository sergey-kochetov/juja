package com.juja;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void test1() {
        assetMethod("xxhixx", "yyhiyy");
    }

    @Test
    public void test2() {
        assetMethod("x", "y");
    }
    @Test
    public void test3() {
        assetMethod("", "");
    }

    private static void assetMethod(String input, String expected) {
        String actual = Main.changeXY(input);
        assertEquals(expected, actual);

    }

}