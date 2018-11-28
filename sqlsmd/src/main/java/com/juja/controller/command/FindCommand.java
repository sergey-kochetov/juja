package com.juja.controller.command;

import com.juja.controller.command.Command;
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
        DataSet[] tableData = manager.getTableData(tableName);
        String[] tableColumns = manager.getTableColumns(tableName);

        printHeader(tableColumns);
        printTable(tableData);
    }


    private void printTable(DataSet[] tableData) {
        for (DataSet row : tableData) {
            printRow(row);
        }
    }

    private void printRow(DataSet row) {
        Object[] values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }
        view.write(result);
    }

    private void printHeader(String[] tableColumns) {

        String result = "|";
        for (String name : tableColumns) {
            result += name +"|";
        }
        view.write("+++++++++++++++");
        view.write(result);
        view.write("+++++++++++++++");
    }
}
