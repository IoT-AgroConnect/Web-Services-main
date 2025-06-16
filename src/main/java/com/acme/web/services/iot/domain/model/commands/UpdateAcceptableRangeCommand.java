package com.acme.web.services.iot.domain.model.commands;

/**
 * Comando para actualizar un rango aceptable de condiciones ambientales en una jaula.
 *
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record UpdateAcceptableRangeCommand(
        Long id,
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
