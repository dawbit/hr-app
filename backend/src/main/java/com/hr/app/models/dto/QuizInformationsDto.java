package com.hr.app.models.dto;

public class QuizInformationsDto {
    private long quizId;
    private long amountOfQuestions;
    private boolean isBackPossible;

    private int responseCode;
//    1-> isactive
//    2-> isinactive
//    3-> permissionallowed
//    4-> permissiondisallowed
//    5-> badCode
//    6-> correctCode
//    7-> serverError


    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    public long getAmountOfQuestions() {
        return amountOfQuestions;
    }

    public void setAmountOfQuestions(long amountOfQuestions) {
        this.amountOfQuestions = amountOfQuestions;
    }

    public boolean isBackPossible() {
        return isBackPossible;
    }

    public void setBackPossible(boolean backPossible) {
        isBackPossible = backPossible;
    }

    public QuizInformationsDto(long quizId, long amountOfQuestions, boolean isBackPossible, int responseCode) {
        this.quizId = quizId;
        this.amountOfQuestions = amountOfQuestions;
        this.isBackPossible = isBackPossible;
        this.responseCode = responseCode;
    }

    public QuizInformationsDto(int responseCode) {
        this.responseCode = responseCode;
    }


}

