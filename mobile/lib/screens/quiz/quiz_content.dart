import 'package:flutter/material.dart';
import 'package:mobile/blocs/quiz_question_bloc.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/models/answer_result_dto.dart';
import 'package:mobile/models/question_result_dto.dart';
import 'package:mobile/models/quiz_information_dto.dart';
import 'package:mobile/screens/quiz/widgets/list_of_answers_widget.dart';
import 'package:mobile/screens/quiz/widgets/question_widget.dart';
import 'package:mobile/values/sizes.dart';
import 'package:mobile/widgets/loading.dart';

class QuizContent extends StatefulWidget {

  final QuizInformationDto quizInformationDto;

  QuizContent(this.quizInformationDto);

  @override
  _QuizContentState createState() => _QuizContentState();
}

class _QuizContentState extends State<QuizContent> {

  QuizQuestionBloc quizQuestionBloc;

  @override
  void initState() {
    super.initState();
    quizQuestionBloc = AppModule.injector.getBloc();
    quizQuestionBloc.getQuizQuestion(widget.quizInformationDto.quizId, widget.quizInformationDto.testCode, 1);
  }

  @override
  Widget build(BuildContext context) {
    return StreamBuilder<QuestionResultDto>(
      stream: quizQuestionBloc.quizQuestionObservable,
      initialData: null,
      builder: (context, snapshotQuestion) {
        return Column(
          children: [
            Expanded(
              flex: 2,
              child: QuestionWidget(
                amountOfQuestions: widget.quizInformationDto.amountOfQuestions,
                questionText: snapshotQuestion.hasData ? snapshotQuestion.data.text : "",
                getQuestionNumber: widget.quizInformationDto
                    .backPossible ? getQuestionNumber : null,
              ),
            ),
            Expanded(
              flex: 3,
              child: StreamBuilder<bool>(
                initialData: true,
                stream: quizQuestionBloc.isLoadingObservable,
                builder: (context, snapshotLoading) {
                  if(snapshotLoading.data) {
                    return LoadingWidget();
                  } else {
                    return Container(
                      child: ListOfAnswersWidget(
                        answers: snapshotQuestion.data.answers,
                        setAnswerForThisQuestion: setAnswerForThisQuestion,),
                    );
                  }
                }
              ),
            ),
            Expanded(
              flex: 0,
              child: Container(
                margin: EdgeInsets.all(20),
                child: MaterialButton(
                    height: Sizes.hugeSize,
                    onPressed: () {
                      finishQuiz();
                    },
                    child: Container(
                      width: MediaQuery
                          .of(context)
                          .size
                          .width,
                      child: Center(
                          child: Text("Zakoncz test")
                      ),
                    )
                ),
              ),
            ),
          ],
        );
      }
    );
  }

  void getNextQuestion() {

  }

  void setAnswerForThisQuestion(AnswerResultDto answerResultDto) {
    setState(() {

    });
  }

  void finishQuiz() {
    Navigator.of(context).pop();
  }

  void getQuestionNumber(int questionNumber) {

  }


}
