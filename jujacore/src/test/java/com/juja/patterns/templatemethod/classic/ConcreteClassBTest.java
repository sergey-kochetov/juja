package com.juja.patterns.templatemethod.classic;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConcreteClassBTest {


    @Test
    public void test() {
        // given
        AbstractClass object = new ConcreteClassB();

        // when
        Object result = object.templateMethod("some_data");

        // then
        assertEquals("default_algorithm1 plus algorithm4B some_data", result);
    }


}