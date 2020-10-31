package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "test_participant")
public class TestParticipantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "test_id", foreignKey = @ForeignKey(name = "FKtestCodetest"))
    @JsonBackReference(value = "test-id")
    @JsonIgnore
    private TestsModel FKtestCodetest;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FKtestCodeuser"))
    @JsonBackReference(value = "user-id")
    @JsonIgnore
    private UsersModel FKtestCodeuser;

    @Column(name = "test_code")
    private String code;

    @Column(name = "currentQuestionNumber")
    private long questionNumber = 1;

    @Column(name = "start_quiz_time_in_milis")
    private long startQuizTimeInMilis = 0;

    @ManyToOne
    @JoinColumn(name = "announcement_id", foreignKey = @ForeignKey(name = "FKtestAnnouncement"))
    @JsonBackReference(value = "testParticipant-announcement")
    @JsonIgnore
    private AnnouncementsModel FKtestAnnouncement;

    @Column(name = "read")
    private Boolean read = false;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FKhrAlertTestParticipant")
    @JsonBackReference(value = "hralert-testParticipant")
    private List<HrAlertModel> hrAlertModels;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getStartQuizTimeInMilis() {
        return startQuizTimeInMilis;
    }

    public void setStartQuizTimeInMilis(long startQuizTimeInMilis) {
        this.startQuizTimeInMilis = startQuizTimeInMilis;
    }

    public long getId() {
        return id;
    }

    public TestsModel getFKtestCodetest() {
        return FKtestCodetest;
    }

    public void setFKtestCodetest(TestsModel FKtestCodetest) {
        this.FKtestCodetest = FKtestCodetest;
    }

    public UsersModel getFKtestCodeuser() {
        return FKtestCodeuser;
    }

    public void setFKtestCodeuser(UsersModel FKtestCodeuser) {
        this.FKtestCodeuser = FKtestCodeuser;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(long questionNumber) {
        this.questionNumber = questionNumber;
    }

    public AnnouncementsModel getFKtestAnnouncement() {
        return FKtestAnnouncement;
    }

    public void setFKtestAnnouncement(AnnouncementsModel FKtestAnnouncement) {
        this.FKtestAnnouncement = FKtestAnnouncement;
    }

    public Boolean isRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public List<HrAlertModel> getHrAlertModels() {
        return hrAlertModels;
    }

    public void setHrAlertModels(List<HrAlertModel> hrAlertModels) {
        this.hrAlertModels = hrAlertModels;
    }

    protected TestParticipantModel(){};

    public TestParticipantModel(TestsModel FKtestCodetest, UsersModel FKtestCodeuser) {
        this.FKtestCodetest = FKtestCodetest;
        this.FKtestCodeuser = FKtestCodeuser;
    }

    public TestParticipantModel(long id, TestsModel FKtestCodetest, UsersModel FKtestCodeuser, String code,
                                long questionNumber, long startQuizTimeInMilis) {
        this.id = id;
        this.FKtestCodetest = FKtestCodetest;
        this.FKtestCodeuser = FKtestCodeuser;
        this.code = code;
        this.questionNumber = questionNumber;
        this.startQuizTimeInMilis = startQuizTimeInMilis;
    }

    public TestParticipantModel(long id, TestsModel FKtestCodetest, UsersModel FKtestCodeuser, String code,
                                long questionNumber, long startQuizTimeInMilis, AnnouncementsModel FKtestAnnouncement,
                                Boolean read) {
        this.id = id;
        this.FKtestCodetest = FKtestCodetest;
        this.FKtestCodeuser = FKtestCodeuser;
        this.code = code;
        this.questionNumber = questionNumber;
        this.startQuizTimeInMilis = startQuizTimeInMilis;
        this.FKtestAnnouncement = FKtestAnnouncement;
        this.read = read;
    }

    public TestParticipantModel(long id, TestsModel FKtestCodetest, UsersModel FKtestCodeuser, String code,
                                long questionNumber, long startQuizTimeInMilis, AnnouncementsModel FKtestAnnouncement,
                                Boolean read, List<HrAlertModel> hrAlertModels) {
        this.id = id;
        this.FKtestCodetest = FKtestCodetest;
        this.FKtestCodeuser = FKtestCodeuser;
        this.code = code;
        this.questionNumber = questionNumber;
        this.startQuizTimeInMilis = startQuizTimeInMilis;
        this.FKtestAnnouncement = FKtestAnnouncement;
        this.read = read;
        this.hrAlertModels = hrAlertModels;
    }
}
