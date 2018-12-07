package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.controller.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.List;

public class Create implements Command {

    private static final String COMMAND_SAMPLE = ConfigMsg.getProperty("create.sample");
    private static final int CORRECT_LENGTH = COMMAND_SAMPLE.split("[|]").length;

    private final View view;
    private final DatabaseManager manager;

    public Create(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) throws SQLException {
        if (!canProcess(command)) {
            return;
        }
        String[] data = command.split("[|]");
        if (!(data.length >= CORRECT_LENGTH && data.length % 2 == 0)) {
            throw new IllegalArgumentException(
                    String.format(ConfigMsg.getProperty("create.err.format"), format(), command));
        }
        String tableName = data[1];

        List<String> dataSet = UtilsCommand.getDataList();
        for (int i = 2; i < data.length; i+=2) {
            dataSet.add(String.format("%s %s", data[i], data[i + 1]));
        }
        manager.create(tableName, dataSet);

        view.write(String.format(ConfigMsg.getProperty("create.success"), data[1]));
    }

    @Override
    public String format() {
        return ConfigMsg.getProperty("create.format");
    }

    @Override
    public String description() {
        return  ConfigMsg.getProperty("create.description");
    }
}
