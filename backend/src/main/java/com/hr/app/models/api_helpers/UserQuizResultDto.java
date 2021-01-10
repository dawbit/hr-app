package com.hr.app.models.api_helpers;

import java.util.List;

public class UserQuizResultDto {
    private long quizId;
    private long userId;
    private String quizName;
    private String login;
    private int quizMaxPoints;
    private int userPoints;

    private List<UserQuestionResultDto> userQuestionResultDtoList;

    public UserQuizResultDto(long quizId, long userId, String quizName, String login, List<UserQuestionResultDto> userQuestionResultDtoList) {
        this.quizId = quizId;
        this.userId = userId;
        this.quizName = quizName;
        this.login = login;
        this.userQuestionResultDtoList = userQuestionResultDtoList;
    }

    public void sumUpPoints() {
        int quizP = 0;
        int userP = 0;
        for (UserQuestionResultDto element: userQuestionResultDtoList) {
            quizP += element.getMaxPoints();
            userP += element.getUserPoints();
        }
        this.userPoints = userP;
        this.quizMaxPoints = quizP;
    }

    public int getQuizMaxPoints() {
        return quizMaxPoints;
    }

    public void setQuizMaxPoints(int quizMaxPoints) {
        this.quizMaxPoints = quizMaxPoints;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<UserQuestionResultDto> getUserQuestionResultDtoList() {
        return userQuestionResultDtoList;
    }

    public void setUserQuestionResultDtoList(List<UserQuestionResultDto> userQuestionResultDtoList) {
        this.userQuestionResultDtoList = userQuestionResultDtoList;
    }
}
