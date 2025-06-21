package com.acme.web.services.iot.domain.model.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record WaterQualityRange(Double minWaterQuality, Double maxWaterQuality) {
    public WaterQualityRange {
        if (minWaterQuality == null || maxWaterQuality == null) {
            throw new IllegalArgumentException("Water quality values cannot be null");
        }
        if (minWaterQuality < 0.0 || maxWaterQuality > 14.0) {
            throw new IllegalArgumentException("Water quality must be between 0.0 and 14.0 pH");
        }
        if (minWaterQuality > maxWaterQuality) {
            throw new IllegalArgumentException("Minimum water quality cannot be greater than maximum water quality");
        }
    }
}