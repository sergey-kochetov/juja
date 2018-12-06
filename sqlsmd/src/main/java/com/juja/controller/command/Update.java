package com.juja.controller.command;

import com.juja.controller.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;

import java.sql.SQLException;
import java.util.Map;
//TODO
public class Update implements Command {
    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {

        return command.startsWith("update|");
    }

    @Override
    public void process(String command) throws SQLException {
        if (!canProcess(command)) {
            return;
        }
        String[] data = command.split("[|]");

        if (data.length > 2 && data.length % 2 == 0) {
            String tableName = data[1];
            Map<String, Object> map = UtilsCommand.getDataMap();
            for (int i = 2; i < data.length ; i += 2) {
                map.put(data[i], data[i + 1]);
            }
            manager.update(tableName,0, map);
            view.write(String.format(
                    "data successfully updated to tables '%s'" ,tableName));
        }
    }

    @Override
    public String format() {
        return "update|tableName|column1|value1|column2|value2|...|columnN|valueN";
    }

    @Override
    public String description() {
        return "update data into a table";
    }
}