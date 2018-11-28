package com.juja.controller.command;

import com.juja.view.View;

public class ExitCommand implements Command {
    private View view;

    public ExitCommand(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(String command) {
        view.write("bye...");
        throw new ExitException();
    }
}
