package com.juja.model;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseManager {

    Connection getConnection();

    DataSet[] getTableData(String tableName);

    int getSize(String tableName) throws SQLException;

    String[] getTableNames();

    void clear(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);
}
