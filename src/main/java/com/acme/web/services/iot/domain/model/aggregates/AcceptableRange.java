package com.acme.web.services.iot.domain.model.aggregates;

import com.acme.web.services.iot.domain.model.commands.CreateAcceptableRangeCommand;
import com.acme.web.services.iot.domain.model.valueobjects.*;
import com.acme.web.services.management.domain.model.aggregates.Cage;
import com.acme.web.services.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Aggregate raíz para los Rangos Aceptables de sensores por jaula.
 *
 * @author Fiorella
 * @version 1.0
 */
@Getter
@Setter
@Entity
public class AcceptableRange extends AuditableAbstractAggregateRoot<AcceptableRange> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private TemperatureRange temperatureRange;

    @Embedded
    private HumidityRange humidityRange;

    @Embedded
    private CO2Range co2Range;

    @Embedded
    private WaterQualityRange waterQualityRange;

    @Embedded
    private WaterQuantityRange waterQuantityRange;

    @OneToOne
    @JoinColumn(name = "cage_id", nullable = false, unique = true)
    private Cage cage;

    protected AcceptableRange() {
        // Constructor vacío requerido por JPA
    }

    public AcceptableRange(TemperatureRange temperatureRange,
                           HumidityRange humidityRange,
                           CO2Range co2Range,
                           WaterQualityRange waterQualityRange,
                           WaterQuantityRange waterQuantityRange,
                           Cage cage) {

        if (cage == null) throw new IllegalArgumentException("La jaula no puede ser nula");

        this.temperatureRange = temperatureRange;
        this.humidityRange = humidityRange;
        this.co2Range = co2Range;
        this.waterQualityRange = waterQualityRange;
        this.waterQuantityRange = waterQuantityRange;
        this.cage = cage;
    }

    public AcceptableRange(CreateAcceptableRangeCommand command, Cage cage) {
        this(
                new TemperatureRange(command.minTemperature(), command.maxTemperature()),
                new HumidityRange(command.minHumidity(), command.maxHumidity()),
                new CO2Range(command.minCO2(), command.maxCO2()),
                new WaterQualityRange(command.minWaterQuality(), command.maxWaterQuality()),
                new WaterQuantityRange(command.minWaterQuantity(), command.maxWaterQuantity()),
                cage
        );
    }

    public Double getMinTemperature() {
        return this.temperatureRange.minTemperature();
    }

    public Double getMaxTemperature() {
        return this.temperatureRange.maxTemperature();
    }

    public Double getMinHumidity() {
        return this.humidityRange.minHumidity();
    }

    public Double getMaxHumidity() {
        return this.humidityRange.maxHumidity();
    }

    public Integer getMinCO2() {
        return this.co2Range.minCO2();
    }

    public Integer getMaxCO2() {
        return this.co2Range.maxCO2();
    }

    public Double getMinWaterQuality() {
        return this.waterQualityRange.minWaterQuality();
    }

    public Double getMaxWaterQuality() {
        return this.waterQualityRange.maxWaterQuality();
    }

    public Double getMinWaterQuantity() {
        return this.waterQuantityRange.minWaterQuantity();
    }

    public Double getMaxWaterQuantity() {
        return this.waterQuantityRange.maxWaterQuantity();
    }
}
