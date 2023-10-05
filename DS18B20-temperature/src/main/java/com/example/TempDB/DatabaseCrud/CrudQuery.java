package com.example.TempDB.DatabaseCrud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * This class provides CRUD (Create, Read, Update, Delete) operations for the database.
 * It handles operations related to temperature data and associated warnings.
 */
public class CrudQuery {

    private Connection connection;

    /**
     * Initializes a new instance of the CrudQuery class.
     *
     * @param connection The SQL connection to use for database operations.
     */
    public CrudQuery(Connection connection) {
        this.connection = connection;
    }


    /**
     * Inserts temperature data into the database and checks if a warning should be added based on the temperature.
     *
     * @param temperature The temperature value to be inserted.
     * @param timestamp   The timestamp associated with the temperature reading.
     */
    public void insertTemperatureData(float temperature, String timestamp) {
        try {
            // 1. Insert temperature data
            insertIntoTempTable(temperature, timestamp);

            // 2. Insert warning (if necessary)
            if (shouldInsertWarning(temperature)) {
                int tempId = getLastInsertedTempId();
                int levelId = getLevelIdFromTemperature(temperature);
                insertIntoWarning(tempId, levelId, timestamp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a warning should be inserted based on the temperature value.
     */
    private boolean shouldInsertWarning(float temperature) {
        String categoryName = getCategorizedTemperatureName(temperature);
        return categoryName.equals("Hög")|| categoryName.equals("Stabil") || categoryName.equals("Låg");
    }

    /**
     * Inserts a warning into the database based on temperature ID, level ID, and timestamp.
     */
    private void insertIntoWarning(int tempId, int levelId, String timestamp) {
        try {
            String sql = "INSERT INTO warning_table(temp_id, level_id, timestamp_warning) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, tempId);
            pstmt.setInt(2, levelId);
            pstmt.setString(3, timestamp);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Inserts temperature data into the database.
     */
    private void insertIntoTempTable(float temperature, String timestamp) {
        try {
            String sql = "INSERT INTO temp_table(temperatures, timestamp) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setFloat(1, temperature);
            pstmt.setString(2, timestamp);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the category name (e.g., Hög, Stabil, Låg) based on a temperature value.
     */
    private String getCategorizedTemperatureName(float temperature) {
        try {
            String sql = "SELECT name FROM temp_category_table  WHERE ? BETWEEN lower_bound AND upper_bound";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setFloat(1, temperature);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves the ID of the last inserted temperature data.
     */
    private int getLastInsertedTempId() {
        try {
            String sql = "SELECT LAST_INSERT_ID()";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets the level ID based on the temperature value.
     */
    private int getLevelIdFromTemperature(float temperature) {
        String categoryName = getCategorizedTemperatureName(temperature);
        return getLevelIdFromCategoryName(categoryName);
    }

    /**
     * Gets the level ID based on the category name.
     */
    private int getLevelIdFromCategoryName(String categoryName) {
        try {
            String sql = "SELECT lt.id FROM level_table lt " +
                    "JOIN temp_category_table tct ON lt.category_id = tct.id " +
                    "WHERE tct.name = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, categoryName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
