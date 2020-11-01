package com.hr.app.models.api_helpers;

public class AnswerCommandDto {
    private long questionId;
    private long answerId;
    private String testCode;
    private long questionNumber;

    public long getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(long questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public AnswerCommandDto(long questionId, long answerId, String testCode, long questionNumber) {
        this.questionId = questionId;
        this.answerId = answerId;
        this.testCode = testCode;
        this.questionNumber = questionNumber;
    }
}
