package com.acme.web.services.iot.domain.model.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record Humidity(Double humidity) {
    public Humidity {
        if (humidity == null || humidity < 0.0 || humidity > 100.0) {
            throw new IllegalArgumentException("Humidity must be between 0.0 and 100.0%");
        }
    }
}