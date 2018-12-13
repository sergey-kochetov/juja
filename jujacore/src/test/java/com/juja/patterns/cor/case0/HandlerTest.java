package com.juja.patterns.cor.case0;

import org.junit.Test;

import static org.junit.Assert.*;

public class HandlerTest {

    private Request processed = null;

    @Test
    public void shouldSendRequestToAccessor_whenNoProcessing() {
        // given
        // первый обработчик
        Handler handler = new Handler(String.class);

        // его второй обработчик
        handler.setSuccessor(new Handler(Class.class) {
            @Override
            public void handlerRequest(Request request) {
                processed = request;
            }
        });

        Request givenRequest = new Request(24);

        // when
        handler.handlerRequest(givenRequest);

        // then
        assertEquals(givenRequest, processed);
    }

    @Test
    public void shouldProcessRequestWhithoutSendingToAccessor_whenProcessing() {
        // given
        // первый обработчик
        Handler handler = new Handler(String.class);

        // его второй обработчик
        handler.setSuccessor(new Handler(Class.class) {
            @Override
            public void handlerRequest(Request request) {
                processed = request;
            }
        });

        Request givenRequest = new Request("request");

        // when
        handler.handlerRequest(givenRequest);

        // then
        assertNull( processed);
    }

}