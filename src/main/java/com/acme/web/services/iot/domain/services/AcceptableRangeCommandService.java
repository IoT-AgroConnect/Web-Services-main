package com.acme.web.services.iot.domain.services;

import com.acme.web.services.iot.domain.model.aggregates.AcceptableRange;
import com.acme.web.services.iot.domain.model.commands.CreateAcceptableRangeCommand;
import com.acme.web.services.iot.domain.model.commands.DeleteAcceptableRangeCommand;
import com.acme.web.services.iot.domain.model.commands.UpdateAcceptableRangeCommand;

import java.util.Optional;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public interface AcceptableRangeCommandService {
    Long handle(CreateAcceptableRangeCommand command);
    Optional<AcceptableRange> handle(UpdateAcceptableRangeCommand command);
    Optional<AcceptableRange> handle(DeleteAcceptableRangeCommand command);
}