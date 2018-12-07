package com.juja.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigMsg {
    private static Properties properties = new Properties();

    public synchronized static String getProperty(String name) {
        if (properties.isEmpty()) {
            try (InputStream is = ConfigDB.class.getClassLoader()
                    .getResourceAsStream("msg.properties")) {

                properties.load(is);

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return properties.getProperty(name);
    }
}