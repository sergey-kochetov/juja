package com.juja;

import java.sql.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Statement stmt = null;

        Connection connection =  new DatabaseManager().getConnection();


        //insert
        stmt = connection.createStatement();

        String sql = "INSERT INTO users (id, name, password) VALUES (100, 'name1', 'pass1')";
        stmt.executeUpdate(sql);

        //select

        String sql1 = "SELECT * FROM users WHERE id > 1";
        ResultSet rs = stmt.executeQuery(sql1);
        while (rs.next()) {
            System.out.print("Column 1 returned ");
            System.out.println("id:" + rs.getInt("id"));
            System.out.println("name:" + rs.getString("name"));
            System.out.println("pass:" + rs.getString("password"));
        }
        rs.close();
        stmt.close();

        // delete
        stmt = connection.createStatement();
        String sql2 = "DELETE FROM users WHERE id > 10 AND id < 100";
        stmt.executeUpdate(sql2);

        //update
        String sql3 = "UPDATE users SET password = ? WHERE id > 3";
        PreparedStatement ps = connection.prepareStatement(sql3);
        ps.setString(1, "password_" + new Random().nextInt(100));

        connection.close();


    }

}
