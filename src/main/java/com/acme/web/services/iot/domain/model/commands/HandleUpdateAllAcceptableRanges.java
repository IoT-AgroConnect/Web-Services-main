package com.acme.web.services.iot.domain.model.commands;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record HandleUpdateAllAcceptableRanges(
        Double minAcceptableRange,
        Double maxAcceptableRange
) {
    public HandleUpdateAllAcceptableRanges {
        if (minAcceptableRange == null || maxAcceptableRange == null)
            throw new IllegalArgumentException("Acceptable ranges must not be null");
        if (minAcceptableRange >= maxAcceptableRange)
            throw new IllegalArgumentException("Minimum acceptable range must be less than maximum acceptable range");
    }
}
