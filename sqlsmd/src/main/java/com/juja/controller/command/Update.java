package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.controller.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.Map;

public class Update implements Command {
    private static final String COMMAND_SAMPLE = ConfigMsg.getProperty("update.sample");
    private static final int CORRECT_LENGTH = COMMAND_SAMPLE.split("[|]").length;

    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void process(String command) {
        if (!canProcess(command)) {
            return;
        }
        String[] data = command.split("[|]");
        if (!isCorrect(data)) {
            view.write(String.format(ConfigMsg.getProperty("update.err.format"),
                    format(), command));
        } else {
            view.write(updateTable(data));
        }
    }

    @Override
    public String format() {
        return ConfigMsg.getProperty("update.format");
    }

    @Override
    public String description() {
        return ConfigMsg.getProperty("update.description");
    }

    private String updateTable(String[] data) {
        String tableName = data[1];
        Map<String, Object> map = UtilsCommand.getDataMap();
        for (int i = 3; i < data.length ; i += 2) {
            map.put(data[i], data[i + 1]);
        }
        try {
            manager.update(tableName, Integer.valueOf(data[2]), map);
            return String.format(ConfigMsg.getProperty("update.success") ,tableName);
        } catch (SQLException e) {
            return String.format(ConfigMsg.getProperty("update.err.format2"), data[1]);
        }
    }

    private boolean isCorrect(String[] data) {
        return data.length > CORRECT_LENGTH && data.length % CORRECT_LENGTH == 1;
    }
}
