package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;

public class Connect implements Command {
    public static final String COMMAND_SAMPLE = "connect|sqlcmd|postgres|postgres";
    private static final int SPLIT = 4;

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
    public void process(String command) {
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

    private int count() {
        return COMMAND_SAMPLE.split("//|").length;
    }



}
