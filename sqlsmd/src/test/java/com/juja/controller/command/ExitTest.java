package com.juja.controller.command;

import com.juja.controller.exception.ExitException;
import com.juja.view.View;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ExitTest {

    private View view = Mockito.mock(View.class);

    @Test
    public void testCanProcessExitStringValid() {
        // given
        Command command = new Exit(view);

        //when
        boolean canProcess = command.canProcess("exit");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessExitStringNoValid() {
        // given
        Command command = new Exit(view);

        //when
        boolean canProcess = command.canProcess("no_exit");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testProcessExitCommand_throwExitException() {
        // given
        Command command = new Exit(view);

        //when
        try {
            command.process("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {
            // do nothing
        }

        // then
        Mockito.verify(view).write("bye...");
    }

}
