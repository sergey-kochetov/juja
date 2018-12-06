package com.juja.integration;

import com.juja.controller.Main;
import com.juja.model.DatabaseManager;
import com.juja.model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;
    private static DatabaseManager manager = new JDBCDatabaseManager();


    @BeforeClass
    public static void startUp() throws URISyntaxException, IOException, SQLException {
        // given
        URL url1 = JDBCDatabaseManager.class.getClassLoader()
                .getResource("create_table.sql");
        URL url2 = JDBCDatabaseManager.class.getClassLoader()
                .getResource("data_customer.sql");


        List<String> lines1 = Files.readAllLines(Paths.get(url1.toURI()));
        String sql1 = lines1.stream().collect(Collectors.joining());

        List<String> lines2 = Files.readAllLines(Paths.get(url2.toURI()));
        String sql2 = lines2.stream().collect(Collectors.joining());



        manager.defaultConnect();
        try (Connection con = manager.getConnection();
             Statement stmp = con.createStatement();
        ) {
            stmp.executeUpdate(sql1);
            stmp.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() {
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
                        "\t\tdeleting data in the table with parameters\r\n" +
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
        in.add("tables");
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
                "table 'customer' was successfully cleared\r\n" +
                "Enter command (or 'help'): \r\n" +
                "+-------+---------------+---------------+\r\n" +
                "|c_id   |c_name         |c_password     |\r\n" +
                "+-------+---------------+---------------+\r\n" +
                "+-------+---------------+---------------+\r\n" +
                "Enter command (or 'help'): \r\n" +
                "bye...\r\n", getData());
    }
    @Test
    public void testFindWithConnect_withData(){
        // given

        in.add("connect|sqlsmd|postgres|postgres");
        in.add("clear|customer");
        in.add("insert|customer|c_id|13|c_name|adam|c_password|***");
        in.add("insert|customer|c_id|14|c_name|eva|c_password|+++");
        in.add("find|customer");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Welcome to sqlsmd.\r\n" +
                "Enter please connect|database|userName|password\r\n" +
                "Connect Successful\r\n" +
                "Enter command (or 'help'): \r\n" +
                "table 'customer' was successfully cleared\r\n" +
                "Enter command (or 'help'): \r\n" +
                "'{c_id=13, c_name=adam, c_password=***}' was successfully created in table 'customer'\r\n" +
                "Enter command (or 'help'): \r\n" +
                "'{c_id=14, c_name=eva, c_password=+++}' was successfully created in table 'customer'\r\n" +
                "Enter command (or 'help'): \r\n" +
                "+-------+---------------+---------------+\r\n" +
                "|c_id   |c_name         |c_password     |\r\n" +
                "+-------+---------------+---------------+\r\n" +
                "|13     |adam           |***            |\r\n" +
                "|14     |eva            |+++            |\r\n" +
                "+-------+---------------+---------------+\r\n" +
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
