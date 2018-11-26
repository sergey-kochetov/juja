package com.juja;

import java.sql.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Statement stmt = null;

        Connection connection =  new DatabaseManager().getConnection();


        //insert
        stmt = connection.createStatement();

        String sql = "INSERT INTO customer (c_id, c_name, c_password) VALUES (10, 'name1', 'pass1')";
        stmt.executeUpdate(sql);

        //select

        String sql1 = "SELECT * FROM customer WHERE c_id > 1";
        ResultSet rs = stmt.executeQuery(sql1);
        while (rs.next()) {
            System.out.print("Column 1 returned ");
            System.out.println("id:" + rs.getInt("c_id"));
            System.out.println("name:" + rs.getString("c_name"));
            System.out.println("pass:" + rs.getString("c_password"));
        }
        rs.close();
        stmt.close();

        // delete
        stmt = connection.createStatement();
        String sql2 = "DELETE FROM customer WHERE c_id > 10 AND c_id < 100";
        stmt.executeUpdate(sql2);

        //update
        String sql3 = "UPDATE customer SET password = ? WHERE c_id > 3";
        PreparedStatement ps = connection.prepareStatement(sql3);
        ps.setString(1, "password_" + new Random().nextInt(100));

        connection.close();


    }

}
