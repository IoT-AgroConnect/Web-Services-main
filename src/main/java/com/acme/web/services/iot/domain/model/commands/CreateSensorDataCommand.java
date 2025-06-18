package com.acme.web.services.iot.domain.model.commands;
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
