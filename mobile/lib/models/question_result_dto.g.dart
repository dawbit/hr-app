// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'question_result_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

QuestionResultDto _$QuestionResultDtoFromJson(Map<String, dynamic> json) {
  return QuestionResultDto(
    id: (json['id'] as num)?.toDouble(),
    text: json['text'] as String,
    answers: (json['answers'] as List)
        ?.map((e) => e == null
            ? null
            : AnswerResultDto.fromJson(e as Map<String, dynamic>))
        ?.toList(),
    responseCode: json['responseCode'] as int,
  );
}

Map<String, dynamic> _$QuestionResultDtoToJson(QuestionResultDto instance) =>
    <String, dynamic>{
      'id': instance.id,
      'text': instance.text,
      'answers': instance.answers,
      'responseCode': instance.responseCode,
    };
