package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;

public class ClearCommand implements Command {
    private final View view;
    private final DatabaseManager manager;

    public ClearCommand(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) throws SQLException {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException("format clear|tableName, but was: " + command);
        }
        manager.clear(data[1]);

        view.write(String.format("table '%s' was successfully cleared.", data[1]));
    }
}
