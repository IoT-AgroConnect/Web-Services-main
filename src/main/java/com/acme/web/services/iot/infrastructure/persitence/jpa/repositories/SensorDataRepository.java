package com.acme.web.services.iot.infrastructure.persitence.jpa.repositories;

import com.acme.web.services.iot.domain.model.aggregates.SensorData;
import com.acme.web.services.iot.domain.model.queries.GetAllSensorDataQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findAllById(Long id);
}

