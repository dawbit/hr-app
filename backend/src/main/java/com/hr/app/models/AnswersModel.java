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
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "FK_answerQuestion"))
    @JsonBackReference(value = "answer-question")
    @JsonIgnore
    private QuestionsModel FK_answerQuestion;

    @Column(name = "text")
    private String text;

    @Column(name = "is_correct")
    private String isCorrect;

    @Column(name = "points")
    private int points;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FK_answerIduserAnswer")
    @JsonBackReference(value = "answer_id_user_answer")
    private List<UserAnswersModel> userAnswers;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================


    public long getId() {
        return id;
    }

    public QuestionsModel getFK_answerQuestion() {
        return FK_answerQuestion;
    }

    public String getText() {
        return text;
    }

    public String getIsCorrect() {
        return isCorrect;
    }

    public int getPoints() {
        return points;
    }

    public List<UserAnswersModel> getUserAnswers() {
        return userAnswers;
    }

    public void setFK_answerQuestion(QuestionsModel FK_answerQuestion) {
        this.FK_answerQuestion = FK_answerQuestion;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setUserAnswers(List<UserAnswersModel> userAnswers) {
        this.userAnswers = userAnswers;
    }

    protected AnswersModel() { }

    public AnswersModel(QuestionsModel FK_answerQuestion, String text, String isCorrect,
                        int points, List<UserAnswersModel> userAnswers) {
        this.FK_answerQuestion = FK_answerQuestion;
        this.text = text;
        this.isCorrect = isCorrect;
        this.points = points;
        this.userAnswers = userAnswers;
    }
}
