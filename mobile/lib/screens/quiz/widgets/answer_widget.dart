import 'package:flutter/material.dart';

class AnswerWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(bottom: 15),
      decoration: BoxDecoration(
        border: Border.all(),
        borderRadius: BorderRadius.circular(10),
      ),
      child: Container(
          margin: EdgeInsets.all(15),
          child: Center(
              child: Text("jhakies odpowiedzjhakies odpowiedz ",
                textAlign: TextAlign.center,
              ),
          )
      ),
    );
  }
}
