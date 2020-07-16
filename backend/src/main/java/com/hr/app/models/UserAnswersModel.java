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
}
