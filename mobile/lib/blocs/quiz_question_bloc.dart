import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/models/question_result_dto.dart';
import 'package:mobile/repositories/quiz_repository.dart';
import 'package:rxdart/rxdart.dart';

class QuizQuestionBloc extends BlocBase {
  QuizRepository quizRepository;

  QuizQuestionBloc(this.quizRepository);

  PublishSubject<bool> _isLoadingSubject = PublishSubject();

  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<QuestionResultDto> _quizQuestionSubject = PublishSubject();

  Stream<QuestionResultDto> get quizQuestionObservable =>
      _quizQuestionSubject.stream;

  Future getQuizQuestion(
      int quizId, String testCode, int questionNumber) async {
    quizRepository
        .getQuestion(questionNumber: questionNumber, quizId: quizId, testCode: testCode)
        .then(_onSuccess)
        .catchError(_onError);
  }

  void _onSuccess(QuestionResultDto questionResultDto) {
    _isLoadingSubject.add(false);
    _quizQuestionSubject.add(questionResultDto);
  }

  void _onError(e) {
    _isLoadingSubject.add(false);
    print("Quiz question error: $e");
  }
}
