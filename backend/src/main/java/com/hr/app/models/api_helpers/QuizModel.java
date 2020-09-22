package com.hr.app.models.api_helpers;

import com.hr.app.models.database.TestsModel;

import java.util.List;

public class QuizModel {

    private TestsModel testsModel;
    private List<QuestionJsonModel> questionsJsonModel;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public List<QuestionJsonModel> getQuestionsModel() {
        return questionsJsonModel;
    }

    public TestsModel getTestsModel() {
        return testsModel;
    }

    public void setTestsModel(TestsModel testsModel) {
        this.testsModel = testsModel;
    }

    public void setQuestionsJsonModel(List<QuestionJsonModel> questionsJsonModel) {
        this.questionsJsonModel = questionsJsonModel;
    }

    protected QuizModel(){
    }

    public QuizModel(List<QuestionJsonModel> questionsModel, TestsModel testsModel){
        this.questionsJsonModel=questionsModel;
        this.testsModel=testsModel;
    }
}