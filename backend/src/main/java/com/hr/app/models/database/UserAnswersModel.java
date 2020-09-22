package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user_answers")
public class UserAnswersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FKuserIduserAnswer"))
    @JsonBackReference(value = "user_id_user_answer")
    @JsonIgnore
    private UsersModel FKuserIduserAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "FKquestionIduserAnswer"))
    @JsonBackReference(value = "question_id_user_answer")
    @JsonIgnore
    private UsersModel FKquestionIduserAnswer;

    @ManyToOne
    @JoinColumn(name = "answer_id", foreignKey = @ForeignKey(name = "FKanswerIduserAnswer"))
    @JsonBackReference(value = "answer_id_user_answer")
    @JsonIgnore
    private UsersModel FKanswerIduserAnswer;

    @ManyToOne
    @JoinColumn(name = "test_id", foreignKey = @ForeignKey(name = "FKtestIduserAnswer"))
    @JsonBackReference(value = "test_id_user_answer")
    @JsonIgnore
    private UsersModel FKtestIduserAnswer;


    // =========================================
    // RELATIONSHIPS
    // =========================================


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public UsersModel getFKuserIduserAnswer() {
        return FKuserIduserAnswer;
    }

    public UsersModel getFKquestionIduserAnswer() {
        return FKquestionIduserAnswer;
    }

    public UsersModel getFKanswerIduserAnswer() {
        return FKanswerIduserAnswer;
    }

    public UsersModel getFKtestIduserAnswer() {
        return FKtestIduserAnswer;
    }

    public void setFKuserIduserAnswer(UsersModel FKuserIduserAnswer) {
        this.FKuserIduserAnswer = FKuserIduserAnswer;
    }

    public void setFKquestionIduserAnswer(UsersModel FKquestionIduserAnswer) {
        this.FKquestionIduserAnswer = FKquestionIduserAnswer;
    }

    public void setFKanswerIduserAnswer(UsersModel FKanswerIduserAnswer) {
        this.FKanswerIduserAnswer = FKanswerIduserAnswer;
    }

    public void setFKtestIduserAnswer(UsersModel FKtestIduserAnswer) {
        this.FKtestIduserAnswer = FKtestIduserAnswer;
    }

    protected UserAnswersModel() { }

    public UserAnswersModel(UsersModel FKuserIduserAnswer, UsersModel FKquestionIduserAnswer,
                            UsersModel FKanswerIduserAnswer, UsersModel FKtestIduserAnswer) {
        this.FKuserIduserAnswer = FKuserIduserAnswer;
        this.FKquestionIduserAnswer = FKquestionIduserAnswer;
        this.FKanswerIduserAnswer = FKanswerIduserAnswer;
        this.FKtestIduserAnswer = FKtestIduserAnswer;
    }
}
