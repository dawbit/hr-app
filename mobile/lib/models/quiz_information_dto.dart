import 'package:json_annotation/json_annotation.dart';

part 'quiz_information_dto.g.dart';

@JsonSerializable()
class QuizInformationDto {

  @JsonKey(name: "quizId")
  int quizId;

  @JsonKey(name: "amountOfQuestions")
  int amountOfQuestions;

  @JsonKey(name: "backPossible")
  bool backPossible;

  @JsonKey(name: "timeForTestInMilis")
  double timeForTestInMilis;

  String testCode;

  QuizInformationDto({this.amountOfQuestions, this.backPossible, this.quizId, this.timeForTestInMilis});

  factory QuizInformationDto.fromJson(Map<String, dynamic> json) => _$QuizInformationDtoFromJson(json);

  Map<String, dynamic> toJson() => _$QuizInformationDtoToJson(this);

  void setTestCode(String testCode) {
    this.testCode = testCode;
  }
}