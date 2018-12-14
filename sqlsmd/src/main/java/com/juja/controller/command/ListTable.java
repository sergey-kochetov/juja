package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

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
    public void process(String command) {
        if (!canProcess(command)) {
            return;
        }
        try {
            Set<String> tableNames = manager.getTableNames();
            view.write(tableNames.toString());
        } catch (SQLException e) {
            view.write(ConfigMsg.getProperty("tables.err.format"));
        }
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
