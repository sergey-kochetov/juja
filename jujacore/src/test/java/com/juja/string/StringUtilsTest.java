package com.juja.string;

import org.junit.Test;

import static com.juja.string.StringUtils.rightShift;
import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void test_1() {
        //given
        String str ="ABCDEFG";
        //when
        String actual = rightShift("ABCDEFG", 0);
        String expect = "ABCDEFG";
        //then
        assertEquals(actual, expect);
    }
    @Test
    public void test_2() {
        //given
        String str ="ABCDEFG";
        //when
        String actual = rightShift("ABCDEFG", 1);
        String expect = "GABCDEF";
        //then
        assertEquals(actual, expect);

    }
    @Test
    public void test_3() {
        //given
        String str ="ABCDEFG";
        //when
        String actual = rightShift("ABCDEFG", -1);
        String expect = "BCDEFGA";
        //then
        assertEquals(actual, expect);

    }

}