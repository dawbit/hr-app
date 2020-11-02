import 'package:flutter/material.dart';
import 'package:mobile/screens/quiz/widgets/list_of_answers_widget.dart';
import 'package:mobile/screens/quiz/widgets/question_widget.dart';
import 'package:mobile/values/sizes.dart';

class QuizContent extends StatefulWidget {
  @override
  _QuizContentState createState() => _QuizContentState();
}

class _QuizContentState extends State<QuizContent> {

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Expanded(
          flex: 2,
          child: QuestionWidget(),
        ),
        Expanded(
          flex: 3,
          child: Container(
            child: ListOfAnswersWidget(),
          ),
        ),
        Expanded(
            flex: 0,
            child: Container(
              margin: EdgeInsets.all(20),
              child: MaterialButton(
                  height: Sizes.hugeSize,
                  onPressed: (){Navigator.of(context).pop();},
                  child: Container(
                    width: MediaQuery.of(context).size.width,
                    child: Center(
                        child: Text("Zakoncz test")
                    ),
                  )
              ),
            ),
        )
      ],
    );
  }
}
