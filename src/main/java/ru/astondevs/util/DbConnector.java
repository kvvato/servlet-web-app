package ru.astondevs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnector {
    private static final String URL = "jdbc:postgresql://localhost:5432/store";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public Connection connect() {
        Connection connection = null;
        try {
            Properties info = new Properties();
            info.setProperty("user", USER);
            info.setProperty("password", PASSWORD);
            info.setProperty("useUnicode", "true");
            info.setProperty("characterEncoding", "utf8");
            connection = DriverManager.getConnection(URL, info);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
