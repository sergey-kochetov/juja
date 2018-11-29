package com.juja.controller.command;

import com.juja.model.DataSet;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class FindCommandTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new FindCommand(manager, view);
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

        DataSet user1 = new DataSet();
        user1.put("c_id", 12);
        user1.put("c_name", "Stiven");
        user1.put("c_password", "*****");

        DataSet user2 = new DataSet();
        user2.put("c_id", 13);
        user2.put("c_name", "Eva");
        user2.put("c_password", "+++++");

        List<DataSet> data = new LinkedList<>();
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

        DataSet user1 = new DataSet();
        user1.put("id", 12);

        DataSet user2 = new DataSet();
        user2.put("id", 13);

        List<DataSet> data = new LinkedList<>();
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
