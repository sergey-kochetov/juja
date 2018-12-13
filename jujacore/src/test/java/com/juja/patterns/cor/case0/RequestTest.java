package com.juja.patterns.cor.case0;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void shouldSaveMessage() {
        // given
        Request request = new Request("Message");

        // when
        Object message = request.getMessage();

        // then
        assertEquals("Message", message);
    }

    @Test
    public void shouldToStringMessage() {
        // given
        Request request = new Request("Message");

        // when
        Object message = request.toString();

        // then
        assertEquals("java.lang.String:Message", message);
    }

    @Test
    public void shouldToIntegerMessage() {
        // given
        Request request = new Request(1);

        // when
        Object message = request.toString();

        // then
        assertEquals("java.lang.Integer:1", message);
    }


}