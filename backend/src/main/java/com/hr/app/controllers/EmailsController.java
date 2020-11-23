package com.hr.app.controllers;

import com.hr.app.mails.EmailServiceImpl;
import com.hr.app.models.dto.MailingDto;
import com.hr.app.models.dto.ResponseTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@CrossOrigin
@RestController
public class EmailsController {

    private final String serviceUrlParam = "/email";

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping(serviceUrlParam + "/send")
    public Object sendMail(@RequestBody MailingDto mailingDto, HttpServletResponse response){
        try {
            // mailing with CC to Author
            emailService.sendSimpleMessage("contact.hrapp@gmail.com", "[CONTACT] " + mailingDto.getSubject(),
                    mailingDto.getTextMessage(), mailingDto.getAuthorMail());
            return new ResponseTransfer("E-mail sent");
        } catch (Error e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            return new ResponseTransfer("E-mail not sent");
        }
    }
}
