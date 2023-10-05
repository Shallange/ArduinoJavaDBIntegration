package com.example.TempDB.Config;

import com.example.TempDB.DatabaseCrud.CrudQuery;
import com.example.TempDB.DatabaseCrud.DataBaseConnection;
import com.example.TempDB.DatabaseCrud.DatabaseInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class responsible for defining and initializing beans.
 */
@Configuration
public class BeanConfig {

    /**
     * Bean definition for database connection.
     *
     * @return An instance of DataBaseConnection initialized with the specified parameters.
     */
    @Bean
    public DataBaseConnection dataBaseConnection() {
        String url = "jdbc:mysql://localhost:3306/dashlacktempen_database";
        String username = "root";
        String password = "INSERT PASSWORD";// Replace  "INSERT PASSWORD" with the database password
        return new DataBaseConnection(url, username, password);
    }

    /**
     * Bean definition for CRUD operations on the database.
     *
     * @param dataBaseConnection An instance of DataBaseConnection.
     * @return An instance of CrudQuery initialized with the active database connection.
     */
    @Bean
    public CrudQuery crudQuery(DataBaseConnection dataBaseConnection) {
        return new CrudQuery(dataBaseConnection.getConnection());
    }

    /**
     * Bean definition for database initialization.
     *
     * @param dataBaseConnection An instance of DataBaseConnection.
     * @return An instance of DatabaseInitializer initialized with the active database connection.
     */
    @Bean
    public DatabaseInitializer databaseInitializer(DataBaseConnection dataBaseConnection) {
        return new DatabaseInitializer(dataBaseConnection.getConnection());
    }

    /**
     * Bean definition to initialize the database upon application startup.
     *
     * @param databaseInitializer An instance of DatabaseInitializer.
     * @return A CommandLineRunner instance that initializes the database.
     */
    @Bean
    public CommandLineRunner initializeDatabase(DatabaseInitializer databaseInitializer) {
        return args -> {
            databaseInitializer.initialize();
            System.out.println("Database initialized successfully.");
        };
    }
}
