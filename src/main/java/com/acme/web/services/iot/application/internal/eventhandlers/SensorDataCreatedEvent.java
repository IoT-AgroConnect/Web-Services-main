package com.acme.web.services.iot.application.internal.eventhandlers;

import com.acme.web.services.iot.domain.model.aggregates.SensorData;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record SensorDataCreatedEvent(SensorData sensorData) {
}
