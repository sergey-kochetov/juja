package com.juja.patterns.templatemethod.classic;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AbstractClassTest {

    Object algorithm2Input = null;
    boolean callAlgorithm3 = false;

    @Test
    public void test() {
        // given
        AbstractClass object = new AbstractClass() {
            @Override
            protected void algorithm2(Object input) {
                algorithm2Input = input;
            }

            @Override
            protected void algorithm3() {
                callAlgorithm3 = true;
            }

            @Override
            protected Object algorithm4(Object input) {
                return "stub4";
            }
        };

        // when
        Object result = object.templateMethod("some_data");

        // then
        assertEquals("default_algorithm1 plus stub4", result);

        assertEquals("some_data", algorithm2Input);

        assertTrue(callAlgorithm3);
    }

    @Test
    public void testWithMokito() {
        // given
        AbstractClass object = mock(AbstractClass.class);
        // програмируем его поведение
        when(object.algorithm4(anyObject())).thenReturn("mocked4");
        // а для algorithm1 вызываем реализацию по-умолчанию
        when(object.algorithm1()).thenCallRealMethod();

        // when
        Object result = object.templateMethod("some_data");

        // then
        assertEquals("default_algorithm1 plus mocked4", result);

        verify(object).algorithm2("some_data");

        verify(object).algorithm3();

        verify(object).algorithm4("some_data");
    }

}