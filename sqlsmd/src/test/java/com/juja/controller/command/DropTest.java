package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DropTest {

    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Drop(manager, view);
    }

    @Test
    public void testCanProcessDropWithParametersValidString() {
        //when
        boolean canProcess = command.canProcess("drop|table");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessDropWithParametersNovalidString() {
        //when
        boolean canProcess = command.canProcess("novalid|table");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testDropTable() throws SQLException {
        // given

        // when
        command.process("drop|customer");

        // then
        shouldPrint("[table 'customer' was successfully droped]");
    }

    @Test
    public void testDropEmptyTable() throws SQLException {
        // given
        Mockito.doThrow(new SQLException()).when(manager).drop("customer");
        // when
        try {
            command.process("drop|customer");
        } catch (IllegalArgumentException e) {
            assertEquals("table 'customer' does not exist", e.getMessage());
        }
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }




}