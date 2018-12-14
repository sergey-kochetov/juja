package com.juja.controller.command;

import com.google.common.base.Strings;

import com.juja.config.ConfigMsg;
import com.juja.controller.util.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.*;

public class Find implements Command {
    private static final int MIN_LENGTH = 15;
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
            findTable(tableName);
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

    private void findTable(String tableName) {
        try {
            List<String> tableColumns = UtilsCommand.getDataList(manager.getTableColumns(tableName));
            Set<Map<String, Object>> tableData = manager.getTableData(tableName);
            List<Integer> lengthRow = lengthRowData(tableColumns);

            printHeader(tableColumns, lengthRow);
            printTable(tableData, lengthRow);
        } catch (SQLException e) {
            view.write(String.format(ConfigMsg.getProperty("find.err.format"), tableName));
        }
    }

    private List<Integer> lengthRowData(List<String> heads) {
        List<Integer> result = new ArrayList<>();
        result.add(MIN_LENGTH / 2);
        for (int i = 1; i < heads.size(); i++) {
            if (heads.get(i).length() >= MIN_LENGTH) {
                result.add(heads.get(i).length());
            } else {
                result.add(MIN_LENGTH);
            }
        }
        return result;
    }

    private void printTable(Set<Map<String, Object>> tableData, List<Integer> lengthRow) {
        for (Map<String, Object> aTableData : tableData) {
            printRow(aTableData, lengthRow);
        }
        printSeparator(lengthRow);
    }

    private void printRow(Map<String, Object> row, List<Integer> lengthRow) {
        StringBuilder result = new StringBuilder("|");
        int index = 0;
        for (Map.Entry<String, Object> p : row.entrySet()) {
            result.append(Strings.padEnd(p.getValue().toString(), lengthRow.get(index++), ' ')).append("|");
        }
        view.write(result.toString());
    }

    private void printHeader(List<String> tableColumns, List<Integer> lengthRow) {
        StringBuilder result = new StringBuilder("|");
        for (int i = 0; i < tableColumns.size() ; i++) {
            result.append(Strings.padEnd(tableColumns.get(i),
                    lengthRow.get(i), ' ')).append("|");
        }
        printSeparator(lengthRow);
        view.write(result.toString());
        printSeparator(lengthRow);
    }

    private void printSeparator(List<Integer> lengthRow) {
        StringBuilder result = new StringBuilder("+");
        for (int length : lengthRow) {
            result.append(Strings.repeat("-", length));
            result.append("+");
        }
        view.write(result.toString());
    }

}
