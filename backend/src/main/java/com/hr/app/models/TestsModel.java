package com.hr.app.models;

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
    private boolean isPossibleToBack;

    @Column(name = "test_code", nullable = false)
    private String testCode;

    @Column(name = "test_type", nullable = false)
    private String testType;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "start_date")
    @FutureOrPresent
    private Date startDate;

    @Column(name = "end_date")
    @Future
    private Date endDate;

    @Column(name = "time_for_test", nullable = false)
    @Positive
    @Max(value = 32700)
    private short timeForTest;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FKquestionTest")
    @JsonBackReference(value = "questionTest")
    private List<QuestionsModel> questions;

    @OneToMany(mappedBy = "FKtestIduserAnswer")
    @JsonBackReference(value = "test_id_user_answer")
    private List<UserAnswersModel> userAnswers;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

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

    public String getTestCode() {
        return testCode;
    }

    public String getTestType() {
        return testType;
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

    public short getTimeForTest() {
        return timeForTest;
    }

    public List<QuestionsModel> getQuestions() {
        return questions;
    }

    public List<UserAnswersModel> getUserAnswers() {
        return userAnswers;
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

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public void setTestType(String testType) {
        this.testType = testType;
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

    public void setTimeForTest(short timeForTest) {
        this.timeForTest = timeForTest;
    }

    public void setQuestions(List<QuestionsModel> questions) {
        this.questions = questions;
    }

    public void setUserAnswers(List<UserAnswersModel> userAnswers) {
        this.userAnswers = userAnswers;
    }

    protected TestsModel() {}

    public TestsModel(String name, UsersModel FKtestUserHr, CompaniesModel FKtestCompany, boolean isPossibleToBack,
                      String testCode, String testType, boolean isActive, Date startDate, Date endDate,
                      short timeForTest, List<QuestionsModel> questions, List<UserAnswersModel> userAnswers) {
        this.name = name;
        this.FKtestUserHr = FKtestUserHr;
        this.FKtestCompany = FKtestCompany;
        this.isPossibleToBack = isPossibleToBack;
        this.testCode = testCode;
        this.testType = testType;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeForTest = timeForTest;
        this.questions = questions;
        this.userAnswers = userAnswers;
    }
}
