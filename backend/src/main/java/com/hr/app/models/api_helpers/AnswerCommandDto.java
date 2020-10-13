package com.hr.app.models.api_helpers;

public class AnswerCommandDto {
    private long questionId;
    private long answerId;

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

    public AnswerCommandDto(long questionId, long answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
    }
}
