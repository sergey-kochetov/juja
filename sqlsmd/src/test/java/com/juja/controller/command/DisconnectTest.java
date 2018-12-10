package com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class DisconnectTest extends CommandHelperTest {
    @Before
    public void setup() {
        command = new Disconnect(manager, view);
    }

    @Test
    public void testCanProcessWithParametersStringValid() {
        // when
        boolean canProcess = command.canProcess("disconnect");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWithParametersStringNovalid() {
        // when
        boolean canProcess = command.canProcess("novalid");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void processWithNormalWorkFromManager() throws SQLException {
        doNothing().when(manager).disconnect();
        command.process("disconnect");
        verify(view).write("disconnected");
    }

}