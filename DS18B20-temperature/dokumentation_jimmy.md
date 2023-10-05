## ArduinoController.java
**Package**: `com.example.TempDB.Controller`

### Overview
The `ArduinoController` class is a Spring REST controller that defines an endpoint to receive temperature data from the Arduino device. It processes this data and saves it to the database.

### Class-Level Annotations
- `@RestController`: Indicates that the class is a Spring REST controller.
- `@RequestMapping("/api/arduino")`: Sets the base URL path for class-level endpoints.

### Dependencies
- **DatabaseInitializer**: Injected dependency for possible database initialization.
- **CrudQuery**: Injected dependency for CRUD operations.

### Endpoints
- `POST /api/arduino/temperature`: Endpoint to receive and process temperature data.

---

## Arduino.java
**Package**: `com.example.TempDB.Device`

### Overview
The `Arduino` class serves as the main interface between the Java application and the Arduino device. It's responsible for reading temperature data from the Arduino via a serial port and subsequently sending this data to a predefined API endpoint.

### Class-Level Constants
- **API_ENDPOINT**: Specifies the API endpoint to which the temperature data is sent.
- **READ_DELAY_MS**: Defines the interval for reading from the Arduino.
- **INIT_DELAY_MS**: Defines the initialization delay.

### Dependencies
- **SerialPort**: Represents the serial port used for communication with the Arduino.
- **RestTemplate**: A utility provided by Spring for making HTTP requests.

### Main Methods
- **initializePort(String portName)**: Sets up the serial port with the given name.
- **readTemperature()**: Reads temperature data from the serial port.
- **sendTemperatureToAPI(String temperature)**: Sends the temperature data to the API.

---

## TemperatureDTO.java
**Package**: `com.example.TempDB.DataTransferObject`

### Overview
A Data Transfer Object (DTO) representing the temperature data structure.

### Attributes
- **temperature**: Represents the temperature value (e.g., in Celsius or Fahrenheit).

---

## BeanConfig.java
**Package**: `com.example.TempDB.Config`

### Overview
A configuration class for defining beans and their dependencies.

### Beans
- **dataBaseConnection()**: Establishes a database connection.
- **crudQuery(DataBaseConnection dataBaseConnection)**: Provides CRUD operations.
- **databaseInitializer(DataBaseConnection dataBaseConnection)**: For database initialization.
- **initializeDatabase(DatabaseInitializer databaseInitializer)**: Initializes the database on startup.

---

## DataBaseConnection.java
**Package**: `com.example.TempDB.DatabaseCrud`

### Overview
Class to establish a connection to the database.

### Methods
- **getConnection()**: Returns the established database connection.

---

## CrudQuery.java
**Package**: `com.example.TempDB.DatabaseCrud`

### Overview
Manages CRUD operations on the database.

### Important Methods
- **initializeDatabase()**: Sets up initial data in the database.
- **insertTemperatureData(float temperature, String timestamp)**: Inserts temperature data into the database.

---

## DatabaseInitializer.java
**Package**: `com.example.TempDB.DatabaseCrud`

### Overview
Focuses on initializing the database with specific data.

### Important Methods
- **initialize()**: Initializes the database with predefined data if not already initialized.

----------




# Arduino Temperature Monitoring

This README provides an overview and documentation of the Arduino code used to monitor temperature using the Dallas temperature sensor and control LEDs based on the temperature range.

## Overview

The Arduino code is designed to:
1. Read temperature values from a Dallas temperature sensor.
2. Display the temperature reading via the Serial monitor.
3. Control LED states based on different temperature ranges.

## Dependencies

- **OneWire Library**: Used for communication with OneWire devices.
- **DallasTemperature Library**: Provides functionalities to interact with Dallas temperature sensors.

## Setup

The code initializes the Serial monitor, the Dallas temperature sensor, and sets the desired temperature resolution. It also sets pins 2 to 4 as OUTPUT pins.

```cpp
#include <OneWire.h>
#include <DallasTemperature.h>

#define ONE_WIRE_BUS 7
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);

void setup() {
  Serial.begin(9600); 
  sensors.begin();
  sensors.setResolution(12);

  for (int pinNumber = 2; pinNumber < 5; pinNumber++) {
    pinMode(pinNumber, OUTPUT);
    digitalWrite(pinNumber, LOW);
  }

  delay(5000);
}
