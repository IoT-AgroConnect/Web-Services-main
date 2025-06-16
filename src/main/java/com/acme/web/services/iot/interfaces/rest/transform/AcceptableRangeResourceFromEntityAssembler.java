package com.acme.web.services.iot.interfaces.rest.transform;/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

import com.acme.web.services.iot.domain.model.aggregates.AcceptableRange;
import com.acme.web.services.iot.interfaces.rest.resources.AcceptableRangeResource;

/**
 * Descripción de la clase.
 *
 * @author Fiorella Jarama Peñaloza
 */
public class AcceptableRangeResourceFromEntityAssembler {

    public static AcceptableRangeResource toResourceFromEntity(AcceptableRange entity) {
        return new AcceptableRangeResource(
                entity.getCage().getId(),
                entity.getMinTemperature(),
                entity.getMaxTemperature(),
                entity.getMinHumidity(),
                entity.getMaxHumidity(),
                entity.getMinCO2(),
                entity.getMaxCO2(),
                entity.getMinWaterQuality(),
                entity.getMaxWaterQuality(),
                entity.getMinWaterQuantity(),
                entity.getMaxWaterQuantity()
        );
    }
}