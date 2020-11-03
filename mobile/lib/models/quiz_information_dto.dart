import 'package:json_annotation/json_annotation.dart';

part 'quiz_information_dto.g.dart';

@JsonSerializable()
class QuizInformationDto {

  @JsonKey(name: "quizId")
  int quizId;

  @JsonKey(name: "amountOfQuestions")
  int amountOfQuestions;

  @JsonKey(name: "isBackPossible")
  bool isBackPossible;

  @JsonKey(name: "timeForTestInMilis")
  double timeForTestInMilis;

  QuizInformationDto({this.amountOfQuestions, this.isBackPossible, this.quizId, this.timeForTestInMilis});

  factory QuizInformationDto.fromJson(Map<String, dynamic> json) => _$QuizInformationDtoFromJson(json);

  Map<String, dynamic> toJson() => _$QuizInformationDtoToJson(this);
}