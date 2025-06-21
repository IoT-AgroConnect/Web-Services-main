package com.acme.web.services.iot.application.internal.queryservices;

import com.acme.web.services.iot.domain.model.aggregates.AcceptableRange;
import com.acme.web.services.iot.domain.model.queries.GetAcceptableRangeByCageIdQuery;
import com.acme.web.services.iot.domain.services.AcceptableRangeQueryService;
import com.acme.web.services.iot.infrastructure.persitence.jpa.repositories.AcceptableRangeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the AcceptableRangeQueryService interface.
 *
 * @author Fiorella Jarama Peñaloza
 */
@Service
public class AcceptableRangeQueryServiceImpl implements AcceptableRangeQueryService {

    private final AcceptableRangeRepository acceptableRangeRepository;

    public AcceptableRangeQueryServiceImpl(AcceptableRangeRepository acceptableRangeRepository) {
        this.acceptableRangeRepository = acceptableRangeRepository;
    }

    @Override
    public List<AcceptableRange> handleGetAllAcceptableRangesQuery() {
        return this.acceptableRangeRepository.findAll();
    }

    @Override
    public Optional<AcceptableRange> handleGetAcceptableRangeByIdQuery(Long id) {
        return this.acceptableRangeRepository.findById(id);
    }

    @Override
    public Optional<AcceptableRange> handle(GetAcceptableRangeByCageIdQuery query) {
        return this.acceptableRangeRepository.findByCageId(query.cageId());
    }

    @Override
    public Optional<AcceptableRange> handleGetAcceptableRangeByCageIdQuery(Long cageId) {
        return this.acceptableRangeRepository.findByCageId(cageId);
    }
}
