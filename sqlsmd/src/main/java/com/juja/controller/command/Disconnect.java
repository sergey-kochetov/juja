package com.juja.controller.command;

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
    public void process(String command) throws SQLException {
        if (!canProcess(command)) {
            return;
        }
        manager.disconnect();
        view.write("disconnected");
    }

    @Override
    public String format() {
        if (!manager.isConnected()) {
            return "";
        }
        return "disconnect";
    }

    @Override
    public String description() {
        if (!manager.isConnected()) {
            return "";
        }
        return "disable database";
    }
}
