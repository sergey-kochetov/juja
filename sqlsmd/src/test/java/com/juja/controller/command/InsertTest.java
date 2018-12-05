package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class InsertTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Insert(manager, view);
    }

    @Test
    public void testCanProcessCreateWithParametersString() {
        // when
        boolean canProcess = command.canProcess("insert|customer|c_ic|10");

        // then
        assertTrue(canProcess);
    }
    @Test
    public void testCantProcessCreateWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("novalid|customer|c_ic|10");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testCantProcessCreateWithoutParametersString2() {
        // when
        boolean canProcess = command.canProcess("novalid");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintCreateDataOne() throws SQLException {
        // given
        List<String> list = new LinkedList<String>();
        list.add("c_id");
        list.add("c_name");
        list.add("c_password");
        when(manager.getTableColumns("customer"))
                .thenReturn(list);
        when(manager.getTableData("customer")).thenReturn(Collections.EMPTY_LIST);

        // when
        command.process("insert|customer|c_id|1|c_name|name1|c_password|+++++");

        // then
        shouldPrint("['{c_id=1, c_name=name1, c_password=+++++}' " +
                "was successfully created in table 'customer']");
    }

    @Test
    public void testPrintCreateDataTwo() throws SQLException {
        // given
        List<String> list = new LinkedList<String>();
        list.add("id");
        when(manager.getTableColumns("customer"))
                .thenReturn(list);

        when(manager.getTableData("customer")).thenReturn(Collections.EMPTY_LIST);

        // when
        command.process("insert|customer|id|1");
        command.process("insert|customer|id|2");

        // then
        shouldPrint("[" +
                "'{id=1}' was successfully created in table 'customer', " +
                "'{id=2}' was successfully created in table 'customer']");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

}