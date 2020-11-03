import 'package:dio/dio.dart';
import 'package:mobile/models/answer_command_dto.dart';
import 'package:mobile/models/question_result_dto.dart';
import 'package:mobile/models/quiz_information_dto.dart';
import 'package:mobile/models/response_transfer.dart';
import 'package:retrofit/http.dart';
import 'package:retrofit/retrofit.dart';

part 'quiz_source.g.dart';

@RestApi(baseUrl: "http://192.168.43.228:8080")
abstract class QuizSource {
  factory QuizSource(Dio dio, {String baseUrl}) = _QuizSource;

  @GET("/quiz/getQuizInformations/{quizcode}")
  Future<QuizInformationDto> getQuizInformation(
      @Path("quizcode") String quizcode);

  @GET("/quiz/quizquestion/{quizId}/{testCode}/{questionNumber}")
  Future<QuestionResultDto> getQuizQuestion(
      @Path("quizId") int quizId,
      @Path("testCode") String testCode,
      @Path("questionNumber") int questionNumber);

  @POST("/question/setanswer")
  Future<ResponseTransfer> setAnswer(@Body() AnswerCommandDto answerCommandDto);
}
