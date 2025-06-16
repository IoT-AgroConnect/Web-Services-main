package com.acme.web.services.iot.domain.model.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record CO2Range(Integer minCO2, Integer maxCO2) {
    public CO2Range {
        if (minCO2 == null || maxCO2 == null) {
            throw new IllegalArgumentException("CO2 values cannot be null");
        }
        if (minCO2 < 0 || maxCO2 > 10000) {
            throw new IllegalArgumentException("CO2 must be between 0 and 10,000 ppm");
        }
        if (minCO2 > maxCO2) {
            throw new IllegalArgumentException("Minimum CO2 cannot be greater than maximum CO2");
        }
    }
}