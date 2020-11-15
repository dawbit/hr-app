// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'announcements_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

AnnouncementsDto _$AnnouncementsDtoFromJson(Map<String, dynamic> json) {
  return AnnouncementsDto(
    announcementDescription: json['announcementDescription'] as String,
    announcementId: json['announcementId'] as int,
    announcementTitle: json['announcementTitle'] as String,
    companyAbout: json['companyAbout'] as String,
    companyId: json['companyId'] as int,
    companyLocation: json['companyLocation'] as String,
    companyName: json['companyName'] as String,
  );
}

Map<String, dynamic> _$AnnouncementsDtoToJson(AnnouncementsDto instance) =>
    <String, dynamic>{
      'announcementId': instance.announcementId,
      'announcementTitle': instance.announcementTitle,
      'announcementDescription': instance.announcementDescription,
      'companyId': instance.companyId,
      'companyName': instance.companyName,
      'companyAbout': instance.companyAbout,
      'companyLocation': instance.companyLocation,
    };
