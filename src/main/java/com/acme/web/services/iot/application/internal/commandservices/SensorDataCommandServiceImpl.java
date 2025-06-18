package com.acme.web.services.iot.application.internal.commandservices;

import com.acme.web.services.iot.application.internal.eventhandlers.SensorDataCreatedEvent;
import com.acme.web.services.iot.domain.exceptions.SensorDataNotFoundException;
import com.acme.web.services.iot.domain.model.aggregates.SensorData;
import com.acme.web.services.iot.domain.model.commands.CreateSensorDataCommand;
import com.acme.web.services.iot.domain.model.commands.DeleteSensorDataCommand;
import com.acme.web.services.iot.domain.model.commands.UpdateSensorDataCommand;
import com.acme.web.services.iot.domain.model.valueobjects.*;
import com.acme.web.services.iot.domain.services.SensorDataCommandService;
import com.acme.web.services.iot.infrastructure.persitence.jpa.repositories.SensorDataRepository;
import com.acme.web.services.management.domain.model.aggregates.Cage;
import com.acme.web.services.management.infrastructure.persitence.jpa.repositories.CageRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Esta clase representa la implementación del servicio de comandos para la agregación SensorData.
 * Implementa los métodos para crear, actualizar y eliminar datos de sensores.
 *
 * @author Fiorella
 * @version 1.0
 */
@Service
public class SensorDataCommandServiceImpl implements SensorDataCommandService {

    private final SensorDataRepository sensorDataRepository;
    private final CageRepository cageRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SensorDataCommandServiceImpl(SensorDataRepository sensorDataRepository, CageRepository cageRepository,
                                        ApplicationEventPublisher eventPublisher) {
        this.sensorDataRepository = sensorDataRepository;
        this.cageRepository = cageRepository;
        this.eventPublisher = eventPublisher;

    }

    /**
     * Crea un nuevo registro de datos de sensor en la base de datos.
     *
     * @param command Comando para crear datos de sensor
     * @return ID del nuevo registro creado
     */
    @Override
    public Long handle(CreateSensorDataCommand command) {
        Cage cage = cageRepository.findById(command.cageId())
                .orElseThrow(() -> new IllegalArgumentException("La jaula con ID " + command.cageId() + " no existe."));
        var sensorData = new SensorData(
                new Temperature(command.temperature()),
                new Humidity(command.humidity()),
                new CO2(command.co2()),
                new WaterQuality(command.waterQuality()),
                new WaterQuantity(command.waterQuantity()),
                cage
        );
        try {
            sensorDataRepository.save(sensorData);
            eventPublisher.publishEvent(new SensorDataCreatedEvent(sensorData));
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar datos del sensor", e);
        }

        return sensorData.getId();
    }

    /**
     * Actualiza un registro de datos de sensor existente.
     *
     * @param command Comando con los nuevos valores
     * @return Registro actualizado si existe
     */
    @Override
    public Optional<SensorData> handle(UpdateSensorDataCommand command) {
        return sensorDataRepository.findById(command.Id()).map(sensorData -> {
            sensorData.setTemperature(new Temperature(command.temperature()));
            sensorData.setHumidity(new Humidity(command.humidity()));
            sensorData.setCo2(new CO2(command.co2()));
            sensorData.setWaterQuality(new WaterQuality(command.waterQuality()));
            sensorData.setWaterQuantity(new WaterQuantity(command.waterQuantity()));
            return sensorDataRepository.save(sensorData);
        });
    }

    /**
     * Elimina un registro de datos de sensor existente.
     *
     * @param command Comando con el ID a eliminar
     * @return Registro eliminado si existía
     */
    @Override
    public Optional<SensorData> handle(DeleteSensorDataCommand command) {
        if (!sensorDataRepository.existsById(command.Id())) {
            throw new SensorDataNotFoundException(command.Id());
        }
        var sensorData = sensorDataRepository.findById(command.Id());
        sensorData.ifPresent(sensorDataRepository::delete);
        return sensorData;
    }
}
