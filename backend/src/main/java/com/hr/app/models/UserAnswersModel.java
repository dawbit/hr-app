package com.hr.app.models;

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
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user_id_user_answer")
    @JsonIgnore
    private UsersModel FK_userIduserAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference(value = "question_id_user_answer")
    @JsonIgnore
    private UsersModel FK_questionIduserAnswer;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    @JsonBackReference(value = "answer_id_user_answer")
    @JsonIgnore
    private UsersModel FK_answerIduserAnswer;

    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonBackReference(value = "test_id_user_answer")
    @JsonIgnore
    private UsersModel FK_testIduserAnswer;


    // =========================================
    // RELATIONSHIPS
    // =========================================


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public UsersModel getFK_userIduserAnswer() {
        return FK_userIduserAnswer;
    }

    public UsersModel getFK_questionIduserAnswer() {
        return FK_questionIduserAnswer;
    }

    public UsersModel getFK_answerIduserAnswer() {
        return FK_answerIduserAnswer;
    }

    public UsersModel getFK_testIduserAnswer() {
        return FK_testIduserAnswer;
    }

    public void setFK_userIduserAnswer(UsersModel FK_userIduserAnswer) {
        this.FK_userIduserAnswer = FK_userIduserAnswer;
    }

    public void setFK_questionIduserAnswer(UsersModel FK_questionIduserAnswer) {
        this.FK_questionIduserAnswer = FK_questionIduserAnswer;
    }

    public void setFK_answerIduserAnswer(UsersModel FK_answerIduserAnswer) {
        this.FK_answerIduserAnswer = FK_answerIduserAnswer;
    }

    public void setFK_testIduserAnswer(UsersModel FK_testIduserAnswer) {
        this.FK_testIduserAnswer = FK_testIduserAnswer;
    }

    protected UserAnswersModel() { }

    public UserAnswersModel(UsersModel FK_userIduserAnswer, UsersModel FK_questionIduserAnswer,
                            UsersModel FK_answerIduserAnswer, UsersModel FK_testIduserAnswer) {
        this.FK_userIduserAnswer = FK_userIduserAnswer;
        this.FK_questionIduserAnswer = FK_questionIduserAnswer;
        this.FK_answerIduserAnswer = FK_answerIduserAnswer;
        this.FK_testIduserAnswer = FK_testIduserAnswer;
    }
}
