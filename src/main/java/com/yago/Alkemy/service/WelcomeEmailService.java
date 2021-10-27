package com.yago.Alkemy.service;

import com.sendgrid.Mail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WelcomeEmailService {

    @Value("${base.url}")
    String baseUrl;

    private EmailService emailService;

    public WelcomeEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public String sendWelcomeEmail(String toEmail, String activationToken){
        return emailService.sendEmail(createWelcomeEmail(toEmail, activationToken));
    }

    private Mail createWelcomeEmail(String toEmail, String activationToken){
        String url = baseUrl+"/auth/activate/"+activationToken;
        String body = "You have successfully registered on my Alkemy Challenge!\n" +
                "To activate your account <html><a href=\""+url+"\">click here</a></html>";
        return emailService.createEmail(toEmail, "Welcome!!", body);
    }
}
