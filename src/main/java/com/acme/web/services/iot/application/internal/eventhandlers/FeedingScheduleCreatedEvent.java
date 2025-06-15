package com.acme.web.services.iot.application.internal.eventhandlers;

import com.acme.web.services.iot.domain.model.aggregates.FeedingSchedule;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record FeedingScheduleCreatedEvent(FeedingSchedule feedingSchedule) {}
