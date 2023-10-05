package com.example.TempDB.DatabaseCrud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseInitializer {

    private Connection connection;

    /**
     * Initializes a new instance of the DatabaseInitializer class.
     *
     * @param connection The SQL connection to use for database operations.
     */
    public DatabaseInitializer(Connection connection) {
        this.connection = connection;
    }

    //metods used in "initialize()" checks if the categories and level data exist based on the in parameter

    /**
     * Checks if a given category exists in the database.
     *
     * @param categoryName The name of the category to check.
     * @return True if the category exists, false otherwise.
     */
    private boolean categoryExists(String categoryName) {
        try {
            String checkQuery = "SELECT COUNT(*) FROM temp_category_table WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Checks if a given level (based on color) exists in the database.
     *
     * @param color The color representing the level to check.
     * @return True if the level exists, false otherwise.
     */
    private boolean levelExists(String color) {
        try {
            String checkQuery = "SELECT COUNT(*) FROM level_table WHERE color = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setString(1, color);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Initializes the database by checking for certain categories and levels.
     * Inserts default values for categories and levels if they do not exist.
     */
    public void initialize() {
        try {
            //if the category column "name" does not have "låg" -"stabil" and "Hög" -> insert values
            if (!categoryExists("Låg") && !categoryExists("Stabil") && !categoryExists("Hög")) {
                String insertTempCategory = "INSERT INTO temp_category_table (id, name, lower_bound, upper_bound) VALUES (1, 'Låg', 10, 15), (2, 'Stabil', 15, 30), (3, 'Hög', 30, 40)";
                PreparedStatement preparedStatement1 = connection.prepareStatement(insertTempCategory);
                preparedStatement1.executeUpdate();
            }
            //check if the levelExist column "color" does not have "Grön" - "Blå" - "Röd" -> insert values
            if (!levelExists("Grön") && !levelExists("Blå") && !levelExists("Röd")) {
                String insertLevel = "INSERT INTO level_table (id, color, category_id) VALUES (1, 'Blå', 1), (2, 'Grön', 2), (3, 'Röd', 3)";
                PreparedStatement preparedStatement2 = connection.prepareStatement(insertLevel);
                preparedStatement2.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error initializing the database.");
        }
    }
}
