package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.List;

public class ListTable implements Command {
    private final View view;
    private final DatabaseManager manager;

    public ListTable(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals(format());
    }

    @Override
    public void process(String command) throws SQLException {
        List<String> tableNames = manager.getTableNames();

        String message = tableNames.toString();

        view.write(message);
    }

    @Override
    public String format() {
        return ConfigMsg.getProperty("tables.format");
    }

    @Override
    public String description() {
        return ConfigMsg.getProperty("tables.description");
    }
}
