package com.juja.controller.command;

import com.google.common.base.Strings;
import com.juja.model.DataSet;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

public class FindCommand implements Command {
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
    public void process(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1];

        String[] tableColumns = manager.getTableColumns(tableName);
        DataSet[] tableData = manager.getTableData(tableName);

        int[] lengthRow = lengthRowData(tableColumns);


        printHeader(tableColumns, lengthRow);
        printTable(tableData, lengthRow);
    }

    private static final int MIN_LENGTH = 15;

    private int[] lengthRowData(String[] head) {
        int[] result = new int[head.length];
        result[0] = MIN_LENGTH / 2;
        for (int i = 1; i < head.length; i++){

            if (head[i].length() >= MIN_LENGTH) {
                result[i] = head[i].length();
            } else {
                result[i] = MIN_LENGTH;
            }

        }
        return result;
    }


    private void printTable(DataSet[] tableData, int[] lengthRow) {
        for (int i = 0; i < tableData.length; i++) {
            printRow(tableData[i], lengthRow);
        }
        printSeporator(lengthRow);
    }

    private void printRow(DataSet row, int[] lengthRow) {
        Object[] values = row.getValues();
        StringBuilder result = new StringBuilder("|");
        for (int i = 0; i < values.length; i++) {
            result.append(Strings.padEnd(values[i].toString(), lengthRow[i], ' ')).append("|");
        }
        view.write(result.toString());
    }

    private void printHeader(String[] tableColumns, int[] lengthRow) {

        StringBuilder result = new StringBuilder("|");
        for (int i = 0; i < lengthRow.length ; i++) {
            result.append(Strings.padEnd(tableColumns[i], lengthRow[i], ' ')).append("|");
        }
        printSeporator(lengthRow);
        view.write(result.toString());
        printSeporator(lengthRow);
    }

    private void printSeporator(int[] lengthRow) {
        StringBuilder result = new StringBuilder("+");
        for (int length : lengthRow) {
            result.append(Strings.repeat("-", length));
            result.append("+");
        }
       view.write(result.toString());
    }
}
