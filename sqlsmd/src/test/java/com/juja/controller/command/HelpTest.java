package com.juja.controller.command;

import com.juja.controller.MainController;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class HelpTest extends CommandHelperTest {
    @Before
    public void setup() {
        command = new Help(view, new MainController(view, manager).getCommands());
    }

    @Test
    public void testCanProcess_valid() {
        // when
        boolean canProcess = command.canProcess("help");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcess_novalid() {
        // when
        boolean canProcess = command.canProcess("novalid");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintMenuHelp() {
        // given

        // when
        command.process("help");

        // then
        shouldPrint("Existing commands\r\n" +
                "\tconnect|database|userName|password\r\n" +
                "\t\tfor connect to database\r\n" +
                "\thelp\r\n" +
                "\t\tthis page\r\n" +
                "\texit\r\n" +
                "\t\tfor exit program\r\n" +
                "\ttables\r\n" +
                "\t\tfor get tables database\r\n" +
                "\tclear|tableName\r\n" +
                "\t\tdatabase cleaning\r\n" +
                "\tdrop|tableName\r\n" +
                "\t\tdrop database\r\n" +
                "\tinsert|tableName|row1|param1|...|rowN|paramN\r\n" +
                "\t\tcreate data for database\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tfor see table 'tableName'\r\n" +
                "\tupdate|tableName|column1|value1|column2|value2|...|columnN|valueN\r\n" +
                "\t\tupdate data into a table\r\n" +
                "\tcreate|tableName|column1|type1|...|columnN|typeN\r\n" +
                "\t\tcreate new table\r\n" +
                "\tdelete|tableName|column|value\r\n" +
                "\t\tdeleting data in the table with parameters");
    }
}