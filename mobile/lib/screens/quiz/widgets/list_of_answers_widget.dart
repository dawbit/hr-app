import 'package:flutter/cupertino.dart';
import 'package:mobile/screens/quiz/widgets/answer_widget.dart';

class ListOfAnswersWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(10),
      child: ListView.builder(
        itemCount: 4,
          itemBuilder: (context, index) => AnswerWidget(),
      ),
    );
  }
}
