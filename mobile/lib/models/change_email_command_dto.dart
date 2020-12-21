import 'package:json_annotation/json_annotation.dart';

part 'change_email_command_dto.g.dart';

@JsonSerializable()
class ChangeEmailCommandDto {
  @JsonKey(name: "password")
  String password;

  @JsonKey(name: "newEmail")
  String newEmail;

  ChangeEmailCommandDto({this.newEmail, this.password});

  factory ChangeEmailCommandDto.fromJson(Map<String, dynamic> json) => _$ChangeEmailCommandDtoFromJson(json);

  Map<String, dynamic> toJson() => _$ChangeEmailCommandDtoToJson(this);

}