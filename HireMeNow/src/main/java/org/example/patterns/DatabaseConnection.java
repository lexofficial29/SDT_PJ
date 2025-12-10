package org.example.patterns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:mariadb://localhost:3306/hiremenow";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private DatabaseConnection() {
        reconnect();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private void reconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }
        } catch (SQLException e) {
            System.err.println("[DB] Connection check failed: " + e.getMessage());
        }

        closeConnection();

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database successfully.");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            connection = null;
        }
    }

    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {}
        }
    }

    public Connection getConnection() {
        reconnect();
        return connection;
    }

    public void query(String sql) {
        reconnect();
        if (connection == null) {
            System.out.println("[DB] No connection – cannot execute: " + sql);
            return;
        }
        try (var stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("[DB] Executed: " + sql);
        } catch (SQLException e) {
            System.err.println("[DB] SQL error: " + e.getMessage());
        }
    }
}