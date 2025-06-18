package com.acme.web.services.iot.domain.model.queries;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record GetFeedingScheduleByIdQuery(Long scheduleId) {
    public GetFeedingScheduleByIdQuery {
        if (scheduleId == null || scheduleId <= 0) {
            throw new IllegalArgumentException("Schedule ID must be a positive number");
        }
    }
}