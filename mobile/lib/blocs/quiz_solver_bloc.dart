import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/enums/quiz_solver_state.dart';
import 'package:mobile/models/answer_command_dto.dart';
import 'package:mobile/models/current_question_controller.dart';
import 'package:mobile/models/question_result_dto.dart';
import 'package:mobile/repositories/quiz_repository.dart';
import 'package:rxdart/rxdart.dart';

class QuizSolverBloc extends BlocBase {
  QuizRepository quizRepository;

  QuizSolverBloc(this.quizRepository);

  PublishSubject<QuizSolverState> _quizStateSubject = PublishSubject();
  Stream<QuizSolverState> get quizStateObservable => _quizStateSubject.stream;

  PublishSubject<QuestionResultDto> _quizQuestionSubject = PublishSubject();
  Stream<QuestionResultDto> get quizQuestionObservable => _quizQuestionSubject.stream;

  PublishSubject<String> _answerErrorMessageSubject = PublishSubject();
  Stream<String> get answerErrorMessageObservable => _answerErrorMessageSubject.stream;

  PublishSubject _finishQuizSubject = PublishSubject();
  Stream get finishQuizSubjectObservable => _finishQuizSubject.stream;

  Future answerQuestionAndGetNextQuestion(
      AnswerCommandDto answerCommandDto,
      int quizId,
      String testCode,
      CurrentQuestionController questionController) async {
    _quizStateSubject.add(QuizSolverState.LOADING);

    quizRepository.setAnswer(answerCommandDto).then((answerResult) {
      questionController.onAnswerSuccess();

      print(answerResult.message);

      if (!questionController.isBackPossible &&
          questionController.noMoreQuestions()) {
        quizRepository
            .finishQuiz(testCode: testCode, quizId: quizId)
            .then(_OnQuizFinishSuccess)
            .catchError(_OnQuizFinishError);
      } else {
        quizRepository
            .getQuestion(
                questionNumber: questionController.currentQuestion,
                quizId: quizId,
                testCode: testCode)
            .then(_onQuestionSuccess)
            .catchError(_onQuestionError);
      }
    }).catchError(_onAnswerError);
  }

  Future getQuizQuestion(
      int quizId, String testCode, int questionNumber) async {
    _quizStateSubject.add(QuizSolverState.LOADING);

    quizRepository
        .getQuestion(
            questionNumber: questionNumber, quizId: quizId, testCode: testCode)
        .then(_onQuestionSuccess)
        .catchError(_onQuestionError);
  }

  Future finishQuiz({int quizId, String testCode}) async {
    quizRepository
        .finishQuiz(testCode: testCode, quizId: quizId)
        .then(_OnQuizFinishSuccess)
        .catchError(_OnQuizFinishError);
  }

  void _OnQuizFinishSuccess(QuestionResultDto questionResultDto) {
    _finishQuizSubject.add(null);
  }

  void _OnQuizFinishError(error) {
    Map errorData = error.response.data;
    print('error: ${errorData['message'].toString()}');

    _answerErrorMessageSubject.add(errorData['message'].toString());
  }

  void _onAnswerError(error) {
     Map errorData = error.response.data;
     print('error: ${errorData['message'].toString()}');

     _answerErrorMessageSubject.add(errorData['message'].toString());
  }

  void _onQuestionSuccess(QuestionResultDto questionResultDto) {
    _quizQuestionSubject.add(questionResultDto);
    _quizStateSubject.add(QuizSolverState.OK);
  }

  void _onQuestionError(error) {
    Map errorData = error.response.data;
    print('error: ${errorData['message'].toString()}');

    _answerErrorMessageSubject.add(errorData['message'].toString());
  }
}
