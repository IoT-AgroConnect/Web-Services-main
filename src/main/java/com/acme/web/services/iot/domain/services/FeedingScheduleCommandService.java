package com.acme.web.services.iot.domain.services;

import com.acme.web.services.iot.domain.model.aggregates.FeedingSchedule;
import com.acme.web.services.iot.domain.model.commands.CreateFeedingScheduleCommand;
import com.acme.web.services.iot.domain.model.commands.HandleUpdateAllSchedules;
import com.acme.web.services.iot.domain.model.commands.UpdateFeedingScheduleCommand;

import java.util.Optional;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public interface FeedingScheduleCommandService {
    Long handle(CreateFeedingScheduleCommand command);

    Optional<FeedingSchedule> handle(UpdateFeedingScheduleCommand command);

    void handle(HandleUpdateAllSchedules command);
}
