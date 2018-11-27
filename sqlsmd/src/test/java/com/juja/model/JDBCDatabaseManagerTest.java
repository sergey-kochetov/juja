package com.juja.model;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest extends DatabaseManagerTest {

    @Override
    public DatabaseManager getDatabaseManager() {
        return new JDBCDatabaseManager();
    }

    @Override
    public Object store() {
        return null;
    }

    @Before
    public void setup() {
//        try {
//            DBinit.startUp();
//        } catch (URISyntaxException | IOException e) {
//            e.printStackTrace();
//        }
        manager = getDatabaseManager();
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
        String customer = "customer";
        manager.clear(customer);

        // when
        DataSet input = new DataSet();
        input.put("c_id", 13);
        input.put("c_name", "Stiven");
        input.put("c_password", "pass");
        manager.create(customer, input);

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
        String customer = "customer";
        manager.clear(customer);

        DataSet input = new DataSet();
        input.put("c_id", 13);
        input.put("c_name", "Stiven");
        input.put("c_password", "pass");
        manager.create(customer, input);

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

    @Test
    public void testGetColumnNames() {
        // given
        String customer = "customer";
        manager.clear(customer);

        // when
        String[] columnNames = manager.getTableColumns(customer);

        // then
        assertEquals("[c_id, c_name, c_password]", Arrays.toString(columnNames));

    }
}