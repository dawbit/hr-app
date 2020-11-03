import 'package:json_annotation/json_annotation.dart';

part 'answer_result_dto.g.dart';

@JsonSerializable()
class AnswerResultDto {
  @JsonKey(name: "id")
  int id;

  @JsonKey(name: "text")
  String text;

  AnswerResultDto({this.id, this.text});

  factory AnswerResultDto.fromJson(Map<String, dynamic> json) => _$AnswerResultDtoFromJson(json);

  Map<String, dynamic> toJson() => _$AnswerResultDtoToJson(this);
}