package com.acme.web.services.iot.domain.model.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record WaterQuality(Double waterQuality) {
    public WaterQuality {
        if (waterQuality == null || waterQuality < 0.0 || waterQuality > 14.0) {
            throw new IllegalArgumentException("Water quality must be between 0.0 and 14.0 pH");
        }
    }
}
