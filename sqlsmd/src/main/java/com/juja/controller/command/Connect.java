package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;

public class Connect implements Command {
    private static final String COMMAND_SAMPLE = "connect|sqlcmd|postgres|postgres";
    private static final int SPLIT = COMMAND_SAMPLE.split("[|]").length;

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
            String[] data = command.split("[|]");
            if (data.length != SPLIT) {
                throw new IllegalArgumentException(String.format(
                        "No valid data '|' actual %s, but was: %s",
                        SPLIT, data.length));
            }
            String databaseName = data[1];
            String userName = data[2];
            String password = data[3];

            manager.connect(databaseName, userName, password);

            view.write("Connect Successful");
    }

    @Override
    public String format() {
        return "connect|database|user|password";
    }

    @Override
    public String description() {
        return "for connect to database";
    }
}
