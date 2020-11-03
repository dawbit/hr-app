import 'package:json_annotation/json_annotation.dart';

part 'answer_command_dto.g.dart';

@JsonSerializable()
class AnswerCommandDto {
  @JsonKey(name: "questionId")
  int questionId;

  @JsonKey(name: "answerId")
  int answerId;

  @JsonKey(name: "testCode")
  String testCode;

  AnswerCommandDto({this.questionId, this.answerId, this.testCode});

  factory AnswerCommandDto.fromJson(Map<String, dynamic> json) => _$AnswerCommandDtoFromJson(json);

  Map<String, dynamic> toJson() => _$AnswerCommandDtoToJson(this);
}