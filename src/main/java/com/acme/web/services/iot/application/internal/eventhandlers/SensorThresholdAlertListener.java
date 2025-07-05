package com.acme.web.services.iot.application.internal.eventhandlers;

import com.acme.web.services.alerts.SendGridEmailService;
import com.acme.web.services.iot.domain.model.aggregates.SensorData;
import com.acme.web.services.iot.infrastructure.persitence.jpa.repositories.AcceptableRangeRepository;
import com.acme.web.services.management.domain.model.aggregates.Cage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener for sensor data events that checks if the sensor readings
 *
 * @author Fiorella Jarama Peñaloza
 */

@Component
public class SensorThresholdAlertListener {

    private final AcceptableRangeRepository acceptableRangeRepository;
    private final SendGridEmailService emailService;

    public SensorThresholdAlertListener(AcceptableRangeRepository acceptableRangeRepository,
                                        SendGridEmailService emailService) {
        this.acceptableRangeRepository = acceptableRangeRepository;
        this.emailService = emailService;
    }

    @EventListener
    public void onSensorDataCreated(SensorDataCreatedEvent event) {
        SensorData data = event.sensorData();
        Cage cage = data.getCage();

        var optionalRange = acceptableRangeRepository.findByCageId(cage.getId());
        if (optionalRange.isEmpty()) return;

        var range = optionalRange.get();

        StringBuilder alertMessage = new StringBuilder();
        alertMessage.append("⚠️ Se detectaron valores fuera de rango en la jaula #" + cage.getId() + ":\n");

        boolean alert = false;

        if (data.getTemperature() < range.getMinTemperature()) {
            alertMessage.append("• Temperatura demasiado baja: ").append(data.getTemperature()).append("°C\n");
            alert = true;
        } else if (data.getTemperature() > range.getMaxTemperature()) {
            alertMessage.append("• Temperatura demasiado alta: ").append(data.getTemperature()).append("°C\n");
            alert = true;
        }

        if (data.getHumidity() < range.getMinHumidity()) {
            alertMessage.append("• Humedad demasiado baja: ").append(data.getHumidity()).append("%\n");
            alert = true;
        } else if (data.getHumidity() > range.getMaxHumidity()) {
            alertMessage.append("• Humedad demasiado alta: ").append(data.getHumidity()).append("%\n");
            alert = true;
        }

        if (data.getCO2() > range.getMaxCO2()) {
            alertMessage.append("• Nivel de CO2 alto: ").append(data.getCO2()).append(" ppm\n");
            alert = true;
        }

        if (data.getWaterQuality() < range.getMinWaterQuality()) {
            alertMessage.append("• Calidad de agua baja: ").append(data.getWaterQuality()).append("\n");
            alert = true;
        }

        if (data.getWaterQuantity() < range.getMinWaterQuantity()) {
            alertMessage.append("• Cantidad de agua baja: ").append(data.getWaterQuantity()).append("\n");
            alert = true;
        }

        if (alert) {
            String email = cage.getBreeder().getUser().getEmail();
            try {
                emailService.sendEmail(email, "⚠️ Alerta de sensores en tu jaula", alertMessage.toString());
                System.out.println("✅ Correo enviado a: " + email);
                System.out.println("Contenido:\n" + alertMessage);
            } catch (Exception e) {
                System.err.println("❌ Error al enviar correo a " + email + ": " + e.getMessage());
            }
        }
    }


}