package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.controller.util.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.Map;

public class Insert implements Command {

    private static final String COMMAND_SAMPLE = ConfigMsg.getProperty("insert.sample");
    private static final int CORRECT_LENGTH = COMMAND_SAMPLE.split("[|]").length;

    private final View view;
    private final DatabaseManager manager;

    public Insert(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {
        if (!canProcess(command)) {
            return;
        }
        String[] data = command.split("[|]");
        if (!isCorrect(data)) {
            view.write(ConfigMsg.getProperty("insert.err.format"));
        } else {
            view.write(insertData(data));
        }
    }

    @Override
    public String format() {
        return ConfigMsg.getProperty("insert.format");
    }

    @Override
    public String description() {
        return ConfigMsg.getProperty("insert.description");
    }

    private String insertData(String[] data) {
        String tableName = data[1];
        Map<String, Object> dataSet = UtilsCommand.getDataMap();
        for (int i = 1; i < data.length / 2; i++) {
            String columnName = data[i * 2];
            String value = data[i * 2 + 1];
            dataSet.put(columnName, value);
        }
        try {
            manager.insert(tableName, dataSet);
            return String.format(ConfigMsg.getProperty("insert.success"),
                    dataSet.toString(), tableName);
        } catch (SQLException e) {
            return String.format(ConfigMsg.getProperty("insert.err.format2"),
                    dataSet.toString(), tableName);
        }
    }

    private boolean isCorrect(String[] data) {
        return data.length  % CORRECT_LENGTH == 0;
    }
}
