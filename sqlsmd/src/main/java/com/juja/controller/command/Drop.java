package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;

public class Drop implements Command {

    private static final String COMMAND_SAMPLE = ConfigMsg.getProperty("drop.sample");
    private static final int CORRECT_LENGTH = COMMAND_SAMPLE.split("[|]").length;

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
        if (data.length != CORRECT_LENGTH) {
            throw new IllegalArgumentException(String.format(
                    ConfigMsg.getProperty("drop.err.format"), format(), command));
        }
        try {
            manager.drop(data[1]);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(String.format(
                    ConfigMsg.getProperty("drop.err.format2"), data[1]));
        }
        view.write(String.format(ConfigMsg.getProperty("drop.success"), data[1]));
    }

    @Override
    public String format() {
        return ConfigMsg.getProperty("drop.format");
    }

    @Override
    public String description() {
        return ConfigMsg.getProperty("drop.description");
    }
}
