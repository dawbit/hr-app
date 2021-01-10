package com.hr.app.models.api_helpers;

public class SingleAnswerQuizQuestionResultDto {

    private int amountOfAnswers;
    private int answerPoints;
    private String answerText;

    public SingleAnswerQuizQuestionResultDto(int amountOfAnswers, int answerPoints, String answerText) {
        this.amountOfAnswers = amountOfAnswers;
        this.answerPoints = answerPoints;
        this.answerText = answerText;
    }

    public int getAmountOfAnswers() {
        return amountOfAnswers;
    }

    public void setAmountOfAnswers(int amountOfAnswers) {
        this.amountOfAnswers = amountOfAnswers;
    }

    public int getAnswerPoints() {
        return answerPoints;
    }

    public void setAnswerPoints(int answerPoints) {
        this.answerPoints = answerPoints;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
