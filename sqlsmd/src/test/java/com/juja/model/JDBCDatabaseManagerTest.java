package com.juja.model;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void setup() throws SQLException {
//        try {
//            DBinit.startUp();
//        } catch (URISyntaxException | IOException e) {
//            e.printStackTrace();
//        }
        manager = getDatabaseManager();
        manager.connect("sqlsmd", "postgres", "postgres");
    }

    @Test
    public void testGetAllTableNames() throws SQLException {
        List<String> tableNames = manager.getTableNames();
        assertEquals("[customer]", tableNames.toString());
    }

    @Test
    public void testGetTableData() throws SQLException {
        // given
        String customer = "customer";
        manager.clear(customer);

        // when
        Map<String, Object> input = new HashMap<>();
        input.put("c_id", 13);
        input.put("c_name", "Stiven");
        input.put("c_password", "pass");
        manager.create(customer, input);

        // then
        List<Map<String, Object>> users = manager.getTableData("customer");
        assertEquals(1, users.size());

        Map<String, Object> user = users.get(0);
        assertEquals("[c_id, c_name, c_password]", user.keySet());
        assertEquals("[13, Stiven, pass]", user.values());
    }

    @Test
    public void testUpdateTableData() throws SQLException {
        // given
        String customer = "customer";
        manager.clear(customer);

        Map<String, Object> input = new HashMap<>();
        input.put("c_id", 10);
        input.put("c_name", "name");
        input.put("c_password", "pass");
        manager.create(customer, input);

        // when
        Map<String, Object> newValue = new HashMap<>();
        newValue.put("c_name", "newName");
        newValue.put("c_password", "newPass");
        manager.update("customer", 10, newValue);

        // then
        List<Map<String, Object>> users = manager.getTableData("customer");
        assertEquals(1, users.size());

        Map<String, Object> user = users.get(0);
        assertEquals("[c_id, c_name, c_password]", user.keySet());
        assertEquals("[10, newName, newPass]", user.values());
    }

    @Test
    public void testGetColumnNames() throws SQLException {
        // given
        String customer = "customer";
        manager.clear(customer);

        // when
        List<String> columnNames = manager.getTableColumns(customer);

        // then
        assertEquals("[c_id, c_name, c_password]", columnNames.toString());

    }
    @Test
    public void testIsConnected() throws SQLException {
        assertEquals(true, manager.isConnected());
    }
}