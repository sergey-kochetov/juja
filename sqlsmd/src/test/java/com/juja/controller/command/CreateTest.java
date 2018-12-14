package com.juja.controller.command;

import com.juja.controller.util.UtilsCommand;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class CreateTest extends CommandHelperTest {
    @Before
    public void setup() {
        command = new Create(manager, view);
    }

    @Test
    public void testCanProcessCreateWithParametersValidString() {
        //when
        boolean canProcess = command.canProcess("create|table");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessCreateWithParametersNovalidString() {
        //when
        boolean canProcess = command.canProcess("novalid|table");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testCreateTable() throws SQLException {
        // given
        List<String> list = UtilsCommand.getDataList();
        list.add("id INT");
        doThrow(new SQLException()).when(manager).create("customer", list);
        // when
        command.process("create|test|id|INT");

        // then
        shouldPrint("table 'test' was successfully created");
    }

    @Test
    public void shouldCreateEmptyTableThenError() throws SQLException {
        // given
        List<String> list = UtilsCommand.getDataList();
        doThrow(new SQLException()).when(manager).create("test", list);

        // when
        command.process("create|test");

        // then
        verify(view).write("format create|tableName|column1|type1|...|columnN|typeN, but was: create|test");
    }
}