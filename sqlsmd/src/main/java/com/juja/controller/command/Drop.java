package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;

public class Drop implements Command {

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
        String[] data = command.split("[|]");
        if (data.length != 2) {
            throw new IllegalArgumentException(String.format("format %s, but was: %s", format(), command));
        }


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
