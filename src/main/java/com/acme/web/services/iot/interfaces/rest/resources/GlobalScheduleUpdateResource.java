package com.acme.web.services.iot.interfaces.rest.resources;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record GlobalScheduleUpdateResource(
        String morningTime,
        String eveningTime
) {}
