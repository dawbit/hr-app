package com.hr.app.models.dto;

import com.hr.app.enums.ResponseEnum;

public class QuizInformationsResultExtendsDto extends QuizInformationsResultDto {

    private String quizName;
    private long companyId;
    private long userHrId;


    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getUserHrId() {
        return userHrId;
    }

    public void setUserHrId(long userHrId) {
        this.userHrId = userHrId;
    }

    public QuizInformationsResultExtendsDto(long quizId, long amountOfQuestions, boolean isBackPossible,
                                            long timeForTestInMilis, ResponseEnum responseEnum, String quizName,
                                            long companyId, long userHrId) {
        super(quizId, amountOfQuestions, isBackPossible, timeForTestInMilis, responseEnum);
        this.quizName = quizName;
        this.companyId = companyId;
        this.userHrId = userHrId;
    }

    public QuizInformationsResultExtendsDto(long quizId, String quizName, long companyId, long userHrId) {
        super(quizId);
        this.quizName = quizName;
        this.companyId = companyId;
        this.userHrId = userHrId;
    }

}
