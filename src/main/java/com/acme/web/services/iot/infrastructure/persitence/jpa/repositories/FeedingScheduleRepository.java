package com.acme.web.services.iot.infrastructure.persitence.jpa.repositories;

import com.acme.web.services.iot.domain.model.aggregates.FeedingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public interface FeedingScheduleRepository extends JpaRepository<FeedingSchedule, Long> {
    Optional<FeedingSchedule> findByCageId(Long cageId);
}
