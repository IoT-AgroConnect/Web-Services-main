package com.acme.web.services.alerts;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
/**
 * Service for sending emails using SendGrid.
 *
 * @author Fiorella Jarama Peñaloza
 */
@Service
public class SendGridEmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public void sendEmail(String to, String subject, String contentText) throws IOException {
        Email from = new Email("alertas.agrocuy@outlook.com");
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", contentText);
        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        System.out.println("📨 SendGrid status code: " + response.getStatusCode());
        System.out.println("📨 SendGrid body: " + response.getBody());
        System.out.println("📨 SendGrid headers: " + response.getHeaders());
    }

}