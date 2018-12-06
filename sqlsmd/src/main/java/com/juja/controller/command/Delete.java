package com.juja.controller.command;

import com.juja.controller.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Delete implements Command {

    private static final String COMMAND_SAMPLE = "delete|tableName|column|value";
    private static final int SPLIT = COMMAND_SAMPLE.split("[|]").length;

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

        Map<String, Object> dataSet = UtilsCommand.getDataMap();
        for (int i = 1; i < data.length / 2; i++) {
            String columnName = data[i * 2];
            String value = data[i * 2 + 1];

            dataSet.put(columnName, value);
        }
        manager.delete(tableName, dataSet);

        view.write(String.format("data table '%s' was successfully deleted", tableName));

    }

    @Override
    public String format() {
        return "delete|tableName|column|value";
    }

    @Override
    public String description() {
        return "deleting data in the table with parameters";
    }
}
