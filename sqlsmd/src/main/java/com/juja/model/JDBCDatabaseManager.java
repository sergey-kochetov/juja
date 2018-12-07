package com.juja.model;

import com.juja.config.ConfigDB;
import com.juja.config.ConfigMsg;
import com.juja.controller.UtilsCommand;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class JDBCDatabaseManager implements DatabaseManager  {

    private static final String URL_CONNECT_DB = "jdbc:postgresql://localhost:5432/";

    private static final String SELECT_TABLE_NAMES = "SELECT table_name FROM information_schema.tables " +
            "WHERE table_schema='public' AND table_type='BASE TABLE'";
    private static final String SELECT_SIZE_TABLE = "SELECT COUNT(*) FROM ";
    private static final String DROP_TABLE = "DROP TABLE ";
    private static final String SQL_INSERT = "INSERT INTO ";
    private static final String SQL_GET_TABLE_COLUMNS = "SELECT * FROM information_schema.columns " +
            "WHERE table_schema=? AND table_name = ?";
    private static final String CLEAR_TABLE = "TRUNCATE ";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE ? (?)";
    private static final String DELETE_DATA = "DELETE FROM ? WHERE (?)";

    private Connection connection;

    @Override
    public void defaultConnect() throws SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection(
                    ConfigDB.getProperty(ConfigDB.DB_URL),
                    ConfigDB.getProperty(ConfigDB.DB_LOGIN),
                    ConfigDB.getProperty(ConfigDB.DB_PASSWORD));
        }
    }

    @Override
    public void connect(String database, String userName, String password) throws SQLException {
        try {
            connection = DriverManager.getConnection(
                    URL_CONNECT_DB + database, userName, password);
        } catch (SQLException e) {
            throw new SQLException(
                    String.format(ConfigMsg.getProperty("db.err.format"),
                            database, userName), e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        checkConnection();
        return connection;
    }

    @Override
    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) throws SQLException {
        checkConnection();
        try (  Statement stmt = connection.createStatement();
               ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
        ) {
            ResultSetMetaData rsmd = rs.getMetaData();
            List<Map<String, Object>> result = UtilsCommand.getDataListMap();
            while (rs.next()) {
                Map<String, Object> dataSet = UtilsCommand.getDataMap();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                result.add(dataSet);
            }
            return result;
        }
    }

    @Override
    public int getSize(String tableName) throws SQLException {
        checkConnection();
        try (Statement  stmt = connection.createStatement();
             ResultSet rsCount = stmt.executeQuery(SELECT_SIZE_TABLE + tableName);
        ) {
            rsCount.next();
            return rsCount.getInt(1);
        }
    }

    @Override
    public List<String> getTableNames() throws SQLException {
        checkConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_TABLE_NAMES);
        ) {
            List<String> tables = UtilsCommand.getDataList();
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
            return tables;
        }
    }

    @Override
    public void clear(String tableName) throws SQLException {
        checkConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(CLEAR_TABLE + tableName);
        }
    }

    @Override
    public void drop(String tableName) throws SQLException {
        checkConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(DROP_TABLE + tableName);
        }
    }
    @Override
    public void delete(String tableName,  Map<String, Object> delValue) throws SQLException {
        checkConnection();
        String delete = getNameValuesFormated(delValue, "%s='%s'");
        try (PreparedStatement stmt= connection.prepareStatement(DELETE_DATA)) {
            stmt.setString(1, tableName);
            stmt.setString(2, delete);
            stmt.executeUpdate();
        }
    }

    @Override
    public void create(String tableName, List<String> input) throws SQLException {
        checkConnection();
        String columnsName = input.stream()
                .collect(Collectors.joining(", "));
        try ( PreparedStatement stmt = connection.prepareStatement(SQL_CREATE_TABLE)) {
            stmt.setString(1, tableName);
            stmt.setString(2, columnsName);
            stmt.executeUpdate();
        }
    }

    @Override
    public void insert(String tableName, Map<String, Object> input) throws SQLException {
        checkConnection();
        String tableNames = getNameFormated(input, "%s");
        String values = getValuesFormated(input, "'%s'");
        String sql = SQL_INSERT + tableName + " (" + tableNames + ")" + "VALUES (" + values + ")";
        try ( Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    @Override
    public void update(String tableName, int id, Map<String, Object> newValue) throws SQLException {
        checkConnection();
        String tableNames = getNameFormated(newValue, "%s = ?");
        String nameId = getTableColumns(tableName).get(0);
        String sql = "UPDATE " + tableName + " SET " + tableNames + " WHERE " + nameId + "= ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : newValue.values()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setObject(index, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<String> getTableColumns(String tableName) throws SQLException {
        checkConnection();
        try(PreparedStatement stmt = connection.prepareStatement(SQL_GET_TABLE_COLUMNS)) {
            stmt.setString(1, "public");
            stmt.setString(2, tableName);
            ResultSet rs = stmt.executeQuery();
            List<String> tables = UtilsCommand.getDataList();
            while (rs.next()) {
                tables.add(rs.getString("column_name"));
            }
            return tables;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    private String getNameFormated(Map<String, Object> input, String format) {

        return input.keySet()
                .stream()
                .map(entry -> String.format(format, entry))
                .collect(Collectors.joining(","));
    }

    private String getValuesFormated(Map<String, Object> input, String format) {

        return input.values()
                .stream()
                .map(entry -> String.format(format, entry))
                .collect(Collectors.joining(","));
    }

    private String getNameValuesFormated(Map<String, Object> delValue, String format) {
        return delValue.entrySet()
                .stream()
                .map(entry -> String.format(format, entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", "));
    }

    private void checkConnection() throws SQLException {
        if (connection == null) {
            throw new SQLException(ConfigMsg.getProperty("db.noconnect"));
        }
    }
}
