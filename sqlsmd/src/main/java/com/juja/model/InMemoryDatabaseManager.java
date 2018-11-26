package com.juja.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class InMemoryDatabaseManager implements DatabaseManager {

    public static final String TABLE_NAME = "customer"; // TODO implement multitables

    private DataSet[] data = new DataSet[1000];
    private int freeIndex = 0;


    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public DataSet[] getTableData(String tableName) {
        validateTable(tableName);

        return Arrays.copyOf(data, freeIndex);
    }

    @Override
    public int getSize(String tableName) throws SQLException {
        validateTable(tableName);
        return freeIndex;
    }

    @Override
    public String[] getTableNames() {
        return new String[] { TABLE_NAME };
    }

    @Override
    public void clear(String tableName) {
        validateTable(tableName);

        data = new DataSet[1000];
        freeIndex = 0;
    }

    @Override
    public void create(String tableName, DataSet input) {
        validateTable(tableName);

        data[freeIndex] = input;
        freeIndex++;
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        validateTable(tableName);

        for (int index = 0; index < freeIndex; index++) {
            if (Integer.valueOf((Integer) data[index].get("id")) == id) {
                data[index].updateFrom(newValue);
            }
        }
    }

    private void validateTable(String tableName) {
        if (!"customer".equals(tableName)) {
            throw new UnsupportedOperationException("Only for 'user' table, but you try to work with: " + tableName);
        }
    }
}
