import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';

class QuizQuestionBloc extends BlocBase {

  QuizQuestionBloc quizRepository;

  QuizQuestionBloc(this.quizRepository);

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<bool> _quizQuestionSubject = PublishSubject();
  Stream<bool> get quizQuestionObservable => _quizQuestionSubject.stream;

}