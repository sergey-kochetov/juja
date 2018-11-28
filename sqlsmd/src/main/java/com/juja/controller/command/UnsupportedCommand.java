package com.juja.controller.command;

import com.juja.view.View;

public class UnsupportedCommand implements Command {

    private final View view;

    public UnsupportedCommand(View view) {

        this.view = view;

    }
    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write("command not exist, try again.");

    }
}
