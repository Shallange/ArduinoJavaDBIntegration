package com.example.TempDB.Device;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

/**
 * Represents an Arduino device that reads temperature data and sends it to an API.
 * This device communicates with the Arduino through a specified serial port.
 */
public class Arduino {

    // Constants for the API endpoint and delay durations.
    private static final String API_ENDPOINT = "http://localhost:8084/api/arduino/temperature";
    private static final int READ_DELAY_MS = 1000;
    private static final int INIT_DELAY_MS = 2000;

    private final SerialPort serialPort;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Initializes the Arduino object with a specified port name.
     *
     * @param portName The name of the port used to connect to the Arduino.
     */
    public Arduino(String portName) {
        this.serialPort = initializePort(portName);
    }
    /**
     * Initializes and returns the serial port for the specified port name.
     *
     * @param portName The name of the port.
     * @return An initialized serial port.
     */
    private SerialPort initializePort(String portName) {
        SerialPort port = SerialPort.getCommPort(portName);
        if (!port.openPort()) {
            throw new RuntimeException("Failed to open " + portName);
        }
        port.setComPortParameters(9600, 8, 1, 0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        return port;
    }

    /**z
     * Reads temperature data from the Arduino via the serial port.
     *
     * @return The temperature data as a string or null if no data is received.
     */
    public String readTemperature() {
        try (Scanner dataScanner = new Scanner(serialPort.getInputStream())) {
            return dataScanner.hasNextLine() ? dataScanner.nextLine() : null;
        }
    }

    /**
     * Sends the received temperature data to the specified API.
     *
     * @param temperature The temperature data to be sent.
     */
    public void sendTemperatureToAPI(String temperature) {
        String json = "{\"temperature\":" + temperature + "}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(API_ENDPOINT, entity, String.class);
            System.out.println("Response from API: " + response.getBody());
        } catch (RestClientException e) {
            System.out.println("Error sending temperature to API: " + e.getMessage());
        }
    }

    /**
     * Closes the serial port connection.
     */
    public void close() {
        if (serialPort != null) {
            serialPort.closePort();
        }
    }

    /**
     * Main entry point for the program.
     * Reads temperature data from the Arduino and sends it to the API.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        String portName = args.length > 0 ? args[0] : "COM3"; // Default to COM3 if no port is provided.
        Arduino temperatureSensor = new Arduino(portName);
        try {
            Thread.sleep(INIT_DELAY_MS);
            while (true) {
                String temperature = temperatureSensor.readTemperature();
                if (temperature != null) {
                    System.out.println("Received temperature: " + temperature);
                    temperatureSensor.sendTemperatureToAPI(temperature);
                } else {
                    System.out.println("No data received");
                }
                Thread.sleep(READ_DELAY_MS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            temperatureSensor.close();
        }
    }
}

