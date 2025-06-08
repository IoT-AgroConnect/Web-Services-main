package com.acme.web.services.iot.domain.model.aggregates;/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

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

    public SensorData(Temperature temperature, Humidity humidity, CO2 co2, WaterQuality waterQuality, WaterQuantity waterQuantity) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
        this.waterQuality = waterQuality;
        this.waterQuantity = waterQuantity;
    }

    public SensorData() {
        // Default constructor for JPA
    }

}
