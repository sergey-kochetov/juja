package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.view.View;

import java.util.List;

public class Help implements Command {
    private final View view;
    private List<Command> commands;

    public Help(View view, List<Command> commands) {
        this.view = view;
        this.commands = commands;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals(format());
    }

    @Override
    public void process(String command) {
        if (!canProcess(command)) {
            return;
        }
        view.write(ConfigMsg.getProperty("help.success"));
        for (Command comm : commands ) {
            if (!comm.description().isEmpty() || !comm.format().isEmpty()) {
                view.write("\t" + comm.format());
                view.write("\t\t" + comm.description());
            }
        }
    }

    @Override
    public String format() {
        return ConfigMsg.getProperty("help.format");
    }

    @Override
    public String description() {
        return ConfigMsg.getProperty("help.description");
    }
}
