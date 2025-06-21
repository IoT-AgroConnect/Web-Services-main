package com.acme.web.services.iot.domain.model.aggregates;

import com.acme.web.services.iot.domain.model.commands.CreateFeedingScheduleCommand;
import com.acme.web.services.iot.domain.model.valueobjects.FeedingTime;
import com.acme.web.services.management.domain.model.aggregates.Cage;
import com.acme.web.services.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */

@Getter
@Setter
@Entity
public class FeedingSchedule extends AuditableAbstractAggregateRoot<FeedingSchedule> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "time", column = @Column(name = "morning_time"))
    })
    private FeedingTime morningTime;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "time", column = @Column(name = "evening_time"))
    })
    private FeedingTime eveningTime;

    @OneToOne
    @JoinColumn(name = "cage_id", unique = true, nullable = false)
    private Cage cage;

    public FeedingSchedule(FeedingTime morningTime, FeedingTime eveningTime, Cage cage) {
        if (cage == null) throw new IllegalArgumentException("Cage must not be null");
        this.morningTime = morningTime;
        this.eveningTime = eveningTime;
        this.cage = cage;
    }

    public FeedingSchedule(LocalTime morningTime, LocalTime eveningTime, Cage cage) {
        this(new FeedingTime(morningTime), new FeedingTime(eveningTime), cage);
    }

    public FeedingSchedule(CreateFeedingScheduleCommand command, Cage cage) {
        this(new FeedingTime(command.morningTime()), new FeedingTime(command.eveningTime()), cage);
    }

    public FeedingSchedule() {}

    public LocalTime getMorningTime() {
        return morningTime.time();
    }

    public LocalTime getEveningTime() {
        return eveningTime.time();
    }
}