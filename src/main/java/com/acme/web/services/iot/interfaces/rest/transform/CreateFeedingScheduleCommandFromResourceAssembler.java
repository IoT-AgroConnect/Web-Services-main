package com.acme.web.services.iot.interfaces.rest.transform;

import com.acme.web.services.iot.domain.model.commands.CreateFeedingScheduleCommand;
import com.acme.web.services.iot.interfaces.rest.resources.FeedingScheduleResource;

import java.time.LocalTime;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public class CreateFeedingScheduleCommandFromResourceAssembler {

    public static CreateFeedingScheduleCommand toCommandFromResource(FeedingScheduleResource resource) {
        return new CreateFeedingScheduleCommand(
                LocalTime.parse(resource.morningTime()),
                LocalTime.parse(resource.eveningTime()),
                resource.cageId(),
                resource.applyToAll()
        );
    }
}
