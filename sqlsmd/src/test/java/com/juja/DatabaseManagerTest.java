package com.juja;

import org.junit.Before;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;

import static org.junit.Assert.*;

public class DatabaseManagerTest {
    private DatabaseManager manager;

    @Before
    public void setup() {
        try {
            DBinit.startUp();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        manager = new DatabaseManager();
       Connection connection = manager.getConnection();
    }

}