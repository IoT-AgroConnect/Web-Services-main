package com.acme.web.services.iot.domain.model.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record WaterQuantity(Double waterQuantity) {
    public WaterQuantity {
        if (waterQuantity == null || waterQuantity < 0.0) {
            throw new IllegalArgumentException("Water quantity cannot be null or negative");
        }
    }
}
