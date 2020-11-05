import 'dart:async';

import 'package:flutter/material.dart';
import 'package:mobile/blocs/quiz_solver_bloc.dart';
import 'package:mobile/enums/quiz_solver_state.dart';
import 'package:mobile/injections/app_module.dart';
import 'package:mobile/models/answer_command_dto.dart';
import 'package:mobile/models/answer_result_dto.dart';
import 'package:mobile/models/current_question_controller.dart';
import 'package:mobile/models/question_result_dto.dart';
import 'package:mobile/models/quiz_information_dto.dart';
import 'package:mobile/screens/quiz/widgets/list_of_answers_widget.dart';
import 'package:mobile/screens/quiz/widgets/question_widget.dart';
import 'package:mobile/screens/quiz/widgets/top_clip_path.dart';
import 'package:mobile/values/sizes.dart';
import 'package:mobile/widgets/connection_error.dart';
import 'package:mobile/widgets/loading.dart';

class QuizContent extends StatefulWidget {
  final QuizInformationDto quizInformationDto;

  QuizContent(this.quizInformationDto);

  @override
  _QuizContentState createState() => _QuizContentState();
}

class _QuizContentState extends State<QuizContent> {
  QuizSolverBloc quizSolverBloc;
  StreamSubscription quizQuestionStream;

  QuestionResultDto currentQuestion;
  CurrentQuestionController currentQuestionController;

  @override
  void initState() {
    super.initState();
    currentQuestionController = CurrentQuestionController(widget.quizInformationDto.amountOfQuestions, widget.quizInformationDto.backPossible);
    quizSolverBloc = AppModule.injector.getBloc();
    quizQuestionStream =
        quizSolverBloc.quizQuestionObservable.listen(_onQuestionResponse);
    quizSolverBloc.getQuizQuestion(
        widget.quizInformationDto.quizId,
        widget.quizInformationDto.testCode,
        currentQuestionController.currentQuestion);
  }

  @override
  void dispose() {
    quizQuestionStream.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Expanded(
          flex: 2,
          child: Stack(
            children: [
              TopClipPath(),
              QuestionWidget(
                currentQuestionController: currentQuestionController,
                questionText:
                    currentQuestion != null ? currentQuestion.text : "",
                getQuestionNumber: widget.quizInformationDto.backPossible
                    ? getQuestionNumber
                    : null,
              ),
            ],
          ),
        ),
        Expanded(
          flex: 3,
          child: StreamBuilder<QuizSolverState>(
              stream: quizSolverBloc.quizStateObservable,
              initialData: QuizSolverState.LOADING,
              builder: (context, snapshotState) {
                return Stack(
                  children: [
                    Visibility(
                      child: ListOfAnswersWidget(
                        answers: currentQuestion != null
                            ? currentQuestion.answers
                            : null,
                        setAnswerForThisQuestion: setAnswerForThisQuestion,
                      ),
                      visible: snapshotState.data == QuizSolverState.OK,
                    ),
                    Visibility(
                      child: LoadingWidget(),
                      visible: snapshotState.data == QuizSolverState.LOADING,
                    ),
                    Visibility(
                      child: ConnectionError(),
                      visible: snapshotState.data == QuizSolverState.ERROR,
                    ),
                  ],
                );
              }),
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
                  width: MediaQuery.of(context).size.width,
                  child: Center(child: Text("Zakoncz test")),
                )),
          ),
        ),
      ],
    );
  }

  void getNextQuestion() {}

  void setAnswerForThisQuestion(AnswerResultDto answerResultDto) {
    AnswerCommandDto answerCommand = AnswerCommandDto(
        testCode: widget.quizInformationDto.testCode,
        answerId: answerResultDto.id,
        questionId: currentQuestion.id);
    quizSolverBloc.answerQuestionAndGetNextQuestion(
        answerCommand,
        widget.quizInformationDto.quizId,
        widget.quizInformationDto.testCode,
        currentQuestionController);
  }

  void _onQuestionResponse(QuestionResultDto question) {
    this.currentQuestion = question;
    setState(() {
      currentQuestionController = currentQuestionController;
    });
  }

  void finishQuiz() {
    quizSolverBloc.finishQuiz(
        quizId: widget.quizInformationDto.quizId,
        testCode: widget.quizInformationDto.testCode);
  }

  void getQuestionNumber(int questionNumber) {
    quizSolverBloc.getQuizQuestion(widget.quizInformationDto.quizId,
        widget.quizInformationDto.testCode, questionNumber);
  }
}
