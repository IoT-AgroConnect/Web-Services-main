package com.acme.web.services.iot.infrastructure.persitence.jpa.repositories;

import com.acme.web.services.iot.domain.model.aggregates.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findAllById(Long id);
    Optional<SensorData> findByCageId(Long cageId);
}

