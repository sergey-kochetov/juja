package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.view.View;

public class Exit implements Command {
    private View view;

    public Exit(View view) {
        this.view = view;
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
        view.write(ConfigMsg.getProperty("exit.success"));
        throw new ExitException();
    }

    @Override
    public String format() {
        return ConfigMsg.getProperty("exit.format");
    }

    @Override
    public String description() {
        return ConfigMsg.getProperty("exit.description");
    }
}
