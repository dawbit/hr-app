import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:rxdart/rxdart.dart';

class QuizInformationBloc extends BlocBase {

  QuizInformationBloc quizRepository;

  QuizInformationBloc(this.quizRepository);

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<bool> _quizInformationSubject = PublishSubject();
  Stream<bool> get quizInformationObservable => _quizInformationSubject.stream;

  Future getQuizInformation() async {
    _isLoadingSubject.add(true);

  }
}