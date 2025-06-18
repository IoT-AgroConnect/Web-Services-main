package com.acme.web.services.iot.domain.model.valueobjects;

import java.time.LocalTime;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record FeedingTime(LocalTime time) {
    public FeedingTime {
        if (time == null) {
            throw new IllegalArgumentException("Feeding time must not be null");
        }
    }
}
