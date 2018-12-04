package com.juja.controller.command;


import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;
import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class FindTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(manager, view);
    }

    @Test
    public void testPrintTableData() throws SQLException {
        // given
        List<String> list = new LinkedList<String>();
        list.add("c_id");
        list.add("c_name");
        list.add("c_password");

        when(manager.getTableColumns("customer"))
                .thenReturn(list);

        Map<String, Object> user1 = new LinkedHashMap<>();
        user1.put("c_id", 12);
        user1.put("c_name", "Stiven");
        user1.put("c_password", "*****");

        Map<String, Object> user2 = new LinkedHashMap<>();
        user2.put("c_id", 13);
        user2.put("c_name", "Eva");
        user2.put("c_password", "+++++");

        List<Map<String, Object>> data = new LinkedList<>();
        data.add(user1);
        data.add(user2);
        when(manager.getTableData("customer"))
                .thenReturn(data);

        // when
        command.process("find|customer");

        // then
        shouldPrint("[" +
                "+-------+---------------+---------------+, " +
                "|c_id   |c_name         |c_password     |, " +
                "+-------+---------------+---------------+, " +
                "|12     |Stiven         |*****          |, " +
                "|13     |Eva            |+++++          |, " +
                "+-------+---------------+---------------+]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

    @Test
    public void testCanProcessFindWithParametersString() {
        // when
        boolean canProcess = command.canProcess("find|customer");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessFindWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("find");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testCantProcessQweString() {
        // when
        boolean canProcess = command.canProcess("qwe|customer");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintEmptyTableData() throws SQLException {
        // given
        List<String> list = new LinkedList<String>();
        list.add("c_id");
        list.add("c_name");
        list.add("c_password");
        when(manager.getTableColumns("customer"))
                .thenReturn(list);

        when(manager.getTableData("customer")).thenReturn(Collections.EMPTY_LIST);

        // when
        command.process("find|customer");

        // then
        shouldPrint("[" +
                "+-------+---------------+---------------+, " +
                "|c_id   |c_name         |c_password     |, " +
                "+-------+---------------+---------------+, " +
                "+-------+---------------+---------------+]");
    }

    @Test
    public void testPrintTableDataWithOneColumn() throws SQLException {
        // given
        List<String> list = new LinkedList<String>();
        list.add("c_id");
        when(manager.getTableColumns("test"))
                .thenReturn(list);

        Map<String, Object> user1 = new LinkedHashMap<>();
        user1.put("id", 12);

        Map<String, Object> user2 = new LinkedHashMap<>();
        user2.put("id", 13);

        List<Map<String, Object>> data = new LinkedList<>();
        data.add(user1);
        data.add(user2);
        when(manager.getTableData("test")).thenReturn(data);

        // when
        command.process("find|test");

        // then
        shouldPrint("[" +
                "+-------+, " +
                "|c_id   |, " +
                "+-------+, " +
                "|12     |, " +
                "|13     |, " +
                "+-------+]");
    }
}
