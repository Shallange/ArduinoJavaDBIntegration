package com.example.TempDB.DataTransferObject;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class representing the temperature data.
 * This object is used for receiving temperature data from external systems
 * (e.g., an Arduino device) and passing it within the application.
 */
@Getter
@Setter
public class TemperatureDTO {

    /**
     * Temperature value, can be in Celsius, Fahrenheit, etc.,
     * depending on the context of usage.
     */
    private Float temperature;
}
