package com.juja.controller.command;

import com.juja.controller.MainController;
import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class HelpTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Help(view, new MainController(view, manager).getCommands());
    }

    @Test
    public void testCanProcess_valid() {
        // when
        boolean canProcess = command.canProcess("help");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcess_novalid() {
        // when
        boolean canProcess = command.canProcess("novalid");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintMenuHelp() throws SQLException {
        // given

        // when
        command.process("help");

        // then
        shouldPrint("[Existing commands, \tconnect|database|userName|password, \t\tfor connect to database, \thelp, \t\tthis page, \texit, \t\tfor exit program, \tlist, \t\tfor get list database, \tclear|tableName, \t\tdatabase cleaning, \tdrop|tableName, \t\tdrop database, \tcreate|database|row1|param1|...|rowN|paramN, \t\tcreate data for database, \tfind|tableName, \t\tfor see table 'tableName']");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

}