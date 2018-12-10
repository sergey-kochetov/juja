package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;

public class Clear implements Command {

    private static final String COMMAND_SAMPLE = ConfigMsg.getProperty("clear.tablename");
    private static final int SPLIT = COMMAND_SAMPLE.split("[|]").length;

    private final View view;
    private final DatabaseManager manager;

    public Clear(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear");
    }

    @Override
    public void process(String command) {
        if (!canProcess(command)) {
            return;
        }
        String[] data = command.split("[|]");
        if (!isValid(data)) {
            view.write(String.format(ConfigMsg.getProperty("clear.err.format"), format(), command));
        } else {
            view.write(clearTable(data[1]));
        }
    }

    private boolean isValid(String[] data) {
        return data.length == SPLIT;
    }

    @Override
    public String format() {
        return COMMAND_SAMPLE;
    }

    @Override
    public String description() {
        return ConfigMsg.getProperty("clear.database");
    }

    private String clearTable(String tableName) {
        try {
            manager.clear(tableName);
            return String.format(ConfigMsg.getProperty("clear.format"), tableName);
        } catch (SQLException e) {
            return String.format(ConfigMsg.getProperty("clear.err.message"), tableName);
        }
    }
}
