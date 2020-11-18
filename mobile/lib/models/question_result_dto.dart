import 'package:json_annotation/json_annotation.dart';

import 'answer_result_dto.dart';

part 'question_result_dto.g.dart';

@JsonSerializable()
class QuestionResultDto {

  @JsonKey(name: "id")
  int id;

  @JsonKey(name: "text")
  String text;

  // @JsonKey(name: "login")
  // byte[] image;

  @JsonKey(name: "answers")
  List<AnswerResultDto> answers;

  @JsonKey(name: "responseCode")
  int responseCode;

  QuestionResultDto({this.id, this.text, this.answers, this.responseCode});

  factory QuestionResultDto.fromJson(Map<String, dynamic> json) => _$QuestionResultDtoFromJson(json);

  Map<String, dynamic> toJson() => _$QuestionResultDtoToJson(this);
}