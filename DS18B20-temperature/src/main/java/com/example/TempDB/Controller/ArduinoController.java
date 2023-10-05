package com.example.TempDB.Controller;

import com.example.TempDB.DataTransferObject.TemperatureDTO;
import com.example.TempDB.DatabaseCrud.CrudQuery;
import com.example.TempDB.DatabaseCrud.DatabaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@RestController
@RequestMapping("/api/arduino")


public class ArduinoController {

    // Auto-injected dependency for database initialization
    @Autowired
    private DatabaseInitializer databaseInitializer;
    // CRUD operations dependency for the database
    @Autowired
    private CrudQuery crudQuery;
    /**
     * Endpoint to process temperature data sent from an Arduino device.
     *
     * @param temperatureDTO DTO containing the temperature data.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PostMapping("/temperature")
    public ResponseEntity<String> receiveTemperature(@RequestBody TemperatureDTO temperatureDTO) {
        if (temperatureDTO == null || temperatureDTO.getTemperature() == null) {
            return new ResponseEntity<>("Invalid temperature data received.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Get the current timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Process the temperature data
            crudQuery.insertTemperatureData(temperatureDTO.getTemperature(), timestamp);

            return new ResponseEntity<>("Temperature data saved and processed successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save and process temperature data.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
