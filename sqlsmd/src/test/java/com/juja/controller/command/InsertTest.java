package com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doThrow;

public class InsertTest extends CommandHelperTest {
    @Before
    public void setup() {
        command = new Insert(manager, view);
    }

    @Test
    public void testCanProcessCreateWithParametersString() {
        // when
        boolean canProcess = command.canProcess("insert|customer|c_id|10");

        // then
        assertTrue(canProcess);
    }
    @Test
    public void testCantProcessCreateWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("novalid|customer|c_id|10");

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
        // when
        command.process("insert|customer|c_id|1|c_name|name1|c_password|+++++");

        // then
        shouldPrint("'{c_id=1, c_name=name1, c_password=+++++}' " +
                "was successfully created in table 'customer'");
    }

    @Test
    public void testPrintCreateDataTwo() throws SQLException {
        // given
        // when
        command.process("insert|test|id|1");
        command.process("insert|test|id|2");

        // then
        shouldPrint("'{id=1}' was successfully created in table 'test'\r\n" +
                "'{id=2}' was successfully created in table 'test'");
    }

    @Test
    public void shouldSQLExceptionThenPrintError() throws SQLException {
        // given
        String com = "insert|x|x";

        // when
        doThrow(new SQLException()).when(manager).getTableColumns("test");
        doThrow(new SQLException()).when(manager).getTableData("test");
        command.process(com);
        shouldPrint("No valid data % 2 == 0");
    }
}