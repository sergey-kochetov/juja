package com.juja.controller.command;

import com.juja.controller.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ListTableTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new ListTable(manager, view);
    }

    @Test
    public void testCanProcessListWithParametersString() {
        // when
        boolean canProcess = command.canProcess("list");

        // then
        assertTrue(canProcess);
    }


    @Test
    public void testListDataWithOneTable() throws SQLException {
        // given
        List<String> list = UtilsCommand.getDataList();
        list.add("test1");
        list.add("test2");
        when(manager.getTableNames())
                .thenReturn(list);
        // when
        command.process("list");

        // then
        shouldPrint("[test1, test2]");
    }

    @Test
    public void testListDataWithEmptyTable() throws SQLException {
        // given
        List<String> list = UtilsCommand.getDataList();
        when(manager.getTableNames())
                .thenReturn(list);
        // when
        command.process("list");

        // then
        shouldPrint("[]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getValue());
    }


}