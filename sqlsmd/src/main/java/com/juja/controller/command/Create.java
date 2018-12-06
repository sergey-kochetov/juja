package com.juja.controller.command;

import com.juja.controller.UtilsCommand;
import com.juja.controller.command.Command;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Create implements Command {

    private static final String COMMAND_SAMPLE = "create|tableName|column1|type1";
    private static final int SPLIT = COMMAND_SAMPLE.split("[|]").length;

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
        if (!(data.length >= SPLIT && data.length % 2 == 0)) {
            throw new IllegalArgumentException(
                    String.format("format %s, but was: %s", format(), command));
        }
        String tableName = data[1];

        List<String> dataSet = UtilsCommand.getDataList();
        for (int i = 2; i < data.length; i+=2) {
            dataSet.add(String.format("%s %s", data[i], data[i + 1]));
        }
        manager.create(tableName, dataSet);

        view.write(String.format("table '%s' was successfully created", data[1]));

    }

    @Override
    public String format() {
        return "create|tableName|column1|type1|...|columnN|typeN";
    }

    @Override
    public String description() {
        return "create new table";
    }
}
