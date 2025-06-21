package com.acme.web.services.iot.interfaces.rest.resources;

/**
 * This class represents the acceptable range of environmental parameters for a cage in an IoT system.
 *
 * @author Fiorella Jarama Peñaloza
 */
public record AcceptableRangeResource(
        Long cageId,
        Double minTemperature,
        Double maxTemperature,
        Double minHumidity,
        Double maxHumidity,
        Integer minCo2,
        Integer maxCo2,
        Double minWaterQuality,
        Double maxWaterQuality,
        Double minWaterQuantity,
        Double maxWaterQuantity,
        boolean applyToAll
) {
}