package com.example.TempDB.DatabaseCrud;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class responsible for establishing and providing a connection to the database.
 */
public class DataBaseConnection {

    // Variable to hold the established database connection
    private Connection connection;

    /**
     * Constructor to initialize and establish a connection to the database.
     *
     * @param url      Database connection URL.
     * @param username Database username.
     * @param password Database password.
     */
    public DataBaseConnection(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter method to retrieve the established database connection.
     *
     * @return The active database connection.
     */
    public Connection getConnection() {
        return connection;
    }
}
