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
    public void process(String command){
        if (!canProcess(command)) {
            return;
        }
        String resultConnect;
        if ("connect|".equals(command)) {
            resultConnect = defaultConnect();
        } else {
            resultConnect = connectToDb(command);
        }
        view.write(resultConnect);
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

    private String connectToDb(String command) {
        String[] data = command.split("[|]");
        if (data.length != CORRECT_LENGTH) {
            return String.format(ConfigMsg.getProperty("connect.novalid"),
                    CORRECT_LENGTH, data.length);
        } else {
            return connectToDbValid(data);
        }
    }

    private String defaultConnect() {
        try {
            manager.defaultConnect();
            return ConfigMsg.getProperty("connect.success");
        } catch (SQLException ex) {
            return ConfigMsg.getProperty("connect.err.message2");
        }
    }

    private String connectToDbValid(String[] data) {
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];
        try {
            manager.connect(databaseName, userName, password);
            return ConfigMsg.getProperty("connect.success");
        } catch (SQLException e) {
            return String.format(ConfigMsg.getProperty("db.err.format"),
                    databaseName, userName);
        }
    }

}
