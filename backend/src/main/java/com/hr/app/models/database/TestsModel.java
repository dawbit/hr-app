package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tests")
public class TestsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_hr_id", foreignKey = @ForeignKey(name = "FKtestUserHr"))
    @JsonBackReference(value = "test-user-hr")
    @JsonIgnore
    private UsersModel FKtestUserHr;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FKtestCompany"))
    @JsonBackReference(value = "test-company")
    @JsonIgnore
    private CompaniesModel FKtestCompany;

    @Column(name = "is_possible_to_back")
    @JsonBackReference(value = "is_possible_to_back")
    private boolean isPossibleToBack;

    @Column(name = "is_open_for_everyone")
    @JsonBackReference(value = "is_open_for_everyone")
    private boolean isOpenForEveryone;

    @ManyToOne
    @JoinColumn(name = "test_type_id", foreignKey = @ForeignKey(name = "FKtestCodetest"))
    @JsonBackReference(value = "test-type")
    @JsonIgnore
    private TestTypeModel FKtestType;

    @Column(name = "is_active")
    @JsonBackReference(value = "is_active")
    private boolean isActive;

    @Column(name = "start_date")
    @FutureOrPresent
    private Date startDate;

    @Column(name = "end_date")
    @Future
    private Date endDate;

    @Column(name = "time_for_test_in_milis", nullable = false)
    @Positive
    private long timeForTestInMilis;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FKquestionTest")
    @JsonBackReference(value = "questionTest")
    private List<QuestionsModel> questions;

    @OneToMany(mappedBy = "FKtestIduserAnswer")
    @JsonBackReference(value = "test_id_user_answer")
    private List<UserAnswersModel> userAnswers;

    @OneToMany(mappedBy = "FKtestCodeuser")
    @JsonBackReference(value = "test-id")
    private List<TestParticipantModel> FKtestCodeuser;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================



    public void setFKtestCodeuser(List<TestParticipantModel> FKtestCodeuser) {
        this.FKtestCodeuser = FKtestCodeuser;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UsersModel getFKtestUserHr() {
        return FKtestUserHr;
    }

    public CompaniesModel getFKtestCompany() {
        return FKtestCompany;
    }

    public boolean isPossibleToBack() {
        return isPossibleToBack;
    }

    public TestTypeModel getFKtestType() {
        return FKtestType;
    }

    public boolean isActive() {
        return isActive;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public long getTimeForTestInMilis() {
        return timeForTestInMilis;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFKtestUserHr(UsersModel FKtestUserHr) {
        this.FKtestUserHr = FKtestUserHr;
    }

    public void setFKtestCompany(CompaniesModel FKtestCompany) {
        this.FKtestCompany = FKtestCompany;
    }

    public void setPossibleToBack(boolean possibleToBack) {
        isPossibleToBack = possibleToBack;
    }

    public void setFKtestType(TestTypeModel FKtestType) {
        this.FKtestType = FKtestType;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFKtest(List<TestParticipantModel> FKtestCodeuser) {
        this.FKtestCodeuser = FKtestCodeuser;
    }

    public void setTimeForTest(long timeForTestInMilis) {
        this.timeForTestInMilis = timeForTestInMilis;
    }

    public void setQuestions(List<QuestionsModel> questions) {
        this.questions = questions;
    }

    public void setUserAnswers(List<UserAnswersModel> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public boolean isOpenForEveryone() {
        return isOpenForEveryone;
    }

    public void setOpenForEveryone(boolean openForEveryone) {
        isOpenForEveryone = openForEveryone;
    }

    protected TestsModel() {}



    public TestsModel(String name, UsersModel FKtestUserHr, CompaniesModel FKtestCompany, boolean isPossibleToBack,
                      boolean isActive, Date startDate, Date endDate, long timeForTestInMilis, List<QuestionsModel> questions,
                      List<UserAnswersModel> userAnswers, boolean isOpenForEveryone) {
        this.name = name;
        this.FKtestUserHr = FKtestUserHr;
        this.FKtestCompany = FKtestCompany;
        this.isPossibleToBack = isPossibleToBack;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeForTestInMilis = timeForTestInMilis;
        this.questions = questions;
        this.userAnswers = userAnswers;
        this.isOpenForEveryone=isOpenForEveryone;
    }
}
