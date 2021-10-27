package com.yago.Alkemy.service;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${email.key}")
    private String key;
    @Value("${email.sender}")
    private String fromEmail;

    public Mail createEmail(String toEmail, String subject, String body) {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content content = new Content("text/html", body);
        return new Mail(from, subject, to, content);
    }

    public String sendEmail(Mail mail) {
        SendGrid sendGrid = new SendGrid(key);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        Response response;
        try {
            request.setBody(mail.build());
            response = sendGrid.api(request);
            return response.getBody();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request.getBody();
    }
}
