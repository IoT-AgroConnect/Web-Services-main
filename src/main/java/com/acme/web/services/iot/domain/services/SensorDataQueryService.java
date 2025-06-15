package com.acme.web.services.iot.domain.services;
/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

import com.acme.web.services.iot.domain.model.aggregates.SensorData;
import com.acme.web.services.iot.domain.model.queries.GetAllSensorDataQuery;
import com.acme.web.services.iot.domain.model.queries.GetSensorDataByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Descripción de la clase.
 *
 * @author Fiorella Jarama Peñaloza
 */
public interface SensorDataQueryService {
    List<SensorData> handle(GetAllSensorDataQuery query);
    Optional<SensorData> handle(GetSensorDataByIdQuery query);
}
