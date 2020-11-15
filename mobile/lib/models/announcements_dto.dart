import 'package:json_annotation/json_annotation.dart';

part 'announcements_dto.g.dart';

@JsonSerializable()
class AnnouncementsDto {
  @JsonKey(name: "announcementId")
  int announcementId;

  @JsonKey(name: "announcementTitle")
  String announcementTitle;

  @JsonKey(name: "announcementDescription")
  String announcementDescription;

  @JsonKey(name: "companyId")
  int companyId;

  @JsonKey(name: "companyName")
  String companyName;

  @JsonKey(name: "companyAbout")
  String companyAbout;

  @JsonKey(name: "companyLocation")
  String companyLocation;

  AnnouncementsDto({this.announcementDescription, this.announcementId, this.announcementTitle, this.companyAbout, this.companyId, this.companyLocation,this.companyName});

  factory AnnouncementsDto.fromJson(Map<String, dynamic> json) => _$AnnouncementsDtoFromJson(json);

  Map<String, dynamic> toJson() => _$AnnouncementsDtoToJson(this);
}