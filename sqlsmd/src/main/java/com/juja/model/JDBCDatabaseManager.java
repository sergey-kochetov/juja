package com.juja.model;

import com.juja.config.Config;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class JDBCDatabaseManager implements DatabaseManager  {
    private static final String SELECT_TABLE_NAMES = "SELECT table_name FROM information_schema.tables " +
            "WHERE table_schema='public' AND table_type='BASE TABLE'";
    private static final String SELECT_SIZE_TABLE = "SELECT COUNT(*) FROM public.";
    private static final String URL_CONNECT_DB = "jdbc:postgresql://localhost:5432/";
    private static final String SQL_DELETE = "DELETE FROM public.";
    private static final String SQL_INSERT = "INSERT INTO public.";
    private static final String SQL_GET_TABLE_COLUMNS = "SELECT * FROM information_schema.columns " +
            "WHERE table_schema=? AND table_name = ?";

    private Connection connection;

    private void defaultConnect() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection(
                        Config.getProperty(Config.DB_URL),
                        Config.getProperty(Config.DB_LOGIN),
                        Config.getProperty(Config.DB_PASSWORD));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void connect(String database, String userName, String password) throws SQLException {
        try {
            connection = DriverManager.getConnection(
                    URL_CONNECT_DB + database,
                    userName,
                    password);
        } catch (SQLException e) {
            connection.close();
            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s",
                            database, userName), e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        checkConnection();
        return connection;
    }

    @Override
    public List<DataSet> getTableData(String tableName) throws SQLException {
        checkConnection();
        try (  Statement stmt = connection.createStatement();
               ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
        ) {
            int size = getSize(tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            List<DataSet> result = new LinkedList<>();
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result.add(dataSet);
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
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
            List<String> tables = new LinkedList<>();
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
            stmt.executeUpdate(SQL_DELETE + tableName);
        }
    }

    @Override
    public void create(String tableName, DataSet input) throws SQLException {
        checkConnection();
        try ( Statement stmt = connection.createStatement()) {
            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            stmt.executeUpdate(SQL_INSERT + tableName + " (" + tableNames + ")" +
                    "VALUES (" + values + ")");
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) throws SQLException {
        checkConnection();
        String tableNames = getNameFormated(newValue, "%s = ?,");
        String nameId = "c_id";
        String sql = "UPDATE public." + tableName + " SET " + tableNames +
                " WHERE " + nameId + "= ?";

        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            int index = 1;
            for (Object value : newValue.getValues()) {
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
        try {
            PreparedStatement stmt = connection.prepareStatement(SQL_GET_TABLE_COLUMNS);
            stmt.setString(1, "public");
            stmt.setString(2, tableName);

            ResultSet rs = stmt.executeQuery();
            List<String> tables = new LinkedList<>();
            while (rs.next()) {
                tables.add(rs.getString("column_name"));
            }
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    private String getNameFormated(DataSet newValue, String format) {
        StringBuilder string = new StringBuilder();
        for (String name : newValue.getNames()) {
            string.append(String.format(format, name));
        }
        string = new StringBuilder(string.substring(0, string.length() - 1));
        return string.toString();
    }

    private String getValuesFormated(DataSet input, String format) {
        StringBuilder values = new StringBuilder();
        for (Object value: input.getValues()) {
            values.append(String.format(format, value));
        }
        values = new StringBuilder(values.substring(0, values.length() - 1));
        return values.toString();
    }

    private void checkConnection() throws SQLException {
        if (connection == null) {
            throw new SQLException("no connect to database.");
        }
    }


}
