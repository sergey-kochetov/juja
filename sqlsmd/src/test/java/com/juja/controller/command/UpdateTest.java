package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UpdateTest {
    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Update(manager, view);
    }

    @Test
    public void testCanProcessUpdateWithParametersValidString() {
        //when
        boolean canProcess = command.canProcess("update|table");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessUpdateWithParametersNovalidString() {
        //when
        boolean canProcess = command.canProcess("novalid|table");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testUpdateTable() throws SQLException {
        // given

        // when
        command.process("update|test|column1|type1");

        // then
        shouldPrint("[data successfully updated to tables 'test']");
}

    @Test
    public void testUpdateEmptyTable() throws SQLException {
        // given
        Mockito.doThrow(new SQLException()).when(manager).drop("test");
        // when
        try {
            command.process("update|test|");
        } catch (IllegalArgumentException e) {
            assertEquals("format create|tableName|column1|type1|..." +
                    "|columnN|typeN, but was: create|test", e.getMessage());
        }
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

}