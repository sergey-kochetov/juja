package com.juja.controller;

import com.juja.model.DatabaseManager;
import com.juja.model.JDBCDatabaseManager;
import com.juja.view.Console;
import com.juja.view.View;

public class MainController {

    public static void main(String[] args) {
        View view = new Console();

        DatabaseManager manager = new JDBCDatabaseManager();


        while (true) {
            view.write("Hello user!");
            view.write("Enter please database|userName|password");
            String line = view.read();

            String[] data = line.split("[|]");
            String databaseName = data[0];
            String userName = data[1];
            String password = data[2];
            try {
                ((JDBCDatabaseManager) manager).connect(databaseName, userName, password);
                view.write("Successful");
            } catch (Exception e) {
                String message = e.getMessage();
                if (e.getCause() != null) {
                    message += " " + e.getCause().getMessage();
                }
                view.write("Error, but was: " + message);
                view.write("Try again.");
            }

        }
    }
}
