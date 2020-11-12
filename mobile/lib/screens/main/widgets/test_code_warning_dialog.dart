import 'dart:async';

import 'package:flutter/material.dart';
import 'package:mobile/blocs/quiz_information_bloc.dart';
import 'package:mobile/config/routes.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/models/quiz_information_dto.dart';
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

  @override
  void initState() {
    super.initState();
    _quizInformationBloc = AppModule.injector.getBloc();
    quizInformationStream = _quizInformationBloc.quizInformationObservable
        .listen(_onQuizInformation);
  }

  @override
  void dispose() {
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
            return snapshot.data ? LoadingWidget() : Text("");
          } else {
            return Text("");
          }
        }
      ),
      title: Text("Czy jestes pewien ze chcesz rozpoczac ten test?", textAlign: TextAlign.center,),
      actions: [
        MaterialButton(
            height: Sizes.hugeSize,
            onPressed: () {
              _quizInformationBloc.getQuizInformation(widget.quizCode);
            },
            child: Container(
              width: MediaQuery.of(context).size.width,
              child: Center(child: Text("Rozpocznij")),
            )),
      ],
    );
  }

  void _onQuizInformation(QuizInformationDto quizInformationDto) {
    QuizInformationDto quizInformation = quizInformationDto;
    quizInformation.setTestCode(widget.quizCode);
    Navigator.of(context).pop();
    Navigator.of(context).pushNamed(quizScreenRoute, arguments: quizInformation);
  }
}
