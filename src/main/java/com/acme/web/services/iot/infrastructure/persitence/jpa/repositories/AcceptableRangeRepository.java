package com.acme.web.services.iot.infrastructure.persitence.jpa.repositories;

import com.acme.web.services.iot.domain.model.aggregates.AcceptableRange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public interface AcceptableRangeRepository extends JpaRepository<AcceptableRange, Long> {
    Optional<AcceptableRange> findByCageId(Long cageId);
}