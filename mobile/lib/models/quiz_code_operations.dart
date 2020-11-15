import 'package:mobile/enums/quiz_response_code.dart';

class ResponseEnumOperations {

  static int getResponseStatusInt(ResponseEnum responseEnum) {
    switch (responseEnum) {
      case ResponseEnum.SUCCESS:
        return 1;
      case ResponseEnum.AUTHORIZATION_FAILED:
        return 2;
      case ResponseEnum.BAD_REQUEST:
        return 3;
      case ResponseEnum.BAD_TEST_CODE:
        return 4;
      case ResponseEnum.INACTIVE_QUIZ:
        return 5;
      case ResponseEnum.NO_PERMISSION:
        return 6;
      case ResponseEnum.QUESTION_NOT_FOUND:
        return 7;
      case ResponseEnum.BAD_TEST:
        return 8;
      case ResponseEnum.QUIZ_AREADY_SOLVED:
        return 9;
      case ResponseEnum.SERVER_ERROR:
        return 10;
      case ResponseEnum.TEST_NOT_FOUND:
        return 11;
      default:
        return 0;
    }
  }

  static ResponseEnum getResponseEnum(int code) {
    switch (code) {
      case 1:
        return ResponseEnum.SUCCESS;
      case 2:
        return ResponseEnum.AUTHORIZATION_FAILED;
      case 3:
        return ResponseEnum.BAD_REQUEST;
      case 4:
        return ResponseEnum.BAD_TEST_CODE;
      case 5:
        return ResponseEnum.INACTIVE_QUIZ;
      case 6:
        return ResponseEnum.NO_PERMISSION;
      case 7:
        return ResponseEnum.QUESTION_NOT_FOUND;
      case 8:
        return ResponseEnum.BAD_TEST;
      case 9:
        return ResponseEnum.QUIZ_AREADY_SOLVED;
      case 10:
        return ResponseEnum.SERVER_ERROR;
      case 11:
        return ResponseEnum.TEST_NOT_FOUND;
      default:
        return ResponseEnum.CONNECTION_ERROR;
    }
  }
}