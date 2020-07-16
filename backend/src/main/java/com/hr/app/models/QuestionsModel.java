package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class QuestionsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonBackReference(value = "question-test")
    @JsonIgnore
    private UsersModel FK_questionTest;

    @Column(name = "text")
    private String text;


    @OneToMany(mappedBy = "FK_answerQuestion")
    @JsonBackReference(value = "answer-question")
    private List<AnswersModel> answers;

    @OneToMany(mappedBy = "FK_questionIduserAnswer")
    @JsonBackReference(value = "question_id_user_answer")
    private List<UserAnswersModel> userAnswers;
}
