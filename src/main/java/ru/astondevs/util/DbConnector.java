package ru.astondevs.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnector {
    private static final String DATABASE_NAME = "store";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static final HikariDataSource ds;

    static {
        Properties props = new Properties();
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", USER);
        props.setProperty("dataSource.password", PASSWORD);
        props.setProperty("dataSource.databaseName", DATABASE_NAME);

        HikariConfig config = new HikariConfig(props);
        ds = new HikariDataSource(config);
    }

    public Connection connect() {
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
