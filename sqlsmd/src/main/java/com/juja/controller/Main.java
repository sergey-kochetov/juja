package com.juja.controller;

import com.juja.model.DatabaseManager;
import com.juja.model.JDBCDatabaseManager;
import com.juja.view.Console;
import com.juja.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();
        MainController controller = new MainController(view, manager);
        controller.run();
    }
}
