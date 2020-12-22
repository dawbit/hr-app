import 'package:flutter/material.dart';

class QuestionNumberWidget extends StatelessWidget {

  final int number;
  final Function getQuestionNumber;
  final bool questionAlreadyAnswered;

  QuestionNumberWidget(this.number, this.getQuestionNumber, this.questionAlreadyAnswered);

  @override
  Widget build(BuildContext context) {
    return getQuestionNumber == null ? Container(
      margin: EdgeInsets.only(right: 8),
      decoration: BoxDecoration(
          border: Border.all(
              color: questionAlreadyAnswered ? Colors.red[800] :Colors.white)
      ),
      height: 40,
      width: 40,
      child: Center(
        child: Text(number.toString(), style: TextStyle(color: questionAlreadyAnswered ? Colors.red[800] :Colors.white),
        ),
      ),
    ):

    InkWell(
      onTap: (){getQuestionNumber(number);},
      child: Container(
        margin: EdgeInsets.only(right: 8),
        decoration: BoxDecoration(
            border: Border.all(color: questionAlreadyAnswered ? Colors.red[800] :Colors.white)
        ),
        height: 40,
        width: 40,
        child: Center(
          child: Text(number.toString(), style: TextStyle(color: questionAlreadyAnswered ? Colors.red[800] :Colors.white)),
        ),
      ),
    );
  }
}
