package com.hr.app.models.api_helpers;

import java.util.List;

public class AddQuestionCommandDto {

    private long testId;
    private QuestionModelDto questionsModel;
    private List<AnswerModelDto> answersModel;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public QuestionModelDto getQuestionsModel() {
        return questionsModel;
    }

    public void setQuestionsModel(QuestionModelDto questionsModel) {
        this.questionsModel = questionsModel;
    }

    public List<AnswerModelDto> getAnswersModel() {
        return answersModel;
    }

    public void setAnswersModel(List<AnswerModelDto> answersModel) {
        this.answersModel = answersModel;
    }

    protected AddQuestionCommandDto(){
    }

    public AddQuestionCommandDto(List<AnswerModelDto> answersModel, QuestionModelDto questionsModel){
        this.answersModel = answersModel;
        this.questionsModel = questionsModel;
    }
}
