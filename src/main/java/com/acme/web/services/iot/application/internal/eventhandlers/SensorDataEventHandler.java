package com.acme.web.services.iot.application.internal.eventhandlers;

import com.acme.web.services.iot.infrastructure.messaging.mqtt.MqttCommandPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Clase que maneja eventos relacionados con la creación de datos de sensores.
 * Publica la información del sensor al topic MQTT correspondiente.
 *
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

@Component
public class SensorDataEventHandler {

    private final MqttCommandPublisher mqttPublisher;

    public SensorDataEventHandler(MqttCommandPublisher mqttPublisher) {
        this.mqttPublisher = mqttPublisher;
    }

    @EventListener
    public void onCreated(SensorDataCreatedEvent event) {
        mqttPublisher.publishSensorDataToCage(event.sensorData());
        System.out.println("✅ Publicado CREATE a MQTT");
    }
}
