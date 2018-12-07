package com.juja.controller.command;

import com.juja.config.ConfigMsg;
import com.juja.view.View;

public class Unsupported implements Command {
    private final View view;

    public Unsupported(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write(ConfigMsg.getProperty("unsupported.success"));
    }

    @Override
    public String format() {
        return "";
    }

    @Override
    public String description() {
        return "";
    }
}
