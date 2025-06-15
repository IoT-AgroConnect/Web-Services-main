package com.acme.web.services.iot.application.internal.commandservices;

import com.acme.web.services.iot.domain.exceptions.SensorDataNotFoundException;
import com.acme.web.services.iot.domain.model.aggregates.SensorData;
import com.acme.web.services.iot.domain.model.commands.CreateSensorDataCommand;
import com.acme.web.services.iot.domain.model.commands.DeleteSensorDataCommand;
import com.acme.web.services.iot.domain.model.commands.UpdateSensorDataCommand;
import com.acme.web.services.iot.domain.model.valueobjects.*;
import com.acme.web.services.iot.domain.services.SensorDataCommandService;
import com.acme.web.services.iot.infrastructure.persitence.jpa.repositories.SensorDataRepository;
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

    public SensorDataCommandServiceImpl(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    /**
     * Crea un nuevo registro de datos de sensor en la base de datos.
     *
     * @param command Comando para crear datos de sensor
     * @return ID del nuevo registro creado
     */
    @Override
    public Long handle(CreateSensorDataCommand command) {
        var sensorData = new SensorData(
                new Temperature(command.temperature()),
                new Humidity(command.humidity()),
                new CO2(command.co2()),
                new WaterQuality(command.waterQuality()),
                new WaterQuantity(command.waterQuantity())
        );

        try {
            sensorDataRepository.save(sensorData);
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
        return sensorDataRepository.findById(command.sensorDataId()).map(sensorData -> {
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
        if (!sensorDataRepository.existsById(command.sensorDataId())) {
            throw new SensorDataNotFoundException(command.sensorDataId());
        }
        var sensorData = sensorDataRepository.findById(command.sensorDataId());
        sensorData.ifPresent(sensorDataRepository::delete);
        return sensorData;
    }
}
