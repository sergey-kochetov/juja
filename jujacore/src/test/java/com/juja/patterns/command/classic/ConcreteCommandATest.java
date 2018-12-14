package com.juja.patterns.command.classic;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ConcreteCommandATest {

    private Object processed;
    private Object returned;

    @Test
    public void shouldCallReceiver() {
        // given
        ReceiverA receiver = new ReceiverA() {
            @Override
            public Object method1(Object input) {
                processed = input;
                return returned;
            }

            @Override
            public Object method2(Object input) {
                throw new IllegalArgumentException("не должен вызываться");
            }
        };
        Command command = new ConcreteCommandA(receiver);
        returned = "returned";

        // when
        Object result = command.execute("data");

        // then
        assertEquals("returned", result);

        assertEquals("data", processed);
    }

    @Test
    public void shouldCallReceiver_mockitoCase() {
        // given
        ReceiverA receiver = mock(ReceiverA.class);

        Command command = new ConcreteCommandA(receiver);

        when(receiver.method1(anyString())).thenReturn("returned");

        // when
        Object result = command.execute("data");

        // then
        assertEquals("returned", result);

        verify(receiver).method1("data");

        verifyNoMoreInteractions(receiver);
    }


}