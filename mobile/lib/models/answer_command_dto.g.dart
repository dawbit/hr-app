// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'answer_command_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

AnswerCommandDto _$AnswerCommandDtoFromJson(Map<String, dynamic> json) {
  return AnswerCommandDto(
    questionId: (json['questionId'] as num)?.toDouble(),
    answerId: (json['answerId'] as num)?.toDouble(),
    testCode: json['testCode'] as String,
  );
}

Map<String, dynamic> _$AnswerCommandDtoToJson(AnswerCommandDto instance) =>
    <String, dynamic>{
      'questionId': instance.questionId,
      'answerId': instance.answerId,
      'testCode': instance.testCode,
    };
