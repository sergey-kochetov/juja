package com.juja.controller.command;

import com.juja.view.View;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExitCommandTest {

    private View view = Mockito.mock(View.class);

    @Test
    public void testCanProcessExitStringValid() {
        // given
        Command command = new ExitCommand(view);

        //when
        boolean canProcess = command.canProcess("exit");

        // then
        assertEquals(true, canProcess);
    }
    @Test
    public void testCanProcessExitStringNoValid() {
        // given
        Command command = new ExitCommand(view);

        //when
        boolean canProcess = command.canProcess("no_exit");

        // then
        assertEquals(false, canProcess);
    }

    @Test
    public void testProcessExitCommand_throwExitException() {
        // given
        Command command = new ExitCommand(view);

        //when
        try {
            command.process("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {
            // do nothing
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // then
        Mockito.verify(view).write("bye...");
    }

}
