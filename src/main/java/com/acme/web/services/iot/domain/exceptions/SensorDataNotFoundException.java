package com.acme.web.services.iot.domain.exceptions;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public class SensorDataNotFoundException extends RuntimeException {
    public SensorDataNotFoundException(Long sensorDataId) {
        super("Sensor data with id " + sensorDataId + " not found");
    }
}
