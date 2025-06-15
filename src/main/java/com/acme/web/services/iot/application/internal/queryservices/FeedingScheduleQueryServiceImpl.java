package com.acme.web.services.iot.application.internal.queryservices;

import com.acme.web.services.iot.domain.model.aggregates.FeedingSchedule;
import com.acme.web.services.iot.domain.model.queries.GetFeedingScheduleByCageIdQuery;
import com.acme.web.services.iot.domain.services.FeedingScheduleQueryService;
import com.acme.web.services.iot.infrastructure.persitence.jpa.repositories.FeedingScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */

@Service
public class FeedingScheduleQueryServiceImpl implements FeedingScheduleQueryService {
    private final FeedingScheduleRepository feedingScheduleRepository;

    public FeedingScheduleQueryServiceImpl(FeedingScheduleRepository feedingScheduleRepository) {
        this.feedingScheduleRepository = feedingScheduleRepository;
    }


    @Override
    public List<FeedingSchedule> handleGetAllFeedingSchedulesQuery() {
        return this.feedingScheduleRepository.findAll();
    }

    @Override
    public Optional<FeedingSchedule> handleGetFeedingScheduleByIdQuery(Long id) {
        return this.feedingScheduleRepository.findById(id);
    }

    @Override
    public Optional<FeedingSchedule> handle(GetFeedingScheduleByCageIdQuery query) {
        return this.feedingScheduleRepository.findByCageId(query.cageId());
    }
}
