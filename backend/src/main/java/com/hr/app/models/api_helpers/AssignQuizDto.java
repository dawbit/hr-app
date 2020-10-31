package com.hr.app.models.api_helpers;

public class AssignQuizDto {
//    long alertId;
    String testName;
    String testCode;
//    long currentQuestionNumber;
//    long startQuizTime;
    long testId;
    long userId;
    Boolean read;
    long announcementId;

    public String getTestName() {
        return testName;
    }

    public String getTestCode() {
        return testCode;
    }

    public long getTestId() {
        return testId;
    }

    public long getUserId() {
        return userId;
    }

    public Boolean getRead() {
        return read;
    }

    public long getAnnouncementId() {
        return announcementId;
    }
}
