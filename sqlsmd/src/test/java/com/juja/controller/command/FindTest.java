package com.juja.controller.command;


import com.juja.controller.util.UtilsCommand;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class FindTest extends CommandHelperTest {
    @Before
    public void setup() {
        command = new Find(manager, view);
    }

    @Test
    public void testPrintTableData() throws SQLException {
        // given
        Set<String> list =UtilsCommand.getDataSet();
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

        Set<Map<String, Object>> data = UtilsCommand.getDataSetMap();
        data.add(user1);
        data.add(user2);
        when(manager.getTableData("customer"))
                .thenReturn(data);

        // when
        command.process("find|customer");

        // then
        shouldPrint("" +
                "+-------+---------------+---------------+\r\n" +
                "|c_id   |c_name         |c_password     |\r\n" +
                "+-------+---------------+---------------+\r\n" +
                "|12     |Stiven         |*****          |\r\n" +
                "|13     |Eva            |+++++          |\r\n" +
                "+-------+---------------+---------------+");
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
        Set<String> list = UtilsCommand.getDataSet();
        list.add("c_id");
        list.add("c_name");
        list.add("c_password");
        when(manager.getTableColumns("customer"))
                .thenReturn(list);

        when(manager.getTableData("customer")).thenReturn(Collections.EMPTY_SET);

        // when
        command.process("find|customer");

        // then
        shouldPrint("" +
                "+-------+---------------+---------------+\r\n" +
                "|c_id   |c_name         |c_password     |\r\n" +
                "+-------+---------------+---------------+\r\n" +
                "+-------+---------------+---------------+");
    }

    @Test
    public void testPrintTableDataWithOneColumn() throws SQLException {
        // given
        Set<String> list = UtilsCommand.getDataSet();
        list.add("c_id");
        when(manager.getTableColumns("test"))
                .thenReturn(list);

        Map<String, Object> user1 = new LinkedHashMap<>();
        user1.put("id", 12);

        Map<String, Object> user2 = new LinkedHashMap<>();
        user2.put("id", 13);

        Set<Map<String, Object>> data = UtilsCommand.getDataSetMap();
        data.add(user1);
        data.add(user2);
        when(manager.getTableData("test")).thenReturn(data);

        // when
        command.process("find|test");

        // then
        shouldPrint("" +
                "+-------+\r\n" +
                "|c_id   |\r\n" +
                "+-------+\r\n" +
                "|12     |\r\n" +
                "|13     |\r\n" +
                "+-------+");
    }

    @Test
    public void shouldSQLExceptionThenPrintError() throws SQLException {
        // given
        String com = "find|test";

        // when
        doThrow(new SQLException()).when(manager).getTableColumns("test");
        doThrow(new SQLException()).when(manager).getTableData("test");
        command.process(com);
        shouldPrint("table 'test' not found");
    }
}
