package com.hr.app.models;

import java.util.List;

public class QuestionJsonModel {
    private long testId;
    private QuestionsModel questionsModel;
    private List<AnswersModel> answersModel;

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public QuestionsModel getQuestionsModel() {
        return questionsModel;
    }

    public void setQuestionsModel(QuestionsModel questionsModel) {
        this.questionsModel = questionsModel;
    }

    public List<AnswersModel> getAnswersModel() {
        return answersModel;
    }

    public void setAnswersModel(List<AnswersModel> answersModel) {
        this.answersModel = answersModel;
    }

    protected QuestionJsonModel(){
    }

    public QuestionJsonModel(List<AnswersModel> answersModel, QuestionsModel questionsModel){
        this.answersModel = answersModel;
        this.questionsModel = questionsModel;
    }
}
