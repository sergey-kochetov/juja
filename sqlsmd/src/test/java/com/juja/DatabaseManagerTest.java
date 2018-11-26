package com.juja;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DatabaseManagerTest {
    private DatabaseManager manager;

    @Before
    public void setup() {
        try {
            DBinit.startUp();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        manager = new DatabaseManager();
       Connection connection = manager.getConnection();
    }

    @Test
    public void testGetAllTableNames() {
        String[] tableNames = manager.getTableNames();
        assertEquals("[customer]", Arrays.toString(tableNames));
    }

    @Test
    public void testGetTableData() {
        // given
        manager.clear("customer");

        // when
        DataSet input = new DataSet();
        input.put("c_id", 13);
        input.put("c_name", "Stiven");
        input.put("c_password", "pass");
        manager.create(input);

        // then
        DataSet[] users = manager.getTableData("customer");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[c_id, c_name, c_password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Stiven, pass]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateTableData() {
        // given
        manager.clear("customer");

        DataSet input = new DataSet();
        input.put("c_id", 13);
        input.put("c_name", "Stiven");
        input.put("c_password", "pass");
        manager.create(input);

        // when
        DataSet newValue = new DataSet();
        newValue.put("c_password", "pass2");
        newValue.put("c_name", "Pup");
        manager.update("customer", 13, newValue);

        // then
        DataSet[] users = manager.getTableData("customer");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[c_id, c_name, c_password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Pup, pass2]", Arrays.toString(user.getValues()));
    }

}