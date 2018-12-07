package com.juja.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigDB {
    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_LIMIT = "db.limit";

    private static Properties properties = new Properties();

    public synchronized static String getProperty(String name) {
        if (properties.isEmpty()) {
            try (InputStream is = ConfigDB.class.getClassLoader()
                    .getResourceAsStream("dao.properties")) {

                properties.load(is);

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return properties.getProperty(name);
    }
}
