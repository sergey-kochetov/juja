package com.juja.patterns.state.classic.case2_implements;

import com.juja.patterns.command.classic.ConsoleMock;
import org.junit.Test;
import org.mockito.ArgumentCaptor;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

// это тест на контракт - построенный по принципу Template Method
public abstract class ConcreteStateTest {

    private ConsoleMock console = new ConsoleMock();

    // тесты-шаблоны
    @Test
    public void testHandle1() throws Exception {
        // given
        Context context = spy(Context.class);
        State state = getState(context);
        console.getOut();

        // when
        state.handle1("data");

        // then
        ArgumentCaptor<State> captor = ArgumentCaptor.forClass(State.class);
        verify(context).setState(captor.capture());
        assertEquals(getNextStateClass(), captor.getValue().getClass());

        assertEquals("Handled by " + getName() + ": data\n" +
                "Set state: " + getNextName() + "\n", console.getOut());
    }

    @Test
    public void testHandle2() throws Exception {
        // given
        Context context = spy(Context.class);
        State state = getState(context);
        console.getOut();

        // when
        String actual = state.handle2();

        // then
        assertEquals(actual, "Handled by " + getName());

        ArgumentCaptor<State> captor = ArgumentCaptor.forClass(State.class);
        verify(context).setState(captor.capture());
        assertEquals(getPrevStateClass(), captor.getValue().getClass());

        assertEquals("Set state: " + getPrevName() + "\n", console.getOut());
    }

    // а это требуется уточнить в тестах-наследниках

    abstract String getPrevName();
    abstract String getName();
    abstract String getNextName();

    abstract Class<? extends State> getPrevStateClass();
    abstract State getState(Context context);
    abstract Class<? extends State> getNextStateClass();


}