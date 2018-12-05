package com.juja.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DatabaseManager {

    void defaultConnect() throws SQLException;

    void connect(String database, String userName, String password) throws SQLException;

    Connection getConnection() throws SQLException;

    List<Map<String, Object>> getTableData(String tableName) throws SQLException;

    int getSize(String tableName) throws SQLException;

    List<String> getTableNames() throws SQLException;

    void clear(String tableName) throws SQLException;

    void delete(String tableName) throws SQLException;

    void create(String tableName, Map<String, Object> input) throws SQLException;

    void update(String tableName, int id, Map<String, Object> newValue) throws SQLException;

    List<String> getTableColumns(String tableName) throws SQLException;

    void disconnect() throws SQLException;

    boolean isConnected();
}
