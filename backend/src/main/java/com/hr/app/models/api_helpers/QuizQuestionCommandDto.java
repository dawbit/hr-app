package com.hr.app.models.api_helpers;

public class QuizQuestionCommandDto {

    private long quizid;
    private long questionnumber;
    private String testCode ;

    public long getQuizid() {
        return quizid;
    }

    public void setQuizid(long quizid) {
        this.quizid = quizid;
    }

    public long getQuestionnumber() {
        return questionnumber;
    }

    public void setQuestionnumber(long questionnumber) {
        this.questionnumber = questionnumber;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    protected QuizQuestionCommandDto() { }

    public QuizQuestionCommandDto(long quizid, long questionnumber, String testCode) {
        this.quizid = quizid;
        this.questionnumber = questionnumber;
        this.testCode = testCode;
    }
}
