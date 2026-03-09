package com.yourcompany.simpleorm.jdbc;

import com.yourcompany.simpleorm.config.OrmConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final ConnectionManager INSTANCE = new ConnectionManager();

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public ConnectionManager() {

        this.jdbcUrl = OrmConfig.getJdbcUrl();
        this.username = OrmConfig.getDbUsername();
        this.password = OrmConfig.getDbPassword();

        if (this.jdbcUrl == null || this.jdbcUrl.trim().isEmpty()) {
            throw new IllegalStateException("JDBC URL cannot be null or empty.");
        }

        if (this.username == null) {
            throw new IllegalStateException("Database username cannot be null.");
        }

        if (this.password == null) {
            throw new IllegalStateException("Database password cannot be null.");
        }

        System.out.println("Singleton ConnectionManager initialized.");
        System.out.println("URL: " + this.jdbcUrl);
        System.out.println("User: " + this.username);
    }

    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    public  Connection getConnection() {

        try {

            System.out.println("Attempting database connection to: " + jdbcUrl);

            Connection connection = DriverManager.getConnection(
                    this.jdbcUrl,
                    this.username,
                    this.password
            );

            System.out.println("Database connection successful!");

            return connection;

        }
        catch (SQLException e) {

            System.err.println("Failed to establish database connection!");
            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to acquire database connection. URL: " + this.jdbcUrl,
                    e
            );
        }
    }

    public static void closeConnection(Connection connection) {

        if (connection != null) {

            try {

                System.out.println("Attempting to close the database connection.");

                connection.close();

                System.out.println("Database connection closed successfully");

            }
            catch (SQLException e) {

                System.err.println("Failed to close database connection!");
                e.printStackTrace();
            }
        }
    }
}