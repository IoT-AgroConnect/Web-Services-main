package com.acme.web.services.iot.application.internal.commandservices;

import com.acme.web.services.iot.domain.model.aggregates.AcceptableRange;
import com.acme.web.services.iot.domain.model.commands.CreateAcceptableRangeCommand;
import com.acme.web.services.iot.domain.model.commands.DeleteAcceptableRangeCommand;
import com.acme.web.services.iot.domain.model.commands.UpdateAcceptableRangeCommand;
import com.acme.web.services.iot.domain.model.valueobjects.*;
import com.acme.web.services.iot.domain.services.AcceptableRangeCommandService;
import com.acme.web.services.iot.infrastructure.persitence.jpa.repositories.AcceptableRangeRepository;
import com.acme.web.services.management.domain.exceptions.CageNotFoundException;
import com.acme.web.services.management.domain.model.aggregates.Cage;
import com.acme.web.services.management.infrastructure.persitence.jpa.repositories.CageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio de comando para AcceptableRange.
 *
 * @author Fiorella
 * @version 1.0
 */
@Service
public class AcceptableRangeCommandServiceImpl implements AcceptableRangeCommandService {

    private final AcceptableRangeRepository acceptableRangeRepository;
    private final CageRepository cageRepository;

    public AcceptableRangeCommandServiceImpl(
            AcceptableRangeRepository acceptableRangeRepository,
            CageRepository cageRepository
    ) {
        this.acceptableRangeRepository = acceptableRangeRepository;
        this.cageRepository = cageRepository;
    }

    @Override
    @Transactional
    public Long handle(CreateAcceptableRangeCommand command) {
        Cage cage = cageRepository
                .findById(command.cageId())
                .orElseThrow(() -> new CageNotFoundException(command.cageId()));

        AcceptableRange acceptableRange = new AcceptableRange(command, cage);
        AcceptableRange saved = acceptableRangeRepository.save(acceptableRange);

        return saved.getId();
    }

    @Override
    @Transactional
    public Optional<AcceptableRange> handle(UpdateAcceptableRangeCommand command) {
        return acceptableRangeRepository.findById(command.id()).map(range -> {
            range.setTemperatureRange(new TemperatureRange(command.minTemperature(), command.maxTemperature()));
            range.setHumidityRange(new HumidityRange(command.minHumidity(), command.maxHumidity()));
            range.setCo2Range(new CO2Range(command.minCO2(), command.maxCO2()));
            range.setWaterQualityRange(new WaterQualityRange(command.minWaterQuality(), command.maxWaterQuality()));
            range.setWaterQuantityRange(new WaterQuantityRange(command.minWaterQuantity(), command.maxWaterQuantity()));
            return acceptableRangeRepository.save(range);
        });
    }

    @Override
    public Optional<AcceptableRange> handle(DeleteAcceptableRangeCommand command) {
        return acceptableRangeRepository.findById(command.id()).map(range ->{
            acceptableRangeRepository.delete(range);
            return range;
        });
    }
}
