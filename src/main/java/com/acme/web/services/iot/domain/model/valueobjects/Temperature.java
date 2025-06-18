package com.acme.web.services.iot.domain.model.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record Temperature(Double temperature) {
    public Temperature {
        if (temperature == null || temperature < -273.15) {
            throw new IllegalArgumentException("Temperature cannot be null or below absolute zero (-273.15°C)");
        }
    }


}
