package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ListCommand implements Command {
    private final View view;
    private final DatabaseManager manager;

    public ListCommand(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) throws SQLException {
        List<String> tableNames = manager.getTableNames();

        String message = tableNames.toString();

        view.write(message);
    }
}
