package com.acme.web.services.iot.application.internal.commandservices;

import com.acme.web.services.iot.application.internal.eventhandlers.FeedingScheduleCreatedEvent;
import com.acme.web.services.iot.application.internal.eventhandlers.FeedingScheduleUpdatedEvent;
import com.acme.web.services.iot.domain.model.aggregates.FeedingSchedule;
import com.acme.web.services.iot.domain.model.commands.CreateFeedingScheduleCommand;
import com.acme.web.services.iot.domain.model.commands.UpdateFeedingScheduleCommand;
import com.acme.web.services.iot.domain.model.valueobjects.FeedingTime;
import com.acme.web.services.iot.domain.services.FeedingScheduleCommandService;
import com.acme.web.services.iot.infrastructure.persitence.jpa.repositories.FeedingScheduleRepository;
import com.acme.web.services.management.domain.exceptions.CageNotFoundException;
import com.acme.web.services.management.domain.model.aggregates.Cage;
import com.acme.web.services.management.infrastructure.persitence.jpa.repositories.CageRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
@Service
public class FeedingScheduleCommandServiceImpl implements FeedingScheduleCommandService {

    private final FeedingScheduleRepository feedingScheduleRepository;
    private final CageRepository cageRepository;
    private final ApplicationEventPublisher eventPublisher;

    public FeedingScheduleCommandServiceImpl(
            FeedingScheduleRepository feedingScheduleRepository,
            CageRepository cageRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.feedingScheduleRepository = feedingScheduleRepository;
        this.cageRepository = cageRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Long handle(CreateFeedingScheduleCommand command) {
        if (command.applyToAll()) {
            List<Cage> cages = cageRepository.findAll();

            for (Cage cage : cages) {
                // evitar duplicados si ya existe horario para esa jaula
                if (feedingScheduleRepository.findByCageId(cage.getId()).isEmpty()) {
                    FeedingSchedule schedule = new FeedingSchedule(command, cage);
                    feedingScheduleRepository.save(schedule);
                    eventPublisher.publishEvent(new FeedingScheduleCreatedEvent(schedule));
                }
            }
            return 0L; // o retornar cantidad creados si deseas
        }

        // flujo normal: una jaula específica
        Cage cage = cageRepository
                .findById(command.cageId())
                .orElseThrow(() -> new CageNotFoundException(command.cageId()));

        FeedingSchedule feedingSchedule = new FeedingSchedule(command, cage);
        FeedingSchedule saved = feedingScheduleRepository.save(feedingSchedule);
        eventPublisher.publishEvent(new FeedingScheduleCreatedEvent(saved));
        return saved.getId();
    }


    @Override
    @Transactional
    public Optional<FeedingSchedule> handle(UpdateFeedingScheduleCommand command) {
        return feedingScheduleRepository.findById(command.scheduleId()).map(schedule -> {
            schedule.setMorningTime(new FeedingTime(command.morningTime()));
            schedule.setEveningTime(new FeedingTime(command.eveningTime()));

            FeedingSchedule updatedSchedule = feedingScheduleRepository.save(schedule);

            // Publicar evento para MQTT
            eventPublisher.publishEvent(new FeedingScheduleUpdatedEvent(updatedSchedule));

            return updatedSchedule;
        });
    }
}