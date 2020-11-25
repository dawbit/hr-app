package com.hr.app.models.api_helpers;

import java.util.List;

public class AddQuizCommandDto {

    private TestModelDto testsModel;
    private List<AddQuestionCommandDto> listOfQuestionCommandDto;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================


    public TestModelDto getTestsModel() {
        return testsModel;
    }

    public void setTestsModel(TestModelDto testsModel) {
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

    public AddQuizCommandDto(List<AddQuestionCommandDto> questionsModel, TestModelDto testsModel){
        this.listOfQuestionCommandDto=questionsModel;
        this.testsModel=testsModel;
    }


}