package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "answers")
public class AnswersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference(value = "answer-question")
    @JsonIgnore
    private QuestionsModel FK_answerQuestion;

    @Column(name = "text")
    private String text;

    @Column(name = "is_correct")
    private String isCorrect;

    @Column(name = "points")
    private int points;


    @OneToMany(mappedBy = "FK_answerIduserAnswer")
    @JsonBackReference(value = "answer_id_user_answer")
    private List<UserAnswersModel> userAnswers;
}
