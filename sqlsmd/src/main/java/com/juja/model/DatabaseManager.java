package com.juja.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DatabaseManager {

    void connect(String database, String userName, String password) throws SQLException;

    Connection getConnection() throws SQLException;

    DataSet[] getTableData(String tableName) throws SQLException;

    int getSize(String tableName) throws SQLException;

    List<String> getTableNames() throws SQLException;

    void clear(String tableName) throws SQLException;

    void create(String tableName, DataSet input) throws SQLException;

    void update(String tableName, int id, DataSet newValue) throws SQLException;

    List<String> getTableColumns(String tableName) throws SQLException;

    boolean isConnected();
}
