import 'package:flutter/cupertino.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/utils/toast_util.dart';

class QuizErrorHander {
  static void handleError(int code, BuildContext context) {
    switch(code) {
      case 1:
        showToast(context, Lang.of(context).translate("ok")); //200
        break;
      case 2:
        showToast(context, Lang.of(context).translate("authorization_failed")); //401
        break;
      case 3:
        showToast(context, Lang.of(context).translate("bad_request")); //400
        break;
      case 4:
        showToast(context, Lang.of(context).translate("bad_test_code")); //403
        break;
      case 5:
        showToast(context, Lang.of(context).translate("inactive_quiz")); //403
        break;
      case 6:
        showToast(context, Lang.of(context).translate("no_permission")); //403
        break;
      case 7:
        showToast(context, Lang.of(context).translate("question_not_found")); //404
        break;
      case 8:
        showToast(context, Lang.of(context).translate("bad_test")); //403
        break;
      case 9:
        showToast(context, Lang.of(context).translate("quiz_already_solved")); //403
        break;
      case 10:
        showToast(context, Lang.of(context).translate("server_error")); //500
        break;
      case 11:
        showToast(context, Lang.of(context).translate("test_not_found")); //404
        break;
      default:
        showToast(context, Lang.of(context).translate("server_error"));
        break;
    }
  }
}