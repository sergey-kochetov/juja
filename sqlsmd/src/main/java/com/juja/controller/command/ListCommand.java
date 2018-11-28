package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.util.Arrays;

public class ListCommand implements Command {
    private final View view;
    private final DatabaseManager manager;

    public ListCommand(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) {
        String[] tableNames = manager.getTableNames();

        String message = Arrays.toString(tableNames);

        view.write(message);
    }
}
