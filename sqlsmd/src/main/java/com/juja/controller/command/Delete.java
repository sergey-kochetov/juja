package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.controller.util.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.Map;

public class Delete implements Command {

    private static final String COMMAND_SAMPLE = ConfigMsg.getProperty("delete.sample");
    private static final int CORRECT_LENGTH = COMMAND_SAMPLE.split("[|]").length;

    private final View view;
    private final DatabaseManager manager;

    public Delete(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }
    @Override
    public boolean canProcess(String command) {
        return command.startsWith("delete|");
    }

    @Override
    public void process(String command) {
        if (!canProcess(command)) {
            return;
        }
        String[] data = command.split("[|]");
        if (!isCorrect(data)) {
            view.write(String.format(ConfigMsg.getProperty("delete.err.format"), format(), command));
        } else {
            view.write(deleteTable(data));
        }
    }

    @Override
    public String format() {
        return ConfigMsg.getProperty("delete.format");
    }

    @Override
    public String description() {
        return ConfigMsg.getProperty("delete.description");
    }

    private String deleteTable(String[] data) {
        String resultDelete;
        String tableName = data[1];
        Map<String, Object> dataSet = UtilsCommand.getDataMap();
        for (int i = 1; i < data.length / 2; i++) {
            String columnName = data[i * 2];
            String value = data[i * 2 + 1];
            dataSet.put(columnName, value);
        }
        try {
            manager.delete(tableName, dataSet);
            resultDelete = String.format(ConfigMsg.getProperty("delete.success"), dataSet.toString(), tableName);
        } catch (SQLException e) {
            resultDelete = String.format(ConfigMsg.getProperty("delete.err.message"), tableName);
        }
        return resultDelete;
    }

    private boolean isCorrect(String[] data) {
        return data.length >= CORRECT_LENGTH && data.length % 2 == 0;
    }

}
