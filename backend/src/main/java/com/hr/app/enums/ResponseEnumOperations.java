package com.hr.app.enums;

import javax.servlet.http.HttpServletResponse;

public class ResponseEnumOperations {

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

    public static int getResponseStatusFromModelResponseCode (int responseCode){
        switch (responseCode) {
            case 1:
                return HttpServletResponse.SC_OK; //200
            case 2:
                return HttpServletResponse.SC_UNAUTHORIZED; //401
            case 3:
                return HttpServletResponse.SC_BAD_REQUEST; //400
            case 4:
                return HttpServletResponse.SC_FORBIDDEN; //403
            case 5:
                return HttpServletResponse.SC_FORBIDDEN; //403
            case 6:
                return HttpServletResponse.SC_FORBIDDEN; //403
            case 7:
                return HttpServletResponse.SC_NOT_FOUND; //404
            case 8:
                return HttpServletResponse.SC_FORBIDDEN; //403
            case 9:
                return HttpServletResponse.SC_FORBIDDEN; //403
            case 10:
                return HttpServletResponse.SC_INTERNAL_SERVER_ERROR; //500
            case 11:
                return HttpServletResponse.SC_NOT_FOUND; //404
            default:
                return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
    }
}
