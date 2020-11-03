// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'quiz_information_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

QuizInformationDto _$QuizInformationDtoFromJson(Map<String, dynamic> json) {
  return QuizInformationDto(
    amountOfQuestions: json['amountOfQuestions'] as int,
    isBackPossible: json['isBackPossible'] as bool,
    quizId: json['quizId'] as int,
    timeForTestInMilis: (json['timeForTestInMilis'] as num)?.toDouble(),
  );
}

Map<String, dynamic> _$QuizInformationDtoToJson(QuizInformationDto instance) =>
    <String, dynamic>{
      'quizId': instance.quizId,
      'amountOfQuestions': instance.amountOfQuestions,
      'isBackPossible': instance.isBackPossible,
      'timeForTestInMilis': instance.timeForTestInMilis,
    };
