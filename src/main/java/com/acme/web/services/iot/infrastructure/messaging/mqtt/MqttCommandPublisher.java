package com.acme.web.services.iot.infrastructure.messaging.mqtt;

import com.acme.web.services.iot.domain.model.aggregates.FeedingSchedule;
import com.acme.web.services.iot.domain.model.aggregates.SensorData;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
@Component
public class MqttCommandPublisher {

    private final MqttClient client;
    private final MqttConnectOptions options;

    public MqttCommandPublisher() throws MqttException {
        MqttClientPersistence persistence = new MemoryPersistence(); // evita carpetas basura
        this.client = new MqttClient("tcp://broker.hivemq.com:1883", "iot-app-cuy-publisher", persistence);
        this.options = new MqttConnectOptions();
        options.setCleanSession(true); // no guarda estado anterior
        options.setAutomaticReconnect(true); // reconectar automáticamente si se pierde la conexión
        options.setConnectionTimeout(10); // tiempo de espera de conexión en segundos
        client.connect(options);
    }

    private void ensureConnected() throws MqttException {
        if (!client.isConnected()) {
            client.connect(options);
        }
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
            ensureConnected();
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1); // nivel de entrega: al menos una vez
            client.publish(topic, message);
        } catch (MqttException e) {
            throw new RuntimeException("Error publishing MQTT message to topic " + topic, e);
        }
    }

    public void publishSensorDataToCage(SensorData sensorData) {
        // Validar que tenga cage
        if (sensorData.getCage() == null || sensorData.getCage().getId() == null) {
            throw new IllegalArgumentException("SensorData no tiene una jaula asociada.");
        }

        String cageId = sensorData.getCage().getId().toString();

        try {
            ensureConnected();

            if (sensorData.getTemperature() != null) {
                publishToTopic("cuys/sensores/" + cageId + "/temperatura", sensorData.getTemperature().toString());
            }
            if (sensorData.getHumidity() != null) {
                publishToTopic("cuys/sensores/" + cageId + "/humedad", sensorData.getHumidity().toString());
            }
            if (sensorData.getCO2() != null) {
                publishToTopic("cuys/sensores/" + cageId + "/co2", sensorData.getCO2().toString());
            }

            if (sensorData.getWaterQuality() != null) {
                publishToTopic("cuys/sensores/" + cageId + "/agua/calidad", sensorData.getWaterQuality().toString());
            }

            if (sensorData.getWaterQuantity() != null) {
                publishToTopic("cuys/sensores/" + cageId + "/agua", sensorData.getWaterQuantity().toString());
            }

            System.out.println("Datos del sensor publicados a MQTT para jaula " + cageId);

        } catch (MqttException e) {
            throw new RuntimeException("Error publicando SensorData a MQTT para jaula " + cageId, e);
        }
    }

    private void publishToTopic(String topic, String payload) throws MqttException {
        ensureConnected();
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);
        client.publish(topic, message);
        System.out.printf("Publicado a MQTT - Topic: %s | Payload: %s%n", topic, payload);
    }

}
