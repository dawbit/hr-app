import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/blocs/quiz_information_bloc.dart';
import 'package:mobile/config/routes.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/localizations/app_localization.dart';
import 'package:mobile/models/quiz_information_dto.dart';
import 'package:mobile/utils/quiz_error_handlers.dart';
import 'package:mobile/utils/toast_util.dart';
import 'package:mobile/values/sizes.dart';
import 'package:mobile/widgets/loading.dart';

class TestCodeWarningDialog extends StatefulWidget {
  final String quizCode;

  TestCodeWarningDialog({this.quizCode});

  @override
  _TestCodeWarningDialogState createState() => _TestCodeWarningDialogState();
}

class _TestCodeWarningDialogState extends State<TestCodeWarningDialog> {
  QuizInformationBloc _quizInformationBloc;

  StreamSubscription quizInformationStream;
  StreamSubscription errorStream;

  @override
  void initState() {
    super.initState();
    _quizInformationBloc = AppModule.injector.getBloc();
    quizInformationStream = _quizInformationBloc.quizInformationObservable
        .listen(_onQuizInformation);
    errorStream = _quizInformationBloc.errorObservable.listen((event) {
      _onErrorCode(event);
    });
  }

  @override
  void dispose() {
    errorStream.cancel();
    quizInformationStream.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      content: StreamBuilder<bool>(
        stream: _quizInformationBloc.isLoadingObservable,
        initialData: false,
        builder: (context, snapshot) {
          if(snapshot.hasData) {
            return snapshot.data ? Container(
              height: 50,
                child: LoadingWidget()) : Text("");
          } else {
            return Text("");
          }
        }
      ),
      title: Text(Lang.of(context).translate("are_you_sure_you_want_to_start_this_test"), textAlign: TextAlign.center,),
      actions: [
        MaterialButton(
            height: Sizes.hugeSize,
            onPressed: () {
              _quizInformationBloc.getQuizInformation(widget.quizCode);
            },
            child: Container(
              width: MediaQuery.of(context).size.width,
              child: Center(child: Text(Lang.of(context).translate("start"))),
            )),
      ],
    );
  }

  void _onQuizInformation(QuizInformationDto quizInformationDto) {
    QuizInformationDto quizInformation = quizInformationDto;
    quizInformation.setTestCode(widget.quizCode);
    Navigator.of(context).popAndPushNamed(quizScreenRoute, arguments: quizInformation);
  }

  void _onErrorCode(Object obj) {
    final dioError = obj as DioError;
    final res = dioError.response;
    if((dioError).error.toString().toLowerCase().contains("connection failed")) {
      setState(() {
        showToast(context, Lang.of(context).translate("connection_error"));
      });
    }
    else if(res.statusCode >= 500) {
      setState(() {
        showToast(context,Lang.of(context).translate("server_error"));
      });
    } else {
      Map errorData = dioError.response.data;
      QuizErrorHander.handleError(errorData['responseCode'], context);
    }
    Navigator.pop(context);
  }
}
