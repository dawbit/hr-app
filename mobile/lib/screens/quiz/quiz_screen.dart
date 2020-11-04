import 'package:flutter/material.dart';
import 'package:mobile/models/quiz_information_dto.dart';
import 'package:mobile/screens/quiz/quiz_content.dart';
import 'package:mobile/widgets/status_bar_widget.dart';

class QuizScreen extends StatelessWidget {

  final QuizInformationDto quizInformationDto;

  QuizScreen({this.quizInformationDto});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Expanded(
              flex: 0,
              child: StatusBarWidget()
          ),
          Expanded(
              flex: 1,
              child: QuizContent(quizInformationDto)
          ),
        ],
      ),
    );
  }
}
