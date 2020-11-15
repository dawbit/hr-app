package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mailing")
public class MailingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "mailing_newQuiz", nullable = false)
    private boolean mailingNewQuiz = true;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FKuserMailing")
    @JsonBackReference(value = "user-mailing")
    @JsonIgnore
    private List<UsersModel> FKuserMailing;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public boolean getMailingNewQuiz() {
        return mailingNewQuiz;
    }

    public void setMailingNewQuiz(boolean mailingNewQuiz) {
        this.mailingNewQuiz = mailingNewQuiz;
    }

    public void setFKuserMailing(List<UsersModel> FKuserMailing) {
        this.FKuserMailing = FKuserMailing;
    }

    public MailingModel() {
    }

    public MailingModel(boolean mailingNewQuiz) {
        this.mailingNewQuiz = mailingNewQuiz;
    }
}
