package com.hr.app.mails;

import com.hr.app.models.database.UsersModel;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class CustomMailing {

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private ITemplateEngine templateEngine;


    public void sendNewQuizCodeMessage(String to, String nickname, String announcementName, String code) {
        if (isMailingNewQuiz(nickname)) {
            String subject = "[HR-APP][" + announcementName + "] New quiz has been assigned to your account!";
            Context context = new Context();
            context.setVariable("nickname", nickname);
            context.setVariable("announcementName", announcementName);
            context.setVariable("testCode", code);
            String text = templateEngine.process("newCode", context);
            this.emailService.sendSimpleMessage(to, subject, text);
        }
    }

    public void sendRegistrationMessage(String to, String nickname) {
        String subject = "[HR-APP] Successful registration";
        Context context = new Context();
        context.setVariable("nickname", nickname);
        String text = templateEngine.process("registration", context);
        this.emailService.sendSimpleMessage(to, subject, text);
    }

    private UsersModel getUserModel(String nickname) {
        return usersRepository.findByLogin(nickname);
    }

    private boolean isMailingNewQuiz(String nickname) {
        return this.getUserModel(nickname).getFKuserMailing().getMailingNewQuiz();
    }
}
