package com.juja.controller;

import com.juja.controller.command.*;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainController {
    private final View view;
    private final DatabaseManager manager;
    private List<Command> commands;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new LinkedList<>();
        initCommands();
    }

    private void initCommands() {
        commands.addAll(Arrays.asList(
                new Connect(manager, view),
                new Help(view, commands),
                new Exit(view),
                new isConnect(manager, view),
                new ListTable(manager, view),
                new Clear(manager, view),
                new Create(manager, view),
                new Find(manager, view),
                new Unsupported(view)
        ));
    }

    public void run() {
        try {
            doWork();
        } catch (ExitException e) {
            // do nothing
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void doWork() throws SQLException {
        view.write("Welcome to sqlsmd.");
        view.write("Enter please connect|database|userName|password");

        while (true) {
            String input = view.read();
            if (input == null) {
               break;
            }
            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException) {
                        throw e;
                    }
                    printError(e);
                    break;
                }
            }
            view.write("Enter command (or 'help'): ");
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null) {
            message += " " + e.getCause().getMessage();
        }
        if (message == null) {
            return;
        }
        view.write("Error, but was: " + message);
        view.write("Try again.");
    }

    public List<Command> getCommands() {
        return commands;
    }
}
