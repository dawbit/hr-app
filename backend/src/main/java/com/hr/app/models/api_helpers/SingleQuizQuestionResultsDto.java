package com.hr.app.models.api_helpers;

import java.util.List;

public class SingleQuizQuestionResultsDto {
    private String questionText;
    private int questionMaxPoints;
    private List<SingleAnswerQuizQuestionResultDto> singleAnswerQuizQuestionResultDtoList;
    private float averageQuizParticipantPoints;

    public SingleQuizQuestionResultsDto(String questionText, int questionMaxPoints, List<SingleAnswerQuizQuestionResultDto> singleAnswerQuizQuestionResultDtoList, float averageQuizParticipantPoints) {
        this.questionText = questionText;
        this.questionMaxPoints = questionMaxPoints;
        this.singleAnswerQuizQuestionResultDtoList = singleAnswerQuizQuestionResultDtoList;
        this.averageQuizParticipantPoints = averageQuizParticipantPoints;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getQuestionMaxPoints() {
        return questionMaxPoints;
    }

    public void setQuestionMaxPoints(int questionMaxPoints) {
        this.questionMaxPoints = questionMaxPoints;
    }

    public List<SingleAnswerQuizQuestionResultDto> getSingleAnswerQuizQuestionResultDtoList() {
        return singleAnswerQuizQuestionResultDtoList;
    }

    public void setSingleAnswerQuizQuestionResultDtoList(List<SingleAnswerQuizQuestionResultDto> singleAnswerQuizQuestionResultDtoList) {
        this.singleAnswerQuizQuestionResultDtoList = singleAnswerQuizQuestionResultDtoList;
    }

    public float getAverageQuizParticipantPoints() {
        return averageQuizParticipantPoints;
    }

    public void setAverageQuizParticipantPoints(float averageQuizParticipantPoints) {
        this.averageQuizParticipantPoints = averageQuizParticipantPoints;
    }
}
