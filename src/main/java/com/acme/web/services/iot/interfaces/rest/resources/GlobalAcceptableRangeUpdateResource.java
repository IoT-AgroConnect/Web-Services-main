package com.acme.web.services.iot.interfaces.rest.resources;/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

/**
 * Descripción de la clase.
 *
 * @author Fiorella Jarama Peñaloza
 */
public record GlobalAcceptableRangeUpdateResource(
        Double minTemperature,
        Double maxTemperature,
        Double minHumidity,
        Double maxHumidity,
        Integer minCO2,
        Integer maxCO2,
        Double minWaterQuality,
        Double maxWaterQuality,
        Double minWaterQuantity,
        Double maxWaterQuantity
) {
}
