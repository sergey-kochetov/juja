package com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class ConnectTest extends CommandHelperTest {
    @Before
    public void setup() {
        command = new Connect(manager, view);
    }

    @Test
    public void testCanProcessWithParametersStringValid() {
        // when
        boolean canProcess = command.canProcess("connect|sqlsmd|postgres|postgres");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWithDefault() {
        // when
        boolean canProcess = command.canProcess("connect|");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWithParametersStringNovalid() {
        // when
        boolean canProcess = command.canProcess("connect2|sqlsmd|postgres|postgres");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void process() {
        // given
        String request = "connect|sqlsmd|postgres|postgres";

        // when
        command.process(request);

        // then
        shouldPrint("Connect Successful");
    }

    @Test
    public void processWithShortCommand() {
        // given
        String request = "connect|anyDB|anyUser";

        // when
        command.process(request);

        // then
        shouldPrint("No valid data '|' actual 4, but was: 3");
    }

    @Test
    public void processWithNormalWorkFromManager() throws SQLException {
        // given
        String com = "connect|anyDB|anyUser|anyPassword";
        String db = "anyDB";
        String user = "anyUser";
        String password = "anyPassword";

        // when
        doNothing().when(manager).connect(db,user,password);
        command.process(com);

        // then
        shouldPrint("Connect Successful");
    }

    @Test
    public void processWithDefaultFromManager() throws SQLException {
        // given
        String com = "connect|";

        // when
        doNothing().when(manager).defaultConnect();
        command.process(com);

        // then
        shouldPrint("Connect Successful");
    }

    @Test
    public void processWithSQLExceptionFromManager() throws SQLException {
        //given
        String com = "connect|x|x|x";

        // when
        doThrow(new SQLException()).when(manager).connect("x", "x", "x");
        command.process(com);

        // then
        verify(view).write("Cant get connection for model:x user:x");
    }
}