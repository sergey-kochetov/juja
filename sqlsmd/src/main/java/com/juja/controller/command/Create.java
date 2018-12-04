package com.juja.controller.command;

import com.juja.controller.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.Map;

public class Create implements Command {
    private final View view;
    private final DatabaseManager manager;

    public Create(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        String[] split = format().split("[|]");
        if (split.length == 1) {
            return false;
        }
        return command.startsWith(split[0]);
    }

    @Override
    public void process(String command) throws SQLException {
        String[] data = command.split("[|]");
        if (data.length  % 2 != 0) {
            throw new IllegalArgumentException("No valid data % 2 == 0");
        }
        String tableName = data[1];

        Map<String, Object> dataSet = UtilsCommand.getDataMap();
        for (int i = 1; i < data.length / 2; i++) {
            String columnName = data[i * 2];
            String value = data[i * 2 + 1];

            dataSet.put(columnName, value);
        }
        manager.create(tableName, dataSet);

        view.write(String.format("'%s' was successfully created in table '%s'",
                dataSet.toString(), tableName));
    }

    @Override
    public String format() {
        return "create|database|row1|param1|...|rowN|paramN";
    }

    @Override
    public String description() {
        return "create data for database";
    }
}
