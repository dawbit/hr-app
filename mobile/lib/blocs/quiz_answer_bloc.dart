import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/repositories/quiz_repository.dart';
import 'package:rxdart/rxdart.dart';

class QuizAnswerBloc extends BlocBase {

  QuizRepository quizRepository;

  QuizAnswerBloc(this.quizRepository);

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<bool> _answerResponseSubject = PublishSubject();
  Stream<bool> get answerResponseObservable => _answerResponseSubject.stream;
}