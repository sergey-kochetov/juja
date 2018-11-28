package com.juja.model;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseManager {

    void connect(String database, String userName, String password);

    Connection getConnection();

    DataSet[] getTableData(String tableName);

    int getSize(String tableName) throws SQLException;

    String[] getTableNames();

    void clear(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    String[] getTableColumns(String tableName);

    boolean isConnected();
}
