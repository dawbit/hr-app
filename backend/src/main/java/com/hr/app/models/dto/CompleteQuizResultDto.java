package com.hr.app.models.dto;

import com.hr.app.models.database.TestsModel;

import java.util.Date;
import java.util.List;

public class CompleteQuizResultDto {

    private long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private long timeForTest;
    private boolean active;

    private List<QuestionResultDto> listOfQuestions;

    public CompleteQuizResultDto(TestsModel testsModel){
        this.id = testsModel.getId();
        this.name = testsModel.getName();
        this.startDate = testsModel.getStartDate();
        this.endDate = testsModel.getEndDate();
        this.timeForTest = testsModel.getTimeForTestInMilis();
        this.active = testsModel.isActive();
    }

    public CompleteQuizResultDto(TestsModel testsModel, List<QuestionResultDto> listOfQuestions) {
        this.id = testsModel.getId();
        this.name = testsModel.getName();
        this.startDate = testsModel.getStartDate();
        this.endDate = testsModel.getEndDate();
        this.timeForTest = testsModel.getTimeForTestInMilis();
        this.active = testsModel.isActive();
        this.listOfQuestions = listOfQuestions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getTimeForTest() {
        return timeForTest;
    }

    public void setTimeForTest(long timeForTest) {
        this.timeForTest = timeForTest;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<QuestionResultDto> getListOfQuestions() {
        return listOfQuestions;
    }

    public void setListOfQuestions(List<QuestionResultDto> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }
}
