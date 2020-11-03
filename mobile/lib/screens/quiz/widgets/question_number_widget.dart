import 'package:flutter/material.dart';

class QuestionNumberWidget extends StatelessWidget {

  final int number;
  final Function getQuestionNumber;

  QuestionNumberWidget(this.number, this.getQuestionNumber);

  @override
  Widget build(BuildContext context) {
    return getQuestionNumber ==null ?
    Container(
      margin: EdgeInsets.only(right: 8),
      decoration: BoxDecoration(
          border: Border.all(color: Colors.black)
      ),
      height: 40,
      width: 40,
      child: Center(
        child: Text(number.toString()),
      ),
    ):

      InkWell(
      onTap: (){getQuestionNumber(number);},
      child: Container(
        margin: EdgeInsets.only(right: 8),
        decoration: BoxDecoration(
          border: Border.all(color: Colors.black)
        ),
        height: 40,
        width: 40,
        child: Center(
          child: Text(number.toString()),
        ),
      ),
    );
  }
}
