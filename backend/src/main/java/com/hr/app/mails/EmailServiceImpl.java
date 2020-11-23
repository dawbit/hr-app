package com.hr.app.mails;

import com.hr.app.models.dto.ResponseTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private Environment env;

    @Autowired
    private JavaMailSender emailSender;

    /*
     * mailing via application
     */
    public void sendSimpleMessage(String to, String subject, String text, String cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    /*
    * automailing
    */
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        MimeMessage mail = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setFrom(env.getProperty("spring.mail.username"));
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (Exception e){ }
        emailSender.send(mail);
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment)
            throws MessagingException {
    }
}