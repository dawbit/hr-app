package com.hr.app.models.api_helpers;

public class UserQuestionResultDto {
    private String questionText;
    private int maxPoints;
    private int userPoints;
    private String answerText;

    public UserQuestionResultDto(String questionText, int maxPoints, int userPoints, String answerText) {
        this.questionText = questionText;
        this.maxPoints = maxPoints;
        this.userPoints = userPoints;
        this.answerText = answerText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
