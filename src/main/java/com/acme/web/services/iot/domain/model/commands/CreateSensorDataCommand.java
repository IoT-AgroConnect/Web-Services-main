package com.acme.web.services.iot.domain.model.commands;/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

/**
 * Descripción de la clase.
 *
 * @author Fiorella Jarama Peñaloza
 */
public record CreateSensorDataCommand(Double temperature,
                                      Double humidity,
                                      Integer co2,
                                      Double waterQuality,
                                      Double waterQuantity,
                                      Long cageId) {
}
