package com.hr.app.models.api_helpers;

import com.hr.app.models.database.TestsModel;

import java.util.List;

public class AddQuizCommandDto {

    private TestsModel testsModel;
    private List<AddQuestionCommandDto> listOfQuestionCommandDto;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================


    public TestsModel getTestsModel() {
        return testsModel;
    }

    public void setTestsModel(TestsModel testsModel) {
        this.testsModel = testsModel;
    }

    public List<AddQuestionCommandDto> getListOfQuestionCommandDto() {
        return listOfQuestionCommandDto;
    }

    public void setListOfQuestionCommandDto(List<AddQuestionCommandDto> questionsJsonModel) {
        this.listOfQuestionCommandDto = questionsJsonModel;
    }

    protected AddQuizCommandDto(){
    }

    public AddQuizCommandDto(List<AddQuestionCommandDto> questionsModel, TestsModel testsModel){
        this.listOfQuestionCommandDto=questionsModel;
        this.testsModel=testsModel;
    }


}