package com.acme.web.services.iot.domain.model.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record CO2(Integer co2) {
    public CO2 {
        if (co2 < 0) {
            throw new IllegalArgumentException("CO2 level cannot be negative");
        }
    }
}
