package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;

public class Clear implements Command {

    private static final String COMMAND_SAMPLE = "clear|customer";
    private static final int SPLIT = COMMAND_SAMPLE.split("[|]").length;

    private final View view;
    private final DatabaseManager manager;

    public Clear(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
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
        manager.clear(data[1]);

        view.write(String.format("table '%s' was successfully cleared", data[1]));
    }

    @Override
    public String format() {
        return "clear|tableName";
    }

    @Override
    public String description() {
        return "database cleaning";
    }
}
