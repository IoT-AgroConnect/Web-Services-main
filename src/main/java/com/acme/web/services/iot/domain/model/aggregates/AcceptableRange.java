package com.acme.web.services.iot.domain.model.aggregates;

import com.acme.web.services.management.domain.model.aggregates.Cage;
import com.acme.web.services.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Descripción de la clase.
 *
 * @author Fiorella Jarama Peñaloza
 *
 */
@Getter
@Setter
@Entity
public class AcceptableRange extends AuditableAbstractAggregateRoot<AcceptableRange> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Asociación con la jaula
    @OneToOne
    @JoinColumn(name = "cage_id", nullable = false, unique = true)
    private Cage cage;

    // Rango de temperatura
    private Double minTemperature;
    private Double maxTemperature;

    // Rango de humedad
    private Double minHumidity;
    private Double maxHumidity;

    // Rango de CO2
    private Integer minCO2;
    private Integer maxCO2;

    // Rango de calidad de agua
    private Double minWaterQuality;
    private Double maxWaterQuality;

    // Rango de cantidad de agua
    private Double minWaterQuantity;
    private Double maxWaterQuantity;

    // Constructor vacío para JPA
    public AcceptableRange() {}

    public AcceptableRange(Cage cage,
                           Double minTemperature, Double maxTemperature,
                           Double minHumidity, Double maxHumidity,
                           Integer minCO2, Integer maxCO2,
                           Double minWaterQuality, Double maxWaterQuality,
                           Double minWaterQuantity, Double maxWaterQuantity) {
        if (cage == null) throw new IllegalArgumentException("La jaula no puede ser nula.");
        this.cage = cage;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
        this.minCO2 = minCO2;
        this.maxCO2 = maxCO2;
        this.minWaterQuality = minWaterQuality;
        this.maxWaterQuality = maxWaterQuality;
        this.minWaterQuantity = minWaterQuantity;
        this.maxWaterQuantity = maxWaterQuantity;
    }
}

