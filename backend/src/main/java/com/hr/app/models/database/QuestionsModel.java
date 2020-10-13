package com.hr.app.models.database;

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
    @JoinColumn(name = "test_id", foreignKey = @ForeignKey(name = "FKquestionTest"))
    @JsonBackReference(value = "question-test")
    @JsonIgnore
    private TestsModel FKquestionTest;

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

    @OneToMany(mappedBy = "FKanswerQuestion")
    @JsonBackReference(value = "answer-question")
    private List<AnswersModel> answers;

    @OneToMany(mappedBy = "FKquestionIduserAnswer")
    @JsonBackReference(value = "question_id_user_answer")
    private List<UserAnswersModel> userAnswers;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public TestsModel getFKquestionTest() {
        return FKquestionTest;
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

    public void setFKquestionTest(TestsModel FKquestionTest) {
        this.FKquestionTest = FKquestionTest;
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

    public QuestionsModel(TestsModel FKquestionTest, String text, byte[] image, List<AnswersModel> answers,
                          List<UserAnswersModel> userAnswers) {
        this.FKquestionTest = FKquestionTest;
        this.text = text;
        this.image = image;
        this.answers = answers;
        this.userAnswers = userAnswers;
    }
}
