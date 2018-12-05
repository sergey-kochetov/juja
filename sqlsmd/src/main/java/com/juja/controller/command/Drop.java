package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;

public class Drop implements Command {

    private static final String COMMAND_SAMPLE = "drop|customer";
    private static final int SPLIT = COMMAND_SAMPLE.split("[|]").length;

    private final View view;
    private final DatabaseManager manager;

    public Drop(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("drop|");
    }

    @Override
    public void process(String command) throws SQLException {
        if (!canProcess(command)) {
            return;
        }
        String[] data = command.split("[|]");
        if (data.length != SPLIT) {
            throw new IllegalArgumentException(String.format("format %s, but was: %s", format(), command));
        }
        try {
            manager.delete(data[1]);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(String.format("table '%s' does not exist", data[1]));
        }
        view.write(String.format("table '%s' was successfully droped", data[1]));
    }

    @Override
    public String format() {
        return "drop|tableName";
    }

    @Override
    public String description() {
        return "drop database";
    }
}
