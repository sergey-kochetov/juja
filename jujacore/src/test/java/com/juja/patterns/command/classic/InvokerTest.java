package com.juja.patterns.command.classic;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InvokerTest {

    private ConsoleMock console = new ConsoleMock();

    private Object processed;
    private Object returned;

    @Test
    public void shouldCallCommand() {
        // given
        Invoker invoker = new Invoker();
        Command command = (input) -> {
            processed = input;
            return returned;
        };
        returned = "returned";
        invoker.setCommand(command);

        // when
        invoker.doit();

        // then
        assertEquals("returned\n", console.getOut());
        assertEquals("data", processed);
    }

    @Test
    public void shouldCallCommand_mockitoCase() {
        // given
        Invoker invoker = new Invoker();
        Command command = mock(Command.class);

        // вот что вернет команда
        when(command.execute(anyObject())).thenReturn("returned");
        // устанавливать команду в invoker
        invoker.setCommand(command);

        // when
        invoker.doit();

        // then
        assertEquals("returned\n", console.getOut());
        // + до команды дошел сигнал из invoker
        verify(command).execute("data");
    }


}