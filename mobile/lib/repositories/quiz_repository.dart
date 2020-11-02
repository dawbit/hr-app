import 'package:mobile/data_sources/remote/quiz_source.dart';
import 'package:mobile/models/answer_command_dto.dart';
import 'package:mobile/models/question_result_dto.dart';
import 'package:mobile/models/quiz_information_dto.dart';
import 'package:mobile/models/response_transfer.dart';

class QuizRepository {
  QuizSource quizSource;

  QuizRepository(this.quizSource);

  Future<QuizInformationDto> getQuizInformation(String quizCode) =>
      quizSource.getQuizInformation(quizCode);

  Future<QuestionResultDto> getQuestion(
          {double quizId, String testCode, int questionNumber}) =>
      quizSource.getQuizQuestion(quizId, testCode, questionNumber);

  Future<ResponseTransfer> setAnswer(AnswerCommandDto answerCommandDto) =>
      quizSource.setAnswer(answerCommandDto);
}
