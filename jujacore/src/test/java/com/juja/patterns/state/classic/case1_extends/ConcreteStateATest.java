package com.juja.patterns.state.classic.case1_extends;

import com.juja.patterns.command.classic.ConsoleMock;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ConcreteStateATest {

    private ConsoleMock console = new ConsoleMock();

    @Test
    public void testHandle() throws Exception {
        // given
        State state = new ConcreteStateA();
        Context context = mock(Context.class);

        // when
        state.handle(context, "data");

        // then
        ArgumentCaptor<State> captor = ArgumentCaptor.forClass(State.class);
        verify(context).setState(captor.capture());
        assertEquals(ConcreteStateB.class, captor.getValue().getClass());

        assertEquals("Handled by A: data\n", console.getOut());
    }
}