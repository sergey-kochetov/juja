package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;

public class Disconnect implements Command {

    private final DatabaseManager manager;
    private final View view;

    public Disconnect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return command.equals("disconnect");
    }

    @Override
    public void process(String command) {
        if (!canProcess(command)) {
            return;
        }
        try {
            manager.disconnect();
            view.write(ConfigMsg.getProperty("disconnect.success"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String format() {
        if (!manager.isConnected()) {
            return "";
        }
        return ConfigMsg.getProperty("disconnect.format");
    }

    @Override
    public String description() {
        if (!manager.isConnected()) {
            return "";
        }
        return ConfigMsg.getProperty("disconnect.description");
    }
}
