package com.acme.web.services.iot.domain.model.commands;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record UpdateSensorDataCommand(Long Id ,
                                      Double temperature,
                                      Double humidity,
                                      Integer co2,
                                      Double waterQuality,
                                      Double waterQuantity,
                                      Long cageId) {
}
