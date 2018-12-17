package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.controller.util.PrintTable;
import com.juja.controller.util.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Find implements Command {
    private static final String COMMAND_SAMPLE = ConfigMsg.getProperty("find.sample");
    private static final int CORRECT_LENGTH = COMMAND_SAMPLE.split("[|]").length;

    private final DatabaseManager manager;
    private final View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        if (!canProcess(command)) {
            return;
        }
        String[] data = command.split("[|]");
        String tableName = data[1];
        if (data.length != CORRECT_LENGTH) {
            view.write(String.format(ConfigMsg.getProperty(
                    "find.err.format2"), format(), command));
        } else {
            view.write(findTable(tableName));
        }
    }

    @Override
    public String format() {
        return ConfigMsg.getProperty("find.format");
    }

    @Override
    public String description() {
        return ConfigMsg.getProperty("find.description");
    }

    private String findTable(String tableName) {
        try {
            List<String> tableColumns = UtilsCommand.getDataList(manager.getTableColumns(tableName));
            Set<Map<String, Object>> tableData = manager.getTableData(tableName);
            return PrintTable.buildTable(tableColumns, tableData);
        } catch (SQLException e) {
            return String.format(ConfigMsg.getProperty("find.err.format"), tableName);
        }
    }
}
