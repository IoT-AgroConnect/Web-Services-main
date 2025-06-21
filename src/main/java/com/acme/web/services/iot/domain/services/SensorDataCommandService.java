package com.acme.web.services.iot.domain.services;

import com.acme.web.services.iot.domain.model.aggregates.SensorData;
import com.acme.web.services.iot.domain.model.commands.CreateSensorDataCommand;
import com.acme.web.services.iot.domain.model.commands.DeleteSensorDataCommand;
import com.acme.web.services.iot.domain.model.commands.UpdateSensorDataCommand;

import java.util.Optional;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public interface SensorDataCommandService {
    Long handle(CreateSensorDataCommand command);
    Optional<SensorData> handle(UpdateSensorDataCommand command);
    Optional<SensorData> handle(DeleteSensorDataCommand command);
}
