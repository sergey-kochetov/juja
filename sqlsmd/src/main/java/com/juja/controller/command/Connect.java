package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;

public class Connect implements Command {
    private static final String COMMAND_SAMPLE = ConfigMsg.getProperty("connect.format");
    private static final int CORRECT_LENGTH = COMMAND_SAMPLE.split("[|]").length;

    private final DatabaseManager manager;
    private final View view;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) throws SQLException {
        if (!canProcess(command)) {
            return;
        } else if ("connect|".equals(command)) {
            manager.defaultConnect();
        } else {
            String[] data = command.split("[|]");
            if (data.length != CORRECT_LENGTH) {
                throw new IllegalArgumentException(String.format(
                        ConfigMsg.getProperty("connect.novalid"),
                        CORRECT_LENGTH, data.length));
            }
            String databaseName = data[1];
            String userName = data[2];
            String password = data[3];
            manager.connect(databaseName, userName, password);
        }
        view.write( ConfigMsg.getProperty("connect.success"));
    }

    @Override
    public String format() {
        if (!manager.isConnected()) {
            return COMMAND_SAMPLE;
        }
        return "";
    }

    @Override
    public String description() {
        if (!manager.isConnected()) {
            return ConfigMsg.getProperty("connect.description");
        }
        return "";
    }
}
