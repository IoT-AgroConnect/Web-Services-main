package com.acme.web.services.iot.domain.model.commands;

/**
 * Comando para crear un rango aceptable de condiciones ambientales en una jaula.
 *
 * @author Fiorella Jarama Peñaloza
 */
public record CreateAcceptableRangeCommand(
        Double minTemperature,
        Double maxTemperature,
        Double minHumidity,
        Double maxHumidity,
        Integer minCO2,
        Integer maxCO2,
        Double minWaterQuality,
        Double maxWaterQuality,
        Double minWaterQuantity,
        Double maxWaterQuantity,
        Long cageId
) {}
