package com.acme.web.services.iot.domain.model.aggregates;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

import com.acme.web.services.iot.domain.model.commands.CreateSensorDataCommand;
import com.acme.web.services.iot.domain.model.valueobjects.*;
import com.acme.web.services.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Descripción de la clase.
 *
 * @author Fiorella Jarama Peñaloza
 */
@Getter
@Entity
public class SensorData extends AuditableAbstractAggregateRoot<SensorData> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Embedded
    private Temperature temperature;

    @Setter
    @Embedded
    private Humidity humidity;

    @Setter
    @Embedded
    private CO2 co2;

    @Setter
    @Embedded
    private WaterQuality waterQuality;

    @Setter
    @Embedded
    private WaterQuantity waterQuantity;

    public SensorData() {
        // Default constructor for JPA
    }

    public SensorData(Temperature temperature, Humidity humidity, CO2 co2, WaterQuality waterQuality, WaterQuantity waterQuantity) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
        this.waterQuality = waterQuality;
        this.waterQuantity = waterQuantity;
    }

    public SensorData(CreateSensorDataCommand command) {
        this.temperature = new Temperature(command.temperature());
        this.humidity = new Humidity(command.humidity());
        this.co2 = new CO2(command.co2());
        this.waterQuality = new WaterQuality(command.waterQuality());
        this.waterQuantity = new WaterQuantity(command.waterQuantity());
    }

    public Double getTemperature() {
       return this.temperature.temperature();
    }

    public Double getHumidity() {
        return this.humidity.humidity();
    }

    public Integer getCO2() {
        return this.co2.co2();
    }

    public Double getWaterQuality() {
        return this.waterQuality.waterQuality();
    }

    public Double getWaterQuantity() {
        return this.waterQuantity.waterQuantity();
    }

}
