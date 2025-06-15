package com.acme.web.services.iot.infrastructure.messaging.mqtt;

import com.acme.web.services.iot.domain.model.aggregates.FeedingSchedule;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
@Component
public class MqttCommandPublisher {

    private final MqttClient client;

    public MqttCommandPublisher() throws MqttException {
        this.client = new MqttClient("tcp://broker.hivemq.com:1883", MqttClient.generateClientId());
        this.client.connect();
    }

    public void publishFeedingScheduleToCage(FeedingSchedule schedule) {
        String cageId = schedule.getCage().getId().toString();
        String topic = "cuys/comandos/" + cageId;

        String payload = String.format(
                "{\"morning\": \"%s\", \"evening\": \"%s\"}",
                schedule.getMorningTime().toString(),
                schedule.getEveningTime().toString()
        );

        try {
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1); // nivel de entrega: al menos una vez
            client.publish(topic, message);
        } catch (MqttException e) {
            throw new RuntimeException("Error publishing MQTT message to topic " + topic, e);
        }
    }
}