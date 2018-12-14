package com.juja.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DatabaseManager {

    void defaultConnect() throws SQLException;

    void connect(String database, String userName, String password) throws SQLException;

    Connection getConnection() throws SQLException;

    Set<Map<String, Object>> getTableData(String tableName) throws SQLException;

    int getSize(String tableName) throws SQLException;

    Set<String> getTableNames() throws SQLException;

    void clear(String tableName) throws SQLException;

    void drop(String tableName) throws SQLException;

    void delete(String tableName, Map<String, Object> delValue) throws SQLException;

    void create(String tableName, List<String> input) throws SQLException;

    void insert(String tableName, Map<String, Object> input) throws SQLException;

    void update(String tableName, int id, Map<String, Object> newValue) throws SQLException;

    Set<String> getTableColumns(String tableName) throws SQLException;

    void disconnect() throws SQLException;

    boolean isConnected();
}
