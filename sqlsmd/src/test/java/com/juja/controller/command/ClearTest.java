package com.juja.controller.command;

import com.juja.controller.UtilsCommand;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
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
        boolean canProcess = command.canProcess("novalid|table");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintEmptyTableData() throws SQLException {
        // given
        List<String> list = UtilsCommand.getDataList();
        list.add("id");
        list.add("name");
        list.add("password");
        when(manager.getTableColumns("test")).thenReturn(list);

        when(manager.getTableData("test")).thenReturn(Collections.EMPTY_LIST);

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
        List<String> list = UtilsCommand.getDataList();
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

        List<Map<String, Object>> data = UtilsCommand.getDataListMap();
        data.add(user1);
        data.add(user2);
        when(manager.getTableData("test"))
                .thenReturn(data);

        // when
        command.process("clear|test");
        command = new Find(manager, view);
        command.process("find|test");

        // then
        shouldPrint("table 'test' was successfully cleared\r\n" +
                "+-------+---------------+---------------+\r\n" +
                "|c_id   |c_name         |c_password     |\r\n" +
                "+-------+---------------+---------------+\r\n" +
                "|12     |Stiven         |*****          |\r\n" +
                "|13     |Eva            |+++++          |\r\n" +
                "+-------+---------------+---------------+");
    }
}