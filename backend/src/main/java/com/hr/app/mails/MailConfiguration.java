package com.hr.app.mails;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@EnableAutoConfiguration
public class MailConfiguration {
    @Configuration
    public class MailConfig {

        @Bean
        public JavaMailSender javaMailService(MailProperties mailProperties) {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(mailProperties.getHost());
            mailSender.setPort(mailProperties.getPort());

            mailSender.setUsername(mailProperties.getUsername());
            mailSender.setPassword(mailProperties.getPassword());

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            return mailSender;
        }
    }

    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        return message;
    }
}