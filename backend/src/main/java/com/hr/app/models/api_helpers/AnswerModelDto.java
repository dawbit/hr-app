package com.hr.app.models.api_helpers;

public class AnswerModelDto {

    private String text;

    private boolean isCorrect;

    private int points;

    public AnswerModelDto(String text, boolean isCorrect, int points) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.points = points;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
