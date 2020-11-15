import 'package:flutter/material.dart';
import 'package:mobile/models/answer_result_dto.dart';

class AnswerWidget extends StatelessWidget {

  final AnswerResultDto answerResultDto;
  final Function setAnswerForThisQuestion;

  AnswerWidget({this.answerResultDto, this.setAnswerForThisQuestion});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(bottom: 15),
      decoration: BoxDecoration(
        border: Border.all(),
        borderRadius: BorderRadius.circular(10),
      ),
      child: InkWell(
        onTap: (){setAnswerForThisQuestion(answerResultDto);},
        child: Container(
            margin: EdgeInsets.all(15),
            child: Center(
                child: Text(answerResultDto.text,
                  textAlign: TextAlign.center,
                ),
            )
        ),
      ),
    );
  }
}
