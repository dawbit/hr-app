import 'package:mobile/models/question_result_dto.dart';
import 'package:mobile/models/quiz_information_dto.dart';

class QuizInfoWithQuestion{
  QuizInformationDto quizInformationDto;
  QuestionResultDto questionResultDto;

  QuizInfoWithQuestion({this.questionResultDto, this.quizInformationDto});
}