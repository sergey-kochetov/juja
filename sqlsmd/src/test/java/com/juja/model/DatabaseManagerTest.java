package com.juja.model;

import com.juja.model.DatabaseManager;

public abstract class DatabaseManagerTest {
    protected DatabaseManager manager;
    public abstract DatabaseManager getDatabaseManager();
    public abstract Object store();
}
