package com.acme.web.services.iot.application.internal.eventhandlers;

import com.acme.web.services.iot.infrastructure.messaging.mqtt.MqttCommandPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */

@Component
public class FeedingScheduleEventHandler {

    private final MqttCommandPublisher mqttPublisher;

    public FeedingScheduleEventHandler(MqttCommandPublisher mqttPublisher) {
        this.mqttPublisher = mqttPublisher;
    }

    @EventListener
    public void onCreated(FeedingScheduleCreatedEvent event) {
        mqttPublisher.publishFeedingScheduleToCage(event.feedingSchedule());
        System.out.println("✅ Publicado CREATE a MQTT");
    }

    @EventListener
    public void onUpdated(FeedingScheduleUpdatedEvent event) {
        mqttPublisher.publishFeedingScheduleToCage(event.feedingSchedule());
        System.out.println("✅ Publicado UPDATE a MQTT");
    }
}