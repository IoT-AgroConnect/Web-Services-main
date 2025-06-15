package com.acme.web.services.iot.domain.model.commands;

import java.time.LocalTime;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record CreateFeedingScheduleCommand(
        LocalTime morningTime,
        LocalTime eveningTime,
        Long cageId,          // si es null y applyToAll es true → aplica a todos
        boolean applyToAll
) {
    public CreateFeedingScheduleCommand {
        if (morningTime == null || eveningTime == null)
            throw new IllegalArgumentException("Feeding times must not be null");
    }
}