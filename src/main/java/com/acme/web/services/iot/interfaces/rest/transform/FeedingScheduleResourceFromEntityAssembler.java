package com.acme.web.services.iot.interfaces.rest.transform;

import com.acme.web.services.iot.domain.model.aggregates.FeedingSchedule;
import com.acme.web.services.iot.interfaces.rest.resources.FeedingScheduleResource;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public class FeedingScheduleResourceFromEntityAssembler {

    public static FeedingScheduleResource toResourceFromEntity(FeedingSchedule entity) {
        return new FeedingScheduleResource(
                entity.getCage().getId(),
                entity.getMorningTime().toString(),
                entity.getEveningTime().toString(),
                false
        );
    }
}
