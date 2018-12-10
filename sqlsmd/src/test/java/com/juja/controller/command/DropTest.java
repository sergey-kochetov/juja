package com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DropTest extends CommandHelperTest {
    @Before
    public void setup() {
        command = new Drop(manager, view);
    }

    @Test
    public void testCanProcessDropWithParametersValidString() {
        //when
        boolean canProcess = command.canProcess("drop|table");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessDropWithParametersNovalidString() {
        //when
        boolean canProcess = command.canProcess("novalid|table");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testDropTable() {
        // given

        // when
        command.process("drop|customer");

        // then
        shouldPrint("table 'customer' was successfully droped");
    }

    @Test
    public void testDropEmptyTable() throws SQLException {
        // given
        Mockito.doThrow(new SQLException()).when(manager).drop("test");

        // when
        command.process("drop|test");

        // then
        shouldPrint("table 'test' does not exist");
    }
}