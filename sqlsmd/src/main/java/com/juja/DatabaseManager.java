package com.juja;

import com.juja.config.Config;

import java.sql.*;
import java.util.Arrays;

public class DatabaseManager {
    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        Config.getProperty(Config.DB_URL),
                        Config.getProperty(Config.DB_LOGIN),
                        Config.getProperty(Config.DB_PASSWORD));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public DataSet[] getTableData(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);

        DataSet[] result = new DataSet[size];

        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
        ResultSetMetaData rsmd = rs.getMetaData();
        int index = 0;
        while (rs.next()) {
            DataSet dataSet = new DataSet();
            result[index++] = dataSet;
            for (int i = 1; i < rsmd.getColumnCount(); i++) {
                dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
            }
        }
        rs.close();
        stmt.close();

        System.out.println(Arrays.toString(result));
        return result;
    }
}
