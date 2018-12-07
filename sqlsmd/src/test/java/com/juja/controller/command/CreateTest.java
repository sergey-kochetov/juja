package com.juja.controller.command;

import com.juja.controller.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateTest {
    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Create(manager, view);
    }

    @Test
    public void testCanProcessCreateWithParametersValidString() {
        //when
        boolean canProcess = command.canProcess("create|table");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessCreateWithParametersNovalidString() {
        //when
        boolean canProcess = command.canProcess("novalid|table");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testCreateTable() throws SQLException {
        // given
        List<String> list = UtilsCommand.getDataList();
        list.add("id INT");
        Mockito.doThrow(new SQLException()).when(manager).create("customer", list);
        // when
        command.process("create|test|id|INT");

        // then
        shouldPrint("[table 'test' was successfully created]");
    }

    @Test
    public void testCreateEmptyTable() throws SQLException {
        // given
        Mockito.doThrow(new SQLException()).when(manager).create("customer", UtilsCommand.getDataList());
        // when
        try {
            command.process("create|test");
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