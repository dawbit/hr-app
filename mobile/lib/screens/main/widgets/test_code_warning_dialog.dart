import 'dart:async';

import 'package:flutter/material.dart';
import 'package:mobile/blocs/quiz_information_bloc.dart';
import 'package:mobile/blocs/quiz_question_bloc.dart';
import 'package:mobile/config/routes.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/models/question_result_dto.dart';
import 'package:mobile/models/quiz_info_with_question.dart';
import 'package:mobile/models/quiz_information_dto.dart';
import 'package:mobile/values/sizes.dart';

class TestCodeWarningDialog extends StatefulWidget {
  final String quizCode;

  TestCodeWarningDialog({this.quizCode});

  @override
  _TestCodeWarningDialogState createState() => _TestCodeWarningDialogState();
}

class _TestCodeWarningDialogState extends State<TestCodeWarningDialog> {
  QuizInformationBloc _quizInformationBloc;
  QuizQuestionBloc _quizQuestionBloc;

  StreamSubscription quizInformationStream;
  StreamSubscription quizQuestionStream;

  QuizInformationDto quizInformationDto;

  @override
  void initState() {
    super.initState();
    _quizInformationBloc = AppModule.injector.getBloc();
    _quizQuestionBloc = AppModule.injector.getBloc();
    quizInformationStream = _quizInformationBloc.quizInformationObservable
        .listen(_onQuizInformation);
    quizQuestionStream =
        _quizQuestionBloc.quizQuestionObservable.listen(_onQuizQuestion);
  }

  @override
  void dispose() {
    quizInformationStream.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      content: Text("saafasfg"),
      title: Text("Czy jestes pewien ze chcesz rozpoczac ten test?"),
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
    this.quizInformationDto = quizInformationDto;

    _quizQuestionBloc.getQuizQuestion(
        quizInformationDto.quizId, widget.quizCode, 1);
  }

  void _onQuizQuestion(QuestionResultDto questionResult) {
    final quizData = QuizInfoWithQuestion(questionResultDto: questionResult,
        quizInformationDto: quizInformationDto);

    Navigator.of(context).pushNamed(quizScreenRoute, arguments: quizData);
  }
}
