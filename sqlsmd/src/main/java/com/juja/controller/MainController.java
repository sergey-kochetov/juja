package com.juja.controller;

import com.juja.config.ConfigMsg;
import com.juja.controller.command.*;
import com.juja.controller.exception.ExitException;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

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
                new Disconnect(manager, view),
                new Help(view, getCommands()),
                new Exit(view),
                new isConnect(manager, view),
                new ListTable(manager, view),
                new Clear(manager, view),
                new Drop(manager, view),
                new Insert(manager, view),
                new Find(manager, view),
                new Update(manager, view),
                new Create(manager, view),
                new Delete(manager, view),
                new Unsupported(view)
        ));
    }

    public void run() {
        try {
            doWork();
        } catch (ExitException e) {
            // do nothing
        }
    }

    private void doWork() {
        view.write(ConfigMsg.getProperty("main.welcome"));
        view.write(ConfigMsg.getProperty("connect.enter"));

        while (true) {
            String input = view.read();
            if (input == null) {
               break;
            }
            processCommands(input);
            view.write(ConfigMsg.getProperty("main.help"));
        }
    }

    private void processCommands(String input) {
        for (Command command : commands) {
            try {
                if (command.canProcess(input)) {
                    command.process(input);
                    break;
                }
            } catch (Exception e) {
                if (e instanceof ExitException) {
                    throw (ExitException) e;
                }
                printError(e);
                break;
            }
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
        view.write(ConfigMsg.getProperty("main.err.format") + message);
        view.write(ConfigMsg.getProperty("main.err.format2"));
    }

    public List<Command> getCommands() {
        return commands;
    }
}
