package com.juja.integration;

import com.juja.controller.Main;
import com.juja.model.DatabaseManager;
import com.juja.model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;
    private static DatabaseManager databaseManager;


    @Before
    public void setup() {
        databaseManager = new JDBCDatabaseManager();

        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testExit() {
        // given
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome to sqlsmd.\r\n" +
                "Enter please connect|database|userName|password\r\n" +
                "bye...\r\n", getData());
    }
    @Test
    public void testHelp() {
        // given
        in.add("help");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome to sqlsmd.\r\n" +
                "Enter please connect|database|userName|password\r\n" +
                "Existing commands\r\n" +
                "\tconnect|databaseName|userName|password\r\n" +
                "\t\tfor connect to database\r\n" +
                "\thelp\r\n" +
                "\t\tthis page\r\n" +
                "\tlist\r\n" +
                "\t\tfor get list database\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tfor see table 'tableName'\r\n" +
                "\texit\r\n" +
                "\t\tfor exit program\r\n" +
                "Enter command (or 'help'): \r\n" +
                "bye...\r\n", getData());
    }
    @Test
    public void testListAndExit(){
        // given

        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome to sqlsmd.\r\n" +
                "Enter please connect|database|userName|password\r\n" +
                "Enter please connect|database|userName|password\r\n" +
                "Enter command (or 'help'): \r\n" +
                "bye...\r\n", getData());
    }
    @Test
    public void testUnsupported(){
        // given

        in.add("connect|sqlsmd|postgres|postgres");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome to sqlsmd.\r\n" +
                "Enter please connect|database|userName|password\r\n" +
                "Connect Successful\r\n" +
                "Enter command (or 'help'): \r\n" +
                "bye...\r\n", getData());
    }
    @Test
    public void testFindAfterConnect(){
        // given

        in.add("connect|sqlsmd|postgres|postgres");
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome to sqlsmd.\r\n" +
                "Enter please connect|database|userName|password\r\n" +
                "Connect Successful\r\n" +
                "Enter command (or 'help'): \r\n" +
                "[customer]\r\n" +
                "Enter command (or 'help'): \r\n" +
                "bye...\r\n", getData());
    }
    @Test
    public void testFindWithAfterConnect(){
        // given

        in.add("connect|sqlsmd|postgres|postgres");
        in.add("clear|customer");
        in.add("find|customer");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome to sqlsmd.\r\n" +
                "Enter please connect|database|userName|password\r\n" +
                "Connect Successful\r\n" +
                "Enter command (or 'help'): \r\n" +
                "table 'customer' was successfully cleared.\r\n" +
                        "Enter command (or 'help'): \r\n" +
                "+++++++++++++++\r\n" +
                "|c_id|c_name|c_password|\r\n" +
                "+++++++++++++++\r\n" +
                "Enter command (or 'help'): \r\n" +
                "bye...\r\n", getData());
    }
    @Test
    public void testFindWithConnect_withData(){
        // given

        in.add("connect|sqlsmd|postgres|postgres");
        in.add("clear|customer");
        in.add("create|customer|c_id|13|c_name|adam|c_password|***");
        in.add("create|customer|c_id|14|c_name|eva|c_password|+++");
        in.add("find|customer");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome to sqlsmd.\r\n" +
                "Enter please connect|database|userName|password\r\n" +
                "Connect Successful\r\n" +
                "Enter command (or 'help'): \r\n" +
                "table 'customer' was successfully cleared.\r\n" +
                "Enter command (or 'help'): \r\n" +
                "data '{names:[c_id, c_name, c_password], values:[13, adam, ***]}' was successfully created in table 'customer'\r\n" +
                "Enter command (or 'help'): \r\n" +
                "data '{names:[c_id, c_name, c_password], values:[14, eva, +++]}' was successfully created in table 'customer'\r\n" +
                "Enter command (or 'help'): \r\n" +
                "+++++++++++++++\r\n" +
                "|c_id|c_name|c_password|\r\n" +
                "+++++++++++++++\r\n" +
                "|13|adam|***|\r\n" +
                "|14|eva|+++|\r\n" +
                "Enter command (or 'help'): \r\n" +
                "bye...\r\n", getData());
    }


    private String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }


}
