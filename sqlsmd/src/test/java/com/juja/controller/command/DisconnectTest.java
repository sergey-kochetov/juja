package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class DisconnectTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
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
        Mockito.doNothing().when(manager).disconnect();
        command.process("disconnect");
        Mockito.verify(view).write("disconnected");
    }

}