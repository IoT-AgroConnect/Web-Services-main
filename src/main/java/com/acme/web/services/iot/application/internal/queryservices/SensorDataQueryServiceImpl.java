package com.acme.web.services.iot.application.internal.queryservices;/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

import com.acme.web.services.iot.domain.model.aggregates.SensorData;
import com.acme.web.services.iot.domain.model.queries.GetAllSensorDataQuery;
import com.acme.web.services.iot.domain.model.queries.GetSensorDataByCageIdQuery;
import com.acme.web.services.iot.domain.model.queries.GetSensorDataByIdQuery;
import com.acme.web.services.iot.domain.services.SensorDataQueryService;
import com.acme.web.services.iot.infrastructure.persitence.jpa.repositories.SensorDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Descripción de la clase.
 *
 * @author Fiorella Jarama Peñaloza
 */
@Service
public class SensorDataQueryServiceImpl implements SensorDataQueryService {

    private final SensorDataRepository sensorDataRepository;

    public SensorDataQueryServiceImpl(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    @Override
    public List<SensorData> handle(GetAllSensorDataQuery query) {
        return this.sensorDataRepository.findAll();
    }

    @Override
    public Optional<SensorData> handle(GetSensorDataByIdQuery query) {
        return this.sensorDataRepository.findById(query.sensorDataId());
    }

    @Override
    public Optional<SensorData> handle(GetSensorDataByCageIdQuery query) {
        if (query.cageId() == null) {
            return Optional.empty();
        }
        return this.sensorDataRepository.findByCageId(query.cageId());
    }

    @Override
    public Optional<SensorData> handleGetSensorDataByIdQuery(Long id) {
        return Optional.ofNullable(this.sensorDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sensor data with ID " + id + " not found.")));
    }
}
