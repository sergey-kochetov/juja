package com.juja;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class DBinit {
    public static void startUp() throws URISyntaxException, IOException {
        // given
        URL url1 = DatabaseManagerTest.class.getClassLoader()
                .getResource("create_table.sql");
        URL url2 = DatabaseManagerTest.class.getClassLoader()
                .getResource("data_customer.sql");


        List<String> lines1 = Files.readAllLines(Paths.get(url1.toURI()));
        String sql1 = lines1.stream().collect(Collectors.joining());

        List<String> lines2 = Files.readAllLines(Paths.get(url2.toURI()));
        String sql2 = lines2.stream().collect(Collectors.joining());


        try (Connection con = new DatabaseManager().getConnection();
             Statement stmp = con.createStatement();
        ) {
            stmp.executeUpdate(sql1);
            stmp.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
