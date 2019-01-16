package com.juja.patterns.state.classic.case2_implements;

import com.juja.patterns.command.classic.ConsoleMock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ContextTest {

    private ConsoleMock console = new ConsoleMock();

    @Test
    public void testRequest1ProcessedByState() {
        // given
        Context context = new Context();
        State state = mock(State.class);
        context.setState(state);

        // when
        context.request1("data");

        // then
        verify(state).handle1("data");
    }

    @Test
    public void testRequest2ProcessedByState() {
        // given
        Context context = new Context();
        State state = mock(State.class);
        context.setState(state);

        // when
        context.request2();

        // then
        verify(state).handle2();
    }

    @Test
    public void testDefaultStateIsA() {
        // given
        Context context = new Context();

        // when
        context.request1("data");

        // then
        assertEquals("Set state: A\n" +
                "Handled by A: data\n" +
                "Set state: B\n", console.getOut());
    }
}
