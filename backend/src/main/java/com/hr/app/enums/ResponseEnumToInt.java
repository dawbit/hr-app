package com.hr.app.enums;

public class ResponseEnumToInt {

    public static int getResponseStatusInt(ResponseEnum responseEnum) {
        switch (responseEnum) {
            case SUCCESS:
                return 1;
            case AUTHORIZATION_FAILED:
                return 2;
            case BAD_REQUEST:
                return 3;
            case BAD_TEST_CODE:
                return 4;
            case INACTIVE_QUIZ:
                return 5;
            case NO_PERMISSION:
                return 6;
            case QUESTION_NOT_FOUND:
                return 7;
            case BAD_TEST:
                return 8;
            case QUIZ_AREADY_SOLVED:
                return 9;
            case SERVER_ERROR:
                return 10;
            case TEST_NOT_FOUND:
                return 11;
            default:
                return 0;
        }
    }
}
