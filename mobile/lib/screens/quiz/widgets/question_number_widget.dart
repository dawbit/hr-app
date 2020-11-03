import 'package:flutter/material.dart';

class QuestionNumberWidget extends StatelessWidget {

  final int number;

  QuestionNumberWidget(this.number);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(right: 8),
      decoration: BoxDecoration(
        border: Border.all(color: Colors.black)
      ),
      height: 40,
      width: 40,
      child: Center(
        child: Text(number.toString()),
      ),
    );
  }
}
