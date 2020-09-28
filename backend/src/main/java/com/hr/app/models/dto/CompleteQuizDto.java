package com.hr.app.models.dto;

import com.hr.app.models.database.TestsModel;

import java.util.Date;
import java.util.List;

public class CompleteQuizDto {

    private long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private short timeForTest;
    private boolean active;

    private List<QuestionDto> listOfQuestions;

    public CompleteQuizDto(TestsModel testsModel){
        this.id = testsModel.getId();
        this.name = testsModel.getName();
        this.startDate = testsModel.getStartDate();
        this.endDate = testsModel.getEndDate();
        this.timeForTest = testsModel.getTimeForTest();
        this.active = testsModel.isActive();
    }

    public CompleteQuizDto(TestsModel testsModel, List<QuestionDto> listOfQuestions) {
        this.id = testsModel.getId();
        this.name = testsModel.getName();
        this.startDate = testsModel.getStartDate();
        this.endDate = testsModel.getEndDate();
        this.timeForTest = testsModel.getTimeForTest();
        this.active = testsModel.isActive();
        this.listOfQuestions = listOfQuestions;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public boolean isActive() {
        return active;
    }

    public List<QuestionDto> getListOfQuestions() {
        return listOfQuestions;
    }

    public void setListOfQuestions(List<QuestionDto> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }


}
