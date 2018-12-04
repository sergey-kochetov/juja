package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConnectTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
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
    public void testCanProcessWithParametersStringNovalid() {
        // when
        boolean canProcess = command.canProcess("connect2|sqlsmd|postgres|postgres");

        // then
        assertFalse(canProcess);
    }



    @Test
    public void process() throws SQLException {
        // given
        String database = "sqlsmd";
        String userName = "postgres";
        String password = "postgres";

        manager.connect(database, userName, password);

        // when
        boolean actual = manager.isConnected();

        // then
        //assertEquals(true, actual);

    }
}