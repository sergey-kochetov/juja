package com.juja.controller.command;

import com.juja.view.View;

public class Help implements Command {
    private final View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        view.write("Existing commands");
        view.write("\tconnect|databaseName|userName|password");
        view.write("\t\tfor connect to database");
        view.write("\thelp");
        view.write("\t\tthis page");
        view.write("\tlist");
        view.write("\t\tfor get list database");
        view.write("\tfind|tableName");
        view.write("\t\tfor see table 'tableName'");
        view.write("\texit");
        view.write("\t\tfor exit program");
    }
}
