package com.hr.app.models.api_helpers;

import com.hr.app.models.database.TestsModel;

import java.util.List;

public class AddQuizCommandDto {

    private TestsModel testsModel;
    private List<AddQuestionCommandDto> questionsJsonModel;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================


    public TestsModel getTestsModel() {
        return testsModel;
    }

    public void setTestsModel(TestsModel testsModel) {
        this.testsModel = testsModel;
    }

    public List<AddQuestionCommandDto> getQuestionsJsonModel() {
        return questionsJsonModel;
    }

    public void setQuestionsJsonModel(List<AddQuestionCommandDto> questionsJsonModel) {
        this.questionsJsonModel = questionsJsonModel;
    }

    protected AddQuizCommandDto(){
    }

    public AddQuizCommandDto(List<AddQuestionCommandDto> questionsModel, TestsModel testsModel){
        this.questionsJsonModel=questionsModel;
        this.testsModel=testsModel;
    }


}