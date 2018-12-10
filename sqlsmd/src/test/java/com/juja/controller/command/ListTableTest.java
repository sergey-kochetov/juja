package com.juja.controller.command;

import com.juja.controller.UtilsCommand;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ListTableTest extends CommandHelperTest {
    @Before
    public void setup() {
        command = new ListTable(manager, view);
    }

    @Test
    public void testCanProcessListWithParametersString() {
        // when
        boolean canProcess = command.canProcess("tables");

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
        command.process("tables");

        // then
        shouldPrint("[test1, test2]");
    }

    @Test
    public void testListDataWithEmptyTable() throws SQLException {
        // given
        List<String> list = UtilsCommand.getDataList();
        when(manager.getTableNames()).thenReturn(list);

        // when
        command.process("tables");

        // then
        shouldPrint("[]");
    }
    @Test
    public void shouldSQLExceptionThenPrintError() throws SQLException {
        // given
        String com = "tables";
        doThrow(new SQLException()).when(manager).getTableData("test");

        // when
        command.process(com);

        // then
        shouldPrint("[]");
    }
}