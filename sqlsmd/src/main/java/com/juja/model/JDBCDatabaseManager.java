package com.juja.model;

import com.juja.config.Config;

import java.sql.*;
import java.util.Arrays;

public class JDBCDatabaseManager implements DatabaseManager  {
    private Connection connection;

    private void connect() {
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
    public void connect(String database, String userName, String password) {
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, userName,
                    password);
        } catch (SQLException e) {
            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s",
                            database, userName),
                    e);
        }
    }

    @Override
    public Connection getConnection() {
        connect();
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        JDBCDatabaseManager manager = new JDBCDatabaseManager();
        Connection connection = manager.getConnection();

        // delete
        String customer = "customer";
        manager.clear(customer);

        // insert
        DataSet data = new DataSet();
        data.put("c_id", 13);
        data.put("c_name", "Stiven");
        data.put("c_password", "pass");
        manager.create(customer, data);

        // select
        String[] tables = manager.getTableNames();
        System.out.println(Arrays.toString(tables));

        String tableName = customer;

        DataSet[] result = manager.getTableData(tableName);
        System.out.println(Arrays.toString(result));

        // update


        connection.close();
    }
    @Override
    public DataSet[] getTableData(String tableName) {
        try (  Statement stmt = connection.createStatement();
               ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
        ) {

            int size = getSize(tableName);

            ResultSetMetaData rsmd = rs.getMetaData();
            DataSet[] result = new DataSet[size];
            int index = 0;
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result[index++] = dataSet;
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new DataSet[0];
        }
    }
    @Override
    public int getSize(String tableName) {
        try ( Statement  stmt = connection.createStatement();
              ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
        ) {
            rsCount.next();
            int size = rsCount.getInt(1);
            return size;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getTableNames() {
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");
        ) {
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("table_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);

            return tables;
        } catch (SQLException e) {
            return new String[0];
        }
    }
    @Override
    public void clear(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public." + tableName);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void create(String tableName, DataSet input) {
        try ( Statement stmt = connection.createStatement()) {


            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            stmt.executeUpdate("INSERT INTO public." + tableName + " (" + tableNames + ")" +
                    "VALUES (" + values + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        String tableNames = getNameFormated(newValue, "%s = ?,");
        String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE c_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setObject(index, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getTableColumns(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns " +
                     "WHERE table_schema='public' AND table_name = '" + tableName +"'")
        ) {
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("column_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            return tables;
        } catch (SQLException e) {
            return new String[0];
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


}
