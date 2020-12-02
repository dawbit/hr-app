package com.hr.app.models.api_helpers;

import java.util.List;

public class SingleQuizResultDto {
    private String quizName;
    private List<SingleQuizQuestionResultsDto> singleQuizQuestionResultsDtoList;

    public SingleQuizResultDto(String quizName, List<SingleQuizQuestionResultsDto> singleQuizQuestionResultsDtoList) {
        this.quizName = quizName;
        this.singleQuizQuestionResultsDtoList = singleQuizQuestionResultsDtoList;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public List<SingleQuizQuestionResultsDto> getSingleQuizQuestionResultsDtoList() {
        return singleQuizQuestionResultsDtoList;
    }

    public void setSingleQuizQuestionResultsDtoList(List<SingleQuizQuestionResultsDto> singleQuizQuestionResultsDtoList) {
        this.singleQuizQuestionResultsDtoList = singleQuizQuestionResultsDtoList;
    }
}
