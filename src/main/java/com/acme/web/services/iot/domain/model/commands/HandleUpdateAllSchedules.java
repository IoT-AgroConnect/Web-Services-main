package com.acme.web.services.iot.domain.model.commands;

import java.time.LocalTime;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record HandleUpdateAllSchedules(
        LocalTime morningTime,
        LocalTime eveningTime
) {
    public HandleUpdateAllSchedules {
        if (morningTime == null || eveningTime == null)
            throw new IllegalArgumentException("Feeding times must not be null");
    }
}