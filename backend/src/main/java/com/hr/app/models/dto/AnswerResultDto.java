package com.hr.app.models.dto;

import com.hr.app.models.database.AnswersModel;

public class AnswerResultDto {

    private long id;
    private String text;

    public AnswerResultDto(AnswersModel answersModel){
        this.id = answersModel.getId();
        this.text = answersModel.getText();
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
