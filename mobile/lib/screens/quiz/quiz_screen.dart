import 'package:flutter/material.dart';
import 'package:mobile/models/quiz_information_dto.dart';
import 'package:mobile/screens/quiz/quiz_content.dart';

class QuizScreen extends StatelessWidget {

  final QuizInformationDto quizInformationDto;

  QuizScreen({this.quizInformationDto});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          image: DecorationImage(
            fit: BoxFit.fitHeight,
            image: AssetImage('assets/images/background-01.jpg'),
          )
      ),
      child: Scaffold(

        resizeToAvoidBottomInset: false,
        body: SafeArea(child: QuizContent(quizInformationDto)),
      ),
    );
  }
}
