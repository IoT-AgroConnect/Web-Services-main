package com.acme.web.services.iot.interfaces.rest.transform;/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

import com.acme.web.services.iot.domain.model.commands.CreateAcceptableRangeCommand;
import com.acme.web.services.iot.interfaces.rest.resources.AcceptableRangeResource;

/**
 * Descripción de la clase.
 *
 * @author Fiorella Jarama Peñaloza
 */
public class CreateAcceptableRangeResourceFromEntityAssembler {
    public static CreateAcceptableRangeCommand toCommandFromResource(AcceptableRangeResource acceptableRangeResource) {
        return new CreateAcceptableRangeCommand(
                acceptableRangeResource.minTemperature(),
                acceptableRangeResource.maxTemperature(),
                acceptableRangeResource.minHumidity(),
                acceptableRangeResource.maxHumidity(),
                acceptableRangeResource.minCo2(),
                acceptableRangeResource.maxCo2(),
                acceptableRangeResource.minWaterQuantity(),
                acceptableRangeResource.maxWaterQuantity(),
                acceptableRangeResource.minWaterQuality(),
                acceptableRangeResource.maxWaterQuality(),
                acceptableRangeResource.cageId(),
                acceptableRangeResource.applyToAll()
        );
    }
}
