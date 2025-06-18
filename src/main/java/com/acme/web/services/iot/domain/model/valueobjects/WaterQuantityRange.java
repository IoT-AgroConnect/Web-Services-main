package com.acme.web.services.iot.domain.model.valueobjects;/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

/**
 * Descripción de la clase.
 *
 * @author Fiorella Jarama Peñaloza
 */
public record WaterQuantityRange(Double minWaterQuantity, Double maxWaterQuantity) {
    public WaterQuantityRange {
        if (minWaterQuantity == null || maxWaterQuantity == null) {
            throw new IllegalArgumentException("Water quantity values cannot be null");
        }
        if (minWaterQuantity < 0.0 || maxWaterQuantity > 100.0) {
            throw new IllegalArgumentException("Water quantity must be between 0.0 and 100.0 liters");
        }
        if (minWaterQuantity > maxWaterQuantity) {
            throw new IllegalArgumentException("Minimum water quantity cannot be greater than maximum water quantity");
        }
    }
}