package com.acme.web.services.iot.domain.model.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record HumidityRange(Double minHumidity, Double maxHumidity) {
    public HumidityRange {
        if (minHumidity == null || maxHumidity == null) {
            throw new IllegalArgumentException("Humidity values cannot be null");
        }
        if (minHumidity < 0.0 || maxHumidity > 100.0) {
            throw new IllegalArgumentException("Humidity must be between 0% and 100%");
        }
        if (minHumidity > maxHumidity) {
            throw new IllegalArgumentException("Minimum humidity cannot be greater than maximum humidity");
        }
    }
}