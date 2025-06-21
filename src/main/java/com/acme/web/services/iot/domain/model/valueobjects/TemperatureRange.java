package com.acme.web.services.iot.domain.model.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record TemperatureRange(Double minTemperature, Double maxTemperature) {
    public TemperatureRange {
        if (minTemperature == null || maxTemperature == null) {
            throw new IllegalArgumentException("Temperature values cannot be null");
        }
        if (minTemperature < -50.0 || maxTemperature > 100.0) {
            throw new IllegalArgumentException("Temperature must be between -50.0°C and 100.0°C");
        }
        if (minTemperature > maxTemperature) {
            throw new IllegalArgumentException("Minimum temperature cannot be greater than maximum temperature");
        }
    }
}