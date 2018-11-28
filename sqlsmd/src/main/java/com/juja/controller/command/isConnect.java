package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;

public class isConnect implements Command {
    private final DatabaseManager manager;
    private final View view;

    public isConnect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;

    }
    @Override
    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process(String command) {
        view.write("Enter please connect|database|userName|password");
    }
}
