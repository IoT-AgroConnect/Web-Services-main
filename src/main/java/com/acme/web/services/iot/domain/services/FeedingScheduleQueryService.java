package com.acme.web.services.iot.domain.services;

import com.acme.web.services.iot.domain.model.aggregates.FeedingSchedule;
import com.acme.web.services.iot.domain.model.queries.GetFeedingScheduleByCageIdQuery;
import com.acme.web.services.management.domain.model.entities.Animal;

import java.util.List;
import java.util.Optional;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public interface FeedingScheduleQueryService {
    List<FeedingSchedule> handleGetAllFeedingSchedulesQuery();
    Optional<FeedingSchedule> handleGetFeedingScheduleByIdQuery(Long id);

    Optional<FeedingSchedule> handle(GetFeedingScheduleByCageIdQuery query);
}
