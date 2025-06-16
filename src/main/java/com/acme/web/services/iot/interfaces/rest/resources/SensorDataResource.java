package com.acme.web.services.iot.interfaces.rest.resources;/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

/**
 * SensorDataResource is a record class that represents the sensor data
 *
 * @author Fiorella Jarama Peñaloza
 */
public record SensorDataResource(
        Long id,
        Double temperature,
        Double humidity,
        Integer co2,
        Double waterQuality,
        Double waterQuantity,
        Long cageId
) {}
