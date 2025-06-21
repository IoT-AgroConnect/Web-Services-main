package com.acme.web.services.iot.domain.services;

import com.acme.web.services.iot.domain.model.aggregates.AcceptableRange;
import com.acme.web.services.iot.domain.model.queries.GetAcceptableRangeByCageIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public interface AcceptableRangeQueryService {
    List<AcceptableRange> handleGetAllAcceptableRangesQuery();
    Optional<AcceptableRange> handleGetAcceptableRangeByIdQuery(Long id);
    Optional<AcceptableRange> handle(GetAcceptableRangeByCageIdQuery query);
    Optional<AcceptableRange> handleGetAcceptableRangeByCageIdQuery(Long cageId);
}