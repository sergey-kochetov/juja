package com.juja.controller.command;

import java.sql.SQLException;

public interface Command {

    boolean canProcess(String command);

    void process(String command) throws SQLException;
}
