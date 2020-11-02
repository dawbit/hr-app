import 'package:bloc_pattern/bloc_pattern.dart';
import 'package:mobile/models/answer_command_dto.dart';
import 'package:mobile/models/response_transfer.dart';
import 'package:mobile/repositories/quiz_repository.dart';
import 'package:rxdart/rxdart.dart';

class QuizAnswerBloc extends BlocBase {

  QuizRepository quizRepository;

  QuizAnswerBloc(this.quizRepository);

  PublishSubject<bool> _isLoadingSubject = PublishSubject();
  Stream<bool> get isLoadingObservable => _isLoadingSubject.stream;

  PublishSubject<ResponseTransfer> _answerResponseSubject = PublishSubject();
  Stream<ResponseTransfer> get answerResponseObservable => _answerResponseSubject.stream;

  Future setAnswer(AnswerCommandDto answerCommandDto) async {
    _isLoadingSubject.add(true);
    quizRepository.setAnswer(answerCommandDto).then(_onSuccess).catchError(_onError);
  }

  void _onSuccess(ResponseTransfer responseTransfer) {
    _isLoadingSubject.add(false);
    _answerResponseSubject.add(responseTransfer);
  }

  void _onError(Exception e) {
    _isLoadingSubject.add(false);
    print("Answer error: $e");
  }
}