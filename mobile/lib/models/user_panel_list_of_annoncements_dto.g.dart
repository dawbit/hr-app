// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_panel_list_of_annoncements_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UserPanelListOfAnnoncementsDto _$UserPanelListOfAnnoncementsDtoFromJson(
    Map<String, dynamic> json) {
  return UserPanelListOfAnnoncementsDto(
    companyName: json['companyName'] as String,
    quizCode: json['quizCode'] as String,
    announcementName: json['announcementName'] as String,
    isCompleted: json['isCompleted'] as bool,
    testParticipantId: json['testParticipantId'] as int,
  );
}

Map<String, dynamic> _$UserPanelListOfAnnoncementsDtoToJson(
        UserPanelListOfAnnoncementsDto instance) =>
    <String, dynamic>{
      'testParticipantId': instance.testParticipantId,
      'companyName': instance.companyName,
      'announcementName': instance.announcementName,
      'quizCode': instance.quizCode,
      'isCompleted': instance.isCompleted,
    };
