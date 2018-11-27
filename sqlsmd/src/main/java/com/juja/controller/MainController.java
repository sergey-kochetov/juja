package com.juja.controller;

import com.juja.model.DataSet;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.util.Arrays;

public class MainController {
    private final View view;
    private final DatabaseManager manager;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public void run() {
        connectToDb();

        while (true) {
            view.write("Enter command (or 'help'): ");
            String command = view.read();
            if (command.equals("list")) {
                doList();
            } else if (command.equals("help")) {
                doHelp();
            } else if (command.equals("exit")) {
                view.write("bye...");
                System.exit(0);
            } else if (command.startsWith("find|")) {
                doFind(command);
            } else {
                view.write("command not exist, try again.");
            }
        }
    }

    private void doFind(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1];
        DataSet[] tableData = manager.getTableData(tableName);
        String[] tableColumns = manager.getTableColumns(tableName);

        printHeader(tableColumns);
        prtintTable(tableData);



    }

    private void prtintTable(DataSet[] tableData) {
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
        view.write(result);
    }

    private void doHelp() {
        view.write("Existing commands");
        view.write("\thelp");
        view.write("\t\tthis page");
        view.write("\tlist");
        view.write("\t\tfor get list database");
        view.write("\tfind|tableName");
        view.write("\t\tfor see table 'tableName'");
        view.write("\texit");
        view.write("\t\tfor exit program");
    }

    private void doList() {
        String[] tableNames = manager.getTableNames();

        String message = Arrays.toString(tableNames);

        view.write(message);
    }


    private void connectToDb() {
        while (true) {
            try {
                view.write("Hello user!");
                view.write("Enter please database|userName|password");
                String line = view.read();

                String[] data = line.split("[|]");
                if (data.length != 3) {
                    throw  new IllegalArgumentException("No valid data '|' actual 3, but was: " + data.length);
                }
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];
                manager.connect(databaseName, userName, password);
                view.write("Successful");
                break;
            } catch (Exception e) {
                printError(e);
            }

        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null) {
            message += " " + e.getCause().getMessage();
        }
        view.write("Error, but was: " + message);
        view.write("Try again.");
    }
}
