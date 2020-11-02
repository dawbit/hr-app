import 'package:dio/dio.dart';
import 'package:retrofit/http.dart';
import 'package:retrofit/retrofit.dart';

part 'quiz_source.g.dart';

@RestApi(baseUrl: "http://192.168.43.228:8080")
abstract class QuizSource{

  factory QuizSource(Dio dio, {String baseUrl})= _QuizSource;

  // @POST("/login")
  // Future<> getQuizInformation();

}