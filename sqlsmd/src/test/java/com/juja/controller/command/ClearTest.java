package com.juja.controller.command;

import com.juja.controller.util.UtilsCommand;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ClearTest extends CommandHelperTest {
    @Before
    public void setup() {
        command = new Clear(manager, view);
    }

    @Test
    public void testCanProcessClearWithParametersValidString() {
        //when
        boolean canProcess = command.canProcess("clear|table");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessClearWithParametersNovalidString() {
        //when
        boolean canProcess = command.canProcess("no|table");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void shouldClearWhenNovalidCommand() {
        // given
        command.process("clear");
        // then
        shouldPrint("format clear|tableName, but was: clear");
    }

    @Test
    public void shouldClearWhenNovalidCommand2() {
        // given
        command.process("clear|table|table");
        // then
        shouldPrint("format clear|tableName, but was: clear|table|table");
    }

    @Test
    public void testPrintEmptyTableData() throws SQLException {
        // given
        Set<String> list = UtilsCommand.getDataSet();
        list.add("id");
        list.add("name");
        list.add("password");
        when(manager.getTableColumns("test")).thenReturn((Set<String>) list);

        when(manager.getTableData("test")).thenReturn(Collections.EMPTY_SET);

        // when
        command.process("clear|test");
        command = new Find(manager, view);
        command.process("find|test");

        // then
        shouldPrint("table 'test' was successfully cleared\r\n" +
                "+-------+---------------+---------------+\r\n" +
                "|id     |name           |password       |\r\n" +
                "+-------+---------------+---------------+\r\n" +
                "+-------+---------------+---------------+");
    }

    @Test
    public void testPrintClearTableData() throws SQLException {
        // given
        Set<String> list = UtilsCommand.getDataSet();
        list.add("c_id");
        list.add("c_name");
        list.add("c_password");

        when(manager.getTableColumns("test"))
                .thenReturn(list);

        Map<String, Object> user1 = UtilsCommand.getDataMap();
        user1.put("c_id", 12);
        user1.put("c_name", "Stiven");
        user1.put("c_password", "*****");

        Map<String, Object> user2 = UtilsCommand.getDataMap();
        user2.put("c_id", 13);
        user2.put("c_name", "Eva");
        user2.put("c_password", "+++++");

        Set<Map<String, Object>> data = UtilsCommand.getDataSetMap();
        data.add(user1);
        data.add(user2);
        when(manager.getTableData("test"))
                .thenReturn(data);

        // when
        command.process("clear|test");

        // then
        shouldPrint("table 'test' was successfully cleared");
    }
}