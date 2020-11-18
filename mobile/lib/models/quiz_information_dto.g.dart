// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'quiz_information_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

QuizInformationDto _$QuizInformationDtoFromJson(Map<String, dynamic> json) {
  return QuizInformationDto(
    amountOfQuestions: json['amountOfQuestions'] as int,
    backPossible: json['backPossible'] as bool,
    quizId: json['quizId'] as int,
    timeForTestInMilis: (json['timeForTestInMilis'] as num)?.toDouble(),
    currentQuestion: json['currentQuestion'] as int,
  );
}

Map<String, dynamic> _$QuizInformationDtoToJson(QuizInformationDto instance) =>
    <String, dynamic>{
      'quizId': instance.quizId,
      'amountOfQuestions': instance.amountOfQuestions,
      'backPossible': instance.backPossible,
      'timeForTestInMilis': instance.timeForTestInMilis,
      'currentQuestion': instance.currentQuestion,
    };
