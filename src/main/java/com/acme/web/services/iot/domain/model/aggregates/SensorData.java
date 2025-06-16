package com.acme.web.services.iot.domain.model.aggregates;

import com.acme.web.services.iot.domain.model.commands.CreateSensorDataCommand;
import com.acme.web.services.iot.domain.model.valueobjects.*;
import com.acme.web.services.management.domain.model.aggregates.Cage;
import com.acme.web.services.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "cage_id")
    private Cage cage;

    public SensorData() {
        // Default constructor for JPA
    }

    public SensorData(Temperature temperature, Humidity humidity, CO2 co2, WaterQuality waterQuality, WaterQuantity waterQuantity, Cage cage) {
        if (cage == null) throw new IllegalArgumentException("Cage must not be null");
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
        this.waterQuality = waterQuality;
        this.waterQuantity = waterQuantity;
        this.cage = cage;
    }

    // Command-based constructor ahora también requiere una Cage
    public SensorData(CreateSensorDataCommand command, Cage cage) {
        if (cage == null) throw new IllegalArgumentException("Cage must not be null");
        this.temperature = new Temperature(command.temperature());
        this.humidity = new Humidity(command.humidity());
        this.co2 = new CO2(command.co2());
        this.waterQuality = new WaterQuality(command.waterQuality());
        this.waterQuantity = new WaterQuantity(command.waterQuantity());
        this.cage = cage;
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
