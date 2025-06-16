package com.acme.web.services.iot.interfaces.rest.transform;

import com.acme.web.services.iot.domain.model.aggregates.SensorData;
import com.acme.web.services.iot.interfaces.rest.resources.SensorDataResource;

public class SensorDataResourceFromEntityAssembler {
    public static SensorDataResource toResourceFromEntity(SensorData entity) {
        return new SensorDataResource(
                entity.getId(),
                entity.getTemperature(),
                entity.getHumidity(),
                entity.getCO2(),
                entity.getWaterQuality(),
                entity.getWaterQuantity(),
                entity.getCage().getId()
        );
    }
}
