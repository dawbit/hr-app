import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/models/quiz_information_dto.dart';
import 'package:mobile/repositories/quiz_repository.dart';
import 'package:rxdart/rxdart.dart';

class QuizInformationBloc extends BlocBase {

  QuizRepository quizRepository;

  QuizInformationBloc(this.quizRepository);

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<QuizInformationDto> _quizInformationSubject = PublishSubject();
  Stream<QuizInformationDto> get quizInformationObservable => _quizInformationSubject.stream;

  PublishSubject<Object> _errorSubject = PublishSubject();
  Stream<Object> get errorObservable => _errorSubject.stream;

  Future getQuizInformation(String testCode) async {
    _isLoadingSubject.add(true);
    quizRepository.getQuizInformation(testCode).then(_onSuccess).catchError(_onError);
  }

  void _onSuccess(QuizInformationDto quizInformationDto) {
    _quizInformationSubject.add(quizInformationDto);
    _isLoadingSubject.add(false);
  }

  void _onError(Object obj) {
    _isLoadingSubject.add(false);
    _errorSubject.add(obj);
  }
}