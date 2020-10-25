package com.hr.app.models.dto;

import com.hr.app.enums.ResponseEnum;
import com.hr.app.enums.ResponseEnumOperations;

public class QuizCodeDto {

    private int responseCode;

    protected QuizCodeDto() {
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public QuizCodeDto(ResponseEnum responseEnum) {
        this.responseCode = ResponseEnumOperations.getResponseStatusInt(responseEnum);
    }
}
