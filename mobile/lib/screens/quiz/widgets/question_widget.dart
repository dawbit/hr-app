import 'package:flutter/material.dart';
import 'package:mobile/screens/quiz/widgets/question_number_widget.dart';

import 'bottom_clipper.dart';

class QuestionWidget extends StatelessWidget {

  final String questionText;
  final int amountOfQuestions;
  final Function getQuestionNumber;

  final List<QuestionNumberWidget> listOfQuestionNumber = [
  ];

  QuestionWidget({this.amountOfQuestions, this.questionText, this.getQuestionNumber}) {
    for(int i= 1; i<=amountOfQuestions; i++) {
      listOfQuestionNumber.add(QuestionNumberWidget(i, getQuestionNumber));
    }
  }

  @override
  Widget build(BuildContext context) {
    return ClipPath(
      clipper: RoundedBottomClipper(),
      child: Container(
        width: MediaQuery.of(context).size.width,
        color: Colors.blue,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          mainAxisSize: MainAxisSize.min,
          children: [
            Expanded(
              flex: 0,
              child: Padding(
                padding: const EdgeInsets.only(left: 10, top: 20, right: 10),
                child: SingleChildScrollView(
                  scrollDirection: Axis.horizontal,
                  child: Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: listOfQuestionNumber
                  ),
                ),
              ),
            ),
            Expanded(
                flex: 1,
                child: Padding(
                  padding: const EdgeInsets.only(top: 10, left: 10, right: 10, bottom: 30),
                  child: Center(child: Text("$questionText",
                    textAlign: TextAlign.center,
                  )),
                )
            ),
          ],
        ),
      ),
    );
  }
}
