package com.hr.app.models.dto;

public class MailingDto {
    private String authorMail;
    private String subject;
    private String textMessage;

    public String getAuthorMail() {
        return authorMail;
    }

    public void setAuthorMail(String authorMail) {
        this.authorMail = authorMail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    protected MailingDto(){
    }

    public MailingDto(String subject, String textMessage) {
        this.subject = subject;
        this.textMessage = textMessage;
    }

    public MailingDto(String authorMail, String subject, String textMessage) {
        this.authorMail = authorMail;
        this.subject = subject;
        this.textMessage = textMessage;
    }
}
