package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CreateCommand implements Command {
    private final View view;
    private final DatabaseManager manager;

    public CreateCommand(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) throws SQLException {
        String[] data = command.split("[|]");
        if (data.length  % 2 != 0) {
            throw new IllegalArgumentException("No valid data % 2 == 0");
        }
        String tableName = data[1];

        Map<String, Object> dataSet = new HashMap<>();
        for (int i = 1; i < data.length / 2; i++) {
            String columnName = data[i * 2];
            String value = data[i * 2 + 1];

            dataSet.put(columnName, value);
        }
        manager.create(tableName, dataSet);

        view.write(String.format("data '%s' was successfully created in table '%s'",
                dataSet.toString(), tableName));
    }
}
