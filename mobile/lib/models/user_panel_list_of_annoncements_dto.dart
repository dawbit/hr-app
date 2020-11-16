import 'package:json_annotation/json_annotation.dart';

part 'user_panel_list_of_annoncements_dto.g.dart';

@JsonSerializable()
class UserPanelListOfAnnoncementsDto {
  @JsonKey(name: "testParticipantId")
  int testParticipantId;

  @JsonKey(name: "companyName")
  String companyName;

  @JsonKey(name: "announcementName")
  String announcementName;

  @JsonKey(name: "quizCode")
  String quizCode;

  @JsonKey(name: "isCompleted")
  bool isCompleted;

  UserPanelListOfAnnoncementsDto({this.companyName,this.quizCode,this.announcementName,this.isCompleted,this.testParticipantId});

  factory UserPanelListOfAnnoncementsDto.fromJson(Map<String, dynamic> json) => _$UserPanelListOfAnnoncementsDtoFromJson(json);

  Map<String, dynamic> toJson() => _$UserPanelListOfAnnoncementsDtoToJson(this);
}