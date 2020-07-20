package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class QuestionsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "test_id", foreignKey = @ForeignKey(name = "FK_questionTest"))
    @JsonBackReference(value = "question-test")
    @JsonIgnore
    private UsersModel FK_questionTest;

    @Column(name = "text")
    private String text;

    // TODO: nazwa pliku + upload, lub plik jako byte[]
    @Lob
    @Column(name = "image")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] image;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FK_answerQuestion")
    @JsonBackReference(value = "answer-question")
    private List<AnswersModel> answers;

    @OneToMany(mappedBy = "FK_questionIduserAnswer")
    @JsonBackReference(value = "question_id_user_answer")
    private List<UserAnswersModel> userAnswers;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public UsersModel getFK_questionTest() {
        return FK_questionTest;
    }

    public String getText() {
        return text;
    }

    public byte[] getImage() {
        return image;
    }

    public List<AnswersModel> getAnswers() {
        return answers;
    }

    public List<UserAnswersModel> getUserAnswers() {
        return userAnswers;
    }

    public void setFK_questionTest(UsersModel FK_questionTest) {
        this.FK_questionTest = FK_questionTest;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setAnswers(List<AnswersModel> answers) {
        this.answers = answers;
    }

    public void setUserAnswers(List<UserAnswersModel> userAnswers) {
        this.userAnswers = userAnswers;
    }

    protected QuestionsModel() { }

    public QuestionsModel(UsersModel FK_questionTest, String text, byte[] image, List<AnswersModel> answers,
                          List<UserAnswersModel> userAnswers) {
        this.FK_questionTest = FK_questionTest;
        this.text = text;
        this.image = image;
        this.answers = answers;
        this.userAnswers = userAnswers;
    }
}
