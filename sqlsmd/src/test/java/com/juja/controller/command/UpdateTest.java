package com.juja.controller.command;

import com.juja.controller.UtilsCommand;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;

public class UpdateTest extends CommandHelperTest {
    @Before
    public void setup() {
        command = new Update(manager, view);
    }

    @Test
    public void testCanProcessUpdateWithParametersValidString() {
        //when
        boolean canProcess = command.canProcess("update|table");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessUpdateWithParametersNovalidString() {
        //when
        boolean canProcess = command.canProcess("novalid|table");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testUpdateTable() {
        // given

        // when
        command.process("update|test|1|column1|type1");

        // then
        shouldPrint("data successfully updated to tables 'test'");
    }

    @Test
    public void testUpdateEmptyTableIncorrect() throws SQLException {
        // given
        doThrow(new SQLException()).when(manager).update("test", 1, UtilsCommand.getDataMap());

        // when
        command.process("update|test|1|x");

        // then
        shouldPrint("format update|tableName|column1|value1|column2|value2|" +
                "...|columnN|valueN, but was: update|test|1|x");
    }

    @Test
    public void testUpdateEmptyTableError() throws SQLException {
        // given
        doThrow(new SQLException()).when(manager).update(anyString(), anyInt(), anyMap());

        // when
        command.process("update|test|1|column1|value1");

        // then
        shouldPrint("incorrect data to update the table 'test'");
    }
}