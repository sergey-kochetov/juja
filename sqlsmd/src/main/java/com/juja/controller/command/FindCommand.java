package com.juja.controller.command;

import com.google.common.base.Strings;
import com.juja.model.DataSet;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.List;

public class FindCommand implements Command {
    private static final int MIN_LENGTH = 15;

    private final DatabaseManager manager;
    private final View view;

    public FindCommand(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) throws SQLException {
        String[] data = command.split("\\|");
        String tableName = data[1];

        List<String> tableColumns = manager.getTableColumns(tableName);
        DataSet[] tableData = manager.getTableData(tableName);

        int[] lengthRow = lengthRowData(tableColumns);

        printHeader(tableColumns, lengthRow);
        printTable(tableData, lengthRow);
    }

    private int[] lengthRowData(List<String> head) {
        int[] result = new int[head.size()];
        result[0] = MIN_LENGTH / 2;
        for (int i = 1; i < head.size(); i++){

            if (head.get(i).length() >= MIN_LENGTH) {
                result[i] = head.get(i).length();
            } else {
                result[i] = MIN_LENGTH;
            }
        }
        return result;
    }


    private void printTable(DataSet[] tableData, int[] lengthRow) {
        for (DataSet aTableData : tableData) {
            printRow(aTableData, lengthRow);
        }
        printSeparator(lengthRow);
    }

    private void printRow(DataSet row, int[] lengthRow) {
        Object[] values = row.getValues();
        StringBuilder result = new StringBuilder("|");
        for (int i = 0; i < values.length; i++) {
            result.append(Strings.padEnd(values[i].toString(), lengthRow[i], ' ')).append("|");
        }
        view.write(result.toString());
    }

    private void printHeader(List<String> tableColumns, int[] lengthRow) {
        StringBuilder result = new StringBuilder("|");
        for (int i = 0; i < lengthRow.length ; i++) {
            result.append(Strings.padEnd(tableColumns.get(i), lengthRow[i], ' ')).append("|");
        }
        printSeparator(lengthRow);
        view.write(result.toString());
        printSeparator(lengthRow);
    }

    private void printSeparator(int[] lengthRow) {
        StringBuilder result = new StringBuilder("+");
        for (int length : lengthRow) {
            result.append(Strings.repeat("-", length));
            result.append("+");
        }
        view.write(result.toString());
    }
}
