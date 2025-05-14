package com.acme.web.services.appointment.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class CreateNotificationByAppointmentCreated extends ApplicationEvent {

    private final Long breederId;
    private final Long advisorId;

    public CreateNotificationByAppointmentCreated(Object source, Long breederId, Long advisorId) {
        super(source);
        this.breederId = breederId;
        this.advisorId = advisorId;
    }

}

