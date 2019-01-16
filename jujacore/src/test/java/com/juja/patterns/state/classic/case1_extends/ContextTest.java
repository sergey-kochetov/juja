package com.juja.patterns.state.classic.case1_extends;

import com.juja.patterns.command.classic.ConsoleMock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ContextTest {

    private ConsoleMock console = new ConsoleMock();

    @Test
    public void testRequestProcessedByState() {
        // given
        Context context = new Context();
        State state = mock(State.class);
        context.setState(state);

        // when
        context.request("data");

        // then
        verify(state).handle(context, "data");
    }

    @Test
    public void testDefaultStateIsA() {
        // given
        Context context = new Context();

        // when
        context.request("data");

        // then
        assertEquals("Set state: A\n" +
                "Handled by A: data\n" +
                "Set state: B\n", console.getOut());
    }
}
