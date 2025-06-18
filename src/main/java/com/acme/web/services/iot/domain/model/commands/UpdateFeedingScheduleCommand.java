package com.acme.web.services.iot.domain.model.commands;

import java.time.LocalTime;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record UpdateFeedingScheduleCommand(
        Long scheduleId,
        LocalTime morningTime,
        LocalTime eveningTime
) {}
