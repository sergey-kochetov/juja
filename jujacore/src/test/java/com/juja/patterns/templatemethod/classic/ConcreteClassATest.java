package com.juja.patterns.templatemethod.classic;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConcreteClassATest {

    @Test
    public void test() {
        // given
        AbstractClass object = new ConcreteClassA();

        // when
        Object result = object.templateMethod("some_data");

        // then
        assertEquals("algorithmA plus algorithm4A some_data", result);
    }

}